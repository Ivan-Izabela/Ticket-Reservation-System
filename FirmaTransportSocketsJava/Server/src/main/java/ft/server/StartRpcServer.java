package ft.server;

import ft.persistance.repository.*;
import network.AbstractServer;
import network.RpcConcurrentServer;
import network.ServerException;
import ft.services.IService;

import java.io.IOException;
import java.util.Properties;


public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        Properties props=new Properties();
        try {
            props.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            props.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }

        OficiuRepository oficiuRepo= new OficiuDBRepository(props);
        CursaRepository cursaRepo=new CursaDBRepository(props);
        RezervareReposytory rezervareRepo=new RezervareDBRepository(props);
        IService service= new Service(oficiuRepo,cursaRepo,rezervareRepo);

        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(props.getProperty("server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new RpcConcurrentServer(chatServerPort, service);
        try {
            server.start();
        } catch ( ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}
