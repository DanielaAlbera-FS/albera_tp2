package modelo;

import enums.EstadoPaquete;

public class Paquete {

    private String id;
    private double peso;
    private String destinatario;
    private String direccion;
    private EstadoPaquete estado;

    public Paquete(String id, double peso, String destinatario, String direccion) {
    this.id = id;
    this.peso = peso;
    this.destinatario = destinatario;
    this.direccion = direccion;
    this.estado = EstadoPaquete.RECIBIDO;
        
}
public Paquete(String id, String destinatario, String direccion) {
    this.id = id;
    this.destinatario = destinatario;
    this.direccion = direccion;
    this.peso = 0;
    this.estado = EstadoPaquete.RECIBIDO;
}
public boolean avanzarEstado() {
    if (this.estado == EstadoPaquete.RECIBIDO) {
        this.estado = EstadoPaquete.EN_PREPARACION;
        return true;
    } else if (this.estado == EstadoPaquete.EN_PREPARACION) {
        this.estado = EstadoPaquete.EN_DISTRIBUCION;
        return true;
    } else if (this.estado == EstadoPaquete.EN_DISTRIBUCION) {
        this.estado = EstadoPaquete.ENTREGADO;
        return true;
    } else {
        return false;
    }
}
public EstadoPaquete getEstado() {
    return this.estado;
}
public double getPeso() {
    return this.peso;
}
public String toString() {
    return "Paquete [" + this.id + "] para " + this.destinatario + " | Estado: " + this.estado;
}
}
