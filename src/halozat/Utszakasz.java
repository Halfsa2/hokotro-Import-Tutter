package halozat;

import java.util.ArrayList;
import java.util.List;
import vezerles.SkeletonLogger;

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
        SkeletonLogger.enter(this, "havazikRa", s);
        s.getAllapot().hoesesEseten(s); // Az útszakasz hatása a sávra, ami továbbadja a hatást az állapotnak
        SkeletonLogger.exit("void");
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