package ft.services;

import ft.domain.Rezervare;

public interface IObserver {
    void saveDRezervare(Rezervare rezervare) throws  FTException;
}
