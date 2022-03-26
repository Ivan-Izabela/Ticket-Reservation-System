package dto;

import domain.Cursa;
import domain.Oficiu;
import domain.Rezervare;

import java.time.LocalDateTime;

public class DTOUtils {
    public static Oficiu getFromDTO(OficiuDTO ofdto){
        Integer id =ofdto.getId();
        String nume= ofdto.getNume();;
        String prenume = ofdto.getParola();
        Oficiu oficiu=new Oficiu(nume,prenume);
        oficiu.setId(id);
        return oficiu;
    }

    public static OficiuDTO getDTO(Oficiu of){
        Integer id =of.getId();
        String nume= of.getNume();;
        String prenume = of.getParola();
        return new OficiuDTO(id,nume,prenume);
    }

    public static Oficiu[] getFromDTO(OficiuDTO[] ofdtos){
        Oficiu[] oficii=new Oficiu[ofdtos.length];
        for(int i=0; i<ofdtos.length;i++){
            oficii[i]=getFromDTO(ofdtos[i]);
        }
        return oficii;
    }

    public static OficiuDTO[] getDTO(Oficiu[] ofs){
        OficiuDTO[] oficii=new OficiuDTO[ofs.length];
        for(int i=0; i<ofs.length;i++){
            oficii[i]=getDTO(ofs[i]);
        }
        return oficii;
    }

    public static Cursa getFromDTO(CursaDTO csdto){
        Integer id=csdto.getId();
        String dest= csdto.getDestinatie();
        LocalDateTime plec =csdto.getPlecare();
        Integer nrl=csdto.getLocuriDisponibile();
        Cursa cursa=new Cursa(dest,plec,nrl);
        cursa.setId(id);
        return cursa;
    }

    public static CursaDTO getDTO(Cursa cs){
        Integer id=cs.getId();
        String dest= cs.getDestinatie();
        LocalDateTime plec =cs.getPlecare();
        Integer nrl=cs.getLocuriDisponibile();

        return new CursaDTO(id,dest,plec,nrl);
    }

    public static Cursa[] getFromDTO(CursaDTO[] csdtos){
        Cursa[] curse=new Cursa[csdtos.length];
        for(int i=0;i<csdtos.length;i++){
            curse[i]=getFromDTO(csdtos[i]);
        }
        return curse;
    }

    public static CursaDTO[] getDTO(Cursa[] css){
        CursaDTO[] curse=new CursaDTO[css.length];
        for(int i=0;i<css.length;i++){
            curse[i]=getDTO(css[i]);
        }
        return curse;
    }

    public static Rezervare getFromDTO(RezervareDto rezdto){
        Integer id=rezdto.getId();
        String numeClient= rezdto.getNumeClient();
        Integer nrL = rezdto.getNrLocuri();
        Integer curs=rezdto.getIdCursa();
        Rezervare rezervare=new Rezervare(numeClient,nrL,curs);
        rezervare.setId(id);
        return rezervare;
    }

    public static domain.RezervareDTO getDTOFromDTO(RezervareDto rezdto){
        String numeClient= rezdto.getNumeClient();
        Integer nrL = rezdto.getNrLocuri();
        return new domain.RezervareDTO(numeClient,nrL);
    }

    public static RezervareDto getDTO(Rezervare rez){
        Integer id=rez.getId();
        String numeClient= rez.getNumeClient();
        Integer nrL = rez.getNrLocuri();
        Integer curs=rez.getIdCursa();
        return new RezervareDto(id,numeClient,nrL,curs);
    }


    public static RezervareDto getDTOdom(domain.RezervareDTO rez){
        //Integer id=rez.getId();
        String numeClient= rez.getNumeClient();
        Integer nrL = rez.getNrLoc();
        //Integer curs=rez.getIdCursa();
        return new RezervareDto(numeClient,nrL);
    }


    public static Rezervare[] getFromDTO(RezervareDto[] rezdtos){
        Rezervare[] rezervari=new Rezervare[rezdtos.length];
        for(int i=0;i< rezdtos.length;i++){
            rezervari[i]=getFromDTO(rezdtos[i]);
        }
        return rezervari;

    }

    public static RezervareDto[] getDTO(Rezervare[] rezs){
        RezervareDto[] rezervari=new RezervareDto[rezs.length];
        for(int i=0;i< rezs.length;i++){
            rezervari[i]=getDTO(rezs[i]);
        }
        return rezervari;

    }

    public static domain.RezervareDTO[] getDTOFromDTO(RezervareDto[] rezdtos){
        domain.RezervareDTO[] rezervari=new domain.RezervareDTO[rezdtos.length];
        for(int i=0;i< rezdtos.length;i++){
           rezervari[i]=getDTOFromDTO(rezdtos[i]);
        }
        return rezervari;

    }

    public static RezervareDto[] getDTOdom(domain.RezervareDTO[] rezs){
        RezervareDto[] rezervari=new RezervareDto[rezs.length];
        for(int i=0;i< rezs.length;i++){
            rezervari[i]=getDTOdom(rezs[i]);
        }
        return rezervari;

    }




}
