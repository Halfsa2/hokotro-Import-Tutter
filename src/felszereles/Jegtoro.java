package felszereles;

import halozat.Sav;
import vezerles.SkeletonLogger;

/**
 * A jégtörő fej egy jeges állapotú sávot feltör.
 * A jeget 1 réteg hóra (SekélyHó) módosítja, megszüntetve a csúszásveszélyt.
 */
public class Jegtoro extends Kotrofej {

    /**
     * Konstruktor a Jégtörő osztályhoz.
     */
    public Jegtoro() {
        SkeletonLogger.create(this);
        SkeletonLogger.exit(this);
    }

    /**
     * Takarítja a jeget a jégtörő fejjel.
     * @param s a tisztítandó sáv
     * @return true, ha sikerült megtisztítani, különben false
     */
    @Override
    public boolean takarit(Sav s) {
        SkeletonLogger.enter(this, "takarit", s);
        
        // A Savallapot.jegTisztit() elvégzi az állapotváltást.
        // A korábban megírt Jeges osztály alapján ez SekelyHo-t csinál az útból.
        boolean ret = s.jegTisztit(); 
        
        SkeletonLogger.exit(ret);
        return ret;
    }
}