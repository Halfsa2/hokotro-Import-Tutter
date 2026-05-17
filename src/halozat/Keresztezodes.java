package halozat;

import jarmu.Jarmu;
import java.util.ArrayList;
import java.util.List;
import static prototipus.CommandInterpreter.reverseNevTar;
import vezerles.SkeletonLogger;

/**
 * Olyan csomópont, ahol több útvonal találkozik, és elosztja a forgalmat.
 * A kereszteződés képes egyszerre több járművet is befogadni és a megfelelő kimenet felé irányítani.
 */
public class Keresztezodes extends Csomopont {

    private List<Csomopont> kimenetek;
    private List<Jarmu> bentLevoJarmuvek;

    /**
     * Konstruktor, inicializálja a kimenetek és a járműlista tárolókat.
     */
    public Keresztezodes() {
        SkeletonLogger.create(this);
        this.kimenetek = new ArrayList<>();
        this.bentLevoJarmuvek = new ArrayList<>();
        SkeletonLogger.exit(this);
    }

    /**
     * Hozzáad egy kimeneti útvonalat a kereszteződéshez.
     * @param csp a kimenő csomópont
     */
    public void addKimenet(Csomopont csp) {
        SkeletonLogger.enter(this, "addKimenet", csp);
        if (!this.kimenetek.contains(csp)) {
            this.kimenetek.add(csp);
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Befogad egy járművet a kereszteződés területére.
     * @param jarmu a befogadni kívánt jármű
     * @return true, ha a jármű bekerült
     */
    @Override
    public boolean befogad(Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", jarmu);
        
        // Nincs foglalt() ellenőrzés (mint a Sávnál vagy a Checkpointnál), 
        // így a kereszteződés akármennyi járművet be tud fogadni egyszerre! (Teszt 42)
        this.bentLevoJarmuvek.add(jarmu);
        
        SkeletonLogger.exit(true);
        return true;
    }

    /**
     * Elengedi a kereszteződésben tartózkodó járművet.
     * @param jarmu a kiengedett jármű
     */
    @Override
    public void elenged(Jarmu jarmu) {
        SkeletonLogger.enter(this, "elenged", jarmu);
        this.bentLevoJarmuvek.remove(jarmu);
        SkeletonLogger.exit("void");
    }

    /**
     * Frissíti a kereszteződés állapotát a szimuláció lépéseihez.
     */
    @Override
    public void frissit() {
        SkeletonLogger.enter(this, "frissit");
        // Itt lehetne implementálni pl. a jelzőlámpák váltakozását a jövőben
        SkeletonLogger.exit("void");
    }

    /**
     * Visszaadja a kereszteződés következő lehetséges kimeneteit.
     * @return kimeneti csomópontok listája
     */
    @Override
    public List<Csomopont> getNext() {
        // A naplózást itt elhagyhatjuk, ha túl sokszor hívódik meg (pl. BFS futásakor)
        return this.kimenetek;
    }

    /**
     * Baleset esetén végrehajtandó logika a kereszteződésben.
     */
    @Override
    public void balesetEseten() {
        SkeletonLogger.enter(this, "balesetEseten");
        
        // Ha valaki balesetet okoz a kereszteződésben, az összes bent lévő jármű karambolozik
        for (Jarmu j : bentLevoJarmuvek) {
            j.balesetetSzenved();
        }
        
        SkeletonLogger.exit("void");
    }

    /**
     * Ellenőrzi, hogy foglalt-e a kereszteződés. 
     * @return mindig false, mivel a kereszteződés kapacitása végtelen
     */
    @Override
    public boolean foglalt() {
        SkeletonLogger.enter(this, "foglalt");
        // A kereszteződésnek végtelen a kapacitása, így sosem "foglalt" olyan értelemben,
        // hogy ne tudna új autót befogadni (Teszt 42).
        SkeletonLogger.exit(false);
        return false;
    }

    /**
     * Hőesés esetén szükséges beavatkozások kezdeményezése a csomóponton.
     */
    @Override
    public void hoesesEseten() {
        SkeletonLogger.enter(this, "hoesesEseten");
        // Nyitott kereszteződés esetén itt is történhet havazás, 
        // de jelenleg nincs az állapotokhoz hasonló bonyolult logikája.
        SkeletonLogger.exit("void");
    }
    @Override
    public String printStat(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("Keresztezodes ").append(name).append(": jarmuvek=");
        for (Jarmu j : bentLevoJarmuvek) {
            sb.append(reverseNevTar.get(j));
            if(j != bentLevoJarmuvek.getLast()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    // Getter a bent lévő járművek listájához, hogy a MapPanel meg tudja rajzolni
        public List<Jarmu> getJarmuvek() {
        return this.bentLevoJarmuvek;
    }
}