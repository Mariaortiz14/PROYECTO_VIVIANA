package modelo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class CalendarioModelo {
    private HashMap<String, Calendario> eventos;
    private String archivoEventos;

    public CalendarioModelo(String archivoEventos) {
        this.eventos = new HashMap<>();
        this.archivoEventos = archivoEventos;
        cargarEventos();
    }

    public void agregarEvento(String id, String nombreEvento, String fecha, String ubicacion, String descripcion) {
        Calendario evento = new Calendario(id, nombreEvento, fecha, ubicacion, descripcion);
        eventos.put(id, evento);
        guardarEventos();
    }

    public Calendario obtenerEvento(String id) {
        return eventos.get(id);
    }

    public void actualizarEvento(String id, String nombreEvento, String fecha, String ubicacion, String descripcion) {
        if (eventos.containsKey(id)) {
            Calendario evento = eventos.get(id);
            evento.setNombreEvento(nombreEvento);
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
        for (Calendario evento : eventos.values()) {
            JSONObject jsonEvento = new JSONObject();
            jsonEvento.put("id", evento.getId());
            jsonEvento.put("nombreEvento", evento.getNombreEvento());
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
                String nombreEvento = jsonEvento.getString("nombreEvento");
                String fecha = jsonEvento.getString("fecha");
                String ubicacion = jsonEvento.getString("ubicacion");
                String descripcion = jsonEvento.getString("descripcion");
                Calendario evento = new Calendario(id, nombreEvento, fecha, ubicacion, descripcion);
                eventos.put(id, evento);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Calendario> getEventos() {
        return eventos;
    }
}

