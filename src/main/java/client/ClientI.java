package client;

import Perfectos.*;
import com.zeroc.Ice.Current;

public class ClientI implements Client {
    @Override
    public void mostrarResultado(long[] perfectos, String idTarea, long tiempoEjecucion, Current current) {
        System.out.println("\n=== Resultado de la tarea " + idTarea + " ===");
        System.out.print("Números perfectos encontrados: ");
        for (long p : perfectos) {
            System.out.print(p + " ");
        }
        System.out.println("\nTiempo total de ejecución: " + tiempoEjecucion + " ms");
        System.out.println("===========================================");
    }
}
