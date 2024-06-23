package modelo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class ComentarioModelo {
    private HashMap<String, Comentario> comentarios;
    private String archivoComentarios;

    public ComentarioModelo(String archivoComentarios) {
        this.comentarios = new HashMap<>();
        this.archivoComentarios = archivoComentarios;
        cargarComentarios();
    }

    public void agregarComentario(String id, String usuarioId, String eventoId, String comentario) {
        Comentario comentarioObj = new Comentario(id, usuarioId, eventoId, comentario);
        comentarios.put(id, comentarioObj);
        guardarComentarios();
    }

    public Comentario obtenerComentario(String id) {
        return comentarios.get(id);
    }

    public void actualizarComentario(String id, String usuarioId, String eventoId, String comentario) {
        if (comentarios.containsKey(id)) {
            Comentario comentarioObj = comentarios.get(id);
            comentarioObj.setUsuarioId(usuarioId);
            comentarioObj.setEventoId(eventoId);
            comentarioObj.setComentario(comentario);
            guardarComentarios();
        }
    }

    public void eliminarComentario(String id) {
        if (comentarios.containsKey(id)) {
            comentarios.remove(id);
            guardarComentarios();
        }
    }

    public void guardarComentarios() {
        JSONArray jsonArray = new JSONArray();
        for (Comentario comentario : comentarios.values()) {
            JSONObject jsonComentario = new JSONObject();
            jsonComentario.put("id", comentario.getId());
            jsonComentario.put("usuarioId", comentario.getUsuarioId());
            jsonComentario.put("eventoId", comentario.getEventoId());
            jsonComentario.put("comentario", comentario.getComentario());
            jsonArray.put(jsonComentario);
        }

        try (FileWriter file = new FileWriter(archivoComentarios)) {
            file.write(jsonArray.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarComentarios() {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(archivoComentarios)));
            JSONArray jsonArray = new JSONArray(contenido);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonComentario = jsonArray.getJSONObject(i);
                String id = jsonComentario.getString("id");
                String usuarioId = jsonComentario.getString("usuarioId");
                String eventoId = jsonComentario.getString("eventoId");
                String comentario = jsonComentario.getString("comentario");
                Comentario comentarioObj = new Comentario(id, usuarioId, eventoId, comentario);
                comentarios.put(id, comentarioObj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Comentario> getComentarios() {
        return comentarios;
    }
}

