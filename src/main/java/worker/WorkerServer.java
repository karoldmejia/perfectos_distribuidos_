package worker;

import Perfectos.*;
import com.zeroc.Ice.*;

public class WorkerServer {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: WorkerServer <nombre> <puerto>");
            return;
        }

        String workerName = args[0]; // Ej: worker1
        String port = args[1];       // Ej: 10003

        try (Communicator communicator = Util.initialize()) {
            // Creamos el adaptador para escuchar en el puerto indicado
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("WorkerAdapter", "default -p " + port);

            // Creamos la implementación del Worker sin necesidad del proxy al Master
            WorkerI workerImpl = new WorkerI();
            adapter.add(workerImpl, Util.stringToIdentity(workerName));

            adapter.activate();
            System.out.println("Worker " + workerName + " listo en puerto " + port);

            communicator.waitForShutdown();
        }
    }
}
