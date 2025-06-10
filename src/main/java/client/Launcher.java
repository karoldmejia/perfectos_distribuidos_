package client;

import Perfectos.*;
import com.zeroc.Ice.*;

import java.lang.Exception;

public class Launcher {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {
            MasterPrx master = MasterPrx.checkedCast(
                    communicator.stringToProxy("master1:default -h 127.0.0.1 -p 10002")
            );

            if (master == null) {
                throw new Error("No se pudo obtener proxy al master.");
            }

            // Valores por defecto
            long inicio = 1;
            long fin = 10000;

            // Validar argumentos para rango
            if (args.length >= 2) {
                try {
                    inicio = Long.parseLong(args[0]);
                    fin = Long.parseLong(args[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Argumentos inválidos, usando rango por defecto 1 a 10000.");
                }
            } else {
                System.out.println("No se especificó rango, usando rango por defecto 1 a 10000.");
            }

            String idTarea = "tarea1";

            System.out.println("Cliente: solicitando búsqueda de perfectos entre " + inicio + " y " + fin);
            master.findPerfectNumbers(inicio, fin, idTarea);

            System.out.println("Cliente: esperando resultados...");
            Thread.sleep(5000);  // Esperar 5 segundos

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
