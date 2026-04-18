package halozat;

import allapot.Savallapot;
import allapot.Tiszta;
import jarmu.Jarmu;
import java.util.ArrayList;
import java.util.List;
import vezerles.SkeletonLogger;

/**
 * A városi hálózat egy sávja, amelyben járművek haladhatnak, és állapotkezelést biztosít.
 */
public class Sav extends Csomopont {

    private Utszakasz utszakasz;
    private Savallapot allapot;
    private Jarmu jarmu;
    private List<Csomopont> szomszedok = new ArrayList<>();
    protected int sozott = 0;

    /**
     * Konstruktor, inicializálja a sáv alapértelmezett állapotát és szomszédait.
     */
    public Sav() {
        this.allapot = new Tiszta();
        this.szomszedok = new ArrayList<>();
    }

    /**
     * Beállítja az útszakasz referenciáját a sáv számára.
     * @param utszakasz a hozzákapcsolt útszakasz
     */
    public void setUtszakasz(Utszakasz utszakasz) {
        this.utszakasz = utszakasz;
    }

    /**
     * Visszaadja az útszakaszt, amelyhez ez a sáv tartozik.
     * @return a sávhoz tartozó útszakasz
     */
    public Utszakasz getUtszakasz() {
        return this.utszakasz;
    }

    /**
     * Beállítja az aktuális sáv állapotát.
     * @param allapot az új állapot
     */
    public void setAllapot(Savallapot allapot) {
        SkeletonLogger.enter(this, "setAllapot", allapot);
        this.allapot = allapot;
        SkeletonLogger.exit("void");
    }

    /**
     * Visszaadja a sáv jelenlegi állapotát.
     * @return a sáv állapota
     */
    public Savallapot getAllapot() {
        return this.allapot;
    }

    /**
     * Frissíti a sávot minden szimulációs lépésben.
     */
    @Override
    public void frissit() {
        SkeletonLogger.enter(this, "frissit");
        if (this.sozott > 0) {
            this.sozott--;
        }
        if (allapot != null) {
            allapot.frissit(this);
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Megpróbálja befogadni a járművet a sávba.
     * @param jarmu a befogadni kívánt jármű
     * @return true, ha sikerült, különben false
     */
    @Override
    public boolean befogad(Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", jarmu);
        if (this.foglalt()) {
            SkeletonLogger.exit(false);
            return false;
        }
        boolean siker = false;
        if (this.allapot != null) {
            siker = this.allapot.befogad(this, jarmu);
        }
        if (siker) {
            this.jarmu = jarmu;
        }
        SkeletonLogger.exit(siker);
        return siker;
    }

    /**
     * Elengedi a járművet a sávból.
     * @param jarmu az elengedendő jármű
     */
    @Override
    public void elenged(Jarmu jarmu) {
        SkeletonLogger.enter(this, "elenged", jarmu);
        if (this.jarmu == jarmu) {
            this.jarmu = null;
            if (allapot != null) {
                allapot.elenged(this, jarmu);
            }
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Visszaadja a következő csomópontok listáját.
     * @return a szomszédos csomópontok
     */
    @Override
    public List<Csomopont> getNext() {
        return szomszedok;
    }

    /**
     * Visszaadja a jobboldali szomszédos sávot a jelenlegi sávhoz képest.
     * @param sav a jelenlegi sáv
     * @return a jobboldali szomszédos sáv vagy null
     */
    public Sav getJobbSzomszed(Sav sav) {
        SkeletonLogger.enter(this, "getJobbSzomszed", sav);
        Sav szomszed = null;
        if (this.utszakasz != null) {
            szomszed = this.utszakasz.getJobbSzomszed(this);
        }
        SkeletonLogger.exit(szomszed);
        return szomszed;
    }

    /**
     * Teszteli, hogy a jármű ráléphet-e a sávra az aktuális állapot alapján.
     * @param jarmu a tesztelendő jármű
     * @return true, ha a jármű ráléphet
     */
    public boolean lepesTeszt(Jarmu jarmu) {
        SkeletonLogger.enter(this, "lepesTeszt", jarmu);
        boolean teszt = false;
        if (allapot != null) {
            teszt = allapot.lepesTeszt(jarmu);
        }
        SkeletonLogger.exit(teszt);
        return teszt;
    }

    /**
     * Kezeli, hogy a sáv baleseti állapotba kerül-e.
     */
    @Override
    public void balesetEseten() {
        if (this.jarmu != null) {
        }
    }

    /**
     * Ellenőrzi, hogy a sáv foglalt-e aktuálisan járművel.
     * @return true, ha jármű van a sávon
     */
    @Override
    public boolean foglalt() {
        return (this.jarmu != null);
    }

    /**
     * A hóesés hatásainak kezelése a sávon.
     */
    @Override
    public void hoesesEseten() {
        SkeletonLogger.enter(this, "hoesesEseten");
        if (sozott == 0) {
            utszakasz.havazikRa(this);
        } else {
            sozott--;
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Megkísérli a jég eltávolítását a sávon.
     * @param olvad jelzi, hogy a jeget törjük, vagy olvasztjuk
     * @return true, ha sikerült a jég eltakarítása
     */
    public boolean jegTisztit(Boolean olvad) {
        SkeletonLogger.enter(this, "jegTisztit");
        boolean ret = false;
        if (allapot != null) {
            ret = allapot.jegTisztit(this, olvad);
        }
        SkeletonLogger.exit(ret);
        return ret;
    }

    /**
     * Megpróbálja eltakarítani a havat a sávon.
     * @return true, ha a hó eltakarítása sikerült
     */
    public boolean hoTisztit() {
        SkeletonLogger.enter(this, "hoTisztit");
        boolean ret = false;
        if (allapot != null) {
            ret = allapot.hoTisztit(this);
        }
        SkeletonLogger.exit(ret);
        return ret;
    }

    /**
     * Só szórása a sáv felületére; csökkenti a jegesedési kockázatot.
     */
    public void soSzoras() {
        SkeletonLogger.enter(this, "soSzoras");
        if (allapot != null) {
            allapot.sotKap(this);
        }
        sozott = 3;
        SkeletonLogger.exit("void");
    }
}
