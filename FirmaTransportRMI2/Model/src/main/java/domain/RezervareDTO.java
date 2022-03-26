package domain;

import java.io.Serializable;

public class RezervareDTO implements Serializable {
    private String numeClient;
    private  Integer nrLoc;

    public RezervareDTO(String numeClient, Integer nrLoc) {
        this.numeClient = numeClient;
        this.nrLoc = nrLoc;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public Integer getNrLoc() {
        return nrLoc;
    }

    public void setNrLoc(Integer nrLoc) {
        this.nrLoc = nrLoc;
    }

    @Override
    public String toString() {
        return "RezervareDTO{" +
                "numeClient='" + numeClient + '\'' +
                ", nrLoc=" + nrLoc +
                '}';
    }
}
