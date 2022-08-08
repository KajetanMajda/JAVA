package Lab7;

public abstract class Game {
    String nazwa;
    String rodzaj;
    String konsola;


    public Game(String nazwa, String rodzaj, String konsola) {
        this.nazwa = nazwa;
        this.rodzaj = rodzaj;
        this.konsola = konsola;
    }

    public abstract void GameXpudlo();
}
