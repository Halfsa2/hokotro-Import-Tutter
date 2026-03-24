package jarmu;

import halozat.Checkpoint;
import halozat.Csomopont;

/**
 * A városban közlekedő, alapszintű, önvezető jármű.
 */
public class Auto extends Jarmu {
    
    private Checkpoint start; // Az autó kiindulási pozíciója
    private Checkpoint cel;   // Az autó célállomása

    public Auto(Checkpoint start, Checkpoint cel) {
        this.start = start;
        this.cel = cel;
    }

    /**
     * Ez a metódus felelős az autó tényleges mozgásáért.
     */
    @Override
    public boolean lep(Csomopont celCsomopont) {
        // Ha az autó balesetet szenvedett, várakoznia kell
        if (varakozik > 0) {
            varakozik--;
            return false;
        }
        if (celCsomopont.befogad(this)) {
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            this.aktualisCsomopont = celCsomopont;
            return true; // A lépés megtörtént
        }
        return false;
    }
}