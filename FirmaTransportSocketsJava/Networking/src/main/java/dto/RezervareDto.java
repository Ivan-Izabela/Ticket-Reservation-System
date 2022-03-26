package dto;

import java.io.Serializable;

public class RezervareDto implements Serializable {
    private Integer id;
    private String numeClient;
    private Integer nrLocuri;
    private Integer idCursa;

    public RezervareDto(Integer id, String numeClient, Integer nrLocuri, Integer idCursa) {
        this.id = id;
        this.numeClient = numeClient;
        this.nrLocuri = nrLocuri;
        this.idCursa = idCursa;
    }

    public RezervareDto(String numeClient, Integer nrLocuri) {
        this.numeClient = numeClient;
        this.nrLocuri = nrLocuri;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public Integer getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(Integer nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public Integer getIdCursa() {
        return idCursa;
    }

    public void setIdCursa(Integer idCursa) {
        this.idCursa = idCursa;
    }

    @Override
    public String toString() {
        return   numeClient + '\'' +
                 nrLocuri +
                 idCursa;
    }
}
