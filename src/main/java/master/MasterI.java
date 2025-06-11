package master;

import Perfectos.*;
import com.zeroc.Ice.Current;

import java.util.ArrayList;
import java.util.List;

public class MasterI implements Master {

    private ClientPrx clientProxy;
    private List<WorkerPrx> workers;

    public MasterI(ClientPrx clientProxy, List<WorkerPrx> workers) {
        this.clientProxy = clientProxy;
        this.workers = workers;
    }

    @Override
    public void findPerfectNumbers(long inicio, long fin, String idTarea, Current current) {
        System.out.println("Master repartiendo tarea " + idTarea + " de " + inicio + " a " + fin);

        long total = fin - inicio + 1;
        long chunk = total / workers.size();

        List<Long> perfectosTotales = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < workers.size(); i++) {
            long subInicio = inicio + i * chunk;
            long subFin = (i == workers.size() - 1) ? fin : subInicio + chunk - 1;

            WorkerPrx worker = workers.get(i);
            System.out.println("Asignando a worker" + (i + 1) + " rango " + subInicio + " a " + subFin);

            try {
                long[] resultados = worker.buscarPerfectos(subInicio, subFin, idTarea);
                for (long num : resultados) {
                    perfectosTotales.add(num);
                }
            } catch (Exception e) {
                System.err.println("Error llamando worker: " + e.getMessage());
            }
        }

        long endTime = System.currentTimeMillis();
        long tiempoTotal = endTime - startTime;

        long[] resultadosFinales = perfectosTotales.stream().mapToLong(Long::longValue).toArray();

        if (clientProxy != null) {
            clientProxy.mostrarResultado(resultadosFinales, idTarea, tiempoTotal);
        } else {
            System.err.println("clientProxy es null, no se puede enviar resultado.");
        }

        System.out.println("Master: tarea " + idTarea + " finalizada. Tiempo total: " + tiempoTotal + " ms");
    }

    @Override
    public void recibirResultado(String idTarea, long[] perfectos, Current current) {
    }
}
