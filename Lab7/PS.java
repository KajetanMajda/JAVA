package Lab7;

public class PS {

    String a;
    String b;

    public PS(String a, String b) {
        this.a = a;
        this.b = b;
    }

    public void gra(){
        if((a=="The last of us II") ||(b=="Uncharted 4: A Thief's End"))
        {
            System.out.println("Fajnie ze wybierasz normalna konsole i piekne gry do niej"+'('+a+')'+'('+b+')');
        }
        else
            System.out.println("idź precz duszo nieczysta");

    }
}
