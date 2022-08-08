package Lab6;

public class Trojkat {
    protected double a;
    protected double h;

    public Trojkat() {
        this.a = 1;
        this.h = 2;
    }

    public Trojkat(double h) {
        this();
        this.h = h;
    }

    public double pole(){
        return a*h/2;
    }

}
