package jarmu;

import felszereles.Kotrofej;
import gazdasag.Takarito;
import halozat.Csomopont;
import halozat.Sav;
import java.util.HashMap;
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
     * @param tipusnev a fej típusának neve, amit le akar kérni
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
        // Nem szenved balesetet, így nem történik semmi. Ez a metódus üres marad.
        SkeletonLogger.exit("void");
    }

    @Override
    public boolean lep(Csomopont celCsomopont) {
        SkeletonLogger.enter(this, "lep", celCsomopont);
        
        if (celCsomopont.befogad(this)) {
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            this.aktualisCsomopont = celCsomopont;
            // Ez itt nem szép megoldás, de realisztikusan nem fog változni már a követelmény, a flexibilitás biztosítása pedig
            // nagyobb erőfeszítéseket igényelne, mint amennyit érne, így marad ez a megoldás.
            if (aktualisCsomopont instanceof Sav sav) {
                // Ha sikerült kitakarítani a sávot, akkor a tulajdonos pénzt keres.
                if(this.takarit(sav)) tulajdonos.keres(5);
            }
            SkeletonLogger.exit(true);
            return true;
        }
        
        SkeletonLogger.exit(false);
        return false;
    }
    
    @Override
    public boolean lephetMelyHora() {
        return true; // A hókotró képes mély hóban is közlekedni
    }
}