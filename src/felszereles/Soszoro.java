package felszereles;
import halozat.Sav;
import vezerles.SkeletonLogger; // <-- IMPORT HOZZÁADVA

/**
 * Sót szór a sávra, amellyel feloldja a havat/jeget, és 9 körig védettséget ad.
 */
public class Soszoro extends Kotrofej {

    protected int so_mennyiseg;

    public Soszoro(int kezdetiSo) {
        this.so_mennyiseg = kezdetiSo;
    }

    @Override
    public boolean takarit(Sav s) {
        SkeletonLogger.enter(this, "takarit", s);
        
        if (so_mennyiseg > 0) {
            s.soSzoras(); // Sót juttat a sávra, ami 3 hóhullásig védettséget ad
            so_mennyiseg--; // Fogyóeszköz csökkentése
            
            SkeletonLogger.exit(true);
            return true;
        }
        // Ha kifogyott a só, a fej használhatatlan.
        SkeletonLogger.exit(false);
        return false;
    }
}
