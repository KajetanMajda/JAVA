package Lab8;

import java.io.IOException;

public class Zadanie3 {
    public static void main(String[] args) {
        try {
            wyjatek();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static  void wyjatek() throws IOException {
        throw  new IOException("blad pliku");
    }
}
