package jarmu;

import halozat.Checkpoint;
import halozat.Csomopont;
import vezerles.SkeletonLogger;

/**
 * A Sofőr által irányított jármű, amely fordulók megtételével pénzt keres.
 */
public class Busz extends IranyitottJarmu {
    private final Checkpoint start; // A busz kiindulási pozíciója
    private final Checkpoint cel; // A busz célállomása
    private boolean oda = true; // Jelzi, hogy a busz éppen a cél felé (oda) vagy vissza (vissza) tart-e

    public Busz(Checkpoint start, Checkpoint cel) {
        this.start = start;
        this.cel = cel;
    }

    /**
     * A jármű mozgatásához szükséges vezérlés, és a bevételek generálása.
     */
    @Override
    public boolean lep(Csomopont celCsomopont) {
        SkeletonLogger.enter(this, "lep", celCsomopont);
        // A busz csak akkor léphet, ha a célcsomópont befogadja
        // A busz pénzkeresése nem az ő, hanem a sofőr felelőssége
        if(celCsomopont.befogad(this)){
            //Csak a biztonság kedvéért
            if(aktualisCsomopont != null) {
                aktualisCsomopont.elenged(this);
            }
            aktualisCsomopont = celCsomopont;
            SkeletonLogger.exit(true);
            return true;
        }else{
            SkeletonLogger.exit(false);
            return false;
        }
    }
    public Checkpoint getStart() {
        return start;
    }
    public Checkpoint getCel() {
        return cel;
    }
    public boolean celhozTart() {
        return oda;
    }
    public void iranytValtoztat() {
        oda = !oda;
    }
}