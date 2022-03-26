package protobuffprotocol;

import domain.Cursa;
import domain.Oficiu;
import domain.Rezervare;
import domain.RezervareDTO;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;

//2020-11-15T21:30:42.575771500
public class ProtoUtils {
    public static FTProtobufs.Request createLoginRequest(Oficiu oficiu){
        FTProtobufs.Oficiu oficiuDTO=FTProtobufs.Oficiu.newBuilder().setNume(oficiu.getNume()).setParola(oficiu.getParola()).build();
        FTProtobufs.Request request=FTProtobufs.Request.newBuilder().setType(FTProtobufs.Request.Type.LOGIN).setOficiu(oficiuDTO).build();
        return request;
    }

    public static FTProtobufs.Request createLogoutRequest(Oficiu oficiu){
        FTProtobufs.Oficiu oficiuDTO=FTProtobufs.Oficiu.newBuilder().setNume(oficiu.getNume()).setParola(oficiu.getParola()).build();
        FTProtobufs.Request request=FTProtobufs.Request.newBuilder().setType(FTProtobufs.Request.Type.LOGOUT).setOficiu(oficiuDTO).build();
        return request;
    }

    public static FTProtobufs.Request createGetFiltredCurseRequest(String destinatie){
        FTProtobufs.Cursa cursaDTO=FTProtobufs.Cursa.newBuilder().setDestinatie(destinatie).build();
        FTProtobufs.Request request=FTProtobufs.Request.newBuilder().setType(FTProtobufs.Request.Type.GET_FILTERED_CURSE).setCursa( cursaDTO).build();
        return request;
    }

    public static FTProtobufs.Request createSaveRezervareRequest(Rezervare rezervare){
        FTProtobufs.Rezervare rezervareDTO=FTProtobufs.Rezervare.newBuilder().setNumeClient(rezervare.getNumeClient()).setNrLocuri(rezervare.getNrLocuri().toString()).setIdCursa(rezervare.getIdCursa().toString()).build();
        FTProtobufs.Request request=FTProtobufs.Request.newBuilder().setType(FTProtobufs.Request.Type.SAVE_REZERVARE).setRezervare(rezervareDTO).build();
        return request;

    }

    public static FTProtobufs.Request createGetAllCurseRequest(){
        //FTProtobufs.Cursa cursaDTO=FTProtobufs.Cursa.newBuilder().setId(cursa.getId().toString()).setDestinatie(cursa.getDestinatie()).setPlecare(cursa.getPlecare().toString()).setLocuriDisponibile(cursa.getLocuriDisponibile().toString()).build();
        FTProtobufs.Request request=FTProtobufs.Request.newBuilder().setType(FTProtobufs.Request.Type.GET_ALL_CURSE).build();
        return request;
    }

    public static FTProtobufs.Request createGetRezervareDtoRequest(Rezervare rezervare){
        FTProtobufs.Rezervare rezervareDTO=FTProtobufs.Rezervare.newBuilder().setId(rezervare.getId().toString()).setNumeClient(rezervare.getNumeClient()).setNrLocuri(rezervare.getNrLocuri().toString()).setIdCursa(rezervare.getIdCursa().toString()).build();
        FTProtobufs.Request request=FTProtobufs.Request.newBuilder().setType(FTProtobufs.Request.Type.GET_REZERVARE_DTO).setRezervare(rezervareDTO).build();
        return request;

    }

    public static FTProtobufs.Response createOKResponse(){
        FTProtobufs.Response response=FTProtobufs.Response.newBuilder().setType(FTProtobufs.Response.Type.Ok).build();
        return response;

    }

    public static FTProtobufs.Response createErrorResponse(String text){
        FTProtobufs.Response response=FTProtobufs.Response.newBuilder()
                .setType(FTProtobufs.Response.Type.Error)
                .setError(text).build();
        return response;
    }

