package domain;

public class Rezervare extends Entity<Integer>{
    private String numeClient;
    private Integer nrLocuri;
    private Integer idCursa;

    public Rezervare(String numeClient, Integer nrLocuri, Integer idCursa) {
        this.numeClient = numeClient;
        this.nrLocuri = nrLocuri;
        this.idCursa=idCursa;
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
        return "Rezervare{" +
                "numeClient='" + numeClient + '\'' +
                ", nrLocuri=" + nrLocuri +
                ", idCursa=" + idCursa +
                ", id=" + id +
                '}';
    }
}
