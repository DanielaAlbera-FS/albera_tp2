package app;

import modelo.Paquete;
import modelo.Envio;
import modelo.EnvioEstandar;
import modelo.EnvioExpress;
import modelo.EnvioInternacional;
import java.util.ArrayList;

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
    }
}