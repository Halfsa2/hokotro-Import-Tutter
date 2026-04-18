package felszereles;
import halozat.Sav;
import vezerles.SkeletonLogger;

/**
 * A jégtörő fej egy jeges állapotú sávot feltör, megszüntetve a csúszásveszélyt.
 */
public class Jegtoro extends Kotrofej {

    /**
     * Takarítja a jeget a jégtörő fejjel.
     * A jeget 1 réteg hóra módosítja.
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
}
