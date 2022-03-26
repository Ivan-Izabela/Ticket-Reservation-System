package domain;

public class Oficiu extends Entity<Integer> {
    private String nume;
    private String parola;

    public Oficiu(String nume, String parola) {
        this.nume = nume;
        this.parola=parola;
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

    @Override
    public String toString() {
        return "Oficiu{" +
                "nume='" + nume + '\'' +
                ", parola='" + parola + '\'' +
                ", id=" + id +
                '}';
    }
}
