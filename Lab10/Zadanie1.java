package Lab10;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Zadanie1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(in.nextLine());
        String formattedDate = date.format(formatter); // 04-05-2020
        System.out.println(formattedDate);

    }
}
