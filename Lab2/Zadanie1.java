package Lab2;

import java.util.Arrays;

public class Zadanie1 {
    public static void main(String[] args) {
        int[] tablica1 = new int[] { 0,10,0 };
        int[] tablica2 = new int[] { 5, 90, 5};
        int[] suma = obliczSume(tablica1, tablica2);
        System.out.println(Arrays.toString(suma));
    }

    public static int[] obliczSume(int[] tab1, int[] tab2){
        if (tab1.length!=tab2.length){
            return new int[] {  };
        }
        int[] suma = new int[tab1.length];
        for(int i=0; i<tab1.length; i++) {
            suma[i] = tab1[i]+tab2[i];
        }
        return  suma;
    }
}
