package Lab5;

public class Dom {
    private String ulica;
    private double powierzchniaDomu;
    private  double powierzchniaOgordu;
    private int iloscKondygnacji;
    private boolean czyZamieszkaly;

    public Dom(String ulica, double powierzchniaDomu, double powierzchniaOgordu, int iloscKondygnacji, boolean czyZamieszkaly) {
        this.ulica = ulica;
        this.powierzchniaDomu = powierzchniaDomu;
        this.powierzchniaOgordu = powierzchniaOgordu;
        this.iloscKondygnacji = iloscKondygnacji;
        this.czyZamieszkaly = czyZamieszkaly;
    }

    @Override
    public String toString() {
        return "Dom{" +
                "ulica='" + ulica + '\'' +
                ", powierzchniaDomu=" + powierzchniaDomu +
                ", powierzchniaOgordu=" + powierzchniaOgordu +
                ", iloscKondygnacji=" + iloscKondygnacji +
                ", czyZamieszkaly=" + czyZamieszkaly +
                '}';
    }
}
