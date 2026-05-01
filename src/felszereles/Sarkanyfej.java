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
        SkeletonLogger.create(this);
        this.kerozin_mennyiseg = kezdetiKerozin;
        SkeletonLogger.exit(this);
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
        
        if (this.kerozin_mennyiseg > 0) {
            
            // 2. TISZTÍTÁS: Üzemanyag felhasználásával azonnali olvasztást végez.
            boolean hoEltakaritva = s.hoTisztit();
            boolean jegEltakaritva = s.jegTisztit(true);
            boolean tortentTakaritas = hoEltakaritva || jegEltakaritva;
            if(tortentTakaritas) {
                // Fogyasztjuk a kerozint
                this.kerozin_mennyiseg--;
            }
            
            SkeletonLogger.exit(tortentTakaritas);
            return tortentTakaritas;
        }
        
        // Ha nincs kerozin, nem történik semmi
        SkeletonLogger.exit(false);
        return false;
    }

    /**
     * Visszaadja a kerozin aktuális mennyiségét.
     * (Hasznos lesz a Prototípus 'stat' parancsához, hogy lássuk mennyi üzemanyag maradt)
     */
    public int getKerozinMennyiseg() {
        return this.kerozin_mennyiseg;
    }
    
    /**
     * Kerozin újratöltése vásárlás esetén.
     */
    @Override
    public void ujratolt(int mennyiseg) {
        this.kerozin_mennyiseg += mennyiseg;
    }
    @Override
    public String printStat(String name) {
        return "Sarkanyfej " + name + ": kerozin_mennyiseg=" + this.kerozin_mennyiseg;
    }

    //CSAK A PROTOTÍPUS CSALÓ PARANCSÁNAK HASZNÁLATÁHOZ!
    public void setKerozinMennyiseg(int kerozin_mennyiseg) {
        this.kerozin_mennyiseg = kerozin_mennyiseg;
    }
}