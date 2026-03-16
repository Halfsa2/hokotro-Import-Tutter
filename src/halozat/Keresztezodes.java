package halozat;
import jarmu.Jarmu;
import java.util.ArrayList;
import java.util.List;

/**
 * Olyan csomópont, ahol több útvonal találkozik, és elosztja a forgalmat [cite: 1022-1024].
 */
public class Keresztezodes extends Csomopont {

    // Tárolja, hogy a kereszteződésből mely további csomópontok felé lehet továbbhaladni [cite: 1028-1029].
    private List<Csomopont> kimenetek;
    private List<Jarmu> bentLevoJarmuvek;

    public Keresztezodes() {
        this.kimenetek = new ArrayList<>();
        this.bentLevoJarmuvek = new ArrayList<>();
    }

    public void addKimenet(Csomopont csp) {
        this.kimenetek.add(csp);
    }

    @Override
    public void befogad(Jarmu jarmu) {
        this.bentLevoJarmuvek.add(jarmu); // Regisztrálja a jelenlétét [cite: 1031]
    }

    @Override
    public void elenged(Jarmu jarmu) {
        this.bentLevoJarmuvek.remove(jarmu); // Felszabadítja a helyet [cite: 1032-1033]
    }

    @Override
    public void frissit() {
        // A szimulációs idő múlására reagáló metódus [cite: 1036-1037]
    }

    @Override
    public List<Csomopont> getNext() {
        return this.kimenetek; // Visszaadja a választható irányokat [cite: 1041-1042]
    }

    @Override
    public void balesetEseten() {
        // Kereszteződés specifikus balesetkezelés
    }

    @Override
    public boolean foglalt() {
        // Logika a foglaltság eldöntésére (pl. van-e benne olyan jármű, ami blokkolja)
        return !this.bentLevoJarmuvek.isEmpty();
    }
}
