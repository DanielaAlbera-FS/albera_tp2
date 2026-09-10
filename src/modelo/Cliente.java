package modelo;

import java.util.ArrayList;

public class Cliente {

    private String id;
    private String nombre;
    private String apellido;
    private String email;
    private ArrayList<Envio> historialDeEnvios;

    public Cliente(String id, String nombre, String apellido, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.historialDeEnvios = new ArrayList<>();
    }

    public void registrarEnvio(Envio envio) {
        this.historialDeEnvios.add(envio);
    }

    public String toString() {
        return "Cliente [" + this.id + "] " + this.nombre + " " + this.apellido;
    }
}