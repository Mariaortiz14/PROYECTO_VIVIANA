package modelo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class UsuarioModelo {
    private HashMap<String, Usuario> usuarios;
    private String archivoUsuarios;

    public UsuarioModelo(String archivoUsuarios) {
        this.usuarios = new HashMap<>();
        this.archivoUsuarios = archivoUsuarios;
        cargarUsuarios();
    }

    public void agregarUsuario(String id, String nombre, String correoElectronico, String contraseña) {
        Usuario usuario = new Usuario(id, nombre, correoElectronico, contraseña);
        usuarios.put(id, usuario);
        guardarUsuarios();
    }

    public Usuario obtenerUsuario(String id) {
        return usuarios.get(id);
    }

    public void actualizarUsuario(String id, String nombre, String correoElectronico, String contraseña) {
        if (usuarios.containsKey(id)) {
            Usuario usuario = usuarios.get(id);
            usuario.setNombre(nombre);
            usuario.setCorreo(correoElectronico);
            usuario.setContrasena(contraseña);
            guardarUsuarios();
        }
    }

    public void eliminarUsuario(String id) {
        if (usuarios.containsKey(id)) {
            usuarios.remove(id);
            guardarUsuarios();
        }
    }

    public void guardarUsuarios() {
        JSONArray jsonArray = new JSONArray();

        // Convertir cada usuario a un objeto JSON y añadirlo al array JSON
        for (Usuario usuario : usuarios.values()) {
            JSONObject jsonUsuario = new JSONObject();
            jsonUsuario.put("id", usuario.getId());
            jsonUsuario.put("nombre", usuario.getNombre());
            jsonUsuario.put("correoElectronico", usuario.getCorreo());
            jsonUsuario.put("contraseña", usuario.getContrasena());
            jsonArray.put(jsonUsuario);
        }

        // Guardar el array JSON en el archivo
        try (FileWriter file = new FileWriter(archivoUsuarios)) {
            file.write(jsonArray.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarUsuarios() {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(archivoUsuarios)));
            JSONArray jsonArray = new JSONArray(contenido);

            // Convertir cada objeto JSON en una instancia de Usuario y añadirla al HashMap
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonUsuario = jsonArray.getJSONObject(i);
                String id = jsonUsuario.getString("id");
                String nombre = jsonUsuario.getString("nombre");
                String correoElectronico = jsonUsuario.getString("correoElectronico");
                String contraseña = jsonUsuario.getString("contraseña");
                Usuario usuario = new Usuario(id, nombre, correoElectronico, contraseña);
                usuarios.put(id, usuario);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Usuario> getUsuarios() {
        return usuarios;
    }
}

