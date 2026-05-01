package allapot;

import halozat.Sav;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

/**
 * A jeges sáv állapotát reprezentáló osztály.
 * Ez az állapot azt jelzi, hogy a sáv jeges, és a járművek megcsúszhatnak rajta,
 * ami balesetet okoz (kivéve, ha zúzalékos).
 */
public class Jeges extends Savallapot{

    private int sozott = 0; 
    private boolean zuzalekos = false;
    
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
        
        if(sozott > 0) {
            sozott--; // Az idő, ami hiányzik, hogy megolvadjon a jég csökken
        }else{
            sav.setAllapot(new Tiszta()); // A jég elolvad
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
        sozott = 3; // Elindítja a jég olvadását (Teszt 45)
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
        } else {
            // Jégtörő esetén marad 1 réteg hó
            sav.setAllapot(new SekelyHo());
        }

        SkeletonLogger.exit(true);
        return true;
    }
    
    @Override
    public boolean zuzalekTisztit(Sav sav) {
        SkeletonLogger.enter(this, "zuzalekTisztit", sav);
        if(zuzalekos){
            zuzalekos = false; // A zuzalék eltávolítása megtisztítja a sávot a jégtől
            SkeletonLogger.exit(true);
            return true;
        }
        SkeletonLogger.exit(false);
        return false; // Nem volt zuzalék, így nem történt tisztítás
    }
    
    @Override
    public void zuzalekSzoras() {
        SkeletonLogger.enter(this, "zuzalekSzoras");
        this.zuzalekos = true; // A zuzalék szórása megvédi a sávot a jégtől
        SkeletonLogger.exit("void");
    }
    @Override
    public void printStat(String name) {
        System.out.println("Jeges " + name + ": sozott=" + this.sozott + ", zuzalekos=" + this.zuzalekos);
    }
}