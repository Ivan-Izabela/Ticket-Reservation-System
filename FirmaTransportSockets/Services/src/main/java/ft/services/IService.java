package ft.services;

import java.util.List;
import ft.domain.Cursa;
import ft.domain.Oficiu;
import ft.domain.Rezervare;
import ft.domain.RezervareDTO;

public interface IService {
    Oficiu login(String nume, String parola,IObserver client) throws FTException;
    List<Cursa> findAllCurse() throws FTException;
    List<Rezervare> findAllRezervari() throws FTException;
    List<RezervareDTO> findRezervareDTO(String id1) throws FTException;
    List<Cursa> findByDestinatie(String destinatie) throws FTException;
    Rezervare saveRezervare(Rezervare rezervare) throws  FTException;
    void logout(String nume, IObserver client) throws  FTException;


}
