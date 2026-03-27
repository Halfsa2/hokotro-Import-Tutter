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
            return false; // Foglalt, elutasítjuk a lépést
        }
        this.jarmu = jarmu;
        return true; // Sikeres rálépés
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

    public boolean lepesTeszt(Jarmu jarmu) {
        boolean teszt = false;
        if (allapot != null) {
            teszt = allapot.lepesTeszt(jarmu);
        }
        return teszt;
    }

    @Override
    public void balesetEseten() {
        // Baleset logikája
        if (this.jarmu != null) {
        }
    }

    @Override
    public boolean foglalt() {
        boolean isFoglalt = (this.jarmu != null);
        return isFoglalt;
    }

    // DOUBLE DISPATCH INDÍTÁSA
    @Override
    public void hoesesEseten() {
        SkeletonLogger.enter(this, "hoesesEseten");
        if(sozott == 0){
            utszakasz.havazikRa(this);// Először az útvonalra hat a hó, ami továbbadja a hatást a sávállapotnak
        }else{
            sozott--;
        } 
        SkeletonLogger.exit("void");
    }

    public boolean jegTisztit() {
        boolean ret = (allapot != null) && allapot.jegTisztit(this);
        return ret;
    }

    public boolean hoTisztit() {
        boolean ret = (allapot != null) && allapot.hoTisztit(this);
        return ret;
    }

    public void soSzoras() {
        if (allapot != null) {
            allapot.sotKap(this);
        }
        sozott = 3; // Sózottság 3 hóhullásig tart [cite: 1191]
    }
}