package modelo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class RedSocialModelo {
    private HashMap<String, RedSocial> redesSociales;
    private String archivoRedesSociales;

    public RedSocialModelo(String archivoRedesSociales) {
        this.redesSociales = new HashMap<>();
        this.archivoRedesSociales = archivoRedesSociales;
        cargarRedesSociales();
    }

    public void agregarRedSocial(String id, String nombre, String url) {
        RedSocial redSocial = new RedSocial(id, nombre, url);
        redesSociales.put(id, redSocial);
        guardarRedesSociales();
    }

    public RedSocial obtenerRedSocial(String id) {
        return redesSociales.get(id);
    }

    public void eliminarRedSocial(String id) {
        if (redesSociales.containsKey(id)) {
            redesSociales.remove(id);
            guardarRedesSociales();
        }
    }

    public void guardarRedesSociales() {
        JSONArray jsonArray = new JSONArray();

        // Convertir cada red social a un objeto JSON y añadirlo al array JSON
        for (RedSocial redSocial : redesSociales.values()) {
            JSONObject jsonRedSocial = new JSONObject();
            jsonRedSocial.put("id", redSocial.getId());
            jsonRedSocial.put("nombre", redSocial.getNombre());
            jsonRedSocial.put("url", redSocial.getUrl());
            jsonArray.put(jsonRedSocial);
        }

        // Guardar el array JSON en el archivo
        try (FileWriter file = new FileWriter(archivoRedesSociales)) {
            file.write(jsonArray.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarRedesSociales() {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(archivoRedesSociales)));
            JSONArray jsonArray = new JSONArray(contenido);

            // Convertir cada objeto JSON en una instancia de RedSocial y añadirla al HashMap
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonRedSocial = jsonArray.getJSONObject(i);
                String id = jsonRedSocial.getString("id");
                String nombre = jsonRedSocial.getString("nombre");
                String url = jsonRedSocial.getString("url");
                RedSocial redSocial = new RedSocial(id, nombre, url);
                redesSociales.put(id, redSocial);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, RedSocial> getRedesSociales() {
        return redesSociales;
    }
}


