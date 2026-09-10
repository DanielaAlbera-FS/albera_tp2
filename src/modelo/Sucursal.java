package modelo;

public class Sucursal {

    private String id;
    private String ciudad;

    public Sucursal(String id, String ciudad) {
        this.id = id;
        this.ciudad = ciudad;
    }

    public boolean recibirEnvio(Envio envio) {
        return envio.recibir(this);
    }

    public boolean despacharEnvio(Envio envio) {
        return envio.despachar(this);
    }

    public String toString() {
        return "Sucursal " + this.ciudad;
    }
}