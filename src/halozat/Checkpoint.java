package halozat;

import jarmu.Jarmu;
import vezerles.SkeletonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * A hálózat speciális, végpontként szolgáló csomópontja.
 */
public class Checkpoint extends Csomopont {

    private Csomopont kimenet;
    private List<Jarmu> varakozoJarmuvek;

    public Checkpoint() {
        this.varakozoJarmuvek = new ArrayList<>();
    }

    public void setKimenet(Csomopont kimenet) {
        this.kimenet = kimenet;
    }

    @Override
    public boolean befogad(Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", jarmu); // Ez írja ki a hívást és kezeli a behúzást
        this.varakozoJarmuvek.add(jarmu);
        SkeletonLogger.exit(true); // Ez írja ki a <- true-t és csökkenti a behúzást
        return true;
    }

    @Override
    public void elenged(Jarmu jarmu) {
        this.varakozoJarmuvek.remove(jarmu);
    }

    @Override
    public void frissit() {
        // Checkpoint állapotának aktualizálása
    }

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

    @Override
    public void balesetEseten() {
        // Baleset esetén a Checkpointon
    }

    @Override
    public boolean foglalt() {
        // A te logikád alapján a Checkpoint valószínűleg sosem "foglalt" olyan
        // értelemben,
        // hogy ne tudna több járművet fogadni (mivel listája van), de itt ezt
        // implementálhatod.
        return false;
    }

    // JAVÍTVA: Hiányzó hoesesEseten() metódus pótlása
    @Override
    public void hoesesEseten() {
    }
}