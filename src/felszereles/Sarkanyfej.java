package felszereles;
import halozat.Sav;
import vezerles.SkeletonLogger; // <-- IMPORT HOZZÁADVA

/**
 * Hő és kerozin felhasználásával azonnal elolvasztja a havat és a jeget.
 */
public class Sarkanyfej extends Kotrofej {

    protected int kerozin_mennyiseg;

    public Sarkanyfej(int kezdetiKerozin) {
        this.kerozin_mennyiseg = kezdetiKerozin;
    }

    @Override
    public boolean takarit(Sav s) {
        SkeletonLogger.enter(this, "takarit", s);
        
        if (kerozin_mennyiseg > 0) {
            // Üzemanyag felhasználásával azonnali olvasztást végez.
            boolean hoEltakaritva = s.hoTisztit();
            boolean jegEltakaritva = s.jegTisztit();
            
            kerozin_mennyiseg--;
            
            boolean siker = hoEltakaritva || jegEltakaritva;
            SkeletonLogger.exit(siker);
            return siker;
        }
        SkeletonLogger.exit(false);
        return false;
    }
}
