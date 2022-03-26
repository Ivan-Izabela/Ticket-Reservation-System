package protobuffprotocol;

import domain.Cursa;
import domain.Oficiu;
import domain.Rezervare;
import domain.RezervareDTO;
import dto.CursaDTO;
import dto.DTOUtils;
import dto.OficiuDTO;
import dto.RezervareDto;
import rpcProtocol.*;
import services.FTException;
import services.IObserver;
import services.IService;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoFTProxy implements IService {
    private String host;
    private int port;

    private IObserver client;

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<FTProtobufs.Response> qResponses;
    private volatile boolean finished;

    public ProtoFTProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qResponses = new LinkedBlockingQueue<FTProtobufs.Response>();
    }



    @Override
    public Oficiu login(String nume, String parola, IObserver client) throws FTException {
        initializeConnection();
        Oficiu oficiuD=new Oficiu(nume,parola);
        sendRequest(ProtoUtils.createLoginRequest(oficiuD));
        FTProtobufs.Response response = readResponse();
        if (response.getType() == FTProtobufs.Response.Type.Ok) {
            this.client = client;
            return null;
        }
        if (response.getType() == FTProtobufs.Response.Type.Error) {
            String err = ProtoUtils.getError(response);
            closeConnection();
            throw new FTException(err);
        }
        return null;
    }

    @Override
    public List<Cursa> findAllCurse() throws FTException {

        Cursa cursa=new Cursa();
        //System.out.println("SRV1");
        sendRequest(ProtoUtils.createGetAllCurseRequest());
        FTProtobufs.Response response = readResponse();
        //System.out.println("SRV2");
        if (response.getType() == FTProtobufs.Response.Type.Error) {
            String err = ProtoUtils.getError(response);
            throw new FTException(err);
        }
        //System.out.println("am trecut");

        Cursa[] cursas=ProtoUtils.getCurse(response);
        List<Cursa> toReturn=new ArrayList<>();
        for(int i=0; i<cursas.length;i++){
            toReturn.add(cursas[i]);
        }

        return  toReturn;
    }

    @Override
    public List<Rezervare> findAllRezervari() throws FTException {
        return null;
    }

    @Override
    public List<RezervareDTO> findRezervareDTO(String id1) throws FTException{
        String str=id1;
        System.out.println("sending request " );
        Rezervare rezervare=new Rezervare("a",1,Integer.parseInt(id1));
        rezervare.setId(1);

        sendRequest(ProtoUtils.createGetRezervareDtoRequest(rezervare));
        FTProtobufs.Response response = readResponse();
        if (response.getType() == FTProtobufs.Response.Type.Error) {
            String err = ProtoUtils.getError(response);
            throw new FTException(err);
        }

        RezervareDTO[] rezervareDTOSdom=ProtoUtils.getRezervariDTO(response);
        List<RezervareDTO> toReturn=new ArrayList<>();
        for(int i=0; i<rezervareDTOSdom.length;i++){
            toReturn.add(rezervareDTOSdom[i]);
        }
        return  toReturn;
    }

    @Override
    public List<Cursa> findByDestinatie(String destinatie) throws FTException {
        String str = destinatie;

        sendRequest(ProtoUtils.createGetFiltredCurseRequest(destinatie));
        FTProtobufs.Response response = readResponse();
        if (response.getType() == FTProtobufs.Response.Type.Error) {
            String err = ProtoUtils.getError(response);
            throw new FTException(err);
        }

        Cursa[] cursas= ProtoUtils.getCurse(response);
        List<Cursa> toReturn=new ArrayList<>();
        for(int i=0; i<cursas.length;i++){
            toReturn.add(cursas[i]);
        }
        return  toReturn;
    }

    @Override
    public Rezervare saveRezervare(Rezervare rezervare) throws FTException {
        System.out.println("Welcome from proxy");

        System.out.println("sending request save rezervare " );
        sendRequest(ProtoUtils.createSaveRezervareRequest(rezervare));
        FTProtobufs.Response response = readResponse();
        System.out.println("got response " + response);
        if (response.getType() == FTProtobufs.Response.Type.Error) {
            String err = ProtoUtils.getError(response);
            throw new FTException(err);
        }
        Rezervare rezervare1=ProtoUtils.getRezervare(response);
        System.out.println(rezervare1.getIdCursa()+"!!");
        return rezervare1;
    }

    @Override
    public void logout(String nume, IObserver client) throws FTException {
        Oficiu oficiuDTO=new Oficiu(nume,"");

        sendRequest(ProtoUtils.createLogoutRequest(oficiuDTO));
        FTProtobufs.Response response = readResponse();
        closeConnection();
        if (response.getType() == FTProtobufs.Response.Type.Error) {
            String err = ProtoUtils.getError(response);
            throw new FTException(err);
        }

    }


    private void handleUpdate(FTProtobufs.Response response) {

        Rezervare c=ProtoUtils.getRezervare(response);
        System.out.println("Ticket bought " + c);
        try {
            System.out.println("doing update...");
            client.saveDRezervare(c);
            System.out.println("here4");

        } catch (FTException e) {
            e.printStackTrace();
        }
    }



    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {

                    FTProtobufs.Response response = FTProtobufs.Response.parseDelimitedFrom(input);;
                    System.out.println("response received " + response);
                    if (response.getType()==FTProtobufs.Response.Type.SAVE_REZERVARE) {
                        handleUpdate(response);
                    } else {

                        try {
                            qResponses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException  e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(FTProtobufs.Request request) throws FTException {
        try {
            System.out.println("Sending request ..."+request);
            //request.writeTo(output);
            request.writeDelimitedTo(output);
            output.flush();
            System.out.println("Request sent.");
        } catch (IOException e) {
            throw new FTException("Error sending object "+e);
        }

    }

    private FTProtobufs.Response readResponse() throws FTException {
        FTProtobufs.Response response = null;
        try {

            response = qResponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws FTException {
        try {
            connection=new Socket(host,port);
            output=connection.getOutputStream();
            //output.flush();
            input=connection.getInputStream();     //new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ProtoFTProxy.ReaderThread());
        tw.start();
    }

    private boolean isUpdateResponse(FTProtobufs.Response.Type type){
        switch (type){
            case SAVE_REZERVARE:return true;
        }
        return false;
    }
}
