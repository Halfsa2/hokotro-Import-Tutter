package allapot;

import halozat.Sav;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

/**
 * A jeges sáv állapotát reprezentáló osztály.
 * Ez az állapot azt jelzi, hogy a sáv jeges, és a járművek megcsúszhatnak rajta,
 * ami balesetet okoz (kivéve, ha zúzalékos).
 */
public class Jeges extends Savallapot {

    // Jelzi, hogy a sáv be van-e sózva (elkezdett-e olvadni a jég)
    private boolean olvadasAlatt = false;
    
    public Jeges() { 
        SkeletonLogger.create(this);
        SkeletonLogger.exit(this);
    }

    /**
     * Ellenőrzi, hogy a jármű befogadható-e a jeges sávba.
     * Ha nincs zúzalék, a jármű megcsúszik és balesetet szenved (Teszt 43).
     */
    @Override
    public boolean befogad(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", sav, jarmu);
        
        // A Sáv-tól kérdezzük meg, hogy van-e rajta zúzalék!
        if (sav.isZuzalekos()) {
            SkeletonLogger.exit(true);
            return true; // Biztonságos áthaladás
        } else {
            jarmu.balesetetSzenved(); // Baleset! (Teszt 43)
            SkeletonLogger.exit(true);
            return true; // Rálépni ráléphet, csak karambolozik.
        }
    }

    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "elenged", sav, jarmu);
        SkeletonLogger.exit("void");
    }

    @Override
    public void hoesesEseten(Sav sav) {
        SkeletonLogger.enter(this, "hoesesEseten", sav);
        // Jelenleg nem csinál semmit a jégen a havazás
        SkeletonLogger.exit("void");
    }

    /**
     * Frissíti a jeges sáv állapotát (idő múlása).
     */
    @Override
    public void frissit(Sav sav) {
        SkeletonLogger.enter(this, "frissit", sav);
        
        // Ha a sózás elindította az olvadást, és a sáv sózott számlálója lejárt (0)
        if (this.olvadasAlatt && sav.getSozott() == 0) {
            sav.setAllapot(new Tiszta());
            sav.setZuzalekos(false); // A sózás a zúzalékot is lemossa
        }
        
        SkeletonLogger.exit("void");
    }

    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        SkeletonLogger.enter(this, "lepesTeszt", jarmu);
        SkeletonLogger.exit(true);
        return true;
    }

    /**
     * Kezeli, ha a sáv sót kap.
     */
    @Override
    public void sotKap(Sav sav) {
        SkeletonLogger.enter(this, "sotKap", sav);
        this.olvadasAlatt = true; // Elindítja a jég olvadását (Teszt 45)
        SkeletonLogger.exit("void");
    }

    @Override
    public boolean hoTisztit(Sav sav) {
        SkeletonLogger.enter(this, "hoTisztit", sav);
        SkeletonLogger.exit(false);
        return false; // Jeget hótolóval nem lehet takarítani
    }

    /**
     * Megpróbálja megtisztítani a jeget a sávból.
     * Ha sárkányfej használja, tiszta lesz; különben sekély hó marad.
     */
    @Override
    public boolean jegTisztit(Sav sav, Boolean olvad) {
        SkeletonLogger.enter(this, "jegTisztit", sav, olvad);
        
        if (olvad) {
            // Sárkányfej esetén teljesen leolvad
            sav.setAllapot(new Tiszta());
            sav.setZuzalekos(false); // Sárkányfej eltünteti a zúzalékot (Teszt 54)
        } else {
            // Jégtörő esetén marad 1 réteg hó
            sav.setAllapot(new SekelyHo());
            sav.setZuzalekos(false);
        }

        SkeletonLogger.exit(true);
        return true;
    }
    
    @Override
    public boolean zuzalekTisztit(Sav sav) {
        SkeletonLogger.enter(this, "zuzalekTisztit", sav);
        sav.setZuzalekos(false);
        SkeletonLogger.exit(true);
        return true;
    }
    
    @Override
    public void zuzalekSzoras() {
        SkeletonLogger.enter(this, "zuzalekSzoras");
        // Ezt most már a Sav.java kezeli közvetlenül!
        SkeletonLogger.exit("void");
    }
}