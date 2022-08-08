package Lab12;

import java.util.Comparator;

public class Osoba implements Comparator<Osoba> {
    private String imie;
    private String nazwisko;

    public Osoba(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }
    @Override
    public String toString() {
        return "{%"+nazwisko+"%,"+"%"+imie+"%}";
    }

    @Override
    public int compare(Osoba o1, Osoba o2) {
        return 0;
    }
}