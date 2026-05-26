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
     * @param sav A sáv, aminek az adott jeges objektum állapota
     * @param jarmu A jármű, ami a sávra akar lépni
     * @return A befogadás sikeressége (Mindenki ráléphet jeges állapotú sávra, de ha nincs felszórva zúzalékkal akkor balesetet okoz.)
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
    /**
     * Jármű elengedésének hatásai
     * @param sav a sáv, aminek az adott objektum a jeges állapota
     * @param jarmu a jármű, melyet el akarunk engedni
     */
    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "elenged", sav, jarmu);
        SkeletonLogger.exit("void");
    }
    /**
     * Kezeli azt az esetet, amikor jeges sávra hó esik
     * @param sav a sáv, aminek az adott objektum a jeges állapota
     */
    @Override
    public void hoesesEseten(Sav sav) {
        SkeletonLogger.enter(this, "hoesesEseten", sav);
        // Jelenleg nem csinál semmit a jégen a havazás
        SkeletonLogger.exit("void");
    }

    /**
     * Frissíti a jeges sáv állapotát (idő múlása).
     * @param sav A sáv aminek a jeges állapota az adott objektum
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
    /**
     * Teszteli, hogy szabad-e az adott járműnek az állapottal rendelkező sávra lépni. 
     * @return true, mivel minden jármű ráléphet jeges sávra
     */
    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        SkeletonLogger.enter(this, "lepesTeszt", jarmu);
        SkeletonLogger.exit(true);
        return true;
    }

    /**
     * Kezeli, ha a sáv sót kap.
     * @param sav a sáv, aminek az adott objektum jeges állapota
     */
    @Override
    public void sotKap(Sav sav) {
        SkeletonLogger.enter(this, "sotKap", sav);
        sozott = 3; // Elindítja a jég olvadását (Teszt 45)
        SkeletonLogger.exit("void");
    }
    /**
     * Kezeli azt az eseményt amikor jeges sávon akarunk havat tisztítani.
     * @return false, mivel jeges sávon nincs hó, amit el lehet takarítani.
     */
    @Override
    public boolean hoTisztit(Sav sav) {
        SkeletonLogger.enter(this, "hoTisztit", sav);
        SkeletonLogger.exit(false);
        return false; // Jeget hótolóval nem lehet takarítani
    }

    /**
     * Megpróbálja megtisztítani a jeget a sávból.
     * Ha sárkányfej használja, tiszta lesz; különben sekély hó marad.
     * @return true, mivel mindenképp sikerül a takarítás
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
    /**
     * Kezeli  azt az eseményt, amikor jeges állapotú sávról zúzalékot takarítunk
     * @param sav a sáv, aminek az adott objektum jeges állapota
     * @return a takarítás sikeressége (ha van zúzalék, akkor true, ha nincs, akkor false)
     */
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
    /**
     * Kezeli azt az esetet, amikor jeges sávot zúzalékkal szórunk fel.
     */
    @Override
    public void zuzalekSzoras() {
        SkeletonLogger.enter(this, "zuzalekSzoras");
        this.zuzalekos = true; // A zuzalék szórása megvédi a sávot a jégtől
        SkeletonLogger.exit("void");
    }
    
    @Override
    public String printStat(String name) {
        return "Jeges " + name + ": sozott=" + this.sozott + ", zuzalekos=" + this.zuzalekos;
    }
}