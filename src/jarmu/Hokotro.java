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
        System.out.println("> hokotro:Hokotro.cserelFej(ujFej)");
        if (this.birtokolja.contains(ujFej)) {
            this.aktiv = ujFej;
        }
        System.out.println("<- void");
    }

    public void addFej(Kotrofej ujFej) {
        this.birtokolja.add(ujFej); 
    }

    public boolean takarit(Sav s) {
        System.out.println("> hokotro:Hokotro.takarit(s)");
        if (this.aktiv != null) {
            boolean siker = this.aktiv.takarit(s);
            System.out.println("<- " + siker);
            return siker;
        }
        System.out.println("<- false");
        return false;
    }

    @Override
    public void balesetetSzenved() {
        System.out.println("> hokotro:Hokotro.balesetetSzenved()");
        // A Hókotró "elpusztíthatatlan", nem áll meg baleset esetén 
        System.out.println("<- void");
    }

    @Override
    public boolean lep(Csomopont celCsomopont) {
        System.out.println("> hokotro:Hokotro.lep(celCsomopont)");
        
        // --- "TELL, DON'T ASK" LOGIKA ---
        if (celCsomopont.befogad(this)) {
            
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            this.aktualisCsomopont = celCsomopont;
            
            // Ha a hókotró sávra lép, automatikusan megpróbálja letakarítani azt
            if (celCsomopont instanceof Sav) {
                this.takarit((Sav) celCsomopont);
            }
            
            System.out.println("<- true (sikeres lepes)");
            return true;
        }

        System.out.println("<- false (sikertelen lepes)");
        return false;
    }
}