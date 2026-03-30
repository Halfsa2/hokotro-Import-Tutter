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
     * Inicializálja a tiszta állapotot és regisztrálja a SkeletonLogger-ben.
     */
    public Tiszta() { /* Konstruktor  */
        SkeletonLogger.create(this);
        SkeletonLogger.register(this, "tiszta");
        SkeletonLogger.exit(this);
    }

    /**
     * Ellenőrzi, hogy a jármű befogadható-e a tiszta sávba.
     * Mivel tiszta, mindig befogadja.
     * @param sav a sáv, amelybe a jármű szeretne befogadódni
     * @param jarmu a jármű, amely befogadódni szeretne
     * @return mindig true, mivel tiszta sáv
     */
    @Override
    public boolean befogad(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", sav, jarmu);
        /*System.out.print("[?] Foglalt a cel sav? (I/N): ");
        java.util.Scanner sc = new java.util.Scanner(System.in);
        String valasz = sc.nextLine();
        
        if (valasz.equalsIgnoreCase("i")) {
            SkeletonLogger.exit(false);
            return false;
        } else {
            SkeletonLogger.exit(true);
            return true;
        }*/
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
        // Csak logoljuk a kilépést, tiszta sávnál nincs más dolgunk
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
        SekelyHo sekelyHo = new SekelyHo();
        SkeletonLogger.register(sekelyHo, "sekelyHo");
        sav.setAllapot(sekelyHo);
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
        // Tiszta sávra rá lehet lépni
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
     * @return false, mivel nincs jég
     */
    @Override
    public boolean jegTisztit(Sav sav) {
        SkeletonLogger.enter(this, "jegTisztit", sav);
        SkeletonLogger.exit(false);
        return false;
    }
}
