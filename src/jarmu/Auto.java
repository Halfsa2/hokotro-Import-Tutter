package jarmu;

import halozat.Checkpoint;
import halozat.Csomopont;
import vezerles.SkeletonLogger;

/**
 * A városban közlekedő, alapszintű, önvezető jármű.
 */
public class Auto extends Jarmu {

    private Checkpoint start; // Az autó kiindulási pozíciója
    private Checkpoint cel; // Az autó célállomása

    public Auto(Checkpoint start, Checkpoint cel) {
        this.start = start;
        this.cel = cel;
    }

    /**
     * Ez a metódus felelős az autó tényleges mozgásáért.
     */
    @Override
    public boolean lep(Csomopont celCsomopont) {
        SkeletonLogger.enter(this, "lep", celCsomopont);
        if (varakozik > 0) {
            varakozik--;
            SkeletonLogger.exit(false);
            return false;
        }
        if (celCsomopont.befogad(this)) {
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            this.aktualisCsomopont = celCsomopont;
            SkeletonLogger.exit(true);
            return true;
        }
        SkeletonLogger.exit(false);
        return false;
    }
    /**
     * Dönt arról, hogy az autó képes-e mély hóban haladni.
     * @return true, ha képes mély hóban haladni
     */
    @Override
    public boolean lephetMelyHora() {
        return true; // Az autó képes mélyhórában is közlekedni
    }
}