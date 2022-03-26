package services;

import java.util.List;
import domain.Cursa;
import domain.Oficiu;
import domain.Rezervare;
import domain.RezervareDTO;

public interface IService {
    Oficiu login(String nume, String parola,IObserver client) throws FTException;
    List<Cursa> findAllCurse() throws FTException;
    List<Rezervare> findAllRezervari() throws FTException;
    List<RezervareDTO> findRezervareDTO(String id1) throws FTException;
    List<Cursa> findByDestinatie(String destinatie) throws FTException;
    Rezervare saveRezervare(Rezervare rezervare) throws  FTException;
    void logout(String nume, IObserver client) throws  FTException;


}
