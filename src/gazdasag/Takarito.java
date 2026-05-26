package gazdasag;

import felszereles.Kotrofej;
import jarmu.Hokotro;
import static prototipus.CommandInterpreter.reverseNevTar;
import vezerles.SkeletonLogger;

/**
 * A városi úthálózat karbantartásáért és téli akadálymentesítéséért felelős játékos.
 * A takarító birtokol és irányít egy vagy több hókotró járművet.
 */
public class Takarito extends Jatekos<Hokotro> {    
   
    /**
     * Konstruktor a Takarito osztályhoz.
     * @param nev A játékos neve
     * @param kassza A közös kassza amibe/amiből a pénzt fogja gyűjteni/költeni
     */
        public Takarito(String nev, KozosKassza kassza) {
                super(nev, kassza); 
                SkeletonLogger.create(this);
                
                // Amint létrejön egy Takarító, azonnal kap egy saját alap hókotrót!
                Hokotro kezdoGep = new Hokotro(this);
                this.addHokotro(kezdoGep);
                SkeletonLogger.exit(this);
            }
            /**
             * A takarító hókotrói közé helyezi a paraméterként megadott
             * hókotrót. Ha eddig egy sem volt, akkor ez lesz az aktív járműve is.
             * @param gep
             */
            public void addHokotro(Hokotro gep) {
                SkeletonLogger.enter(this, "addHokotro", gep);        
                this.jarmuvek.add(gep);  
                // Ha eddig nem volt gépe, akkor automatikusan ez lesz az aktív!
                if (this.aktivJarmu == null) {
                    this.aktivJarmu = gep;
                }
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