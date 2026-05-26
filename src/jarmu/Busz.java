package jarmu;

import gazdasag.Sofor;
import halozat.Checkpoint;
import halozat.Csomopont;
import java.util.List;
import static prototipus.CommandInterpreter.reverseNevTar;
import vezerles.SkeletonLogger;

/**
 * A Sofőr által irányított jármű, amely fordulók megtételével pénzt keres.
 * Két végpont (Checkpoint) között ingázik, és minden sikeres út (oda vagy vissza) 
 * végén bevételt generál a járművet irányító játékosnak.
 */
public class Busz extends IranyitottJarmu {
    /** A busz sofőrje, aki irányítja a járművet és felelős a pénzkeresésért. */
    private final Sofor vezeto; 
    /** A busz kiindulási pozíciója. */
    private final Checkpoint start; 
    /** A busz célállomása. */
    private final Checkpoint cel;
    /** Jelzi, hogy a busz éppen a cél felé (true) vagy a start felé (false) tart-e. */
    private boolean oda = true; 

    /**
     * Konstruktor a Busz osztályhoz.
     * @param start A busz kiindulási ellenőrzőpontja
     * @param cel A busz célállomása
     * @param vezeto A járművet irányító és a jutalmakat gyűjtő sofőr
     */
    public Busz(Checkpoint start, Checkpoint cel, Sofor vezeto) {
        this.start = start;
        this.cel = cel;
        this.vezeto = vezeto;
    }

    /**
     * A jármű mozgatásához szükséges vezérlés, és a bevételek generálása.
     * Ellenőrzi a várakozást, a topológiai érvényességet, majd megpróbál a célcsomópontra lépni.
     * Ha eléri az aktuális úti célját, a sofőr jutalmat kap, és a busz irányt vált.
     * @param celCsomopont A csomópont, ahová a busz lépni szeretne
     * @return true, ha a lépés sikeres volt, egyébként false
     */
    @Override
    public boolean lep(Csomopont celCsomopont) {
        SkeletonLogger.enter(this, "lep", celCsomopont);
        
        // 1. Várakozás ellenőrzése (pl. baleset vagy elakadás miatt) [cite: 47-48, 247-248]
        // Ha a járműnek várakoznia kell, a számlálót csökkenti, a lépés meghiúsul[cite: 48].
        if (this.varakozik > 0) {
            this.varakozik--;
            SkeletonLogger.exit(false);
            return false;
        }

        // 2. Szomszédság validációja: Csak szomszédos csomópontra léphet 
        // Megvizsgálja, hogy a célcsomópont szerepel-e az érvényes kimenetek között.
        if (this.aktualisCsomopont != null) {
            List<Csomopont> szomszedok = this.aktualisCsomopont.getNext();
            if (szomszedok == null || !szomszedok.contains(celCsomopont)) {
                SkeletonLogger.exit(false);
                return false;
            }
        }

        // 3. Befogadás megkísérlése a célcsomóponton [cite: 93]
        if (celCsomopont.befogad(this)) {
            
            // 4. Előző hely elengedése [cite: 95]
            if (aktualisCsomopont != null) {
                aktualisCsomopont.elenged(this);
            }
            
            // 5. Pozíció frissítése [cite: 95]
            aktualisCsomopont = celCsomopont;
            
            // 6. Célállomások és jutalmazás kezelése [cite: 96]
            // Ha a célállomásra ért (cel) és éppen arrafelé tartott (oda == true) [cite: 97]
            if (aktualisCsomopont.equals(cel) && oda) {
                if (vezeto != null) {
                    vezeto.keres(100); // A sofőr 100 Zúzmara Tallér jutalmat kap [cite: 97, 1872]
                }
                iranytValtoztat(); // Irányt vált ("vissza") [cite: 97]
            }
            // Ha visszaért a kezdőállomásra (start) és visszafelé tartott (oda == false) [cite: 98]
            else if (aktualisCsomopont.equals(start) && !oda) {
                if (vezeto != null) {
                    vezeto.keres(100); // A sofőr a visszaútért is megkapja a 100 ZT jutalmat!
                }
                iranytValtoztat(); // Ismét irányt vált ("oda"), hogy új fordulót kezdjen
            }
            
            SkeletonLogger.exit(true);
            return true;
        } else {
            // Befogadás sikertelen (pl. foglalt csomópont vagy mély hó) [cite: 94, 1850, 1899]
            SkeletonLogger.exit(false);
            return false;
        }
    }

    /**
     * Lekérdezi a busz indulási (start) állomását.
     * @return A kiindulási Checkpoint
     */
    public Checkpoint getStart() {
        return start;
    }
    /**
     * Lekérdezi a busz célállomását.
     * @return A cél Checkpoint
     */
    public Checkpoint getCel() {
        return cel;
    }
    /**
     * Lekérdezi, hogy a busz éppen a célállomás felé tart-e.
     * @return true, ha a cél felé tart, false, ha vissza a start felé
     */
    public boolean celhozTart() {
        return oda;
    }
    /**
     * Megfordítja a busz haladási irányát (oda/vissza), amikor elér egy végpontot.
     */
    public void iranytValtoztat() {
        oda = !oda; // Megfordítja a busz haladási irányát [cite: 104]
    }
    @Override
    public String printStat(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("Busz ").append(name).append(": aktualisCsomopont=");
        if (this.aktualisCsomopont != null) {
            sb.append(aktualisCsomopont != null ? reverseNevTar.get(this.aktualisCsomopont) : "null");
        }
        sb.append(", start=").append(reverseNevTar.get(this.start));
        sb.append(", cel=").append(reverseNevTar.get(this.cel));
        sb.append(", vezeto=").append(vezeto != null ? reverseNevTar.get(vezeto) : "null");
        sb.append(", celhozTart=").append(celhozTart());
        sb.append(", varakozik=").append(varakozik);
        return sb.toString();
    }
}