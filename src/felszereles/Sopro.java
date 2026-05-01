package felszereles;

import halozat.Sav;
import vezerles.SkeletonLogger;

/**
 * A söprő fej a havat az aktuális sávról jobbra tolja.
 * Jeget nem képes eltávolítani.
 */
public class Sopro extends Kotrofej {

    /**
     * Konstruktor a Söprő osztályhoz.
     */
    public Sopro() {
        SkeletonLogger.create(this);
        SkeletonLogger.exit(this);
    }

    /**
     * Takarítja a havat a söprő fejjel.
     * A havat jobbra tolja a szomszédos sávra.
     * @param s a tisztítandó sáv
     * @return true, ha sikerült megtisztítani, különben false
     */
    @Override
    public boolean takarit(Sav s) {
        SkeletonLogger.enter(this, "takarit", s);
        
        // 1. Megpróbáljuk eltakarítani a havat a jelenlegi sávról (csak hóra működik)
        boolean sikeres = s.hoTisztit();
        boolean zuzalekVolt = s.zuzalekTisztit();
        
        // 2. Ha volt mit eltakarítani (tehát nem tiszta jégen vagy tiszta aszfalton toltuk)
        if (sikeres) {
            // Lekérjük a jobb oldali szomszédot
            Sav szomszedos = s.getJobbSzomszed(s); 
            
            // Ha van jobb oldali sáv, áttoljuk rá a havat
            if (szomszedos != null) {
                // A hóesés szimulálásával "növeljük" a szomszédos sáv hórétegét (Teszt 52)
                szomszedos.hoesesEseten(); 
                if(zuzalekVolt){
                    szomszedos.zuzalekSzoras(); // Ha volt zúzalék, azt is áttoljuk
                }
            }
        }
        
        SkeletonLogger.exit(sikeres);
        return sikeres;
    }
    @Override
    public void printStat(String name) {
        System.out.println("Sopro "+ name);}

    /**
     * Söprő fejnek nincsenek fogyóeszközei, így az újratöltés nem csinál semmit.
     */
    @Override
    public void ujratolt(int mennyiseg) {
        SkeletonLogger.enter(this, "ujratolt", mennyiseg);
        SkeletonLogger.exit("void");
    }
}