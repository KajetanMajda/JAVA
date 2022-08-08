package Lab1;

import java.util.Scanner;

public class Zad4 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); //to daje nam mozliwosc pobrania liczby z klawiatury
        int x = scan.nextInt();
    if(x==1)  {
        System.out.println("Poniedzialek");

        } else if(x==2) {
        System.out.println("Wtorek");
    } else if(x==3) {
        System.out.println("Sroda");
    } else if(x==4) {
        System.out.println("Czwartek");
    } else if(x==5) {
        System.out.println("Piatek");
    } else if(x==6) {
        System.out.println("Sobota");
    } else if(x==7) {
        System.out.println("Niedziela");
    }
    }
}
