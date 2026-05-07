package halozat;

import java.util.ArrayList;
import java.util.List;
import static prototipus.CommandInterpreter.reverseNevTar;
import prototipus.IStatable;
import vezerles.SkeletonLogger;

/**
 * Útszakaszt reprezentál, amely több párhuzamos sávból áll.
 * Kezeli a sávok közti szomszédsági viszonyokat és a hóesés továbbítását.
 */
public class Utszakasz implements IStatable {
    
    protected List<Sav> savok;

    /**
     * Konstruktor az Utszakasz osztályhoz.
     */
    public Utszakasz() {
        SkeletonLogger.create(this);
        this.savok = new ArrayList<>();
        SkeletonLogger.exit(this);
    }

    /**
     * Hozzáad egy új sávot az útszakaszhoz, és beállítja a sáv útszakasz referenciáját.
     * @param s a hozzáadandó sáv
     */
    public void addSav(Sav s) {
        SkeletonLogger.enter(this, "addSav", s);
        if (!this.savok.contains(s)) {
            this.savok.add(s);
            s.setUtszakasz(this);
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Visszaadja az útszakaszban lévő sávok listáját.
     * @return az útszakasz sávjai
     */
    public List<Sav> getSavok() {
        SkeletonLogger.enter(this, "getSavok");
        SkeletonLogger.exit("lista");
        return this.savok;
    }

    /**
     * Hóesés hatását alkalmazza a megadott sávon.
     * Ezt a metódust az Alagút osztály felül fogja definiálni (Teszt 38).
     * @param s a hóesés hatása alá kerülő sáv
     */
    public void havazikRa(Sav s) {
        SkeletonLogger.enter(this, "havazikRa", s);
        // Delegáljuk a hatást a sáv aktuális állapotának
        if (s.getAllapot() != null) {
            s.getAllapot().hoesesEseten(s); 
        }
        SkeletonLogger.exit("void");
    }
    
    /**
     * Visszaadja a vizsgált sávhoz képest a menetirány szerinti "külső" sávot.
     * Elengedhetetlen a Söprő fej helyes működéséhez (Teszt 52).
     */
    public Sav getJobbSzomszed(Sav sav){
        SkeletonLogger.enter(this, "getJobbSzomszed", sav); 
        
        int id = this.savok.indexOf(sav);
        
        // Ha valamiért nem találjuk, vagy nem 4 sávos az út, marad az alapértelmezett logika
        if (id == -1 || this.savok.size() != 4) {
            if (id != -1 && id < this.savok.size() - 1) {
                Sav szomszed = this.savok.get(id + 1);
                SkeletonLogger.exit(szomszed);
                return szomszed;
            }
            SkeletonLogger.exit(null);
            return null;
        }
        
        // --- 4 SÁVOS ÚT "KIFELÉ TOLÓ" LOGIKÁJA ---
        Sav szomszed = null;
        
        // Ha az 1-es belső sávon vagyunk, a hó a 0-ás külső sávra kerül
        if (id == 1) {
            szomszed = this.savok.get(0);
        }
        // Ha a 2-es belső sávon vagyunk (szemben), a hó a 3-as külső sávra kerül
        else if (id == 2) {
            szomszed = this.savok.get(3);
        }
        
        // (Ha a 0-ás vagy 3-as külső sávon vagyunk, a hó leesik az útról az árokba, így null marad)

        SkeletonLogger.exit(szomszed);
        return szomszed;
    }
    
    /**
     * Visszaadja a vizsgált sávtól balra lévő sávot.
     * @param sav a vizsgált sáv
     * @return a balra lévő sáv referenciája, vagy null
     */
    public Sav getBalSzomszed(Sav sav){
        SkeletonLogger.enter(this, "getBalSzomszed", sav); 
        
        int id = this.savok.indexOf(sav);
        
        // Ha nem a szélső bal oldali sáv (0. index)
        if (id > 0) {
            Sav szomszed = this.savok.get(id - 1);
            SkeletonLogger.exit(szomszed);
            return szomszed;
        }
        
        SkeletonLogger.exit(null);
        return null;
    }
    @Override
    public String printStat(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("Utszakasz ").append(name).append(": savok=");
        for (Sav s : this.savok) {
            sb.append(reverseNevTar.get(s));
            if(s != this.savok.getLast()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}