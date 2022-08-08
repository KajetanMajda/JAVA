package Lab4;

import java.util.Random;

public class Zawodnik {
    private String  Imie;
    private int Vmin;
    private int Vmax;
    private int S = 0;
    private Random random = new Random();


    public Zawodnik(String imie, int vmin, int vmax) {
        Imie = imie;
        Vmin = vmin;
        Vmax = vmax;

    }
    public void przedstawsie(){
        System.out.println("Siemka mam na imie "+Imie+" oraz biegam z predkoscia "+Vmin+"-"+Vmax);
    }
    public void biegnij(){
    S =+ random.nextInt(Vmax+1-Vmin)+Vmin;
    }
}
