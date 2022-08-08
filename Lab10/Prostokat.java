package Lab10;

public class Prostokat implements Figura{
    private Double a;
    private Double b;

    public Prostokat(Double a, Double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Double obliczPole() {
        return a*b;
    }

    @Override
    public Double obliczObwod() {
        return 2*a+2*b;
    }
}
