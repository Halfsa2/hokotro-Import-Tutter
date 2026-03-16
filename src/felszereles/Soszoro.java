package felszereles;
import halozat.Sav;
/**
 * Sót szór a sávra, amellyel feloldja a havat/jeget, és 9 körig védettséget ad[cite: 1219].
 */
public class Soszoro extends Kotrofej {

    // A rendelkezésre álló só mennyisége[cite: 1228].
    protected int so_mennyiseg; //

    public Soszoro(int kezdetiSo) {
        this.so_mennyiseg = kezdetiSo;
    }

    @Override
    public boolean takarit(Sav s) {
        if (so_mennyiseg > 0) {
            s.soSzoras(); // Sót juttat a sávra, ami beállítja a 9 körös védettséget [cite: 1230, 1231]
            so_mennyiseg--; // Fogyóeszköz csökkentése
            return true;
        }
        // Ha kifogyott a só, a fej használhatatlan[cite: 1220].
        return false;
    }
}
