package jarmu;

import gazdasag.Sofor;
import halozat.Checkpoint;
import halozat.Csomopont;
import static prototipus.CommandInterpreter.reverseNevTar;
import java.util.List;
import vezerles.SkeletonLogger;

/**
 * A Sofőr által irányított jármű, amely fordulók megtételével pénzt keres. [cite: 78]
 */
public class Busz extends IranyitottJarmu {
    private final Sofor vezeto; // A busz sofőrje, aki irányítja a járművet és felelős a pénzkeresésért [cite: 84]
    private final Checkpoint start; // A busz kiindulási pozíciója [cite: 86]
    private final Checkpoint cel; // A busz célállomása [cite: 87]
    private boolean oda = true; // Jelzi, hogy a busz éppen a cél felé (oda) vagy vissza (vissza) tart-e [cite: 88]

    public Busz(Checkpoint start, Checkpoint cel, Sofor vezeto) {
        this.start = start;
        this.cel = cel;
        this.vezeto = vezeto;
    }

    /**
     * A jármű mozgatásához szükséges vezérlés, és a bevételek generálása. [cite: 92]
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
                iranytValtoztat(); // Ismét irányt vált ("oda"), hogy új fordulót kezdjen [cite: 98]
            }
            
            SkeletonLogger.exit(true);
            return true;
        } else {
            // Befogadás sikertelen (pl. foglalt csomópont vagy mély hó) [cite: 94, 1850, 1899]
            SkeletonLogger.exit(false);
            return false;
        }
    }

    public Checkpoint getStart() {
        return start;
    }

    public Checkpoint getCel() {
        return cel;
    }

    public boolean celhozTart() {
        return oda;
    }

    public void iranytValtoztat() {
        oda = !oda; // Megfordítja a busz haladási irányát [cite: 104]
    }
    @Override
    public void printStat(String name) {
        System.out.print("Busz " + name + ": aktualisCsomopont=");
        if (this.aktualisCsomopont != null) {
            System.out.print(aktualisCsomopont != null ? reverseNevTar.get(this.aktualisCsomopont) : "null");
        }
        System.out.print(", start="+ reverseNevTar.get(this.start));
        System.out.print(", cel="+ reverseNevTar.get(this.cel));
        System.out.print(", vezeto=" + (vezeto != null ? reverseNevTar.get(vezeto) : "null"));
        System.out.print(", celhozTart=" + celhozTart());
        System.out.print(", varakozik=" + varakozik);
        System.out.println();
    }
}