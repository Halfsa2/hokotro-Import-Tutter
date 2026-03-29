package jarmu;

import halozat.Checkpoint;
import halozat.Csomopont;
import vezerles.SkeletonLogger;

/**
 * A városban közlekedő, alapszintű, önvezető jármű.
 */
public class Auto extends Jarmu {
    
    private Checkpoint start; // Az autó kiindulási pozíciója
    private Checkpoint cel;   // Az autó célállomása

    public Auto(Checkpoint start, Checkpoint cel) {
        SkeletonLogger.create(this);
        this.start = start;
        this.cel = cel;
        SkeletonLogger.exit(this);
    }

    /**
     * Ez a metódus felelős az autó tényleges mozgásáért.
     */
    @Override
    public boolean lep(Csomopont celCsomopont) {
        
        SkeletonLogger.enter(this, "lep", celCsomopont);
        // Ha az autó balesetet szenvedett, várakoznia kell
        if (varakozik > 0) {
            varakozik--;
            // LOGOLÁS KILÉPÉSKOR
            SkeletonLogger.exit(false);
            return false;
        }
        if (celCsomopont.befogad(this)) {
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            this.aktualisCsomopont = celCsomopont;
            SkeletonLogger.exit(true);
            return true; // A lépés megtörtént
        }
        SkeletonLogger.exit(false);
        return false;
    }
}