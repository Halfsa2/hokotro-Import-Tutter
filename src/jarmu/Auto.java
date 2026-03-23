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
        System.out.println("> auto:Auto.lep(celCsomopont)");
        
        // Ha az autó balesetet szenvedett, várakoznia kell
        if (varakozik > 0) {
            varakozik--;
            System.out.println("<- false (varakozik)");
            return false;
        }

        // --- "TELL, DON'T ASK" LOGIKA ---
        // Nem kérdezzük meg, hogy foglalt-e a csomópont. Egyszerűen megkérjük, hogy 
        // fogadja be az autót. A befogad() metódus true-t ad, ha sikerült a lépés.
        if (celCsomopont.befogad(this)) {
            
            // Mivel a befogadás SIKERES volt az új mezőre, most már szólhatunk 
            // a régi mezőnek (ha volt ilyen), hogy engedjen el minket.
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            
            // Frissítjük az autó pozícióját az újra
            this.aktualisCsomopont = celCsomopont;
            
            System.out.println("<- true (sikeres lepes)");
            return true; // A lépés megtörtént
        }

        // Ha a befogad() false-t adott (pl. mert már állt ott egy autó), a lépés sikertelen
        System.out.println("<- false (sikertelen lepes, foglalt vagy akadály)");
        return false;
    }
}