package felszereles;

import halozat.Sav;
import vezerles.SkeletonLogger;

/**
 * A Zúzalékszóró fej apró kavicsokat szór a jégre.
 * Nem szünteti meg a jeget, de megakadályozza a járművek megcsúszását.
 */
public class ZuzalekSzoro extends Kotrofej {

    @Override
    public boolean takarit(Sav s) {
        SkeletonLogger.enter(this, "takarit", s);
        s.zuzalekSzoras(); // Zúzalék szórása a sávra
            
            SkeletonLogger.exit(true);
            return true;
    }
}