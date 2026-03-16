package felszereles;
import halozat.Sav;
/**
 * A jégtörő fej egy jeges állapotú sávot feltör, megszüntetve a csúszásveszélyt[cite: 1010].
 */
public class Jegtoro extends Kotrofej {

    @Override
    public boolean takarit(Sav s) {
        // A jégtörő specifikus művelete: a jeget 1 réteg hóra módosítja[cite: 1019].
        return s.jegTisztit(); // A Savallapot.jegTisztit() elvégzi az állapotváltást
    }
}
