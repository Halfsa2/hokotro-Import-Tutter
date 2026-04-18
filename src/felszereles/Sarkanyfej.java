package felszereles;
import halozat.Sav;
import vezerles.SkeletonLogger;

/**
 * Hő és kerozin felhasználásával azonnal elolvasztja a havat és a jeget.
 */
public class Sarkanyfej extends Kotrofej {

    /**
     * A rendelkezésre álló kerozin mennyisége.
     */
    protected int kerozin_mennyiseg;

    /**
     * Konstruktor a Sarkanyfej osztályhoz.
     * @param kezdetiKerozin a kezdeti kerozin mennyisége
     */
    public Sarkanyfej(int kezdetiKerozin) {
        this.kerozin_mennyiseg = kezdetiKerozin;
    }

    /**
     * Takarítja a havat és jeget a sárkányfejjel.
     * Üzemanyag felhasználásával azonnali olvasztást végez.
     * @param s a tisztítandó sáv
     * @return true, ha sikerült megtisztítani, különben false
     */
    @Override
    public boolean takarit(Sav s) {
        SkeletonLogger.enter(this, "takarit", s);
        
        if (kerozin_mennyiseg > 0) {
            // Üzemanyag felhasználásával azonnali olvasztást végez.
            boolean hoEltakaritva = s.hoTisztit();
            boolean jegEltakaritva = s.jegTisztit(true);
            
            kerozin_mennyiseg--;
            
            boolean siker = hoEltakaritva || jegEltakaritva;
            SkeletonLogger.exit(siker);
            return siker;
        }
        SkeletonLogger.exit(false);
        return false;
    }
}
