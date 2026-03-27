package allapot;
import halozat.Sav;
import jarmu.Jarmu;

/**
 * A State (Állapot) tervezési minta egyik konkrét állapot-megvalósítása.
 * Egy sáv (Sav) viselkedését definiálja lefagyott útfelület esetén.
 */
public class Jeges extends Savallapot {

    // A jeges állapot saját maga is nyilvántart egy ilyen értéket,
    // ami azt mutatja, hogy a jeges sáv mennyire sózott.
    public int sozott = 0; 

    @Override
    public void befogad(Sav sav, Jarmu jarmu) {
        // Lekezeli a jármű rálépését a jeges sávra.
        // A jármű megcsúszik és balesetet szenved a jégen.
        jarmu.balesetetSzenved();
    }

    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
        // Adminisztrálja a jármű kilépését a jeges sávról.
    }

    @Override
    public void hoesesEseten(Sav sav) {
        // Reagál arra az eseményre, ha a jeges útszakaszon havazni kezd.
        // Itt például vastagodhat a jégen lévő hóréteg, átválthat egy speciális havas-jeges állapotba.
    }

    @Override
    public void frissit(Sav sav) {
        // Kezelheti például a sózás hatására történő olvadás folyamatát, 
        // csökkentve a sozott értéket.
        if (sozott > 0) {
            sozott--;
            if (sozott == 0) {
                // Ha a só kifejtette a hatását (3 kör után a dokumentáció korábbi része alapján), 
                // a jég elolvad.
                sav.setAllapot(new Tiszta());
            }
        }
    }

    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        // Eldönti, hogy a paraméterben kapott jármű rá tud-e lépni a jégre.
        return true; // Ráléphet, de utána balesetet fog szenvedni a befogad() metódusban.
    }

    @Override
    public void sotKap(Sav sav) {
        // Módosítja a sozott attribútum értékét.
        this.sozott = 3; // A jég 3 kör után olvad el a só hatására
    }

    @Override
    public boolean hoTisztit(Sav sav) {
        // Mivel jeges állapotban nincs hó, így nincs hatása.
        return false;
    }

    @Override
    public boolean jegTisztit(Sav sav) {
        // Eltakarítja a sávról a jeget és átváltja a sáv állapotát Tiszta állapotra.
        sav.setAllapot(new Tiszta());
        return true;
    }
}
