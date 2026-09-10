package modelo;

import enums.TipoMovimiento;
import java.time.LocalDateTime;

public class Movimiento {

    private Sucursal sucursal;
    private TipoMovimiento tipoMovimiento;
    private LocalDateTime fechaHora;

    public Movimiento(Sucursal sucursal, TipoMovimiento tipoMovimiento) {
        this.sucursal = sucursal;
        this.tipoMovimiento = tipoMovimiento;
        this.fechaHora = LocalDateTime.now();
    }

    public Sucursal getSucursal() {
        return this.sucursal;
    }

    public TipoMovimiento getTipoMovimiento() {
        return this.tipoMovimiento;
    }

    public String toString() {
        return this.tipoMovimiento + " en " + this.sucursal + " (" + this.fechaHora + ")";
    }
}
