package Lab8;

public class Wyjatek1 {
    public static void main(String[] args) {
        try{
            int a = 5/0;
        }catch (ArithmeticException e){
            System.out.println(e.getMessage());
        }finally {
            System.out.println("Koniec i tyle nie mam nic co tu bylo wczesniej XD");
        }

        System.out.println("TEST");
    }
}
