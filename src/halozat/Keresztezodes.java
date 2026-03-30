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

    /**
     * Konstruktor, inicializálja a kimenetek és a járműlista tárolókat.
     */
    public Keresztezodes() {
        this.kimenetek = new ArrayList<>();
        this.bentLevoJarmuvek = new ArrayList<>();
    }

    /**
     * Hozzáad egy kimeneti útvonalat a kereszteződéshez.
     * @param csp a kimenő csomópont
     */
    public void addKimenet(Csomopont csp) {
        this.kimenetek.add(csp);
    }

    /**
     * Befogad egy járművet a kereszteződés területére.
     * @param jarmu a befogadni kívánt jármű
     * @return true, ha a jármű bekerült
     */
    @Override
    public boolean befogad(Jarmu jarmu) {
        this.bentLevoJarmuvek.add(jarmu);
        return true;
    }

    /**
     * Elengedi a kereszteződésben tartózkodó járművet.
     * @param jarmu a kiengedett jármű
     */
    @Override
    public void elenged(Jarmu jarmu) {
        this.bentLevoJarmuvek.remove(jarmu);
    }

    /**
     * Frissíti a kereszteződés állapotát a szimuláció lépéseihez.
     */
    @Override
    public void frissit() {
        // A szimulációs idő múlására reagáló kód
        SkeletonLogger.enter(this, "frissit");
        SkeletonLogger.exit("void");
    }

    /**
     * Visszaadja a kereszteződés következő lehetséges kimeneteit.
     * @return kimeneti csomópontok listája
     */
    @Override
    public List<Csomopont> getNext() {
        return this.kimenetek;
    }

    /**
     * Baleset esetén végrehajtandó logika.
     */
    @Override
    public void balesetEseten() {
        // Kereszteződésbeli baleset logikája
    }

    /**
     * Ellenőrzi, hogy foglalt-e a kereszteződés.
     * @return true, ha vannak járművek a kereszteződésben
     */
    @Override
    public boolean foglalt() {
        return !this.bentLevoJarmuvek.isEmpty();
    }

    /**
     * Hőesés esetén szükséges beavatkozások kezdeményezése.
     */
    @Override
    public void hoesesEseten() {
        // Nincs speciális logika jelenleg
    }
}
