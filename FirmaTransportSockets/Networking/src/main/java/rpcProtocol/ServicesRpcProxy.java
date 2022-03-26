package rpcProtocol;


import domain.Cursa;
import domain.Oficiu;
import domain.Rezervare;
import domain.RezervareDTO;
import dto.CursaDTO;
import dto.DTOUtils;
import dto.OficiuDTO;
import dto.RezervareDto;
import services.FTException;
import services.IObserver;
import services.IService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ServicesRpcProxy implements IService {
    private String host;
    private int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qResponses;
    private volatile boolean finished;

    public ServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qResponses = new LinkedBlockingQueue<Response>();
    }

    @Override
    public Oficiu login(String nume, String parola, IObserver client) throws FTException {
        initializeConnection();
        OficiuDTO oficiuDTO=new OficiuDTO(nume,parola);
        Request req = new Request.Builder().type(RequestType.LOGIN).data(oficiuDTO).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {
            this.client = client;
            return null;
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            closeConnection();
            throw new FTException(err);
        }
        return null;
    }

    @Override
    public List<Cursa> findAllCurse() throws FTException {
        Request request = new Request.Builder().type(RequestType.GET_ALL_CURSE).build();
        System.out.println("SRV1");
        sendRequest(request);
        Response response = readResponse();
        System.out.println("SRV2");
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new FTException(err);
        }
        System.out.println("am trecut");
        CursaDTO[] cursaDTOS=(CursaDTO[]) response.data();
        Cursa[] cursas= DTOUtils.getFromDTO(cursaDTOS);
        System.out.println("avem curse: "+ cursas.length);
        List<Cursa> toReturn=new ArrayList<>();
        for(int i=0; i<cursas.length;i++){
            toReturn.add(cursas[i]);
        }

        System.out.println("avem curse array: "+toReturn.size());
        toReturn.forEach(System.out::println);
        return  toReturn;
    }

    @Override
    public List<Rezervare> findAllRezervari() throws FTException {
        return null;
    }

    @Override
    public List<RezervareDTO> findRezervareDTO(String id1) throws FTException{
        String str=id1;
        Request request = new Request.Builder().type(RequestType.GET_REZERVARE_DTO).data(str).build();
        System.out.println("sending request " + request);

        sendRequest(request);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new FTException(err);
        }
        RezervareDto[] rezervareDTOS=(RezervareDto[]) response.data();
        RezervareDTO[] rezervareDTOSdom=DTOUtils.getDTOFromDTO(rezervareDTOS);
        List<RezervareDTO> toReturn=new ArrayList<>();
        for(int i=0; i<rezervareDTOSdom.length;i++){
            toReturn.add(rezervareDTOSdom[i]);
        }
        return  toReturn;
    }

    @Override
    public List<Cursa> findByDestinatie(String destinatie) throws FTException {
        String str = destinatie;
        Request request = new Request.Builder().type(RequestType.GET_FILTERED_CURSE).data(str).build();
        sendRequest(request);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new FTException(err);
        }
        CursaDTO[] cursaDTOS=(CursaDTO[]) response.data();
        Cursa[] cursas= DTOUtils.getFromDTO(cursaDTOS);
        List<Cursa> toReturn=new ArrayList<>();
        for(int i=0; i<cursas.length;i++){
            toReturn.add(cursas[i]);
        }
        return  toReturn;
    }

    @Override
    public Rezervare saveRezervare(Rezervare rezervare) throws FTException {
        System.out.println("Welcome from proxy");
        RezervareDto rezervareDTO=DTOUtils.getDTO(rezervare);

        System.out.println(rezervareDTO.getIdCursa()+"!!");

        Request request = new Request.Builder().type(RequestType.SAVE_REZERVARE).data(rezervareDTO).build();
        System.out.println("sending request " + request);
        sendRequest(request);
        Response response = readResponse();
        System.out.println("got response " + response);
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new FTException(err);
        }
        Rezervare rezervare1=DTOUtils.getFromDTO((RezervareDto) response.data());
        System.out.println(rezervare1.getIdCursa()+"!!");
        return rezervare1;
    }

    @Override
    public void logout(String nume, IObserver client) throws FTException {
        OficiuDTO oficiuDTO=new OficiuDTO(nume,"");
        Request req = new Request.Builder().type(RequestType.LOGOUT).data(oficiuDTO).build();
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new FTException(err);
        }

    }


    private void handleUpdate(Response response) {
        RezervareDto rezervareDTO=(RezervareDto) response.data();
        
        Rezervare c=DTOUtils.getFromDTO(rezervareDTO);
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
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    Response response1 = (Response) response;
                    if (response1.type() == ResponseType.SAVE_REZERVARE) {
                        handleUpdate(response1);
                    } else {

                        try {
                            qResponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
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

    private void sendRequest(Request request) throws FTException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new Error("Error sending object " + e);
        }

    }

    private Response readResponse() throws FTException {
        Response response = null;
        try {

            response = qResponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws FTException {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }
}
