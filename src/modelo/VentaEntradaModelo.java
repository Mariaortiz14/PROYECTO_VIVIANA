package modelo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class VentaEntradaModelo {
    private HashMap<String, VentaEntrada> ventasEntradas;
    private String archivoVentasEntradas;

    public VentaEntradaModelo(String archivoVentasEntradas) {
        this.ventasEntradas = new HashMap<>();
        this.archivoVentasEntradas = archivoVentasEntradas;
        cargarVentasEntradas();
    }

    public void agregarVentaEntrada(String id, String idUsuario, String idEvento, int cantidad, String metodoPago) {
        VentaEntrada ventaEntrada = new VentaEntrada(id, idUsuario, idEvento, cantidad, metodoPago);
        ventasEntradas.put(id, ventaEntrada);
        guardarVentasEntradas();
    }

    public VentaEntrada obtenerVentaEntrada(String id) {
        return ventasEntradas.get(id);
    }

    public void actualizarVentaEntrada(String id, String idUsuario, String idEvento, int cantidad, String metodoPago) {
        if (ventasEntradas.containsKey(id)) {
            VentaEntrada ventaEntrada = ventasEntradas.get(id);
            ventaEntrada.setIdUsuario(idUsuario);
            ventaEntrada.setIdEvento(idEvento);
            ventaEntrada.setCantidad(cantidad);
            ventaEntrada.setMetodoPago(metodoPago);
            guardarVentasEntradas();
        }
    }

    public void eliminarVentaEntrada(String id) {
        if (ventasEntradas.containsKey(id)) {
            ventasEntradas.remove(id);
            guardarVentasEntradas();
        }
    }

    public void guardarVentasEntradas() {
        JSONArray jsonArray = new JSONArray();

        for (VentaEntrada ventaEntrada : ventasEntradas.values()) {
            JSONObject jsonVentaEntrada = new JSONObject();
            jsonVentaEntrada.put("id", ventaEntrada.getId());
            jsonVentaEntrada.put("idUsuario", ventaEntrada.getIdUsuario());
            jsonVentaEntrada.put("idEvento", ventaEntrada.getIdEvento());
            jsonVentaEntrada.put("cantidad", ventaEntrada.getCantidad());
            jsonVentaEntrada.put("metodoPago", ventaEntrada.getMetodoPago());
            jsonArray.put(jsonVentaEntrada);
        }

        try (FileWriter file = new FileWriter(archivoVentasEntradas)) {
            file.write(jsonArray.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarVentasEntradas() {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(archivoVentasEntradas)));
            JSONArray jsonArray = new JSONArray(contenido);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonVentaEntrada = jsonArray.getJSONObject(i);
                String id = jsonVentaEntrada.getString("id");
                String idUsuario = jsonVentaEntrada.getString("idUsuario");
                String idEvento = jsonVentaEntrada.getString("idEvento");
                int cantidad = jsonVentaEntrada.getInt("cantidad");
                String metodoPago = jsonVentaEntrada.getString("metodoPago");
                VentaEntrada ventaEntrada = new VentaEntrada(id, idUsuario, idEvento, cantidad, metodoPago);
                ventasEntradas.put(id, ventaEntrada);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, VentaEntrada> getVentasEntradas() {
        return ventasEntradas;
    }
}

