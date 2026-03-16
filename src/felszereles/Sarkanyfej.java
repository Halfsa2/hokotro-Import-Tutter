package felszereles;
import halozat.Sav;
/**
 * Hő és kerozin felhasználásával azonnal elolvasztja a havat és a jeget[cite: 1097].
 */
public class Sarkanyfej extends Kotrofej {

    // A tartályban lévő kerozin mennyisége[cite: 1106].
    protected int kerozin_mennyiseg; //

    public Sarkanyfej(int kezdetiKerozin) {
        this.kerozin_mennyiseg = kezdetiKerozin;
    }

    @Override
    public boolean takarit(Sav s) {
        if (kerozin_mennyiseg > 0) {
            // Üzemanyag felhasználásával azonnali olvasztást végez[cite: 1109].
            boolean hoEltakaritva = s.hoTisztit();
            boolean jegEltakaritva = s.jegTisztit();
            
            kerozin_mennyiseg--; // Fogyóeszköz csökkentése
            
            return hoEltakaritva || jegEltakaritva;
        }
        // Kerozin nélkül nem használható[cite: 1098].
        return false;
    }
}
