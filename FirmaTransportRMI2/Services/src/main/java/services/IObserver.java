package services;

import domain.Rezervare;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
    void saveDRezervare(Rezervare rezervare) throws  FTException, RemoteException;
}
