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

    
    /**
     * A sáv sózott szintjét jelzi. Ha nagyobb mint 0, a só hatása alatt van.
     */
    public int sozott = 0;
    /**
     * Jelzi, hogy a sáv zuzalekos-e.
     */
    private boolean zuzalekos = false;
    
    /**
     * Konstruktor a Jeges osztályhoz.
     * Inicializálja a jeges állapotot és regisztrálja a SkeletonLogger-ben.
     */
    public Jeges() { /* Konstruktor */
        SkeletonLogger.create(this);
        SkeletonLogger.exit(this);
    }



    /**
     * Ellenőrzi, hogy a jármű befogadható-e a jeges sávba.
     * Ha nincs zúzalék, a jármű megcsúszik és balesetet szenved.
     * @param sav a sáv, amelybe a jármű szeretne befogadódni
     * @param jarmu a jármű, amely befogadódni szeretne
     * @return mindig true (ráléphet, de lehet, hogy karambolozik)
     */
    @Override
    public boolean befogad(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", sav, jarmu);
        if (this.zuzalekos) {
            SkeletonLogger.exit(true);
            return true;
        } else {
            jarmu.balesetetSzenved();
            SkeletonLogger.exit(true);
            return true; 
        }
    }

    /**
     * Elengedi a járművet a jeges sávból.
     */
    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "elenged", sav, jarmu);
        SkeletonLogger.exit("void");
    }

    /**
     * Kezeli a hóesés esetét a jeges sávon.
     */
    @Override
    public void hoesesEseten(Sav sav) {
        SkeletonLogger.enter(this, "hoesesEseten", sav);
        // Itt a jövőben implementálható, hogy havazás hatására havas-jég jöjjön létre
        SkeletonLogger.exit("void");
    }

    /**
     * Frissíti a jeges sáv állapotát (idő múlása).
     */
    @Override
    public void frissit(Sav sav) {
        SkeletonLogger.enter(this, "frissit", sav);
        
        // Logikai javítás: Csak akkor csökken a só hatása, ha tényleg be van sózva.
        if (this.sozott > 0) {
            this.sozott--;
            
            // Ha a só kifejtette a hatását (lejárt a számláló), a jég elolvad.
            if (this.sozott <= 0) {
                sav.setAllapot(new Tiszta());
            }
        }
        
        SkeletonLogger.exit("void");
    }

    /**
     * Teszteli, hogy a jármű ráléphet-e a jeges sávra.
     */
    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        SkeletonLogger.enter(this, "lepesTeszt", jarmu);
        SkeletonLogger.exit(true);
        return true;
    }

    /**
     * Kezeli, ha a sáv sót kap.
     * Beállítja a sózott szintet 3-ra.
     */
    @Override
    public void sotKap(Sav sav) {
        SkeletonLogger.enter(this, "sotKap", sav);
        this.sozott = 3;
        SkeletonLogger.exit("void");
    }

    /**
     * Megpróbálja megtisztítani a havat a sávból.
     * Jeges sávnál nincs hó, így nem sikerül.
     */
    @Override
    public boolean hoTisztit(Sav sav) {
        SkeletonLogger.enter(this, "hoTisztit", sav);
        SkeletonLogger.exit(false);
        return false;
    }

    /**
     * Megpróbálja megtisztítani a jeget a sávból.
     * Ha sárkányfej használja, tiszta lesz; különben sekély hó marad.
     * @param sav a tisztítandó sáv
     * @param olvad jelzi, hogy a jeget törjük, vagy olvasztjuk
     * @return true, mivel sikerül
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
        this.zuzalekos = false;
        SkeletonLogger.exit(true);
        return true;

    }
    @Override
    public void zuzalekSzoras() {
        SkeletonLogger.enter(this, "zuzalekSzoras");
        this.zuzalekos = true;
        SkeletonLogger.exit("void");
    }
}