package gazdasag;

import felszereles.Kotrofej;
import jarmu.Hokotro;
import java.util.ArrayList;
import static prototipus.CommandInterpreter.reverseNevTar;
import vezerles.SkeletonLogger;

/**
 * A városi úthálózat karbantartásáért és téli akadálymentesítéséért felelős játékos.
 * A takarító birtokol és irányít egy vagy több hókotró járművet.
 */
public class Takarito extends Jatekos<Hokotro> {    
   
   /**
    * Konstruktor a Takarítóhoz. Létrehozáskor kap egy alapértelmezett hókotrót.
    * @param kassza a közös kassza referenciája
    */
   public Takarito(KozosKassza kassza) {
        super(kassza);
        SkeletonLogger.create(this);
        
        this.jarmuvek = new ArrayList<>();
        
        SkeletonLogger.exit(this);
    }

    /**
     * Hozzáad egy új hókotrót a takarító flottájához (pl. vásárlás után).
     * @param gep az új hókotró
     */
    public void addHokotro(Hokotro gep) {
        SkeletonLogger.enter(this, "addHokotro", gep);        
        this.jarmuvek.add(gep);        
        SkeletonLogger.exit("void"); 
    }

    /**
     * Sót tölt a megadott hókotró számára, amely a sószóró fej működéséhez szükséges.
     */
    public void soToltes(Hokotro gep) {
        SkeletonLogger.enter(this, "soToltes", gep);
        
        Kotrofej fej = gep.getFej("Soszoro");
        if (fej != null) {
            fej.ujratolt(10); // Polimorf hívás
        }
        
        SkeletonLogger.exit("void");
    }

    /**
     * Kerozinnal tölti fel a paraméterként átadott hókotrót.
     */
    public void kerozinToltes(Hokotro gep) {
        SkeletonLogger.enter(this, "kerozinToltes", gep);
        
        Kotrofej fej = gep.getFej("Sarkanyfej");
        if (fej != null) {
            fej.ujratolt(10); // Polimorf hívás
        }
        
        SkeletonLogger.exit("void");
    }

    /**
     * Zúzalékkal tölti fel a paraméterként átadott hókotrót.
     */
    public void zuzalekToltes(Hokotro gep) {
        SkeletonLogger.enter(this, "zuzalekToltes", gep);
        
        Kotrofej fej = gep.getFej("ZuzalekSzoro");
        if (fej != null) {
            fej.ujratolt(10); // Polimorf hívás
        }
        
        SkeletonLogger.exit("void");
    }

    /**
     * Levonja a kapott pénzösszeget a KozosKassza objektumon keresztül vásárláskor.
     */
    public boolean fizet(int osszeg) {
        SkeletonLogger.enter(this, "fizet", osszeg);       
        boolean siker = this.kassza.penzKivonas(osszeg);       
        SkeletonLogger.exit(siker);
        return siker;
    }
    @Override
    public String printStat(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("Takarito ").append(name).append(": jarmuvek=");
        for (Hokotro h : jarmuvek) {
            sb.append(reverseNevTar.get(h));
            if(h != jarmuvek.getLast()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}  