package Lab9;
import java.util.Scanner;
public class Zadanie4 {
    public static void main(String[] args) {
        while (true) {
        Scanner scan = new Scanner(System.in);
        double a = scan.nextDouble();


            if (a == 1) {
                double b = scan.nextDouble();;
                double c = scan.nextDouble();;
                System.out.println("Wynik Dodawania na liczbach ‘"+b+"‘ oraz ‘"+c+"’ wynosi ‘"+(b+c)+"’");

            } else if (a == 2) {
                double b = scan.nextDouble();;
                double c = scan.nextDouble();;
                System.out.println("Wynik odejmowania na liczbach ‘"+b+"‘ oraz ‘"+c+"’ wynosi ‘"+(b-c)+"’");

            } else if (a == 3) {
                double b = scan.nextDouble();;
                double c = scan.nextDouble();;
                System.out.println("Wynik mnozenia na liczbach ‘"+b+"‘ oraz ‘"+c+"’ wynosi ‘"+(b*c)+"’");

            } else if (a == 4) {
                double b = scan.nextDouble();;
                double c = scan.nextDouble();;
                System.out.println("Wynik Dzielenia na liczbach ‘"+b+"‘ oraz ‘"+c+"’ wynosi ‘"+(b/c)+"’");

            } else
                System.out.println("Podana liczba jest nie wlasciwa. Proszę podaj nowa");
        }
    }
}
