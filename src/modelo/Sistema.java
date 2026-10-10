package modelo;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Sistema {

    private ArrayList<Envio> envios;

    public Sistema() {
        this.envios = new ArrayList<>();
    }

    public void registrarEnvio(Envio envio) {
        this.envios.add(envio);
    }

    public Envio buscarEnvio(String id) {
        for (Envio envio : envios) {
            if (envio.getId().equals(id)) {
                return envio;
            }
        }
        throw new NoSuchElementException("No existe un envio con id " + id + "."); // antes devolvia null
    }
}