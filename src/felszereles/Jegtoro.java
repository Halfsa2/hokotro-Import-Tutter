package felszereles;
import halozat.Sav;
import vezerles.SkeletonLogger; // <-- IMPORT HOZZÁADVA

/**
 * A jégtörő fej egy jeges állapotú sávot feltör, megszüntetve a csúszásveszélyt.
 */
public class Jegtoro extends Kotrofej {

    @Override
    public boolean takarit(Sav s) {
        SkeletonLogger.enter(this, "takarit", s);
        // A jégtörő specifikus művelete: a jeget 1 réteg hóra módosítja.
        boolean ret = s.jegTisztit(); // A Savallapot.jegTisztit() elvégzi az állapotváltást
        
        SkeletonLogger.exit(ret);
        return ret;
    }
}
