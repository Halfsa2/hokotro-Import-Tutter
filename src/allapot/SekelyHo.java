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

    /**
     * Konstruktor a SekelyHo osztályhoz.
     */
    public SekelyHo() {
        SkeletonLogger.create(this);
        SkeletonLogger.exit(this);
    }

    /**
     * Beállítja a hórétegek számát.
     * @param horeteg az új hórétegek száma
     */
    public void setHoreteg(int horeteg) {
        this.horeteg = horeteg;
    }

    /**
     * Ellenőrzi, hogy a jármű befogadható-e a sekély hó sávba.
     * Növeli a nyomvonalat, és ha az eléri a 3-at, jeges állapotba vált.
     */
    @Override
    public boolean befogad(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", sav, jarmu);
        
        this.nyomvonal++;
        if (this.nyomvonal >= 3) {
            sav.setAllapot(new Jeges());
        }
        
        SkeletonLogger.exit(true);
        return true;
    }

    /**
     * Elengedi a járművet a sekély hó sávból.
     * Növeli a nyomvonalat, és ha az eléri a 3-at, jeges állapotba vált.
     */
    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "elenged", sav, jarmu);
        
        this.nyomvonal++;
        if (this.nyomvonal >= 3) {
            sav.setAllapot(new Jeges());
        }
        
        SkeletonLogger.exit("void");
    }

    /**
     * Kezeli a hóesés esetét a sekély hó sávon.
     * Növeli a hóréteget, és ha eléri a 3-at, mély hó állapotba vált.
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

    /**
     * Frissíti a sekély hó sáv állapotát (pl. olvadás).
     * Csökkenti a hóréteget, és ha elfogy, tiszta állapotba vált.
     */
    @Override
    public void frissit(Sav sav) {
        SkeletonLogger.enter(this, "frissit", sav);
        
        this.horeteg--;
        if (this.horeteg <= 0) {
            sav.setAllapot(new Tiszta());
        }
        
        SkeletonLogger.exit("void");
    }

    /**
     * Teszteli, hogy a jármű ráléphet-e a sekély hó sávra.
     * Sekély hóban még minden jármű tud közlekedni.
     */
    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        SkeletonLogger.enter(this, "lepesTeszt", jarmu);
        SkeletonLogger.exit(true);
        return true; 
    }

    /**
     * Kezeli, ha a sáv sót kap.
     * A só csökkenti a hóréteget, ha pedig elfogy, tiszta lesz az út.
     */
    @Override
    public void sotKap(Sav sav) {
        SkeletonLogger.enter(this, "sotKap", sav);
        
        this.horeteg--;
        if (this.horeteg <= 0) {
            sav.setAllapot(new Tiszta());
        }
        
        SkeletonLogger.exit("void");
    }

    /**
     * Megpróbálja megtisztítani a havat a sávból.
     * Sikeresen tiszta állapotba vált.
     */
    @Override
    public boolean hoTisztit(Sav sav) {
        SkeletonLogger.enter(this, "hoTisztit", sav);

        sav.setAllapot(new Tiszta());

        SkeletonLogger.exit(true);
        return true;
    }

    /**
     * Megpróbálja megtisztítani a jeget a sávból.
     * Sekély hóban nincs jég, így nem sikerül.
     * @param sav a tisztítandó sáv
     * @param olvad jelzi, hogy a jeget törjük, vagy olvasztjuk
     * @return false, mivel nincs jég
     */
    @Override
    public boolean jegTisztit(Sav sav,Boolean olvad) {
        SkeletonLogger.enter(this, "jegTisztit", sav, olvad);
        SkeletonLogger.exit(false);
        return false;
    }
}