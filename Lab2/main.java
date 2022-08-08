package Lab2;

public class main {
    public static void main(String[] args) {
        Punkt punkt = new Punkt( 3, 3);
        Punkt zero = new Punkt();
        zero.wyswietl();
        System.out.println(punkt.odlegloscOd(4,4));
        System.out.println(punkt.odlegloscOd(zero));
    }

}
