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
        SkeletonLogger.enter(this, "lep", celCsomopont);
        //Kérdés a tesztelőhöz
        System.out.print("\t[?] Foglalt a célállomás? (i/n): ");
        Scanner sc = new Scanner(System.in);
        String valasz = sc.nextLine();       
        if (valasz.equalsIgnoreCase("n")) {
            celCsomopont.befogad(this);             // Ha nem foglalt, akkor a célállomás befogadja a buszt
            SkeletonLogger.exit(true);
            return true;
        } 
        else {
            // Ha foglalt, nem lépünk
            SkeletonLogger.exit(false);
            return false;
        }

        /* EZ MÉG A SZKELETONBA NEM KELL
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
        return false;*/
    }
}