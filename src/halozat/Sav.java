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
    
    // Privátra véve, Getter/Setter használatával a csomagok közti átjárhatóságért
    private int sozott = 0;
    private boolean zuzalekos = false;

    /**
     * Konstruktor, inicializálja a sáv alapértelmezett állapotát és szomszédait.
     */
    public Sav() {
        SkeletonLogger.create(this);
        this.allapot = new Tiszta();
        this.szomszedok = new ArrayList<>();
        SkeletonLogger.exit(this);
    }

    public void addSzomszed(Csomopont szomszed) {
        SkeletonLogger.enter(this, "addSzomszed", szomszed);
        this.szomszedok.add(szomszed);
        SkeletonLogger.exit("void");
    }

    public void setUtszakasz(Utszakasz utszakasz) {
        this.utszakasz = utszakasz;
    }

    public Utszakasz getUtszakasz() {
        return this.utszakasz;
    }

    public void setAllapot(Savallapot allapot) {
        SkeletonLogger.enter(this, "setAllapot", allapot);
        this.allapot = allapot;
        SkeletonLogger.exit("void");
    }

    public Savallapot getAllapot() {
        return this.allapot;
    }

    // --- ÚJ GETTER/SETTER METÓDUSOK AZ ÁLLAPOTOK SZÁMÁRA ---
    public boolean isZuzalekos() {
        return this.zuzalekos;
    }

    public void setZuzalekos(boolean zuzalekos) {
        this.zuzalekos = zuzalekos;
    }

    public int getSozott() {
        return this.sozott;
    }

    public void setSozott(int sozott) {
        this.sozott = sozott;
    }
    // --------------------------------------------------------

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

    @Override
    public boolean befogad(Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", jarmu);
        if (this.foglalt()) {
            SkeletonLogger.exit(false);
            return false;
        }
        boolean siker = false;
        if (this.allapot != null) {
            // Itt dől el (a State-ben), hogy jégre lép, letapossa a havat, stb.
            siker = this.allapot.befogad(this, jarmu);
        }
        if (siker) {
            this.jarmu = jarmu;
        }
        SkeletonLogger.exit(siker);
        return siker;
    }

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

    @Override
    public List<Csomopont> getNext() {
        return szomszedok;
    }

    public Sav getJobbSzomszed(Sav sav) {
        SkeletonLogger.enter(this, "getJobbSzomszed", sav);
        Sav szomszed = null;
        if (this.utszakasz != null) {
            szomszed = this.utszakasz.getJobbSzomszed(this);
        }
        SkeletonLogger.exit(szomszed);
        return szomszed;
    }

    public boolean lepesTeszt(Jarmu jarmu) {
        SkeletonLogger.enter(this, "lepesTeszt", jarmu);
        boolean teszt = false;
        if (allapot != null) {
            teszt = allapot.lepesTeszt(jarmu);
        }
        SkeletonLogger.exit(teszt);
        return teszt;
    }

    @Override
    public void balesetEseten() {
        SkeletonLogger.enter(this, "balesetEseten");
        if (this.jarmu != null) {
            this.jarmu.balesetetSzenved(); // A baleset manuális előidézése (Teszt 50)
        }
        SkeletonLogger.exit("void");
    }

    @Override
    public boolean foglalt() {
        return (this.jarmu != null); // Kapacitáskorlát biztosítása (Teszt 41)
    }

    @Override
    public void hoesesEseten() {
        SkeletonLogger.enter(this, "hoesesEseten");
        if (this.sozott == 0) {
            // NullPointerException védelem: ha nincs útszakaszban, egyből az állapotnak szólunk
            if (this.utszakasz != null) {
                this.utszakasz.havazikRa(this);
            } else {
                if (this.allapot != null) {
                    this.allapot.hoesesEseten(this);
                }
            }
        } else {
            this.sozott--; // Teszt 47: A só védi a sávot a havazástól!
        }
        SkeletonLogger.exit("void");
    }

    public boolean jegTisztit(Boolean olvad) {
        SkeletonLogger.enter(this, "jegTisztit", olvad);
        boolean ret = false;
        if (allapot != null) {
            ret = allapot.jegTisztit(this, olvad);
        }
        SkeletonLogger.exit(ret);
        return ret;
    }

    public boolean hoTisztit() {
        SkeletonLogger.enter(this, "hoTisztit");
        boolean ret = false;
        if (allapot != null) {
            ret = allapot.hoTisztit(this);
        }
        SkeletonLogger.exit(ret);
        return ret;
    }

    public void soSzoras() {
        SkeletonLogger.enter(this, "soSzoras");
        if (allapot != null) {
            allapot.sotKap(this);
        }
        this.sozott = 3;
        SkeletonLogger.exit("void");
    }

    public void zuzalekSzoras() {
        SkeletonLogger.enter(this, "zuzalekSzoras");
        if (allapot != null) {
            allapot.zuzalekSzoras(); 
        }
        this.zuzalekos = true;
        SkeletonLogger.exit("void");
    }
}