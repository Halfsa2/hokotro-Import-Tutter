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
        System.out.println("> busz:Busz.lep(celCsomopont)");
        
        if (varakozik > 0) {
            varakozik--;
            System.out.println("<- false (varakozik)");
            return false;
        }

        // --- "TELL, DON'T ASK" LOGIKA ---
        if (celCsomopont.befogad(this)) {
            
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            this.aktualisCsomopont = celCsomopont;
            
            // Ha elérte a célállomást, a Sofőr pénzt keres 
            if (this.aktualisCsomopont == this.cel) {
                System.out.println("[!] Busz elérte a célállomást, forduló teljesítve!");
                // (Itt majd a KozosKassza-ba lehet pénzt tenni)
            }
            
            System.out.println("<- true (sikeres lepes)");
            return true;
        }

        System.out.println("<- false (sikertelen lepes)");
        return false;
    }
}