package Lab9;

public class Prostokat extends Figura2D {

    public Prostokat(int row1, int row2) {
        super(row1, row2);
    }

    public int pole() {

        return row1 * row2;
    }

    public int krawedz() {

        return 2 * row1 + 2 * row2;
    }
}
