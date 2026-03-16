package jarmu;
import halozat.Csomopont;
import halozat.Checkpoint;
/**
 * A városban közlekedő, alapszintű, önvezető jármű.
 */
public class Auto extends Jarmu {
    
    private Checkpoint start; // Az autó kiindulási pozíciója [cite: 771]
    private Checkpoint cel;   // Az autó célállomása [cite: 774]

    public Auto(Checkpoint start, Checkpoint cel) {
        this.start = start;
        this.cel = cel;
    }

    /**
     * Ez a metódus felelős az autó tényleges mozgásáért[cite: 781].
     */
    @Override
    public boolean lep(Csomopont celCsomopont) {
        // Ha az autó balesetet szenvedett, várakoznia kell [cite: 963]
        if (varakozik > 0) {
            varakozik--;
            return false;
        }

        // Ellenőrizzük, hogy a cél csomópontra rá lehet-e lépni (pl. nem foglalt, vagy a sáv járható)
        if (!celCsomopont.foglalt()) {
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this); // Elengedjük a régi mezőt [cite: 782]
            }
            celCsomopont.befogad(this); // Rálépünk az új mezőre [cite: 782]
            this.aktualisCsomopont = celCsomopont;
            return true; // Sikeres lépés [cite: 783]
        }
        
        // Ha foglalt vagy járhatatlan, az útvonalkereső vagy sávváltó logika lépne életbe
        return false; 
    }
}
