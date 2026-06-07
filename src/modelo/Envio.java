package modelo;

public abstract class Envio {

    private String id;
    protected Paquete[] paquetes;
    protected double costo;

    public Envio(String id, Paquete[] paquetes) {
        this.id = id;
        this.paquetes = paquetes;
        calcularCosto();
    }

    protected abstract void calcularCosto();

    public void iniciar() {
        for (Paquete paquete : paquetes) {
            if (paquete != null) {
                paquete.avanzarEstado();
            }
        }
    }

    public String toString() {
        return "Envio [" + this.id + "] | Costo: $" + this.costo;
    }
}