package Lab11;

import java.util.*;

public class Zadanie4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String tekst = scanner.nextLine();
        System.out.println(skorowidzLiterowy(tekst));

    }

    public static Map<String, Set<Integer>> skorowidzLiterowy(String tekst){
        String[] literki = tekst.split("");
        Map<String, Set<Integer>> skorowidz = new TreeMap<>();
        for (int i=0; i<literki.length; i++){
            if (skorowidz.containsKey(literki[i])){
                Set<Integer> indeksy = skorowidz.get(literki[i]);
                indeksy.add(i);
                //fix xD
            }else{
                skorowidz.put(literki[i], new TreeSet<>(Arrays.asList(i)));
            }
        }
        return skorowidz;
    }
}
