package allapot;

import halozat.Sav;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

/**
 * A jeges sáv állapotát reprezentáló osztály.
 * Ez az állapot azt jelzi, hogy a sáv jeges, és a járművek megcsúszhatnak rajta,
 * ami balesetet okoz.
 */
public class Jeges extends Savallapot {

    /**
     * Konstruktor a Jeges osztályhoz.
     * Inicializálja a jeges állapotot és regisztrálja a SkeletonLogger-ben.
     */
    public Jeges() { /* Konstruktor */
        SkeletonLogger.create(this);
        SkeletonLogger.register(this, "sekelyHo");
        SkeletonLogger.exit(this);
    }

    /**
     * A sáv sózott szintjét jelzi. Ha nagyobb mint 0, a só hatása alatt van.
     */
    public int sozott = 0;
    public boolean zuzalekos = false;

    /**
     * Jelzi, hogy a sárkányfej használata miatt olvad a jég.
     */
    public static boolean sarkanyfejOlvassza = false;

    /**
     * Ellenőrzi, hogy a jármű befogadható-e a jeges sávba.
     * A jármű megcsúszik és balesetet szenved.
     * @param sav a sáv, amelybe a jármű szeretne befogadódni
     * @param jarmu a jármű, amely befogadódni szeretne
     * @return mindig true, de balesetet okoz
     */
    @Override
    public boolean befogad(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", sav, jarmu);
        
        // Ha van zúzalék, a jármű biztonságosan ráhajthat, NINCS baleset
        if (this.zuzalekos) {
            SkeletonLogger.exit(true);
            return true;
        } 
        // Ha nincs zúzalék, a jármű megcsúszik és balesetet szenved
        else {
            jarmu.balesetetSzenved();
            SkeletonLogger.exit(true);
            return true; // Ráléphet, de karambolozik
        }
    }

    /**
     * Elengedi a járművet a jeges sávból.
     * Jeges sávnál nincs speciális művelet.
     * @param sav a sáv, amelyből a jármű elengedésre kerül
     * @param jarmu a jármű, amely elengedésre kerül
     */
    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
    }

    /**
     * Kezeli a hóesés esetét a jeges sávon.
     * Jeges sávnál nincs változás, mivel már jeges.
     * @param sav a sáv, amelyen hóesés történik
     */
    @Override
    public void hoesesEseten(Sav sav) {
        // Reagál arra az eseményre, ha a jeges útszakaszon havazni kezd.
        // Itt például vastagodhat a jégen lévő hóréteg, átválthat egy speciális
        // havas-jeges állapotba.
    }

    /**
     * Frissíti a jeges sáv állapotát.
     * @param sav a frissítendő sáv
     */
    @Override
    public void frissit(Sav sav) {
        SkeletonLogger.enter(this, "frissit", sav);
        this.sozott--;
        if (this.sozott <= 0) {
            Tiszta tiszta = new Tiszta();
            SkeletonLogger.register(tiszta, "tiszta");
            sav.setAllapot(tiszta);
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Teszteli, hogy a jármű ráléphet-e a jeges sávra.
     * Mindig lehetséges, de balesetet okoz.
     * @param jarmu a jármű, amely tesztelni szeretne
     * @return mindig true
     */
    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        return true;
    }

    /**
     * Kezeli, ha a sáv sót kap.
     * Beállítja a sózott szintet 3-ra.
     * @param sav a sáv, amely sót kap
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
     * Ha sárkányfej használja, tiszta lesz; különben sekély hó marad.
     * @param sav a tisztítandó sáv
     * @return true, mivel sikerül
     */
    @Override
    public boolean jegTisztit(Sav sav) {
        SkeletonLogger.enter(this, "jegTisztit", sav);
        if (sarkanyfejOlvassza) {
            // Ha Sárkányfej (2-es teszt), akkor tisztára olvasztja
            Tiszta tiszta = new Tiszta();
            SkeletonLogger.register(tiszta, "tiszta1");
            sav.setAllapot(tiszta);
        } else {
            // Ha Jégtörő (9-es teszt), akkor marad egy réteg hó
            SekelyHo sekely = new SekelyHo();
            SkeletonLogger.register(sekely, "sekely1");
            sav.setAllapot(sekely);
        }

        SkeletonLogger.exit(true);
        return true;
    }
}