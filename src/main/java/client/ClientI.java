package client;

import Perfectos.*;
import com.zeroc.Ice.Current;

public class ClientI implements Client {
    @Override
    public void mostrarResultado(long[] perfectos, String idTarea, Current current) {
        System.out.print("Cliente recibió resultado de la tarea " + idTarea + ": ");
        for (long p : perfectos) {
            System.out.print(p + " ");
        }
        System.out.println();
    }
}
