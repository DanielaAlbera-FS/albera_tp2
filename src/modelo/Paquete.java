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

    // Accion concreta: una sucursal recibe el paquete.
    public void recibir() {
        if (!puedeRecibirse()) { // el propio paquete valida su regla
            throw new IllegalStateException("El paquete " + this.id + " no puede recibirse porque esta " + this.estado + ".");
        }
        this.estado = EstadoPaquete.EN_PREPARACION; // queda en la sucursal
    }

    // Accion concreta: el paquete sale de la sucursal.
    public void despachar() {
        if (!estaEnPreparacion()) { // solo sale si esta en una sucursal
            throw new IllegalStateException("El paquete " + this.id + " no puede despacharse porque esta " + this.estado + ".");
        }
        this.estado = EstadoPaquete.EN_DISTRIBUCION; // queda en viaje
    }

    // Accion concreta: el paquete llega al destinatario.
    public void entregar() {
        if (!fueDespachado()) { // no se entrega algo que no salio
            throw new IllegalStateException("El paquete " + this.id + " no puede entregarse porque no fue despachado.");
        }
        this.estado = EstadoPaquete.ENTREGADO; // estado final
    }

    // Informativo: lo usa Envio para saber si termino (tell, don't ask).
    public boolean estaEntregado() {
        return this.estado == EstadoPaquete.ENTREGADO;
    }

    // Regla: se recibe un paquete nuevo o uno que viene en viaje.
    private boolean puedeRecibirse() {
        return this.estado == EstadoPaquete.RECIBIDO || this.estado == EstadoPaquete.EN_DISTRIBUCION;
    }

    // Regla: esta dentro de una sucursal.
    private boolean estaEnPreparacion() {
        return this.estado == EstadoPaquete.EN_PREPARACION;
    }

    // Regla: ya salio de la sucursal.
    private boolean fueDespachado() {
        return this.estado == EstadoPaquete.EN_DISTRIBUCION;
    }

    public double getPeso() {
        return this.peso;
    }

    public String toString() {
        return "Paquete [" + this.id + "] para " + this.destinatario + " | Estado: " + this.estado;
    }
}