package halozat;

import java.util.ArrayList;
import java.util.List;
import vezerles.SkeletonLogger;

public class Utszakasz {
    
    protected List<Sav> savok = new ArrayList<>();

    public void addSav(Sav s) {
        savok.add(s);
        s.setUtszakasz(this);
    }

    public List<Sav> getSavok() {
        return savok;
    }

    public void havazikRa(Sav s) {
        SkeletonLogger.enter(this, "havazikRa", s);
        s.getAllapot().hoesesEseten(s); // Az útszakasz hatása a sávra, ami továbbadja a hatást az állapotnak
        SkeletonLogger.exit("void");
    }
    
    /**
     * @param sav
     * @return paramétertől jobbra lévő sáv referenciája, vagy null érték, ha nincs az adott irányban szomszédja
     */
    public Sav getJobbSzomszed(Sav sav){
        SkeletonLogger.enter(this, "getJobbSzomszed", sav); // LOGOLÁS
        
        int id = savok.indexOf(sav);
        // Biztonsági ellenőrzés: ha benne van a listában, és NEM az utolsó elem
        if (id != -1 && id < savok.size() - 1) {
            Sav szomszed = savok.get(id + 1);
            SkeletonLogger.exit(szomszed);
            return szomszed;
        }
        
        SkeletonLogger.exit(null); // Ha nincs jobb szomszéd, null-al térünk vissza
        return null;
    }
    
    /**
     * @param sav
     * @return paramétertől balra lévő sáv referenciája, vagy null érték, ha nincs az adott irányban szomszédja
     */
    public Sav getBalSzomszed(Sav sav){
        SkeletonLogger.enter(this, "getBalSzomszed", sav); // LOGOLÁS
        
        int id = savok.indexOf(sav);
        // Biztonsági ellenőrzés: ha benne van a listában, és NEM a legelső elem
        if (id > 0) {
            Sav szomszed = savok.get(id - 1);
            SkeletonLogger.exit(szomszed);
            return szomszed;
        }
        
        SkeletonLogger.exit(null); // Ha nincs bal szomszéd, null-al térünk vissza
        return null;
    }
}