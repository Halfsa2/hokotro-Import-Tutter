package felszereles;
import halozat.Sav;
import vezerles.SkeletonLogger; // <-- IMPORT HOZZÁADVA

/**
 * A hányófej a havat távolra szórja, így nem növeli a szomszédos sáv hómennyiségét.
 * Jeget ez sem távolít el.
 */
public class Hanyofej extends Kotrofej {

    @Override
    public boolean takarit(Sav s) {
        SkeletonLogger.enter(this, "takarit", s);
        // A hó eltűnik a hálózatról, nem kerül a szomszédos sávra.
        boolean ret = s.hoTisztit(); 
        
        SkeletonLogger.exit(ret);
        return ret;
    }
}
