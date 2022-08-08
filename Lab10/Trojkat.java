package Lab10;

public class Trojkat implements Figura{

    private Double h;
    private Double a;

    public Trojkat(Double h, Double a) {
        this.h = h;
        this.a = a;
    }

    @Override
    public Double obliczPole() {
        return a*h/2;
    }

    @Override
    public Double obliczObwod() {
        return 3*a;
    }
}
