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
        
        // 1. Megpróbáljuk eltakarítani a havat a jelenlegi sávról
        boolean sikeres = s.hoTisztit();
        
        // 2. Ha volt mit eltakarítani, a havat áttoljuk a jobb oldali szomszédra
        if (sikeres) {
            Sav szomszedos = s.getJobbSzomszed(s); // <-- ITT A JAVÍTÁS
            
            if (szomszedos != null) {
                // A hóesés szimulálásával "növeljük" a szomszédos sáv hórétegét
                szomszedos.hoesesEseten(); 
            }
        }
        
        SkeletonLogger.exit(sikeres);
        return sikeres;
    }
}