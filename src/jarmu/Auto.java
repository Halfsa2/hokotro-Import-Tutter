package jarmu;

import halozat.Checkpoint;
import halozat.Csomopont;
import static prototipus.CommandInterpreter.reverseNevTar;
import vezerles.SkeletonLogger;

/**
 * A városban közlekedő, alapszintű, önvezető jármű.
 */
public class Auto extends Jarmu {

    private final Checkpoint start; // Az autó kiindulási pozíciója (nem változik a játék során)
    private final Checkpoint cel; // Az autó célállomása (nem változik a játék során)

    public Auto(Checkpoint start, Checkpoint cel) {
        this.start = start;
        this.cel = cel;
    }

    /**
     * Ez a metódus felelős az autó tényleges mozgásáért.
     */
    @Override
    public boolean lep(Csomopont celCsomopont) {
        SkeletonLogger.enter(this, "lep", celCsomopont);
        if (varakozik > 0) {
            varakozik--;
            SkeletonLogger.exit(false);
            return false;
        }
        if (celCsomopont.befogad(this)) {
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            this.aktualisCsomopont = celCsomopont;
            SkeletonLogger.exit(true);
            return true;
        }
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