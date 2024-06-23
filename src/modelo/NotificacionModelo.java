package modelo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class NotificacionModelo {
    private HashMap<String, Notificacion> notificaciones;
    private String archivoNotificaciones;

    public NotificacionModelo(String archivoNotificaciones) {
        this.notificaciones = new HashMap<>();
        this.archivoNotificaciones = archivoNotificaciones;
        cargarNotificaciones();
    }

    public void agregarNotificacion(String id, String usuarioId, String mensaje) {
        Notificacion notificacion = new Notificacion(id, usuarioId, mensaje);
        notificaciones.put(id, notificacion);
        guardarNotificaciones();
    }

    public Notificacion obtenerNotificacion(String id) {
        return notificaciones.get(id);
    }

    public void eliminarNotificacion(String id) {
        if (notificaciones.containsKey(id)) {
            notificaciones.remove(id);
            guardarNotificaciones();
        }
    }

    public void guardarNotificaciones() {
        JSONArray jsonArray = new JSONArray();
        for (Notificacion notificacion : notificaciones.values()) {
            JSONObject jsonNotificacion = new JSONObject();
            jsonNotificacion.put("id", notificacion.getId());
            jsonNotificacion.put("usuarioId", notificacion.getUsuarioId());
            jsonNotificacion.put("mensaje", notificacion.getMensaje());
            jsonArray.put(jsonNotificacion);
        }

        try (FileWriter file = new FileWriter(archivoNotificaciones)) {
            file.write(jsonArray.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarNotificaciones() {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(archivoNotificaciones)));
            JSONArray jsonArray = new JSONArray(contenido);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonNotificacion = jsonArray.getJSONObject(i);
                String id = jsonNotificacion.getString("id");
                String usuarioId = jsonNotificacion.getString("usuarioId");
                String mensaje = jsonNotificacion.getString("mensaje");
                Notificacion notificacion = new Notificacion(id, usuarioId, mensaje);
                notificaciones.put(id, notificacion);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Notificacion> getNotificaciones() {
        return notificaciones;
    }
}

