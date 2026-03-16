package halozat;
import jarmu.Jarmu;
import java.util.ArrayList;
import java.util.List;
/**
 * A hálózat speciális, végpontként szolgáló csomópontja[cite: 504].
 */
public class Checkpoint extends Csomopont {

    private Csomopont kimenet; // Csak egyetlen irányba lehet továbbhaladni [cite: 830-831]
    private List<Jarmu> varakozoJarmuvek; // A még forgalomba nem állt járművek [cite: 821]

    public Checkpoint() {
        this.varakozoJarmuvek = new ArrayList<>();
    }

    public void setKimenet(Csomopont kimenet) {
        this.kimenet = kimenet;
    }

    @Override
    public void befogad(Jarmu jarmu) {
        this.varakozoJarmuvek.add(jarmu); // Adminisztrálja a megérkezést [cite: 833]
    }

    @Override
    public void elenged(Jarmu jarmu) {
        this.varakozoJarmuvek.remove(jarmu); // Regisztrálja a távozást [cite: 834]
    }

    @Override
    public void frissit() {
        // Checkpoint állapotának aktualizálása [cite: 835]
    }

    @Override
    public List<Csomopont> getNext() {
        List<Csomopont> lista = new ArrayList<>();
        if (kimenet != null) {
            lista.add(kimenet);
        }
        return lista;
    }

    @Override
    public void balesetEseten() { }

    @Override
    public boolean foglalt() {
        // Logikai értékkel tér vissza (igaz, ha tartózkodik rajta jármű) [cite: 836-837]
        return !this.varakozoJarmuvek.isEmpty();
    }
}
