package jarmu;
import halozat.Csomopont;
import halozat.Checkpoint;
/**
 * A Sofőr által irányított jármű, amely fordulók megtételével pénzt keres .
 */
public class Busz extends IranyitottJarmu {
    
    private Checkpoint start; // A busz kiindulási pozíciója [cite: 810]
    private Checkpoint cel;   // A busz célállomása [cite: 813]

    public Busz(Checkpoint start, Checkpoint cel) {
        this.start = start;
        this.cel = cel;
    }

    /**
     * A jármű mozgatásához szükséges vezérlés, és a bevételek generálása [cite: 816-817].
     */
    @Override
    public boolean lep(Csomopont celCsomopont) {
        if (varakozik > 0) {
            varakozik--;
            return false;
        }

        if (!celCsomopont.foglalt()) {
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            celCsomopont.befogad(this);
            this.aktualisCsomopont = celCsomopont;
            
            // Ha elérte a célállomást, a Sofőr pénzt keres (ez a logika összekötendő a Játékossal) [cite: 804]
            if (this.aktualisCsomopont == this.cel) {
                // Sikeres forduló - itt hívódik majd meg a Jatekos/KozosKassza bevétel generálása
            }
            return true;
        }
        return false;
    }
}
