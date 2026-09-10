package app;

import modelo.Paquete;
import modelo.Envio;
import modelo.EnvioEstandar;
import modelo.EnvioExpress;
import modelo.EnvioInternacional;
import java.util.ArrayList;
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

        sucursalViedma.recibirEnvio(envioDeCliente);
        sucursalViedma.despacharEnvio(envioDeCliente);
        sucursalBahiaBlanca.recibirEnvio(envioDeCliente);
        sucursalBahiaBlanca.despacharEnvio(envioDeCliente);
        sucursalViedma.recibirEnvio(envioDeCliente);

        System.out.println(envioDeCliente.mostrarHistorial());
        System.out.println("Sucursales recorridas: " + envioDeCliente.sucursalesRecorridas());
        System.out.println("Ultimo movimiento: " + envioDeCliente.ultimoMovimiento());

        Envio envioInexistente = sistema.buscarEnvio("ENV999");
        if (envioInexistente == null) {
            System.out.println("No se encontro el envio buscado.");
        }
    }
}