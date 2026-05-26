package halozat;

import jarmu.Jarmu;
import java.util.ArrayList;
import java.util.List;
import static prototipus.CommandInterpreter.reverseNevTar;
import vezerles.SkeletonLogger;

/**
 * A hálózat speciális, végpontként szolgáló csomópontja.
 */
public class Checkpoint extends Csomopont {
    /**
     * A checkpointból elérhető szomszédos csomópontok listája.
     */
    private List<Csomopont> szomszedok = new ArrayList<>();
    /**
     * A checkpointon jelenleg bent lévő (várakozó) járművek listája.
     */
    private List<Jarmu> varakozoJarmuvek = new ArrayList<>();

    public Checkpoint() {
        this.varakozoJarmuvek = new ArrayList<>();
    }

    /**
     * A paraméterként kapott 
     * kimenetet hozzáadja a szomszédok listájához.
     * @param kimenet A beállítani/hozzáadni kívánt kimeneti csomópont
     */
    public void setKimenet(Csomopont kimenet) {
        addSzomszed(kimenet);
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
        // Checkpoint állapotának aktualizálása (jelenleg nem csinál semmit)
    }

   @Override
    public List<Csomopont> getNext() {
        return this.szomszedok; // Most már több irányt is visszaad!
    }

    /**
     * Baleset esetére meghívott logika. Jelenleg üres implementáció.
     */
    @Override
    public void balesetEseten() {
        // Baleset esetén a Checkpointon... nem történik semmi különös, mivel ez egy végpont. Ez a metódus üres marad.
    }

    /**
     * Ellenőrzi, hogy a checkpoint foglalt-e.
     * @return true, ha van várakozó jármű; false különben
     */
    @Override
    public boolean foglalt() {
        // A checkpoint foglalt, ha van legalább egy jármű a várakozó listában
        return !varakozoJarmuvek.isEmpty();
    }
    @Override
    public String printStat(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("Checkpoint ").append(name).append(": jarmuvek=");
        for (Jarmu j : varakozoJarmuvek) {
            sb.append(reverseNevTar.get(j));
            if(j != varakozoJarmuvek.getLast()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
/**
     * Getter a bent lévő járművek listájához. 
     * Ezt például a MapPanel használhatja a járművek grafikus kirajzolásához.
     * @return A várakozó járművek listája
     */        
    public List<Jarmu> getJarmuvek() {
        return this.varakozoJarmuvek;
    }

/**
     * Hozzáad egy új szomszédos csomópontot a kimenetek listájához, 
     * ha az még nem szerepel benne (nem felülírja a meglévőket).
     * @param szomszed A hozzáadni kívánt szomszédos csomópont
     */
    public void addSzomszed(Csomopont szomszed) {
        if (!this.szomszedok.contains(szomszed)) {
            this.szomszedok.add(szomszed);
        }
    }
}