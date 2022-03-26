package network;

import protobuffprotocol.ProtoFTWorker;
import services.IService;

import java.net.Socket;

public class ProtobuffConcurrentServer extends AbsConcurrentServer {
    private IService service;
    public ProtobuffConcurrentServer(int port, IService service) {
        super(port);
        this.service=service;
        System.out.println("Chat- ChatProtobuffConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        System.out.println("Sunt aici");

        ProtoFTWorker worker = new ProtoFTWorker(service,client);

        Thread tw=new Thread(worker);
        return tw;
    }
}
