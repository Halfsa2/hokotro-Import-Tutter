package felszereles;

import halozat.Sav;

/**
 * Általános, hókotróra szerelhető tisztítóeszköz absztrakt ősosztálya.
 * A különböző típusú kotrófejek (Hányófej, Jégtörő, Seprű, stb.) ebből származnak le.
 */
public abstract class Kotrofej {
    
    /**
     * Előírja a leszármazottak számára a sávra ható működési logikát.
     * @param s A sáv, amit takarítani kell.
     * @return true, ha a takarítás sikeres volt, különben false.
     */
    public abstract boolean takarit(Sav s); 
}