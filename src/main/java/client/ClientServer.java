package client;

import com.zeroc.Ice.*;

public class ClientServer {
    public static void main(String[] args) {
        try (Communicator communicator = Util.initialize(args)) {
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("ClientAdapter", "tcp -p 10001");
            adapter.add(new ClientI(), Util.stringToIdentity("cliente1"));
            adapter.activate();

            System.out.println("Cliente esperando conexiones...");
            communicator.waitForShutdown();
        }
    }
}
