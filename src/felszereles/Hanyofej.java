package felszereles;

import halozat.Sav;
import vezerles.SkeletonLogger;

/**
 * A hányófej a havat távolra szórja, így nem növeli a szomszédos sáv hómennyiségét.
 * Jeget ez sem távolít el.
 */
public class Hanyofej extends Kotrofej {

    /**
     * Konstruktor a Hányófej osztályhoz.
     */
    public Hanyofej() {
        SkeletonLogger.create(this);
        SkeletonLogger.exit(this);
    }

    /**
     * Takarítja a havat a hányófejjel.
     * A hó eltűnik a hálózatról, nem kerül a szomszédos sávra.
     * @param s a tisztítandó sáv
     * @return true, ha sikerült megtisztítani, különben false
     */
    @Override
    public boolean takarit(Sav s) {
        SkeletonLogger.enter(this, "takarit", s);
        
        // A hó eltűnik a hálózatról, az állapot (pl. SekelyHo) lecserélődik Tisztára.
        boolean ret = s.hoTisztit(); 
        s.zuzalekTisztit();
        SkeletonLogger.exit(ret);
        return ret;
    }
    @Override
    public void printStat(String name) {
        System.out.println("Hanyofej "+ name);
    }
}