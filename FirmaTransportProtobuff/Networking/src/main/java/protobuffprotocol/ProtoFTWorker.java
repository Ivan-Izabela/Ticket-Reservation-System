package protobuffprotocol;

import domain.Cursa;
import domain.Oficiu;
import domain.Rezervare;
import dto.CursaDTO;
import dto.DTOUtils;
import dto.OficiuDTO;
import dto.RezervareDto;
import rpcProtocol.Request;
import rpcProtocol.Response;
import rpcProtocol.ResponseType;
import services.FTException;
import services.IObserver;
import services.IService;

import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;

public class ProtoFTWorker implements Runnable, IObserver {
    private IService server;
    private Socket connection;

    private InputStream input;
    private OutputStream output;
    private volatile boolean connected;
    public ProtoFTWorker(IService server, Socket connection){
        this.server=server;
        this.connection=connection;
        try{
            output=connection.getOutputStream();
            input=connection.getInputStream();
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(connected){
            try {
                // Object request=input.readObject();
                System.out.println("Waiting requests ...");
                FTProtobufs.Request request=FTProtobufs.Request.parseDelimitedFrom(input);
                System.out.println("Request received: "+request);
                FTProtobufs.Response response=handleRequest(request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
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
            System.out.println("Error "+e);
        }
    }

    private void sendResponse(FTProtobufs.Response response) throws IOException {
        System.out.println("sending response "+response);
        response.writeDelimitedTo(output);
        //output.writeObject(response);
        output.flush();
    }

    private FTProtobufs.Response handleRequest(FTProtobufs.Request request) {
        FTProtobufs.Response response = null;
        String handlerName = "handle" + (request).getType();
        System.out.println("HandlerName " + handlerName);
        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, FTProtobufs.Request.class);
            response = (FTProtobufs.Response) method.invoke(this, request);
            System.out.println("Method " + handlerName + " invoked");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return response;
    }



    private FTProtobufs.Response handleLOGIN(FTProtobufs.Request request) {
        System.out.println("Login request ..." + request.getType());
        Oficiu oficiu= ProtoUtils.getOficiu(request);

        try{
            server.login(oficiu.getNume(),oficiu.getParola(),this);
            return ProtoUtils.createOKResponse();
        }catch (FTException e){
            connected = false;
            return ProtoUtils.createErrorResponse(e.getMessage());
        }

    }

    private FTProtobufs.Response handleLOGOUT(FTProtobufs.Request request) {
        System.out.println("Logout request...");

        Oficiu oficiu=ProtoUtils.getOficiu(request);

        try {
            server.logout(oficiu.getNume(),this);
            connected = false;
            return ProtoUtils.createOKResponse();
        }catch (FTException e){
            connected = false;
            return ProtoUtils.createErrorResponse(e.getMessage());
        }

    }

    private FTProtobufs.Response handleSAVE_REZERVARE(FTProtobufs.Request request) {
        System.out.println("SaveRezervareRequest...");

        try{

            Rezervare rezervare=ProtoUtils.getRezervare(request);
            System.out.println("!!"+rezervare.getIdCursa());
            if(server.saveRezervare(rezervare)!=null){
                System.out.println("Sending response....");
                return ProtoUtils.createSavedRezervareResponse(rezervare);
            }
            else
                return ProtoUtils.createErrorResponse("Not buyed...");
        }catch (FTException e){
            return ProtoUtils.createErrorResponse(e.getMessage());
        }

    }

    private FTProtobufs.Response handleGET_ALL_CURSE(FTProtobufs.Request request) {
        System.out.println("FindingAllEvents...");
        try{
            Cursa[] rezervari= new Cursa[server.findAllCurse().size()];
            server.findAllCurse().toArray(rezervari);

            return ProtoUtils.createGetAllCurseResponse(rezervari);
        }catch (FTException e){
            return ProtoUtils.createErrorResponse(e.getMessage());
        }

    }


    private FTProtobufs.Response handleGET_FILTERED_CURSE(FTProtobufs.Request request) {
        System.out.println("FilteringCurse....");
        Cursa cursa=ProtoUtils.getCursa(request);

        try {
            System.out.println("dest: " +cursa.getDestinatie());
            List<Cursa> curse=server.findByDestinatie(cursa.getDestinatie());
            Cursa[] curse1=new Cursa[curse.size()];
            curse.toArray(curse1);
            return ProtoUtils.createGetFiltredCurseResponse(curse1);
        } catch (FTException e) {
            return ProtoUtils.createErrorResponse(e.getMessage());
        }

    }

    private FTProtobufs.Response handleGET_REZERVARE_DTO(FTProtobufs.Request request){
        System.out.println("RezervareDto....");
        Rezervare rezervare=ProtoUtils.getRezervare(request);
        try{
            System.out.println("id: " +rezervare.getIdCursa());
            List<domain.RezervareDTO> rezervari=server.findRezervareDTO(rezervare.getIdCursa().toString());
            domain.RezervareDTO[] rezervari1=new domain.RezervareDTO[rezervari.size()];
            rezervari.toArray(rezervari1);

            return ProtoUtils.createGetRezervareDTOResponse(rezervari1);
        }catch (FTException e) {
            return ProtoUtils.createErrorResponse(e.getMessage());
        }

    }




    @Override
    public void saveDRezervare(Rezervare rezervare) throws FTException {

        System.out.println("Notifing user...");

        try {
            System.out.println("here1");
            sendResponse(ProtoUtils.createSaveRezervareResponse(rezervare));
            System.out.println("here2");
        } catch (IOException e) {
            throw new Error("Sending error: " + e);
        }


    }
}
