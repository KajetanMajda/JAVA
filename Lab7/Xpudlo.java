package Lab7;

public class Xpudlo {
    String x;
    String y;

    public Xpudlo(String x, String y) {
        this.x = x;
        this.y = y;
    }

    public void jakaGra(){
        if((x=="Forza Horizon 5") || (y=="Among us"))
        {
            System.out.println("Ta gra jest kompatybilna z Xpudlem, ale nie oczekuj ze ci na niej pojdzie"+'('+x+')'+'('+y+')');
        }
        else
            System.out.println("Nie no  gra do PS na xpudlo nie odbrazaj mnie");
    }
}
