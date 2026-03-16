package allapot;
import halozat.Sav;
import jarmu.Hokotro;
import jarmu.Jarmu;

/**
 * A legextrémebb téli útviszonyokat reprezentálja, ahol a normál forgalom teljesen megbénul .
 */
public class MelyHo extends Savallapot {

    @Override
    public void befogad(Sav sav, Jarmu jarmu) {
        // Lekezeli a jármű rálépését a mély hóval borított sávra.
        // Mivel ide normál járművek be sem hajthatnak, ez a munkagépek (hókotrók) 
        // belépését adminisztrálja [cite: 1079-1080].
    }

    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
        // Adminisztrálja a jármű sikeres áthaladását és kilépését a sávról[cite: 1081].
    }

    @Override
    public void hoesesEseten(Sav sav) {
        // Lekezeli a havazás eseményét. Mivel a sáv már "mély hó" állapotban van, 
        // marad a MelyHo állapotban [cite: 1082-1083].
    }

    @Override
    public void frissit(Sav sav) {
        // Nincs automatikus állapotváltozás takarítás nélkül[cite: 1084].
    }

    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        // Kulcsfontosságú függvény, amely eldönti, hogy a jármű rá tud-e lépni a sávra. 
        // A mély hó miatt ez egy sima Auto esetén hamis, míg egy Hokotro számára igaz [cite: 1085-1090].
        if (jarmu instanceof Hokotro) {
            return true;
        }
        return false; // Normál autók és buszok nem léphetnek rá.
    }

    @Override
    public void sotKap(Sav sav) {
        // Reagál a sószórásra. Elképzelhető, hogy a mély hó állapotában a só önmagában hatástalan, 
        // vagy csak nagyon lassan olvasztja fel a havat[cite: 1091].
    }

    @Override
    public boolean hoTisztit(Sav sav) {
        // Eltakarítja a sávról a havat és Tiszta állapotba váltja[cite: 1092].
        sav.setAllapot(new Tiszta());
        return true;
    }

    @Override
    public boolean jegTisztit(Sav sav) {
        // Mivel a sávon nincs jég MelyHo állapotban, így meghívásának nincs hatása[cite: 1093].
        return false;
    }
}
