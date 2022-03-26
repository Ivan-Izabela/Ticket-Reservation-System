package dto;

import java.io.Serializable;

public class OficiuDTO implements Serializable {
    private Integer id;
    private String nume;
    private String parola;

    public OficiuDTO( Integer id,String nume, String parola) {
        this.nume = nume;
        this.parola = parola;
        this.id = id;
    }

    public OficiuDTO(String nume, String parola) {
        this.nume = nume;
        this.parola = parola;
        this.id=0;
    }

    public OficiuDTO(Integer id) {
        this.id = id;
        this.nume = "";
        this.parola = "";
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nume + '\'' +
                 parola + '\'' ;
    }
}
