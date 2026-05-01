package jarmu;

import felszereles.Hanyofej;
import felszereles.Jegtoro;
import felszereles.Kotrofej;
import gazdasag.Takarito;
import halozat.Csomopont;
import halozat.Sav;
import java.util.HashMap;
import java.util.List;
import static prototipus.CommandInterpreter.reverseNevTar;
import vezerles.SkeletonLogger;

/**
 * A Takarító által vezérelt munkagép, amely a sávok tisztításáért felel.
 */
public class Hokotro extends IranyitottJarmu {
    
    private Takarito tulajdonos; 
    private Kotrofej aktiv; 
    private HashMap<String, Kotrofej> birtokolja; 

    public Hokotro(Takarito tulajdonos) {
        this.tulajdonos = tulajdonos;
        this.birtokolja = new HashMap<>();
        addFej(new Hanyofej()); // Alapértelmezett fej, minden hókotró rendelkezik vele
        addFej(new Jegtoro());
    }

    /**
     * Kicseréli az aktív fejet a hókotróban.
     * @param ujFej az új fej, amire át akar kapcsolni
     */
    public void cserelFej(Kotrofej ujFej) {
        SkeletonLogger.enter(this, "cserelFej", ujFej); // BELÉPÉS LOGOLÁSA
        Kotrofej sameTipus = this.birtokolja.get(ujFej.getClass().getSimpleName());
        if (sameTipus != null) {
            this.aktiv = sameTipus;
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Hozzáad egy további takarítófejet a hókotró készletéhez.
     * @param ujFej az új fej, amit hozzáad
     */
    public void addFej(Kotrofej ujFej) {
        SkeletonLogger.enter(this, "addFej", ujFej);
        if(this.birtokolja.containsKey(ujFej.getClass().getSimpleName())) {
            SkeletonLogger.exit("void");
            return; // Már van ilyen típusú fej, nem adunk hozzá újat
        }
        this.birtokolja.put(ujFej.getClass().getSimpleName(), ujFej);
        SkeletonLogger.exit("void");
    }
    
    /**
     * Getter, ami név alapján visszaad egy birtokolt fejet
     * @param tipusNev a fej típusának neve, amit le akar kérni
     */
    public Kotrofej getFej(String tipusNev) {
        return this.birtokolja.get(tipusNev);
    }

    /**
     * Takarítja a megadott sávot a jelenleg aktív takarítófejjel.
     * @param s a takarítandó sáv
     * @return true, ha sikerült a takarítás
     */
    public boolean takarit(Sav s) {
        SkeletonLogger.enter(this, "takarit", s);
        if (this.aktiv != null) {
            boolean siker = this.aktiv.takarit(s);
            SkeletonLogger.exit(siker);
            return siker;
        }
        SkeletonLogger.exit(false);
        return false;
    }

    /**
     * Kezeli, ha a hókotró balesetet szenved.
     */
    @Override
    public void balesetetSzenved() {
        SkeletonLogger.enter(this, "balesetetSzenved");
        // A hókotró nem szenved balesetet a jégen sem, így ez üres marad! (Teszt 46)
        SkeletonLogger.exit("void");
    }

    @Override
    public boolean lep(Csomopont celCsomopont) {
        SkeletonLogger.enter(this, "lep", celCsomopont);
        
        // 1. Várakozás (büntetés) ellenőrzése
        if (this.varakozik > 0) {
            this.varakozik--; // Eltelt egy próbálkozás / kör
            // Ideális esetben a hibaüzenetet a JatekVezerlo írja ki, de itt adjuk vissza a false-t
            SkeletonLogger.exit(false);
            return false;
        }

        // 2. Topológiai validáció: szomszédos-e a célcsomópont? (Kivéve az első lehelyezést)
        if (this.aktualisCsomopont != null) {
            List<Csomopont> szomszedok = this.aktualisCsomopont.getNext();
            if (szomszedok == null || !szomszedok.contains(celCsomopont)) {
                // Nincs kapcsolat!
                SkeletonLogger.exit(false);
                return false;
            }
        }

        // 3. Befogadás megkísérlése a célcsomóponton
        if (celCsomopont.befogad(this)) {
            // Ha volt korábbi sávunk, onnan kilépünk
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            // Frissítjük a pozíciót
            this.aktualisCsomopont = celCsomopont;
            
            // 4. Takarítás, ha sávra léptünk
            if (aktualisCsomopont instanceof Sav sav) {
                // Ha sikerült kitakarítani a sávot, akkor a tulajdonos pénzt keres.
                if(this.takarit(sav)) {
                    tulajdonos.keres(5);
                }
            }
            SkeletonLogger.exit(true);
            return true;
        }
        
        // Ha a befogad() false-t adott (pl. foglalt, vagy mély hó busznál), ide futunk ki
        SkeletonLogger.exit(false);
        return false;
    }
    
    @Override
    public boolean lephetMelyHora() {
        return true; // A hókotró képes mély hóban is közlekedni
    }
    @Override
    public String printStat(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hokotro ").append(name).append(": aktualisCsomopont=");
        if (this.aktualisCsomopont != null) {
            sb.append(aktualisCsomopont != null? reverseNevTar.get(this.aktualisCsomopont) : "null");
        }
        sb.append(", aktivFej=").append(aktiv != null ? aktiv.getClass().getSimpleName() : "null");
        return sb.toString();
    }
}