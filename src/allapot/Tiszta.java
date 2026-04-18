package allapot;

import halozat.Sav;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

/**
 * A tiszta sáv állapotát reprezentáló osztály.
 * Ez az állapot azt jelzi, hogy a sáv teljesen tiszta, nincs rajta hó vagy jég.
 */
public class Tiszta extends Savallapot {

    /**
     * Konstruktor a Tiszta osztályhoz.
     */
    public Tiszta() {
        SkeletonLogger.create(this);
        SkeletonLogger.exit(this);
    }

    /**
     * Ellenőrzi, hogy a jármű befogadható-e a tiszta sávba.
     * Mivel tiszta, az állapot szempontjából mindig befogadható.
     * @param sav a sáv, amelybe a jármű szeretne befogadódni
     * @param jarmu a jármű, amely befogadódni szeretne
     * @return mindig true, mivel tiszta sáv
     */
    @Override
    public boolean befogad(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", sav, jarmu);
        
        // A foglaltság ellenőrzését maga a Sav osztály végzi el, 
        // az állapot szempontjából a tiszta sáv nem akadályozza a befogadást.
        SkeletonLogger.exit(true);
        return true;
    }

    /**
     * Elengedi a járművet a tiszta sávból.
     * Tiszta sávnál nincs speciális művelet.
     * @param sav a sáv, amelyből a jármű elengedésre kerül
     * @param jarmu a jármű, amely elengedésre kerül
     */
    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "elenged", sav, jarmu);
        SkeletonLogger.exit("void");
    }

    /**
     * Kezeli a hóesés esetét a tiszta sávon.
     * A sáv állapotát sekély hóra változtatja.
     * @param sav a sáv, amelyen hóesés történik
     */
    @Override
    public void hoesesEseten(Sav sav) {
        SkeletonLogger.enter(this, "hoesesEseten", sav);
        
        sav.setAllapot(new SekelyHo());
        
        SkeletonLogger.exit("void");
    }

    /**
     * Frissíti a tiszta sáv állapotát.
     * Tiszta sávnál nincs változás.
     * @param sav a frissítendő sáv
     */
    @Override
    public void frissit(Sav sav) {
    }

    /**
     * Teszteli, hogy a jármű ráléphet-e a tiszta sávra.
     * Mindig lehetséges.
     * @param jarmu a jármű, amely tesztelni szeretne
     * @return mindig true
     */
    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        SkeletonLogger.enter(this, "lepesTeszt", jarmu);
        SkeletonLogger.exit(true);
        return true;
    }

    /**
     * Kezeli, ha a sáv sót kap.
     * Tiszta sávnál nincs hatás.
     * @param sav a sáv, amely sót kap
     */
    @Override
    public void sotKap(Sav sav) {
        SkeletonLogger.enter(this, "sotKap", sav);
        SkeletonLogger.exit("void");
    }

    /**
     * Megpróbálja megtisztítani a havat a sávból.
     * Tiszta sávnál nincs hó, így nem sikerül.
     * @param sav a tisztítandó sáv
     * @return false, mivel nincs hó
     */
    @Override
    public boolean hoTisztit(Sav sav) {
        SkeletonLogger.enter(this, "hoTisztit", sav);
        SkeletonLogger.exit(false);
        return false;
    }

    /**
     * Megpróbálja megtisztítani a jeget a sávból.
     * Tiszta sávnál nincs jég, így nem sikerül.
     * @param sav a tisztítandó sáv
     * @param olvad jelzi, hogy a jeget törjük, vagy olvasztjuk
      * @return false, mivel nincs jég
     */
    @Override
    public boolean jegTisztit(Sav sav, Boolean olvad) {
        SkeletonLogger.enter(this, "jegTisztit", sav);
        SkeletonLogger.exit(false);
        return false;
    }
}