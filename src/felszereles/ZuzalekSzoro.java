package felszereles;

import halozat.Sav;
import vezerles.SkeletonLogger;

/**
 * A Zúzalékszóró fej apró kavicsokat szór a jégre.
 * Nem szünteti meg a jeget, de megakadályozza a járművek megcsúszását.
 */
public class ZuzalekSzoro extends Kotrofej {
    protected int zuzalek_mennyiseg;
    @Override
    public boolean takarit(Sav s) {
        SkeletonLogger.enter(this, "takarit", s);
        s.zuzalekSzoras(); // Zúzalék szórása a sávra
            
            SkeletonLogger.exit(true);
            return true;
    }

    @Override
    public void ujratolt(int mennyiseg) {
        this.zuzalek_mennyiseg += mennyiseg;
    }

    /**
     * Visszaadja a zúzalék aktuális mennyiségét.
     * (Hasznos lesz a Prototípus 'stat' parancsához, hogy lássuk mennyi zúzalék maradt)
     */
    public int getZuzalekMennyiseg() {
        return this.zuzalek_mennyiseg;
    }
}