package Lab11;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Zadanie6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Set<Integer> liczby = new HashSet<>();
        while(liczby.size()<10){
            liczby.add(pobierzLiczbe(scanner));
        }
        int suma = 0;
        for (Integer liczba:liczby){
            suma+=liczba;
        }
        System.out.println(liczby);
        System.out.println("Suma ="+suma);
    }

    public static int pobierzLiczbe(Scanner scanner){
        System.out.println("Podaj liczbe a nie sie obijasz");
        String text = scanner.nextLine();
        try{
            return Integer.parseInt(text);
        }catch (NumberFormatException e){
            System.out.println("Podales nie wlasciwa liczbe staraj sie a nie ");
            return pobierzLiczbe(scanner);
        }
    }
}
