package domain;


import java.time.LocalDateTime;


public class Cursa extends Entity<Integer> {
    private String destinatie;
    private LocalDateTime plecare;
    private Integer locuriDisponibile;


    public Cursa(String destinatie, LocalDateTime plecare, Integer locuriDisponibile) {
        this.destinatie = destinatie;
        this.plecare = plecare;
        this.locuriDisponibile = locuriDisponibile;
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
        return "Cursa{" +
                "destinatie='" + destinatie + '\'' +
                ", plecare=" + plecare +
                ", locuriDisponibile=" + locuriDisponibile +
                ", id=" + id +
                '}';
    }
}
