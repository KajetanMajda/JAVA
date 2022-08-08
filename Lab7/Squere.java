package Lab7;

public class Squere  extends shape{
    int bok;

    public Squere(int bok) {
        this.bok = bok;
    }

    @Override
    public double getArea() {
        return bok*bok;
    }

    @Override
    public double getPermiter() {
        return 4*bok;
    }
}
