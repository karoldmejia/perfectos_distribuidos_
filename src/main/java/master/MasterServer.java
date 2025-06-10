package master;

import Perfectos.*;
import com.zeroc.Ice.*;

import java.util.ArrayList;
import java.util.List;

public class MasterServer {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {

            // Proxy al cliente remoto
            ClientPrx clientProxy = ClientPrx.checkedCast(
                    communicator.stringToProxy("cliente1:tcp -h 127.0.0.1 -p 10001")
            );

            if (clientProxy == null) {
                System.err.println("No se pudo conectar al cliente");
                return;
            }

            // Crear proxies a los Workers remotos
            List<WorkerPrx> workers = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                int port = 10003 + i; // puertos: 10003, 10004, 10005, 10006, 10007
                String proxyString = "worker" + i + ":default -h 127.0.0.1 -p " + port;
                WorkerPrx workerProxy = WorkerPrx.checkedCast(communicator.stringToProxy(proxyString));

                if (workerProxy == null) {
                    System.err.println("No se pudo conectar al worker en puerto " + port);
                    return;
                }

                workers.add(workerProxy);
            }

            // Crear el adaptador y registrar el Master
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("MasterAdapter", "default -p 10002");

            MasterI masterImpl = new MasterI(clientProxy, workers);
            adapter.add(masterImpl, Util.stringToIdentity("master1"));

            adapter.activate();
            System.out.println("Master esperando resultados...");
            communicator.waitForShutdown();
        }
    }
}
