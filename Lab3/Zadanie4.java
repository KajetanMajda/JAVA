package Lab3;

import java.util.Scanner;

public class Zadanie4 {
    public static void main(String[] args) {
Konto Konto = new Konto(1);
        System.out.println("Enter an ID: ");
        Scanner scan = new Scanner(System.in);
        int id = scan.nextInt();
            while (true) {

        if(id==1)
        {
            System.out.println(Konto.Str());
            Scanner numer = new Scanner(System.in);
            int n = numer.nextInt();
            int suma = Konto.kasa();
            if(n==1){
                System.out.println("The balance is " + suma + "\n");
            }else if(n==2){
                System.out.println("Ile chcesz wypłacic ");
                Scanner wyplata = new Scanner(System.in);
                int w = wyplata.nextInt();
                int s = Konto.kasa() - w;
                System.out.println("The balance is " + s + "\n");
            }else if(n==3){
                System.out.println("Ile chcesz wpłacic ");
                Scanner wplata = new Scanner(System.in);
                int k = wplata.nextInt();
                int g = Konto.kasa() + k;
                System.out.println("The balance is " + g + "\n");
            }else if(n==4){
                System.exit(0);
            }

        }else
        {
            System.out.println("Nie zdazylem :(");
        }

    }
    }
}
