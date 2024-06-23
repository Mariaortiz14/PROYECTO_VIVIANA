package modelo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class EventoModelo {
    private HashMap<String, Evento> eventos;
    private String archivoEventos;

    public EventoModelo(String archivoEventos) {
        this.eventos = new HashMap<>();
        this.archivoEventos = archivoEventos;
        cargarEventos();
    }

    public void agregarEvento(String id, String nombre, String fecha, String ubicacion, String descripcion) {
        Evento evento = new Evento(id, nombre, fecha, ubicacion, descripcion);
        eventos.put(id, evento);
        guardarEventos();
    }

    public Evento obtenerEvento(String id) {
        return eventos.get(id);
    }

    public void actualizarEvento(String id, String nombre, String fecha, String ubicacion, String descripcion) {
        if (eventos.containsKey(id)) {
            Evento evento = eventos.get(id);
            evento.setNombre(nombre);
            evento.setFecha(fecha);
            evento.setUbicacion(ubicacion);
            evento.setDescripcion(descripcion);
            guardarEventos();
        }
    }

    public void eliminarEvento(String id) {
        if (eventos.containsKey(id)) {
            eventos.remove(id);
            guardarEventos();
        }
    }

    public void guardarEventos() {
        JSONArray jsonArray = new JSONArray();
        for (Evento evento : eventos.values()) {
            JSONObject jsonEvento = new JSONObject();
            jsonEvento.put("id", evento.getId());
            jsonEvento.put("nombre", evento.getNombre());
            jsonEvento.put("fecha", evento.getFecha());
            jsonEvento.put("ubicacion", evento.getUbicacion());
            jsonEvento.put("descripcion", evento.getDescripcion());
            jsonArray.put(jsonEvento);
        }

        try (FileWriter file = new FileWriter(archivoEventos)) {
            file.write(jsonArray.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarEventos() {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(archivoEventos)));
            JSONArray jsonArray = new JSONArray(contenido);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonEvento = jsonArray.getJSONObject(i);
                String id = jsonEvento.getString("id");
                String nombre = jsonEvento.getString("nombre");
                String fecha = jsonEvento.getString("fecha");
                String ubicacion = jsonEvento.getString("ubicacion");
                String descripcion = jsonEvento.getString("descripcion");
                Evento evento = new Evento(id, nombre, fecha, ubicacion, descripcion);
                eventos.put(id, evento);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Evento> getEventos() {
        return eventos;
    }
}
