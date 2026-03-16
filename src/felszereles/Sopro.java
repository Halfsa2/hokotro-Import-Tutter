package felszereles;
import halozat.Sav;
/**
 * A söprő fej a havat az aktuális sávról jobbra tolja[cite: 523].
 * Jeget nem képes eltávolítani[cite: 526].
 */
public class Sopro extends Kotrofej {

    @Override
    public boolean takarit(Sav s) {
        // Megpróbáljuk eltakarítani a havat a jelenlegi sávról
        boolean sikeres = s.hoTisztit(); //
        
        if (sikeres) {
            // A specifikáció szerint a havat a jobb oldali sávra kell tolni[cite: 1211, 1212].
            // (Ennek teljes megvalósításához a VárosModellnek biztosítania kellene 
            // egy metódust a szomszédos sáv lekérdezésére, pl. s.getJobbSzomszed().hoesesEseten())
        }
        
        return sikeres;
    }
}