    public static FTProtobufs.Response createGetFiltredCurseResponse(Cursa[] curse){
        FTProtobufs.Response.Builder response=FTProtobufs.Response.newBuilder().setType(FTProtobufs.Response.Type.GET_FILTERED_CURSE);
        for(Cursa cursa:curse){
            FTProtobufs.Cursa cursaDTO=FTProtobufs.Cursa.newBuilder().setId(cursa.getId().toString()).setDestinatie(cursa.getDestinatie()).setPlecare(cursa.getPlecare().toString()).setLocuriDisponibile(cursa.getLocuriDisponibile().toString()).build();
            response.addCurse(cursaDTO);
        }
        return response.build();
    }

    public static FTProtobufs.Response createSaveRezervareResponse(Rezervare rezervare){
        FTProtobufs.Rezervare rezervareDTO=FTProtobufs.Rezervare.newBuilder().setNumeClient(rezervare.getNumeClient()).setNrLocuri(rezervare.getNrLocuri().toString()).setIdCursa(rezervare.getIdCursa().toString()).build();
        FTProtobufs.Response response=FTProtobufs.Response.newBuilder().setType(FTProtobufs.Response.Type.SAVE_REZERVARE).setRezervare(rezervareDTO).build();
        return response;

    }


    public static FTProtobufs.Response createSavedRezervareResponse(Rezervare rezervare){
        FTProtobufs.Rezervare rezervareDTO=FTProtobufs.Rezervare.newBuilder().setNumeClient(rezervare.getNumeClient()).setNrLocuri(rezervare.getNrLocuri().toString()).setIdCursa(rezervare.getIdCursa().toString()).build();
        FTProtobufs.Response response=FTProtobufs.Response.newBuilder().setType(FTProtobufs.Response.Type.SAVED_REZERVARE).setRezervare(rezervareDTO).build();
        return response;

    }



    public static FTProtobufs.Response createGetAllCurseResponse(Cursa[] curse){
        FTProtobufs.Response.Builder response=FTProtobufs.Response.newBuilder().setType(FTProtobufs.Response.Type.GET_ALL_CURSE);
        for(Cursa cursa:curse){
            FTProtobufs.Cursa cursaDTO=FTProtobufs.Cursa.newBuilder().setId(cursa.getId().toString()).setDestinatie(cursa.getDestinatie()).setPlecare(cursa.getPlecare().toString()).setLocuriDisponibile(cursa.getLocuriDisponibile().toString()).build();
            response.addCurse(cursaDTO);
        }
        return response.build();
    }

    public static FTProtobufs.Response createGetRezervareDTOResponse(RezervareDTO[] rezervareDTOS){
        FTProtobufs.Response.Builder response=FTProtobufs.Response.newBuilder().setType(FTProtobufs.Response.Type.GET_REZERVARE_DTO);
        for(RezervareDTO rezervare:rezervareDTOS){
            FTProtobufs.RezervareDTO rezervareDTO=FTProtobufs.RezervareDTO.newBuilder().setNumeClient(rezervare.getNumeClient()).setNrLoc(rezervare.getNrLoc().toString()).build();
            response.addRezervariDTO(rezervareDTO);
        }
        return response.build();
    }

    public static String getError(FTProtobufs.Response response){
        String errorMessage=response.getError();
        return errorMessage;
    }

    public static Oficiu getOficiu(FTProtobufs.Response response){
        Oficiu oficiu=new Oficiu(response.getOficiu().getNume(),response.getOficiu().getParola());
        //oficiu.setId(Integer.parseInt(response.getOficiu().getId()));
        return oficiu;
    }

    public static Oficiu getOficiu(FTProtobufs.Request request){
        Oficiu oficiu=new Oficiu(request.getOficiu().getNume(),request.getOficiu().getParola());

        return oficiu;
    }

    public static Cursa getCursa(FTProtobufs.Response response){
        Cursa cursa =new Cursa(response.getCursa().getDestinatie(), LocalDateTime.parse(response.getCursa().getPlecare()),Integer.parseInt(response.getCursa().getLocuriDisponibile()));
        cursa.setId(Integer.parseInt(response.getCursa().getId()));
        return cursa;
    }

