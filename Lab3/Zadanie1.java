package Lab3;

import java.util.Scanner;

public class Zadanie1 {
    public static void  main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int x = scan.nextInt();
        int y = scan.nextInt();

        if(x>y)
        {
            System.out.println("-1");
        }
        else if(x==y)
        {
            System.out.println("0");
        }
        else
        {
            System.out.println("1");
        }
    }
}
