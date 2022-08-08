package Lab6;

public class Rownoboczny extends Trojkat{

    public Rownoboczny(double a) {
        super(a);
    }

    public double pole(){
        return (a*a*Math.sqrt(3))/4;
    }
}
