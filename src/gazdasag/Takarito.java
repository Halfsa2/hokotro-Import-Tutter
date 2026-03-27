package gazdasag;
import jarmu.Hokotro;
import java.util.ArrayList;
import java.util.List;
import vezerles.SkeletonLogger;
/**
 * A városi úthálózat karbantartásáért és téli akadálymentesítéséért felelős játékos[cite: 1267].
 */
public class Takarito extends Jatekos {    
   // A takarító birtokol és irányít egy vagy több hókotró járművet.
   private List<Hokotro> hokotrok; 
   public Takarito(KozosKassza kassza) {
        super(kassza);
        SkeletonLogger.create(this);
        SkeletonLogger.register(this, "takarito"); // Automatikus regisztráció       
        this.hokotrok = new ArrayList<>();       
        SkeletonLogger.exit(this);
    }

    public void addHokotro(Hokotro gep) {
        SkeletonLogger.enter(this, "addHokotro", gep);        
        this.hokotrok.add(gep);        
        SkeletonLogger.exit("void"); // Void metódusnál nincs visszatérési érték
    }

    /**
     * Sót tölt a megadott hókotró számára, amely a sószóró fej működéséhez szükséges.
     */
    public void soToltes(Hokotro gep) {
        SkeletonLogger.enter(this, "soToltes", gep);       
        // Logika a sószóró fej újratöltésére        
        SkeletonLogger.exit("void");
    }

    /**
     * Kerozinnal tölti fel a paraméterként átadott hókotrót.
     */
    public void kerozinToltes(Hokotro gep) {
        SkeletonLogger.enter(this, "kerozinToltes", gep);        
        // Itt lenne a sárkányfej újratöltése        
        SkeletonLogger.exit("void");
    }

    /**
     * Levonja a kapott pénzösszeget a KozosKassza objektumon keresztül.
     */
    public boolean fizet(int osszeg) {
        SkeletonLogger.enter(this, "fizet", osszeg);       
        boolean siker = this.kassza.penzKivonas(osszeg);       
        SkeletonLogger.exit(siker);
        return siker;
    }
}
