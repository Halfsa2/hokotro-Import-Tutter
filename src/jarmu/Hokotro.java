package jarmu;

import felszereles.Kotrofej;
import gazdasag.Takarito;
import halozat.Csomopont;
import halozat.Sav;
import java.util.ArrayList;
import java.util.List;
import vezerles.SkeletonLogger;

/**
 * A Takarító által vezérelt munkagép, amely a sávok tisztításáért felel.
 */
public class Hokotro extends IranyitottJarmu {
    
    private Takarito tulajdonos; 
    private Kotrofej aktiv; 
    private List<Kotrofej> birtokolja; 

    public Hokotro(Takarito tulajdonos) {
        this.tulajdonos = tulajdonos;
        this.birtokolja = new ArrayList<>();
    }

    public void cserelFej(Kotrofej ujFej) {
        SkeletonLogger.enter(this, "cserelFej", ujFej); // BELÉPÉS LOGOLÁSA
        if (this.birtokolja.contains(ujFej)) {
            this.aktiv = ujFej;
        }
        SkeletonLogger.exit("void");
    }

    public void addFej(Kotrofej ujFej) {
        SkeletonLogger.enter(this, "addFej", ujFej);
        this.birtokolja.add(ujFej); 
        SkeletonLogger.exit("void");
    }

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

    @Override
    public void balesetetSzenved() {
        SkeletonLogger.enter(this, "balesetetSzenved");
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
            
            if (celCsomopont instanceof Sav) {
                this.takarit((Sav) celCsomopont);
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