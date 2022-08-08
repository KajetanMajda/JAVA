package Lab2;

public class Zadanie2 {
    public static void main(String[] args) { //psvm i tab i dziala
        Kwadrat kwadrat = new Kwadrat(5);
        System.out.println("Pole "+kwadrat.pole());  //sout i tab i dziala
        System.out.println(kwadrat.obwod());

        Kwadrat kwadrat2 = new Kwadrat(10);
        System.out.println(kwadrat2.pole());


    }
}
