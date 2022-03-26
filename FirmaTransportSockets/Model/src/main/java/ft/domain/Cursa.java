package ft.domain;


import java.time.LocalDateTime;


public class Cursa extends Entity<Integer> {
    private String destinatie;
    private String plecare;
    private Integer locuriDisponibile;

    public Cursa() {
    }


    public Cursa(String destinatie) {
        this.destinatie = destinatie;
    }

    public Cursa(String destinatie, String plecare) {
        this.destinatie = destinatie;
        this.plecare = plecare;
    }



    public Cursa(Integer locuriDisponibile) {
        this.locuriDisponibile = locuriDisponibile;
    }

    public Cursa(String destinatie, String plecare, Integer locuriDisponibile) {
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

    public String getPlecare() {
        return plecare;
    }

    public void setPlecare(String plecare) {
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
