package halozat;

public class Alagut extends Utszakasz {

    @Override
    public void havazikRa(Sav s) {
        System.out.println("> alagut:Alagut.havazikRa(s1)");
        // Üresen hagyjuk: az alagút nem engedi át a havat a sáv állapotának!
        System.out.println("<- void");
    }
}