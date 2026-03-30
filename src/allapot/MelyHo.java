package allapot;

import halozat.Sav;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

/**
 * A legextrémebb téli útviszonyokat reprezentálja, ahol a normál forgalom
 * teljesen megbénul .
 */
public class MelyHo extends Savallapot {

    /**
     * Konstruktor a MelyHo osztályhoz.
     * Inicializálja a mély hó állapotot és regisztrálja a SkeletonLogger-ben.
     */
    public MelyHo() { /* Konstruktor [cite: 1173] */
        SkeletonLogger.create(this);
        SkeletonLogger.register(this, "melyho");
        SkeletonLogger.exit(this);
    }

    /**
     * Ellenőrzi, hogy a jármű befogadható-e a mély hó sávba.
     * Meghívja a lepesTeszt metódust.
     * @param sav a sáv, amelybe a jármű szeretne befogadódni
     * @param jarmu a jármű, amely befogadódni szeretne
     * @return true, ha a jármű ráléphet, különben false (hókotró ráléphet, autó nem)
     */
    @Override
    public boolean befogad(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", jarmu);
        boolean lephet = lepesTeszt(jarmu);
        SkeletonLogger.exit(lephet);
        return lephet;
    }

    /**
     * Elengedi a járművet a mély hó sávból.
     * Nincs speciális művelet.
     * @param sav a sáv, amelyből a jármű elengedésre kerül
     * @param jarmu a jármű, amely elengedésre kerül
     */
    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
    }

    /**
     * Kezeli a hóesés esetét a mély hó sávon.
     * Marad mély hó állapotban.
     * @param sav a sáv, amelyen hóesés történik
     */
    @Override
    public void hoesesEseten(Sav sav) {
    }

    /**
     * Frissíti a mély hó sáv állapotát.
     * Csökkenti a hóréteget és sekély hóra vált.
     * @param sav a frissítendő sáv
     */
    @Override
    public void frissit(Sav sav) {
        SkeletonLogger.enter(this, "frissit", sav);
        System.out.println("                > melyHo:MelyHo.horeteg--");
        SekelyHo sekelyHo = new SekelyHo();
        SkeletonLogger.register(sekelyHo, "sekelyHo");
        sav.setAllapot(sekelyHo);

        SkeletonLogger.exit("void");
    }

    /**
     * Teszteli, hogy a jármű ráléphet-e a mély hó sávra.
     * Csak hókotró léphet mély hóra.
     * @param jarmu a jármű, amely tesztelni szeretne
     * @return true, ha hókotró, különben false
     */
    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        // Kulcsfontosságú függvény, amely eldönti, hogy a jármű rá tud-e lépni a sávra.
        // A mély hó miatt ez egy sima Auto esetén hamis, míg egy Hokotro számára igaz
        // [cite: 1085-1090].
        return jarmu.lephetMelyHora();
    }

    /**
     * Kezeli, ha a sáv sót kap.
     * Mély hóra nincs hatás.
     * @param sav a sáv, amely sót kap
     */
    @Override
    public void sotKap(Sav sav) {
        SkeletonLogger.enter(this, "sotKap", sav);
        SkeletonLogger.exit("void");
    }

    /**
     * Megpróbálja megtisztítani a havat a sávból.
     * Sikeresen tiszta állapotba vált.
     * @param sav a tisztítandó sáv
     * @return true, mivel sikerül
     */
    @Override
    public boolean hoTisztit(Sav sav) {
        SkeletonLogger.enter(this, "hoTisztit", sav);

        Tiszta tiszta = new Tiszta();
        SkeletonLogger.register(tiszta, "tiszta1");
        sav.setAllapot(tiszta);

        SkeletonLogger.exit(true);
        return true;
    }

    /**
     * Megpróbálja megtisztítani a jeget a sávból.
     * Mély hóban nincs jég, így nem sikerül.
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