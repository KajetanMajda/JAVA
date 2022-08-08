package Lab12;

import java.util.Scanner;

public class Zadanie4 {
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        System.out.println("<--"+text+"-->");
            int count = text.replaceAll("[^a]","").length();
            System.out.println("a = " + count);
            int count2 = text.replaceAll("[^i]","").length();
            System.out.println("i = " + count2);
            int count3 = text.replaceAll("[^e]","").length();
            System.out.println("e = " + count3);
            int count4 = text.replaceAll("[^o]","").length();
            System.out.println("o = " + count4);
            int count5 = text.replaceAll("[^u]","").length();
            System.out.println("u = " + count5);
            int count6 = text.replaceAll("[^y]","").length();
            System.out.println("y = " + count6);

        }
    }
