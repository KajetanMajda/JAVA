package Lab9;

public class Kwadrat extends Figura2D{


    public Kwadrat(int row1, int row2) {
        super(row1, row2);
    }

    public int pole() {

        return row1 * row1;
    }

    public int krawedz() {

        return 4 * row1;
    }
}