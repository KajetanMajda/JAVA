package Lab6;

public class Rower extends Pojazd{
    protected  String rodzaj;
    public Rower(int liczbaKol, String kolor, String dzwiek, String rodzaj) {
        super(liczbaKol, kolor, dzwiek);
        this.rodzaj = rodzaj;
    }
        public void Informacje(){
            System.out.printf("[%s %s %s %s]\n",liczbaKol, kolor, dzwiek, rodzaj); }

}
