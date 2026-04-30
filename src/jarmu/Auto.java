package jarmu;

import halozat.Checkpoint;
import halozat.Csomopont;
import static prototipus.CommandInterpreter.reverseNevTar;
import java.util.List;
import vezerles.SkeletonLogger;

/**
 * A városban közlekedő, alapszintű, önvezető jármű.
 */
public class Auto extends Jarmu {

    private final Checkpoint start; // Az autó kiindulási pozíciója (nem változik a játék során)
    private final Checkpoint cel; // Az autó célállomása (nem változik a játék során)

    public Auto(Checkpoint start, Checkpoint cel) {
        SkeletonLogger.create(this);
        this.start = start;
        this.cel = cel;
        SkeletonLogger.exit(this);
    }

    /**
     * Ez a metódus felelős az autó tényleges mozgásáért.
     */
    @Override
    public boolean lep(Csomopont celCsomopont) {
        SkeletonLogger.enter(this, "lep", celCsomopont);
        
        // 1. Várakozás (büntetés) ellenőrzése baleset után (Teszt 53)
        if (this.varakozik > 0) {
            this.varakozik--; // A büntetés csökken minden próbálkozásnál/körnél
            SkeletonLogger.exit(false);
            return false;
        }
        
        // 2. Szomszédság validációja: Csak topológiailag elérhető szomszédra léphet
        if (this.aktualisCsomopont != null) {
            List<Csomopont> szomszedok = this.aktualisCsomopont.getNext();
            if (szomszedok == null || !szomszedok.contains(celCsomopont)) {
                SkeletonLogger.exit(false);
                return false;
            }
        }

        // 3. Befogadás megkísérlése az új csomóponton
        if (celCsomopont.befogad(this)) {
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            this.aktualisCsomopont = celCsomopont;
            SkeletonLogger.exit(true);
            return true;
        }
        
        // Ha a befogadás elutasítva (pl. foglalt a Checkpoint)
        SkeletonLogger.exit(false);
        return false;
    }
    
    public Checkpoint getStart(){
        return start;
    }
    
    public Checkpoint getCel(){
        return cel;
    }
   @Override
    public void printStat(String name) {
        System.out.print("Auto " + name + ": aktualisCsomopont=");
        if (this.aktualisCsomopont != null) {
            System.out.print(aktualisCsomopont != null? reverseNevTar.get(this.aktualisCsomopont) : "null");
        }
        System.out.print(", start="+ reverseNevTar.get(this.start));
        System.out.print(", cel="+ reverseNevTar.get(this.cel));
        System.out.print(", varakozik=" + varakozik);
        System.out.println();
    }
}