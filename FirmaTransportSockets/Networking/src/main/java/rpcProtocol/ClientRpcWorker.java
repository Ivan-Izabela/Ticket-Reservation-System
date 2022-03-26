package rpcProtocol;


import domain.Cursa;
import domain.Oficiu;
import domain.Rezervare;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;


public class ClientRpcWorker implements  Runnable, IObserver {
    private IService server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    public ClientRpcWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveDRezervare(Rezervare rezervare) throws FTException {
        RezervareDto rezervareDTO= DTOUtils.getDTO(rezervare);
        System.out.println("Notifing user...");
        Response response = new Response.Builder().type(ResponseType.SAVE_REZERVARE).data(rezervareDTO).build();
        try {
            System.out.println("here1");
            sendResponse(response);
            System.out.println("here2");
        } catch (IOException e) {
            throw new Error("Sending error: " + e);
        }

    }


    @Override
    public void run() {
        while (connected) {
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }

    private Response handleRequest(Request request) {
        Response response = null;
        String handlerName = "handle" + (request).type();
        System.out.println("HandlerName " + handlerName);
        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response) method.invoke(this, request);
            System.out.println("Method " + handlerName + " invoked");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return response;
    }

    private Response handleLOGIN(Request request) {
        System.out.println("Login request ..." + request.type());
        OficiuDTO oficiuDTO=(OficiuDTO) request.data();
        Oficiu oficiu=DTOUtils.getFromDTO(oficiuDTO);

        try{
            server.login(oficiu.getNume(),oficiu.getParola(),this);
            return okResponse;
        }catch (FTException e){
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }

    }

    private Response handleLOGOUT(Request request) {
        System.out.println("Logout request...");

        OficiuDTO oficiuDTO=(OficiuDTO) request.data();
        Oficiu oficiu=DTOUtils.getFromDTO(oficiuDTO);

        try {
            server.logout(oficiu.getNume(),this);
            connected = false;
            return okResponse;
        }catch (FTException e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }

    }

    private Response handleSAVE_REZERVARE(Request request) {
        System.out.println("SaveRezervareRequest...");

        try{
            RezervareDto rezervareDTO=(RezervareDto) request.data();
            System.out.println("!!"+rezervareDTO.getIdCursa());
            Rezervare rezervare=DTOUtils.getFromDTO(rezervareDTO);
            System.out.println("!!"+rezervare.getIdCursa());
            if(server.saveRezervare(rezervare)!=null){
                System.out.println("Sending response....");
                return new Response.Builder().type(ResponseType.SAVED_REZERVARE).data(rezervareDTO).build();
            }
            else
                return new Response.Builder().type(ResponseType.ERROR).data("Not buyed...").build();
        }catch (FTException e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }

    }

    private Response handleGET_ALL_CURSE(Request request) {
        System.out.println("FindingAllEvents...");
        try{
            Cursa[] rezervari= new Cursa[server.findAllCurse().size()];
            server.findAllCurse().toArray(rezervari);
            CursaDTO[] rezervariDTO =DTOUtils.getDTO(rezervari);
            return new Response.Builder().type(ResponseType.GET_ALL_CURSE).data(rezervariDTO).build();
        }catch (FTException e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }

    }


    private Response handleGET_FILTERED_CURSE(Request request) {
        System.out.println("FilteringCurse....");
        String req = (String) request.data();
        System.out.println("request: "+req);
        String[] params = req.split(" ");
        System.out.println(params.length);

        try {
            System.out.println("dest: " +params[0]);
            List<Cursa> curse=server.findByDestinatie(params[0]);
            Cursa[] curse1=new Cursa[curse.size()];
            curse.toArray(curse1);
            CursaDTO[] cursaDTO=DTOUtils.getDTO(curse1);
            return new Response.Builder().type(ResponseType.GET_FILTERED_CURSE).data(cursaDTO).build();
        } catch (FTException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }

    }

    private Response handleGET_REZERVARE_DTO(Request request){
        System.out.println("RezervareDto....");
        String req = (String) request.data();
        System.out.println("request: "+req);
        String[] params = req.split(" ");
        System.out.println(params.length);
        try{
            System.out.println("id: " +params[0]);
            List<domain.RezervareDTO> rezervari=server.findRezervareDTO(params[0]);
            domain.RezervareDTO[] rezervari1=new domain.RezervareDTO[rezervari.size()];
            rezervari.toArray(rezervari1);
            RezervareDto[] rezervareDTO=DTOUtils.getDTOdom(rezervari1);

            return new Response.Builder().type(ResponseType.GET_REZERVARE_DTO).data(rezervareDTO).build();
        }catch (FTException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }

    }



}


