package gazdasag;
import jarmu.Hokotro;
import java.util.ArrayList;
import java.util.List;
/**
 * A városi úthálózat karbantartásáért és téli akadálymentesítéséért felelős játékos[cite: 1267].
 */
public class Takarito extends Jatekos {
    
    // A takarító birtokol és irányít egy vagy több hókotró járművet[cite: 1274].
    private List<Hokotro> hokotrok; 

    public Takarito(KozosKassza kassza) {
        super(kassza);
        this.hokotrok = new ArrayList<>();
    }

    public void addHokotro(Hokotro gep) {
        System.out.println("\t\t> takarito1:Takarito.addHokotro(ujHokotro: Hokotro)");
        this.hokotrok.add(gep);
        System.out.println("\t\t<- void");
    }

    /**
     * Sót tölt a megadott hókotró számára, amely a sószóró fej működéséhez szükséges[cite: 1277].
     */
    public void soToltes(Hokotro gep) {
        System.out.println("\t\t> takarito1:Takarito.soToltes(hokotro1: Hokotro)");
        // Logika a sószóró fej újratöltésére
        System.out.println("\t\t<- void");
    }

    /**
     * Kerozinnal tölti fel a paraméterként átadott hókotrót[cite: 1278].
     */
    public void kerozinToltes(Hokotro gep) {
        System.out.println("\t\t> takarito1:Takarito.kerozinToltes(hokotro1: Hokotro)");
        
        // Itt lenne a sárkányfej újratöltése
        
        System.out.println("\t\t<- void");
    }

    /**
     * Levonja a kapott pénzösszeget a KozosKassza objektumon keresztül [cite: 1279-1280].
     */
    public boolean fizet(int osszeg) {
        System.out.println("\t\t> takarito1:Takarito.fizet(" + osszeg + ")");
        boolean siker = this.kassza.penzKivonas(osszeg);
        System.out.println("\t\t<- " + siker);
        return siker;
    }
}
