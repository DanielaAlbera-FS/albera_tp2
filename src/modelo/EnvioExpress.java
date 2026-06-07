package modelo;

public class EnvioExpress extends Envio {

    public EnvioExpress(String id, Paquete[] paquetes) {
        super(id, paquetes);
    }

    protected void calcularCosto() {
        double total = 0;
        for (Paquete paquete : paquetes) {
            if (paquete != null) {
                total = total + paquete.getPeso() * 50;
            }
        }
        this.costo = total * 1.25;
    }
}