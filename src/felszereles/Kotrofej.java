package felszereles;
import halozat.Sav;
/**
 * Általános, hókotróra szerelhető tisztítóeszköz absztrakt ősosztálya.
 */
public abstract class Kotrofej {
    
    /**
     * Előírja a leszármazottak számára a sávra ható működési logikát.
     * @param s A sáv, amit takarítani kell.
     * @return Igaz, ha a takarítás sikeres volt.
     */
    public abstract boolean takarit(Sav s); 
}
