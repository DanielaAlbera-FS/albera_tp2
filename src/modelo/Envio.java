package modelo;

import enums.EstadoPaquete;
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

    public boolean iniciar() {
        boolean exito = true;
        for (Paquete paquete : paquetes) {
            if (paquete != null) {
                boolean ok = paquete.avanzarEstado();
                if (!ok) {
                    exito = false;
                }
            }
        }
        return exito;
    }

    public boolean recibir(Sucursal sucursal) {
        boolean exitoPaquetes = true;
        for (Paquete paquete : paquetes) {
            if (paquete != null) {
                boolean ok = paquete.avanzarEstado();
                if (!ok) {
                    exitoPaquetes = false;
                }
            }
        }
        TipoMovimiento tipo = todosEntregados() ? TipoMovimiento.ENTREGADO : TipoMovimiento.RECIBIDO;
        boolean exitoMovimiento = registrarMovimiento(sucursal, tipo);
        return exitoPaquetes && exitoMovimiento;
    }

    public boolean despachar(Sucursal sucursal) {
        return registrarMovimiento(sucursal, TipoMovimiento.DESPACHADO);
    }

    private boolean todosEntregados() {
        for (Paquete paquete : paquetes) {
            if (paquete != null && paquete.getEstado() != EstadoPaquete.ENTREGADO) {
                return false;
            }
        }
        return true;
    }

    private boolean registrarMovimiento(Sucursal sucursal, TipoMovimiento tipo) {
        if (!transicionValida(tipo)) {
            return false;
        }
        this.historialDeMovimientos.add(new Movimiento(sucursal, tipo));
        return true;
    }

    private boolean transicionValida(TipoMovimiento nuevoTipo) {
        if (historialDeMovimientos.isEmpty()) {
            return nuevoTipo == TipoMovimiento.RECIBIDO;
        }
        TipoMovimiento ultimo = historialDeMovimientos.get(historialDeMovimientos.size() - 1).getTipoMovimiento();
        if (ultimo == TipoMovimiento.RECIBIDO) {
            return nuevoTipo == TipoMovimiento.DESPACHADO;
        }
        if (ultimo == TipoMovimiento.DESPACHADO) {
            return nuevoTipo == TipoMovimiento.RECIBIDO || nuevoTipo == TipoMovimiento.ENTREGADO;
        }
        return false;
    }

    public String mostrarHistorial() {
        StringBuilder sb = new StringBuilder();
        for (Movimiento movimiento : historialDeMovimientos) {
            sb.append(movimiento.toString()).append("\n");
        }
        return sb.toString();
    }

    public String sucursalesRecorridas() {
        StringBuilder sb = new StringBuilder();
        for (Movimiento movimiento : historialDeMovimientos) {
            sb.append(movimiento.getSucursal().toString()).append(" ");
        }
        return sb.toString();
    }

    public String ultimoMovimiento() {
        if (historialDeMovimientos.isEmpty()) {
            return "Sin movimientos registrados.";
        }
        return historialDeMovimientos.get(historialDeMovimientos.size() - 1).toString();
    }

    public String toString() {
        return "Envio [" + this.id + "] | Costo: $" + this.costo;
    }
}