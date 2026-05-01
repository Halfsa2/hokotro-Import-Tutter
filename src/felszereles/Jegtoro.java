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
        // A jégtörő specifikus művelete: a jeget 1 réteg hóra módosítja.
        boolean ret = s.jegTisztit(false); // A Savallapot.jegTisztit() elvégzi az állapotváltást
        
        SkeletonLogger.exit(ret);
        return ret;
    }
    @Override
    public String printStat(String name) {
        return "Jegtoro " + name;
    }
}