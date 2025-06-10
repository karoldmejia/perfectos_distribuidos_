package worker;

import Perfectos.*;
import com.zeroc.Ice.Current;

import java.util.ArrayList;
import java.util.List;

public class WorkerI implements Worker {

    public WorkerI() {
    }

    private boolean esPerfecto(long num) {
        long suma = 0;
        for (long i = 1; i <= num / 2; i++) {
            if (num % i == 0) {
                suma += i;
            }
        }
        return suma == num;
    }

    @Override
    public long[] buscarPerfectos(long inicio, long fin, String idTarea, Current current) {
        System.out.println("Worker: buscando perfectos entre " + inicio + " y " + fin + " para tarea " + idTarea);

        List<Long> resultados = new ArrayList<>();
        for (long i = inicio; i <= fin; i++) {
            if (esPerfecto(i)) {
                resultados.add(i);
            }
        }

        long[] resultadoArray = resultados.stream().mapToLong(Long::longValue).toArray();
        System.out.println("Worker: encontrados " + resultadoArray.length + " números perfectos.");
        return resultadoArray;
    }
}
