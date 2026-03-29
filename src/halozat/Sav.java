package halozat;

import allapot.Savallapot;
import allapot.Tiszta;
import jarmu.Jarmu;
import java.util.ArrayList;
import java.util.List;
import vezerles.SkeletonLogger;

public class Sav extends Csomopont {
    
    private Utszakasz utszakasz;
    private Savallapot allapot;
    private Jarmu jarmu;
    private List<Csomopont> szomszedok = new ArrayList<>();
    protected int sozott = 0;

    public Sav() {
        this.allapot = new Tiszta();
        this.szomszedok = new ArrayList<>();
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

    @Override
    public void frissit() {
        if (this.sozott > 0) {
            this.sozott--;
        }
        if (allapot != null) {
            allapot.frissit(this);
        }
    }

    @Override
    public boolean befogad(Jarmu jarmu) {
        if (this.foglalt()) {
            return false; 
        }
        this.jarmu = jarmu;
        return true; 
    }

    @Override
    public void elenged(Jarmu jarmu) {
        if (this.jarmu == jarmu) {
            this.jarmu = null;
        }
    }

    @Override
    public List<Csomopont> getNext() {
        return szomszedok;
    }
    
    // Szomszédos sáv lekérése a Söprőfej miatt
    public Sav getJobbSzomszed(Sav sav) {
        SkeletonLogger.enter(this, "getJobbSzomszed", sav);
        Sav szomszed = null;
        if (this.utszakasz != null) {
            szomszed = this.utszakasz.getJobbSzomszed(this); // Továbbadjuk a kérést az útszakasznak
        }
        SkeletonLogger.exit(szomszed);
        return szomszed;
    }

    public boolean lepesTeszt(Jarmu jarmu) {
        boolean teszt = false;
        if (allapot != null) {
            teszt = allapot.lepesTeszt(jarmu);
        }
        return teszt;
    }

    @Override
    public void balesetEseten() {
        if (this.jarmu != null) {
        }
    }

    @Override
    public boolean foglalt() {
        return (this.jarmu != null);
    }

   @Override
    public void hoesesEseten() {
        SkeletonLogger.enter(this, "hoesesEseten");
        if(sozott == 0){
            utszakasz.havazikRa(this);
        }else{
            sozott--;
        } 
        SkeletonLogger.exit("void");
    }

    public boolean jegTisztit() {
        SkeletonLogger.enter(this, "jegTisztit");
        boolean ret = false;
        if (allapot != null) {
            ret = allapot.jegTisztit(this);
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
        sozott = 3; 
        SkeletonLogger.exit("void");
    }
}