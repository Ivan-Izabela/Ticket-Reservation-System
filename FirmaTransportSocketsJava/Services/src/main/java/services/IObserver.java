package services;

import domain.Rezervare;

public interface IObserver {
    void saveDRezervare(Rezervare rezervare) throws  FTException;
}
