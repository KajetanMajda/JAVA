package Lab2;

public class Punkt {
    int x,y;

    public Punkt() {
    }

    public Punkt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double odlegloscOd(int x, int y){
        int a = Math.abs(this.x-x);
        int b = Math.abs(this.y-y);
        return Math.sqrt(a*a+b*b);
    }

    public double odlegloscOd(Punkt inny){
        return odlegloscOd(inny.x, inny.y);
    }

    public void wyswietl(){
        System.out.printf("(%d,%d)",x,y);  //souf i tab a daje nam to: formatowany teks
    }


}
