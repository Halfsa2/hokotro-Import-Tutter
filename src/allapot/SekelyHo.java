package allapot;

import halozat.Sav;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

/**
 * A sekély hó állapotát reprezentáló osztály.
 * Ez az állapot azt jelzi, hogy a sávon van egy kevés hó, de még járható.
 */
public class SekelyHo extends Savallapot {

    /**
     * A hórétegek száma a sávon.
     */
    protected int horeteg = 1;

    /**
     * A járművek által hagyott nyomvonalak száma.
     */
    protected int nyomvonal = 0;
    
    private boolean zuzalekos = false; // Jelzi, hogy van-e zúzalék a sávon 

    public SekelyHo() {
        SkeletonLogger.create(this);
        SkeletonLogger.exit(this);
    }

    public void setHoreteg(int horeteg) {
        this.horeteg = horeteg;
    }

    /**
     * Ellenőrzi, hogy a jármű befogadható-e a sekély hó sávba.
     * Növeli a nyomvonalat (letaposás), és ha az eléri a 3-at, jeges állapotba vált (Teszt 39).
     */
    @Override
    public boolean befogad(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", sav, jarmu);
        
       
        
        SkeletonLogger.exit(true);
        return true;
    }

    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "elenged", sav, jarmu);
        if(!zuzalekos){
            this.nyomvonal++; // A jármű kerekei letapossák a havat
            if (this.nyomvonal >= 3) {
                sav.setAllapot(new Jeges()); // Jéggé tömörödik
            }
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Kezeli a hóesés esetét a sekély hó sávon.
     * Növeli a hóréteget, és ha eléri a 3-at, mély hó állapotba vált (Teszt 37).
     */
    @Override
    public void hoesesEseten(Sav sav) {
        SkeletonLogger.enter(this, "hoesesEseten", sav);
        
        this.horeteg++;
        if (this.horeteg >= 3) {
            sav.setAllapot(new MelyHo());
        }

        SkeletonLogger.exit("void");
    }

    @Override
    public void frissit(Sav sav) {
        SkeletonLogger.enter(this, "frissit", sav);
        // A napos idő szimulálása
        this.horeteg--;
        if (this.horeteg <= 0) {
            sav.setAllapot(new Tiszta());
        }
        
        SkeletonLogger.exit("void");
    }

    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        SkeletonLogger.enter(this, "lepesTeszt", jarmu);
        SkeletonLogger.exit(true);
        return true; 
    }

    @Override
    public void sotKap(Sav sav) {
        SkeletonLogger.enter(this, "sotKap", sav);
        SkeletonLogger.exit("void");
    }

    @Override
    public boolean hoTisztit(Sav sav) {
        SkeletonLogger.enter(this, "hoTisztit", sav);
        sav.setAllapot(new Tiszta());
        SkeletonLogger.exit(true);
        return true;
    }

    @Override
    public boolean jegTisztit(Sav sav, Boolean olvad) {
        SkeletonLogger.enter(this, "jegTisztit", sav, olvad);
        SkeletonLogger.exit(false);
        return false;
    }
    @Override
    public String printStat(String name) {
        return "SekelyHo " + name + ": horeteg=" + this.horeteg + ", nyomvonal=" + this.nyomvonal + ", zuzalekos=" + this.zuzalekos;
    }
    @Override
    public void zuzalekSzoras() {
        SkeletonLogger.enter(this, "zuzalekSzoras");
        this.zuzalekos = true; // A zúzalék szórása megvédi a sávot a letaposástól
        this.nyomvonal = 0; // A zúzalék szórása eltünteti a nyomvonalat
        SkeletonLogger.exit("void");
    }
    @Override
    public boolean zuzalekTisztit(Sav sav) {
        SkeletonLogger.enter(this, "zuzalekTisztit", sav);
        if(zuzalekos){
            zuzalekos = false; // A zúzalék eltávolítása lehetővé teszi a letaposást
            SkeletonLogger.exit(true);
            return true;
        }
        SkeletonLogger.exit(false);
        return false; // Nem volt zúzalék, így nem történt tisztítás
    }



    //CSAK A PROTOTÍPUS CSALÓ PARANCSÁNAK HASZNÁLATÁHOZ!
    public void setNyomvonal(int nyomvonal) {
        this.nyomvonal = nyomvonal;
    }
}