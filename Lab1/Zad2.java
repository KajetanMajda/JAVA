package Lab1;

import java.util.Scanner;

public class Zad2 {

    public static void main(String[] args) {
	Scanner scan = new Scanner(System.in); //to daje nam mozliwosc pobrania liczby z klawiatury
    double c = scan.nextInt();
    double f;
    f = (9/5.0) * c + 32;
    System.out.println(f);
    }
}