    public static Cursa getCursa(FTProtobufs.Request request){
        Cursa cursa =new Cursa(request.getCursa().getDestinatie(), LocalDateTime.parse(request.getCursa().getPlecare()),Integer.parseInt(request.getCursa().getLocuriDisponibile()));
        cursa.setId(Integer.parseInt(request.getCursa().getId()));
        return cursa;
    }

    private static LocalDateTime myparse(String str){
        String[] splited = str.split("\\s+");
        String[] data = splited[0].split("/");
        String[] time=splited[1].split(":");

        int year=Integer.parseInt(data[2]);
        int monthI=Integer.parseInt(data[0]);
        int day=Integer.parseInt(data[1]);

        int hour=Integer.parseInt(time[0]);
        int minute=Integer.parseInt(time[1]);
        int second=Integer.parseInt(time[2]);

        LocalDateTime localDateTime = LocalDateTime.of(year,monthI,day,hour,minute,second);


        return localDateTime;

    }

    public static Cursa[] getCurse(FTProtobufs.Response response){
        Cursa[] curse=new Cursa[response.getCurseCount()];
        for(int i=0;i< response.getCurseCount();i++){
            FTProtobufs.Cursa cursaDto=response.getCurse(i);

            Cursa cursa=new Cursa(cursaDto.getDestinatie(),myparse(cursaDto.getPlecare()),Integer.parseInt(cursaDto.getLocuriDisponibile()));
            cursa.setId(Integer.parseInt(cursaDto.getId()));
            curse[i]=cursa;
        }

        return curse;
    }

    public static Rezervare getRezervare(FTProtobufs.Response response){
        Rezervare rezervare=new Rezervare(response.getRezervare().getNumeClient(),Integer.parseInt(response.getRezervare().getNrLocuri()),Integer.parseInt(response.getRezervare().getIdCursa()));
        //rezervare.setId(Integer.parseInt(response.getRezervare().getId()));
        return rezervare;
    }

    public static Rezervare getRezervare(FTProtobufs.Request request){
        Rezervare rezervare=new Rezervare(request.getRezervare().getNumeClient(),Integer.parseInt(request.getRezervare().getNrLocuri()),Integer.parseInt(request.getRezervare().getIdCursa()));
        //rezervare.setId(Integer.parseInt(request.getRezervare().getId()));
        return rezervare;
    }

    public static Rezervare[] getRezervari(FTProtobufs.Response response){
        Rezervare[] rezervari=new Rezervare[response.getRezervariCount()];
        for(int i=0;i< response.getRezervariCount();i++){
            FTProtobufs.Rezervare rezervareDto=response.getRezervari(i);
            Rezervare rezervare=new Rezervare(rezervareDto.getNumeClient(),Integer.parseInt(rezervareDto.getNrLocuri()),Integer.parseInt(rezervareDto.getIdCursa()));
            //rezervare.setId(Integer.parseInt(rezervareDto.getId()));
            rezervari[i]=rezervare;
        }
        return rezervari;
    }

    public static RezervareDTO getRezervareDTO(FTProtobufs.Response response){
        RezervareDTO rezervareDTO=new RezervareDTO(response.getRezervareDTO().getNumeClient(),Integer.parseInt(response.getRezervareDTO().getNrLoc()));
        return rezervareDTO;
    }

    public static RezervareDTO getRezervareDTO(FTProtobufs.Request request){
        RezervareDTO rezervareDTO=new RezervareDTO(request.getRezervareDTO().getNumeClient(),Integer.parseInt(request.getRezervareDTO().getNrLoc()));
        return rezervareDTO;
    }

    public  static RezervareDTO[] getRezervariDTO(FTProtobufs.Response response){
        RezervareDTO[] rezervariDTO= new RezervareDTO[response.getRezervariDTOCount()];
        for(int i=0;i< response.getRezervariDTOCount();i++){
            FTProtobufs.RezervareDTO rezervareDTOP=response.getRezervariDTO(i);
            RezervareDTO rezervareDTO=new RezervareDTO(rezervareDTOP.getNumeClient(),Integer.parseInt(rezervareDTOP.getNrLoc()));
            rezervariDTO[i]=rezervareDTO;
        }
        return rezervariDTO;
    }














}
