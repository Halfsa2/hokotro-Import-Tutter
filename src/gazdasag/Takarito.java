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
        this.hokotrok.add(gep);
    }

    /**
     * Sót tölt a megadott hókotró számára, amely a sószóró fej működéséhez szükséges[cite: 1277].
     */
    public void soToltes(Hokotro gep) {
        // Logika a sószóró fej újratöltésére
    }

    /**
     * Kerozinnal tölti fel a paraméterként átadott hókotrót[cite: 1278].
     */
    public void kerozinToltes(Hokotro gep) {
        // Logika a sárkányfej újratöltésére
    }

    /**
     * Levonja a kapott pénzösszeget a KozosKassza objektumon keresztül [cite: 1279-1280].
     */
    public boolean fizet(int osszeg) {
        return this.kassza.penzKivonas(osszeg);
    }
}
