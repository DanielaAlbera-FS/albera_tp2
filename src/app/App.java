package app;

import modelo.Paquete;
import modelo.Envio;
import modelo.EnvioEstandar;
import modelo.EnvioExpress;
import modelo.EnvioInternacional;
import modelo.Movimiento;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import modelo.Cliente;
import modelo.Sucursal;
import modelo.Sistema;

public class App {
    public static void main(String[] args) {

        Paquete paquete1 = new Paquete("PKG001", 2.5, "Daniela Albera", "Av. Roca 506");
        Paquete paquete2 = new Paquete("PKG002", 3.0, "Maria Lopez", "Av. San Martin 456");
        Paquete paquete3 = new Paquete("PKG003", 4.0, "Carlos Ruiz", "Av. Belgrano 789");

        ArrayList<Envio> envios = new ArrayList<>();
        envios.add(new EnvioEstandar("ENV001", new Paquete[]{paquete1}));
        envios.add(new EnvioExpress("ENV002", new Paquete[]{paquete2}));
        envios.add(new EnvioInternacional("ENV003", new Paquete[]{paquete3}));

        for (Envio envio : envios) {
            System.out.println(envio);
        }

        Cliente cliente1 = new Cliente("CLI001", "Daniela", "Albera", "daniela@mail.com");
        Sucursal sucursalViedma = new Sucursal("SUC001", "Viedma");
        Sucursal sucursalBahiaBlanca = new Sucursal("SUC002", "Bahia Blanca");
        Sistema sistema = new Sistema();

        Envio envioDeCliente = new EnvioEstandar("ENV004", new Paquete[]{
            new Paquete("PKG004", 1.5, "Daniela Albera", "Av. Roca 506")
        });

        cliente1.registrarEnvio(envioDeCliente);
        sistema.registrarEnvio(envioDeCliente);

        // ---------- Recorrido correcto ----------
        try {
            sucursalViedma.recibirEnvio(envioDeCliente);
            sucursalViedma.despacharEnvio(envioDeCliente);
            sucursalBahiaBlanca.recibirEnvio(envioDeCliente);
            sucursalBahiaBlanca.despacharEnvio(envioDeCliente);
            sucursalBahiaBlanca.entregarEnvio(envioDeCliente);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // ---------- Consultas: el envio devuelve el dato y App lo muestra ----------
        System.out.println("\nHistorial completo:");
        for (Movimiento movimiento : envioDeCliente.obtenerHistorial()) {
            System.out.println(movimiento);
        }

        System.out.println("\nSucursales recorridas:");
        for (Sucursal sucursal : envioDeCliente.obtenerSucursalesRecorridas()) {
            System.out.println(sucursal);
        }

        System.out.println("\nUltimo movimiento: " + envioDeCliente.obtenerUltimoMovimiento());

        // ---------- Casos invalidos: cada uno lanza su excepcion ----------
        System.out.println("\nCasos invalidos:");

        Envio envioDePrueba = new EnvioExpress("ENV005", new Paquete[]{
            new Paquete("PKG005", 2.0, "Maria Lopez", "Av. San Martin 456")
        });

        try { // entregar un paquete no despachado
            sucursalViedma.entregarEnvio(envioDePrueba);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try { // despachar un envio ya entregado
            sucursalBahiaBlanca.despacharEnvio(envioDeCliente);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try { // registrar un envio sin paquetes
            new EnvioEstandar("ENV006", new Paquete[]{});
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try { // movimiento incoherente: lo recibe Viedma y lo quiere despachar Bahia Blanca
            sucursalViedma.recibirEnvio(envioDePrueba);
            sucursalBahiaBlanca.despacharEnvio(envioDePrueba);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try { // recibir un envio que esta en otra etapa (ya esta en una sucursal)
            sucursalBahiaBlanca.recibirEnvio(envioDePrueba);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try { // operar sobre un envio inexistente
            Envio envioInexistente = sistema.buscarEnvio("ENV999");
            sucursalViedma.recibirEnvio(envioInexistente);
        } catch (NoSuchElementException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}