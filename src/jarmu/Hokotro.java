package jarmu;

import felszereles.Kotrofej;
import gazdasag.Takarito;
import halozat.Csomopont;
import halozat.Sav;
import java.util.ArrayList;
import java.util.List;

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
        if (this.birtokolja.contains(ujFej)) {
            this.aktiv = ujFej;
        }
    }

    public void addFej(Kotrofej ujFej) {
        this.birtokolja.add(ujFej); 
    }

    public boolean takarit(Sav s) {
        if (this.aktiv != null) {
            boolean siker = this.aktiv.takarit(s);
            return siker;
        }
        return false;
    }

    @Override
    public void balesetetSzenved() {
    }

    @Override
    public boolean lep(Csomopont celCsomopont) {
        
        if (celCsomopont.befogad(this)) {
            
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            this.aktualisCsomopont = celCsomopont;
            
            if (celCsomopont instanceof Sav) {
                this.takarit((Sav) celCsomopont);
            }
            return true;
        }
        return false;
    }
}