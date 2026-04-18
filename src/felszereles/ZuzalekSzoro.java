package felszereles;

import halozat.Sav;
import allapot.Jeges;
import vezerles.SkeletonLogger;

/**
 * A Zúzalékszóró fej apró kavicsokat szór a jégre.
 * Nem szünteti meg a jeget, de megakadályozza a járművek megcsúszását.
 */
public class ZuzalekSzoro extends Kotrofej {

    @Override
    public boolean takarit(Sav s) {
        SkeletonLogger.enter(this, "takarit", s);
        
        // Csak akkor van értelme szórni, ha a sáv aktuális állapota Jeges
        if (s.getAllapot() instanceof Jeges) {
            Jeges jegesAllapot = (Jeges) s.getAllapot();
            jegesAllapot.zuzalekos = true;
            
            SkeletonLogger.exit(true);
            return true;
        }
        
        // Ha nem jeges a sáv, a zúzalék nem csinál semmit
        SkeletonLogger.exit(false);
        return false;
    }
}