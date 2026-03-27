package gazdasag;
import vezerles.SkeletonLogger;
/**
 * A játékosok közös perselye, amely a megszerzett Zúzmara Tallérokat tárolja.
 */
public class KozosKassza {
    private int penzosszeg; // A Zúzmara Tallérok aktuális egyenlege.
    public KozosKassza(int kezdetiOsszeg) {
        SkeletonLogger.create(this);
        SkeletonLogger.register(this, "kassza");       
        this.penzosszeg = kezdetiOsszeg;       
        SkeletonLogger.exit(this);
    }

    /**
     * Hozzáadja a paraméterként kapott összeget a közös egyenleghez.
     */
    public void penzHozzaadas(int osszeg) {
        SkeletonLogger.enter(this, "penzHozzaadas", osszeg);       
        this.penzosszeg += osszeg;        
        SkeletonLogger.exit("void"); // Void metódus
    }

    /**
     * Levonja a megadott összeget a kasszából egy vásárlás során.
     */
    public boolean penzKivonas(int osszeg) {
        SkeletonLogger.enter(this, "penzKivonas", osszeg);        
        System.out.print("\t\t\t[?] Van elég pénz a közös kasszában a termékre? (i/n): ");         // interakció
        java.util.Scanner sc = new java.util.Scanner(System.in);
        String valasz = sc.nextLine();       
        if (valasz.equalsIgnoreCase("i")) {
            SkeletonLogger.exit(true);
            return true;
        } 
        else {
            SkeletonLogger.exit(false);
            return false;
        }
        /*
        if (this.penzosszeg >= osszeg) {
            this.penzosszeg -= osszeg;
            return true; // Sikeres tranzakció
        }
        return false; // Nincs elég fedezet*/
    }
}
