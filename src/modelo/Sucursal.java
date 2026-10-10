package modelo;

public class Sucursal {

    private String id;
    private String ciudad;

    public Sucursal(String id, String ciudad) {
        this.id = id;
        this.ciudad = ciudad;
    }

    public void recibirEnvio(Envio envio) { // void: si algo falla, la excepcion sube sola hasta App
        envio.recibir(this);
    }

    public void despacharEnvio(Envio envio) {
        envio.despachar(this);
    }

    public void entregarEnvio(Envio envio) { // accion nueva
        envio.entregar(this);
    }

    public String toString() {
        return "Sucursal " + this.ciudad;
    }
}