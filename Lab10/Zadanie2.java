package Lab10;

public class Zadanie2 {
    public static void main(String[] args) {


        Figura[] tab = {new Kwadrat(2.0), new Prostokat(2.0,3.0), new Trojkat(2.0,4.0)};

         for(Figura fig : tab){
             System.out.println(fig.obliczObwod());
             System.out.println(fig.obliczPole());
         }
    }
}
