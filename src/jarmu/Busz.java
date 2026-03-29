package jarmu;

import halozat.Checkpoint;
import halozat.Csomopont;
import java.util.Scanner;
import vezerles.SkeletonLogger;

/**
 * A Sofőr által irányított jármű, amely fordulók megtételével pénzt keres.
 */
public class Busz extends IranyitottJarmu {
    private Checkpoint start; // A busz kiindulási pozíciója
    private Checkpoint cel; // A busz célállomása

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
        celCsomopont.getNext();
        System.out.print("\t[?] Foglalt a célállomás? (i/n): ");
        Scanner sc = new Scanner(System.in);
        String valasz = sc.nextLine();
        if (valasz.equalsIgnoreCase("n")) {
            if (celCsomopont.befogad(this)) {
                if (this.aktualisCsomopont != null) {
                    this.aktualisCsomopont.elenged(this);
                }
                this.aktualisCsomopont = celCsomopont;
                SkeletonLogger.exit(true);
                return true;
            } else {
                SkeletonLogger.exit(false);
                return false;
            }
        } else {
            SkeletonLogger.exit(false);
            return false;
        }
    }
}