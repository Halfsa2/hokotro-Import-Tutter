package felszereles;
import halozat.Sav;
import vezerles.SkeletonLogger;

/**
 * Sót szór a sávra, amellyel feloldja a havat/jeget, és 9 körig védettséget ad.
 */
public class Soszoro extends Kotrofej {

    /**
     * A rendelkezésre álló só mennyisége.
     */
    protected int so_mennyiseg;

    /**
     * Konstruktor a Soszoro osztályhoz.
     * @param kezdetiSo a kezdeti só mennyisége
     */
    public Soszoro(int kezdetiSo) {
        this.so_mennyiseg = kezdetiSo;
    }

    /**
     * Takarítja a havat és jeget a sószóró fejjel.
     * Sót szór a sávra, ami feloldja a havat/jeget és védettséget ad.
     * @param s a tisztítandó sáv
     * @return true, ha sikerült, különben false
     */
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
