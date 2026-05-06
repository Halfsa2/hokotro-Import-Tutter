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
     * Só újratöltése vásárlás esetén.
     */
    @Override
    public void ujratolt(int mennyiseg) {
        this.so_mennyiseg += mennyiseg;
    }
    /**
     * Konstruktor a Soszoro osztályhoz.
     * @param kezdetiSo a kezdeti só mennyisége
     */
    public Soszoro(int kezdetiSo) {
        this.so_mennyiseg = kezdetiSo;
    }

    /**
     * Visszaadja a só aktuális mennyiségét.
     * (Hasznos lesz a Prototípus 'stat' parancsához, hogy lássuk mennyi só maradt)
     */
    public int getSoMennyiseg() {
        return this.so_mennyiseg;
    }

    @Override
    public int getToltet() {
        return this.getSoMennyiseg(); 
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
    @Override
    public String printStat(String name) {
        return "Soszoro " + name + ": so_mennyiseg=" + this.so_mennyiseg;
    }

    //CSAK A PROTOTÍPUS CSALÓ PARANCSÁNAK HASZNÁLATÁHOZ!
    public void setSoMennyiseg(int so_mennyiseg) {
        this.so_mennyiseg = so_mennyiseg;
    }
}
