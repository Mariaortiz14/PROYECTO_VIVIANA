package modelo;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.IIOException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class PagoModelo {
    private HashMap<String, Pago> pagos;
    private String archivoPagos;

    public PagoModelo(String archivoPagos){
        this.pagos = new HashMap<>();
        this.archivoPagos = archivoPagos;
        cargarPagos();
    }

    public void agregarPagos(String id, String usuarioId, String eventoId, double monto, String metodoPago, String fecha){
        Pago pago = new Pago(id, usuarioId, eventoId,monto,metodoPago,fecha);
        pagos.put(id,pago);
        guardarPagos();
    }

    public Pago obtenerPago(String id){
        return pagos.get(id);
    }

    public void eliminarPago(String id) {
        if (pagos.containsKey(id)) {
            pagos.remove(id);
            guardarPagos();
        }
    }

    public void guardarPagos(){
        JSONArray jsonArray = new JSONArray();
        for (Pago pago : pagos.values()){
            JSONObject jsonPago = new JSONObject();
            jsonPago.put("id", pago.getId());
            jsonPago.put("usuarioId", pago.getUsuarioId());
            jsonPago.put("eventoId",pago.getEventoId());
            jsonPago.put("monto",pago.getMonto());
            jsonPago.put("metodo de pago", pago.getMetodoPago());
            jsonPago.put("fecha",pago.getFecha());
            jsonArray.put(jsonPago);
        }

        try (FileWriter file = new FileWriter(archivoPagos)) {
            file.write(jsonArray.toString());
            file.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void cargarPagos (){
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(archivoPagos)));
            JSONArray jsonArray = new JSONArray(contenido);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonPago = jsonArray.getJSONObject(i);
                String id = jsonPago.getString("id");
                String eventoId = jsonPago.getString("eventoId:");
                String usuarioId = jsonPago.getString("usuarioId");
                double monto = jsonPago.getDouble("monto");
                String metodoPago = jsonPago.getString("metodo de pago");
                String fecha = jsonPago.getString("fecha");
                Pago pago = new Pago(id, eventoId, usuarioId, monto, metodoPago,fecha);
                pagos.put(id, pago);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public HashMap<String, Pago> getPagos(){
        return pagos;
    }


}
