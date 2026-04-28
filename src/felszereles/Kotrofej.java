package felszereles;
import halozat.Sav;

public abstract class Kotrofej {
    
    public abstract boolean takarit(Sav s); 

    /**
     * Újratölti a fejet a szükséges fogyóeszközzel.
     * Alapértelmezésben üres, a leszármazottak felülírják, ha van fogyóeszközük.
     */
    public void ujratolt(int mennyiseg) {
        // Alapértelmezetten nem csinál semmit
    }
}