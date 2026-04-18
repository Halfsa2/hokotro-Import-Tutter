package jarmu;

import halozat.Checkpoint;
import halozat.Csomopont;
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
        
        // 1. Megpróbálunk belépni a cél csomópontba. 
        // A befogad() metódus belül magától ellenőrzi a foglaltságot és az állapotokat.
        if (celCsomopont.befogad(this)) {
            
            // 2. Ha sikerült a belépés, elengedjük a korábbi csomópontot (ha volt)
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            
            // 3. Frissítjük a busz saját pozícióját
            this.aktualisCsomopont = celCsomopont;
            
            // Megjegyzés: Ha Checkpoint-ra értünk, a pénz jóváírását a 
            // játékvezérlő (VarosModell) fogja majd lekezelni azzal, hogy 
            // ellenőrzi a lépés sikerességét és a cél csomópont típusát.

            SkeletonLogger.exit(true);
            return true;
        } 
        
        // Ha a befogadás elutasításra került (pl. mert foglalt a csomópont)
        SkeletonLogger.exit(false);
        return false;
    }
    
    // (Opcionális) Getterek a start és cel pontokhoz, ha a Játékvezérlőnek 
    // vizsgálnia kell, hogy a busz tényleg a céljába ért-e:
    public Checkpoint getStart() {
        return start;
    }

    public Checkpoint getCel() {
        return cel;
    }
}