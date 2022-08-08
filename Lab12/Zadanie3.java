package Lab12;

import Lab10.DogAgeComparator;

import java.util.Arrays;
import java.util.Collections;

public class Zadanie3 extends Osoba {
    public Zadanie3(String imie, String nazwisko) {
        super(imie, nazwisko);
    }

    public static void main(String[] args) {
        Osoba osoba1 = new Osoba("Kajetan", "Majda");
        Osoba osoba2 = new Osoba("Martyna", "Marczak");
        Osoba osoba3 = new Osoba("Igor", "Pieper");
        Osoba osoba4 = new Osoba("Michał", "Koter");
        Osoba osoba5 = new Osoba("Adrian", "Reszka");

        Osoba [] osobas = {osoba5, osoba3, osoba2, osoba1, osoba4};
        Arrays.sort(osobas, new OsobaComparator());
        System.out.println(Arrays.toString(osobas));
    }
}
