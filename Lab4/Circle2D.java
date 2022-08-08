package Lab4;

public class Circle2D {
    private double x;
    private double y;
    private double r;

    private Circle2D() {
        this.y = 0;
        this.x = 0;
        this.r = 0.5;
    }

    public Circle2D(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public double getArea(){
        return  Math.PI*r*r;
    }

    public double getPerimeter(){
        return Math.PI*2*r;
    }

}
