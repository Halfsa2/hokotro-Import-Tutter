package gazdasag;

import prototipus.IStatable;
import vezerles.SkeletonLogger;

/**
 * A játékosok közös perselye, amely a megszerzett Zúzmara Tallérokat tárolja.
 */
public class KozosKassza implements IStatable {
    
    private int penzosszeg; // A Zúzmara Tallérok aktuális egyenlege.

    public KozosKassza(int kezdetiOsszeg) {
        SkeletonLogger.create(this);
        this.penzosszeg = kezdetiOsszeg;       
        SkeletonLogger.exit(this);
    }

    /**
     * Hozzáadja a paraméterként kapott összeget a közös egyenleghez.
     */
    public void penzHozzaadas(int osszeg) {
        SkeletonLogger.enter(this, "penzHozzaadas", osszeg);       
        this.penzosszeg += osszeg;        
        SkeletonLogger.exit("void"); 
    }

    /**
     * Levonja a megadott összeget a kasszából egy vásárlás során.
     * @param osszeg a levonni kívánt Zúzmara Tallérok összege
     * @return true, ha van elég pénz és a vásárlás sikeres, különben false
     */
    public boolean penzKivonas(int osszeg) {
        SkeletonLogger.enter(this, "penzKivonas", osszeg);        
        
        boolean sikeres = false;
        
        // Valós logika: Ellenőrzi a belső változót, és ha van elég pénz, levonja
        if (this.penzosszeg >= osszeg) {
            this.penzosszeg -= osszeg;
            sikeres = true; 
        }
        
        SkeletonLogger.exit(sikeres);
        return sikeres; 
    }

    /**
     * Visszaadja a jelenleg a kasszában lévő pénzösszeget.
     * (A prototípus tesztelői nyelvének 'stat' parancsához hasznos lesz!)
     * @return a Zúzmara Tallérok aktuális egyenlege
     */
    public int getPenzosszeg() {
        return this.penzosszeg;
    }
    @Override
    public String printStat(String name) {
        return "KozosKassza " + name + ": penzmennyiseg=" + this.penzosszeg;
    }
}