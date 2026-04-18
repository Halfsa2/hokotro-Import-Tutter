package halozat;

import java.util.ArrayList;
import java.util.List;
import vezerles.SkeletonLogger;

/**
 * Útszakaszt reprezentál, amely több párhuzamos sávból áll.
 * Kezeli a sávok közti szomszédsági viszonyokat és a hóesés továbbítását.
 */
public class Utszakasz {
    
    protected List<Sav> savok;

    /**
     * Konstruktor az Utszakasz osztályhoz.
     */
    public Utszakasz() {
        SkeletonLogger.create(this);
        this.savok = new ArrayList<>();
        SkeletonLogger.exit(this);
    }

    /**
     * Hozzáad egy új sávot az útszakaszhoz, és beállítja a sáv útszakasz referenciáját.
     * @param s a hozzáadandó sáv
     */
    public void addSav(Sav s) {
        SkeletonLogger.enter(this, "addSav", s);
        this.savok.add(s);
        s.setUtszakasz(this);
        SkeletonLogger.exit("void");
    }

    /**
     * Visszaadja az útszakaszban lévő sávok listáját.
     * @return az útszakasz sávjai
     */
    public List<Sav> getSavok() {
        SkeletonLogger.enter(this, "getSavok");
        SkeletonLogger.exit("lista");
        return this.savok;
    }

    /**
     * Hóesés hatását alkalmazza a megadott sávon.
     * @param s a hóesés hatása alá kerülő sáv
     */
    public void havazikRa(Sav s) {
        SkeletonLogger.enter(this, "havazikRa", s);
        // Az útszakasz hatása a sávra, ami továbbadja a hatást az állapotnak
        s.getAllapot().hoesesEseten(s); 
        SkeletonLogger.exit("void");
    }
    
    /**
     * Visszaadja a vizsgált sávtól jobbra lévő sávot.
     * @param sav a vizsgált sáv
     * @return a jobbra lévő sáv referenciája, vagy null érték, ha nincs az adott irányban szomszédja
     */
    public Sav getJobbSzomszed(Sav sav){
        SkeletonLogger.enter(this, "getJobbSzomszed", sav); 
        
        int id = this.savok.indexOf(sav);
        
        // Biztonsági ellenőrzés: ha benne van a listában, és NEM az utolsó elem
        if (id != -1 && id < this.savok.size() - 1) {
            Sav szomszed = this.savok.get(id + 1);
            SkeletonLogger.exit(szomszed);
            return szomszed;
        }
        
        SkeletonLogger.exit(null); // Ha nincs jobb szomszéd, null-al térünk vissza
        return null;
    }
    
    /**
     * Visszaadja a vizsgált sávtól balra lévő sávot.
     * @param sav a vizsgált sáv
     * @return a balra lévő sáv referenciája, vagy null érték, ha nincs az adott irányban szomszédja
     */
    public Sav getBalSzomszed(Sav sav){
        SkeletonLogger.enter(this, "getBalSzomszed", sav); 
        
        int id = this.savok.indexOf(sav);
        
        // Biztonsági ellenőrzés: ha benne van a listában, és NEM a legelső elem
        if (id > 0) {
            Sav szomszed = this.savok.get(id - 1);
            SkeletonLogger.exit(szomszed);
            return szomszed;
        }
        
        SkeletonLogger.exit(null); // Ha nincs bal szomszéd, null-al térünk vissza
        return null;
    }
}