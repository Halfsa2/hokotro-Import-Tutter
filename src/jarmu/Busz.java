package jarmu;

import halozat.Checkpoint;
import halozat.Csomopont;

/**
 * A Sofőr által irányított jármű, amely fordulók megtételével pénzt keres.
 */
public class Busz extends IranyitottJarmu {
    
    private Checkpoint start; // A busz kiindulási pozíciója
    private Checkpoint cel;   // A busz célállomása

    public Busz(Checkpoint start, Checkpoint cel) {
        this.start = start;
        this.cel = cel;
    }

    /**
     * A jármű mozgatásához szükséges vezérlés, és a bevételek generálása.
     */
    @Override
    public boolean lep(Csomopont celCsomopont) {
        
        if (varakozik > 0) {
            varakozik--;
            return false;
        }

        if (celCsomopont.befogad(this)) {
            
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            this.aktualisCsomopont = celCsomopont;
            
            // Ha elérte a célállomást, a Sofőr pénzt keres 
            if (this.aktualisCsomopont == this.cel) {
                // (Itt majd a KozosKassza-ba lehet pénzt tenni)
            }
            return true;
        }
        return false;
    }
}