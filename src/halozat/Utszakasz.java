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
        // Ha nem alagútban vagyunk, a sáv állapota megkapja a havat
        if (s.getAllapot() != null) {
            s.getAllapot().hoesesEseten(s);
        }
    }
    /**
     * 
     * @param sav
     * @return paramétertől jobbra lévő sáv referenciája, vagy null érték, ha nincs az adott irányban szomszédja
     */
    public Sav getJobbSzomszed(Sav sav){
        int id = savok.indexOf(sav);
        return savok.get(id+1);
    }
    /**
     * 
     * @param sav
     * @return paramétertől balra lévő sáv referenciája, vagy null érték, ha nincs az adott irányban szomszédja
     */
    public Sav getBalSzomszed(Sav sav){
        int id = savok.indexOf(sav);
        return savok.get(id-1);
    }
}