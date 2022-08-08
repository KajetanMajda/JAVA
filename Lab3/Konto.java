package Lab3;

import java.util.Scanner;

public class Konto {
    int pier;

    public Konto(int pier) {
        this.pier = pier;
    }
    public String Str()
    {
        return  "Main menu\n" +
                "1:check balance\n" +
                "2:withdraw\n" +
                "3:deposit\n"+
                "4:exit\n"+
                "Enter a choice: ";


    }
    public Integer kasa()
    {
        return 100;
    }
}
