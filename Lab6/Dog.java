package Lab6;

public class Dog {
   int ileRazy;

    public Dog(int ileRazy) {
        this.ileRazy = ileRazy;
        for (ileRazy=1;ileRazy<5;++ileRazy)
            System.out.printf("Szczek ");
    }

    public void szczekaj() {
        System.out.printf("Szczek ");
    }
}
