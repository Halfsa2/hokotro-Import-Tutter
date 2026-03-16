package felszereles;
import halozat.Sav;
/**
 * A hányófej a havat távolra szórja, így nem növeli a szomszédos sáv hómennyiségét[cite: 532].
 * Jeget ez sem távolít el[cite: 533].
 */
public class Hanyofej extends Kotrofej {

    @Override
    public boolean takarit(Sav s) {
        // A hó eltűnik a hálózatról, nem kerül a szomszédos sávra [cite: 863-864].
        return s.hoTisztit(); //
    }
}
