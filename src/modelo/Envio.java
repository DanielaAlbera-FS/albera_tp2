package modelo;

import enums.TipoMovimiento;
import java.util.ArrayList;

public abstract class Envio {

    private String id;
    protected Paquete[] paquetes;
    protected double costo;
    private ArrayList<Movimiento> historialDeMovimientos;

    public Envio(String id, Paquete[] paquetes) {
        if (paquetes == null || paquetes.length == 0) {
            throw new IllegalArgumentException("Un envio no puede registrarse sin paquetes.");
        }
        this.id = id;
        this.paquetes = paquetes;
        this.historialDeMovimientos = new ArrayList<>();
        calcularCosto();
    }

    protected abstract void calcularCosto();

    public String getId() {
        return this.id;
    }

    // ---------- Acciones del envio ----------

    public void recibir(Sucursal sucursal) { // ahora es void: si falla, lanza excepcion
        if (estaFinalizado()) {
            throw new IllegalStateException("El envio " + this.id + " ya fue entregado: no puede recibirse.");
        }
        if (estaEnSucursal()) {
            throw new IllegalStateException("El envio " + this.id + " ya esta en una sucursal: debe despacharse antes de recibirse de nuevo.");
        }
        for (Paquete paquete : paquetes) {
            if (paquete != null) {
                paquete.recibir(); // le dice al paquete que hacer, no le cambia el estado
            }
        }
        registrarMovimiento(sucursal, TipoMovimiento.RECIBIDO);
    }

    public void despachar(Sucursal sucursal) {
        if (estaFinalizado()) {
            throw new IllegalStateException("El envio " + this.id + " ya fue entregado: no puede despacharse.");
        }
        if (!estaEnSucursal()) {
            throw new IllegalStateException("El envio " + this.id + " no esta en una sucursal: debe recibirse antes de despacharse.");
        }
        if (!estaEn(sucursal)) {
            throw new IllegalStateException("Movimiento incoherente: el envio " + this.id + " no esta en " + sucursal + ".");
        }
        for (Paquete paquete : paquetes) {
            if (paquete != null) {
                paquete.despachar();
            }
        }
        registrarMovimiento(sucursal, TipoMovimiento.DESPACHADO);
    }

    public void entregar(Sucursal sucursal) { // accion nueva: antes la entrega pasaba sola dentro de recibir
        if (!estaEnViaje()) {
            throw new IllegalStateException("El envio " + this.id + " no puede entregarse porque no fue despachado.");
        }
        for (Paquete paquete : paquetes) {
            if (paquete != null) {
                paquete.entregar();
            }
        }
        registrarMovimiento(sucursal, TipoMovimiento.ENTREGADO);
    }

    // ---------- Consultas: devuelven el dato, no un texto ----------

    public ArrayList<Movimiento> obtenerHistorial() {
        return new ArrayList<>(this.historialDeMovimientos); // copia: nadie de afuera modifica la lista original
    }

    public ArrayList<Sucursal> obtenerSucursalesRecorridas() {
        ArrayList<Sucursal> sucursales = new ArrayList<>();
        for (Movimiento movimiento : historialDeMovimientos) {
            Sucursal sucursal = movimiento.getSucursal();
            if (!sucursales.contains(sucursal)) { // evita repetir la misma sucursal
                sucursales.add(sucursal);
            }
        }
        return sucursales;
    }

    public Movimiento obtenerUltimoMovimiento() {
        if (historialDeMovimientos.isEmpty()) {
            throw new IllegalStateException("El envio " + this.id + " todavia no tiene movimientos.");
        }
        return historialDeMovimientos.get(historialDeMovimientos.size() - 1);
    }

    // ---------- Reglas de negocio (privadas y reutilizables) ----------

    private boolean estaFinalizado() {
        return todosEntregados();
    }

    private boolean estaEnSucursal() {
        return ultimoMovimientoEs(TipoMovimiento.RECIBIDO);
    }

    private boolean estaEnViaje() {
        return ultimoMovimientoEs(TipoMovimiento.DESPACHADO);
    }

    private boolean estaEn(Sucursal sucursal) {
        return obtenerUltimoMovimiento().getSucursal() == sucursal;
    }

    private boolean ultimoMovimientoEs(TipoMovimiento tipo) {
        if (historialDeMovimientos.isEmpty()) {
            return false;
        }
        return obtenerUltimoMovimiento().getTipoMovimiento() == tipo;
    }

    private boolean todosEntregados() {
        for (Paquete paquete : paquetes) {
            if (paquete != null && !paquete.estaEntregado()) { // le pregunta al paquete, ya no compara su estado
                return false;
            }
        }
        return true;
    }

    private void registrarMovimiento(Sucursal sucursal, TipoMovimiento tipo) { // ahora solo registra
        this.historialDeMovimientos.add(new Movimiento(sucursal, tipo));
    }

    public String toString() {
        return "Envio [" + this.id + "] | Costo: $" + this.costo;
    }
}