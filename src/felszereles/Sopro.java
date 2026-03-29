package felszereles;
import halozat.Sav;
import vezerles.SkeletonLogger; 

/**
 * A söprő fej a havat az aktuális sávról jobbra tolja.
 * Jeget nem képes eltávolítani.
 */
public class Sopro extends Kotrofej {

    @Override
    public boolean takarit(Sav s) {
        SkeletonLogger.enter(this, "takarit", s);
        
        // Megpróbáljuk eltakarítani a havat a jelenlegi sávról
        boolean sikeres = s.hoTisztit();
        
        if (sikeres) {
            Sav szomszedos = s.getJobbSzomszed(s); 
            if (szomszedos != null) {
                szomszedos.hoesesEseten(); // Áttoljuk rá a havat
            }
        }
        
        SkeletonLogger.exit(sikeres);
        return sikeres;
    }
}
