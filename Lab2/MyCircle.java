package Lab2;

public class MyCircle {
    int promien;

    public MyCircle(int promien) {
        this.promien = promien;
    }

    public double pole() {
        int a = promien;
        return Math.PI * a * a;
    }

    public double obwod(){
        int b = promien;
        return Math.PI * 2 * b;
    }

    public double srednica(){
        int r = promien;
        return 2*r;
    }
}
