package halozat;

import jarmu.Jarmu;
import java.util.ArrayList;
import java.util.List;
import vezerles.SkeletonLogger;

/**
 * A hálózat speciális, végpontként szolgáló csomópontja.
 */
public class Checkpoint extends Csomopont {

    private Csomopont kimenet;
    private List<Jarmu> varakozoJarmuvek;

    public Checkpoint() {
        this.varakozoJarmuvek = new ArrayList<>();
    }

    /**
     * Beállítja a kimenő csomópontot a checkpointhoz.
     * @param kimenet a célként szolgáló csomópont
     */
    public void setKimenet(Csomopont kimenet) {
        this.kimenet = kimenet;
    }

    /**
     * Próbál befogadni egy járművet a checkpointra.
     * @param jarmu befogadni kívánt jármű
     * @return true, ha a járműt sikerült fogadni
     */
    @Override
    public boolean befogad(Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", jarmu); // Ez írja ki a hívást és kezeli a behúzást
        if (this.foglalt()) {
            SkeletonLogger.exit(false);
            return false;
        }
        this.varakozoJarmuvek.add(jarmu);
        SkeletonLogger.exit(true); // Ez írja ki a <- true-t és csökkenti a behúzást
        return true;
    }

    /**
     * Elengedi a checkpointon várakozó járművet.
     * @param jarmu elengedni kívánt jármű
     */
    @Override
    public void elenged(Jarmu jarmu) {
        this.varakozoJarmuvek.remove(jarmu);
    }

    /**
     * Frissíti a checkpoint állapotát (pl. sor feldolgozása).
     */
    @Override
    public void frissit() {
        // Checkpoint állapotának aktualizálása
    }

    /**
     * Visszaadja a checkpoint következő csomópontjait.
     * @return a kimeneti csomópontok listája
     */
    @Override
    public List<Csomopont> getNext() {
        SkeletonLogger.enter(this, "getNext"); // Logoljuk a hívást
        List<Csomopont> kimenetek = new ArrayList<>();
        if (kimenet != null) {
            kimenetek.add(kimenet);
        }
        SkeletonLogger.exit("lista");
        return kimenetek;
    }

    /**
     * Baleset esetére meghívott logika. Jelenleg üres implementáció.
     */
    @Override
    public void balesetEseten() {
        // Baleset esetén a Checkpointon
    }

    /**
     * Ellenőrzi, hogy a checkpoint foglalt-e.
     * @return true, ha van várakozó jármű; false különben
     */
    @Override
    public boolean foglalt() {
        return !varakozoJarmuvek.isEmpty();
    }

    /**
     * Hőesés esetén végrehajtandó logika a checkpointon.
     */
    @Override
    public void hoesesEseten() {
    }
}