package halozat;

import java.util.ArrayList;
import java.util.List;

public class Utszakasz {
    
    protected List<Sav> savok = new ArrayList<>();

    public void addSav(Sav s) {
        savok.add(s);
        s.setUtszakasz(this); // Beállítjuk a sávnál a szülő útszakaszt a Double Dispatch-hez
    }

    public List<Sav> getSavok() {
        return savok;
    }

    // DOUBLE DISPATCH LOGIKA
    public void havazikRa(Sav s) {
        System.out.println("> utszakasz:Utszakasz.havazikRa(s1)");
        // Ha nem alagútban vagyunk, a sáv állapota megkapja a havat
        if (s.getAllapot() != null) {
            s.getAllapot().hoesesEseten(s);
        }
        System.out.println("<- void");
    }
}