package Lab10;

public class Kwadrat implements Figura{

    private Double a;

    public Kwadrat(Double a) {
        this.a = a;
    }


    @Override
    public Double obliczPole() {
        return a*a;

    }

    @Override
    public Double obliczObwod() {
        return a*a;

    }
}
