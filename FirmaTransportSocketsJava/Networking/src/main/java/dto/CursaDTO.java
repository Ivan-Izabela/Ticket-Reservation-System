package dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CursaDTO implements Serializable {
    private Integer id;
    private String destinatie;
    private LocalDateTime plecare;
    private Integer locuriDisponibile;

    public CursaDTO(Integer id, String destinatie, LocalDateTime plecare, Integer locuriDisponibile) {
        this.id = id;
        this.destinatie = destinatie;
        this.plecare = plecare;
        this.locuriDisponibile = locuriDisponibile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDestinatie() {
        return destinatie;
    }

    public void setDestinatie(String destinatie) {
        this.destinatie = destinatie;
    }

    public LocalDateTime getPlecare() {
        return plecare;
    }

    public void setPlecare(LocalDateTime plecare) {
        this.plecare = plecare;
    }

    public Integer getLocuriDisponibile() {
        return locuriDisponibile;
    }

    public void setLocuriDisponibile(Integer locuriDisponibile) {
        this.locuriDisponibile = locuriDisponibile;
    }

    @Override
    public String toString() {
        return   destinatie + '\'' +
                 plecare +
                 locuriDisponibile;
    }
}
