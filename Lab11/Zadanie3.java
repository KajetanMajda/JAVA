package Lab11;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Zadanie3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String tekst = scanner.nextLine();
        System.out.println(zliczSlowa(tekst));

    }

    public static Map<String, Integer> zliczSlowa(String tekst){
        String[] slowa = tekst.split(" ");
        Map<String, Integer> mapa = new TreeMap<>();
        //HashMap to tez bedziemu uzywac ale nie wiem jeszcze gdzie
        for (String slowo:slowa){
            if (mapa.containsKey(slowo)){
                int wystapienia = mapa.get(slowo);
                mapa.put(slowo, ++wystapienia);
            }else{
                mapa.put(slowo, 1);
            }
        }
        return mapa;
    }
}
