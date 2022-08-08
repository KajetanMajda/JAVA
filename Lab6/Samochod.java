package Lab6;

public class Samochod extends Pojazd{
    public Samochod(int liczbaKol, String kolor, String dzwiek){
        super(liczbaKol, kolor, dzwiek);
    }
public void informacje(){
    System.out.printf("[%s %s %s]\n",liczbaKol, kolor, dzwiek); }

}
