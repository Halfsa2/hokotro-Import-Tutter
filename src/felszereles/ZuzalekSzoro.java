package felszereles;

import halozat.Sav;
import vezerles.SkeletonLogger;

/**
 * A Zúzalékszóró fej apró kavicsokat szór a jégre.
 * Nem szünteti meg a jeget, de megakadályozza a járművek megcsúszását.
 */
public class ZuzalekSzoro extends Kotrofej {
    
    /**
     * A rendelkezésre álló zúzalék mennyisége (maximum 10 lehet).
     */
    protected int zuzalek_mennyiseg;

    /**
     * Konstruktor a ZuzalekSzoro osztályhoz.
     * @param kezdetiZuzalek a kezdeti zúzalék mennyisége
     */
    public ZuzalekSzoro(int kezdetiZuzalek) {
        SkeletonLogger.create(this);
        // Beállítjuk a kezdőértéket, de figyelünk, hogy ne lehessen 10-nél több
        this.zuzalek_mennyiseg = Math.min(kezdetiZuzalek, 10);
        SkeletonLogger.exit(this);
    }

    /**
     * Takarítja a jeget/havat a zúzalékszóró fejjel.
     * Ha van elég zúzalék, szór a sávra és csökkenti a készletet.
     * @param s a tisztítandó sáv
     * @return true, ha sikerült a szórás, különben false
     */
    @Override
    public boolean takarit(Sav s) {
        SkeletonLogger.enter(this, "takarit", s);
        
        // Ellenőrizzük, hogy van-e még zúzalék a tartályban
        if (this.zuzalek_mennyiseg > 0) {
            s.zuzalekSzoras(); // Zúzalék szórása a sávra
            this.zuzalek_mennyiseg--; // Fogyóeszköz csökkentése
            
            SkeletonLogger.exit(true);
            return true;
        }
        
        // Ha kifogyott a zúzalék, a művelet sikertelen
        SkeletonLogger.exit(false);
        return false;
    }

    /**
     * Zúzalék újratöltése. A tartály kapacitása maximum 10 egység.
     * @param mennyiseg a betöltendő zúzalék mennyisége
     */
    @Override
    public void ujratolt(int mennyiseg) {
        SkeletonLogger.enter(this, "ujratolt", mennyiseg); // LOGOLÁS PÓTOLVA
        
        this.zuzalek_mennyiseg += mennyiseg;
        
        // Kapacitás korlátozása: ha átlépné a 10-et, visszavágjuk 10-re (Teszt 59)
        if (this.zuzalek_mennyiseg > 10) {
            this.zuzalek_mennyiseg = 10;
        }
        
        SkeletonLogger.exit("void"); // LOGOLÁS PÓTOLVA
    }

    /**
     * Visszaadja a zúzalék aktuális mennyiségét.
     * (Hasznos lesz a Prototípus 'stat' parancsához, hogy lássuk mennyi zúzalék maradt)
     * @return a zúzalék mennyisége
     */
    public int getZuzalekMennyiseg() {
        return this.zuzalek_mennyiseg;
    }
}