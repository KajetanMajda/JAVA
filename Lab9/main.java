package Lab9;

public class main {
    public static void main(String[] args) {
        Kwadrat kwadrat = new Kwadrat(2,2 );
        System.out.println(kwadrat.pole());
        System.out.println(kwadrat.krawedz());
        Prostokat prostokat = new Prostokat(3,4);
        System.out.println(prostokat.pole());
        System.out.println(prostokat.krawedz());

    }
}
