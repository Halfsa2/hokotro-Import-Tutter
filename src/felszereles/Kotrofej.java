package felszereles;
import halozat.Sav;
import prototipus.IStatable;

public abstract class Kotrofej implements IStatable{
    
    public abstract boolean takarit(Sav s); 

    /**
     * Újratölti a fejet a szükséges fogyóeszközzel.
     * Alapértelmezésben üres, a leszármazottak felülírják, ha van fogyóeszközük.
     * @param mennyiseg A fogyóeszköz mennyisége, amennyit újratöltünk
     */
    public void ujratolt(int mennyiseg) {
        // Alapértelmezetten nem csinál semmit
    }

    /**
     * Visszaadja a fejben lévő fogyóeszköz mennyiségét.
     * Alapértelmezésben 0, a leszármazottak (pl. Sószóró) felülírják.
     * @return a fogyóeszköz mennyisége (0 ha a fej nem használ fogyóeszközt)
     */
    public int getToltet() {
        return 0;
    }
}