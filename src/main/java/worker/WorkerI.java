package worker;

import Perfectos.*;
import com.zeroc.Ice.Current;

import java.util.ArrayList;
import java.util.List;

public class WorkerI implements Worker {

    public WorkerI() {
    }

    private boolean esPerfecto(long num) {
        if (num <= 1) return false;

        long suma = 1;
        for (long i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                suma += i;
                long complemento = num / i;
                if (complemento != i) {
                    suma += complemento;
                }
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
