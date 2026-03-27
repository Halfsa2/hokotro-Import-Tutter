package halozat;
import jarmu.Jarmu;
import java.util.ArrayList;
import java.util.List;
import vezerles.SkeletonLogger;

/**
 * Olyan csomópont, ahol több útvonal találkozik, és elosztja a forgalmat.
 */
public class Keresztezodes extends Csomopont {

    private List<Csomopont> kimenetek;
    private List<Jarmu> bentLevoJarmuvek;

    public Keresztezodes() {
        this.kimenetek = new ArrayList<>();
        this.bentLevoJarmuvek = new ArrayList<>();
    }

    public void addKimenet(Csomopont csp) {
        this.kimenetek.add(csp);
    }

    // JAVÍTVA: void helyett boolean visszatérés
    @Override
    public boolean befogad(Jarmu jarmu) {
        this.bentLevoJarmuvek.add(jarmu); 
        return true; 
    }

    @Override
    public void elenged(Jarmu jarmu) {
        this.bentLevoJarmuvek.remove(jarmu); 
    }

    @Override
    public void frissit() {
        // A szimulációs idő múlására reagáló kód
        SkeletonLogger.enter(this, "frissit");
        SkeletonLogger.exit("void");
    }

    @Override
    public List<Csomopont> getNext() {
        return this.kimenetek;
    }

    @Override
    public void balesetEseten() {
        // Kereszteződésbeli baleset logikája
    }

    @Override
    public boolean foglalt() {
        // Visszaadja, hogy van-e benne jármű
        return !this.bentLevoJarmuvek.isEmpty();
    }

    // JAVÍTVA: Hiányzó hoesesEseten() metódus pótlása
    @Override
    public void hoesesEseten() {
    }
}