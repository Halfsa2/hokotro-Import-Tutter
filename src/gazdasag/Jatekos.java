package gazdasag;

import jarmu.IranyitottJarmu;
import java.util.List;

/**
 * A rendszerben lévő aktív szereplők általános reprezentációja [cite: 974-975].
 */
public abstract class Jatekos <T extends IranyitottJarmu> {
    protected List<T> jarmuvek;
    // Lehetővé teszi, hogy a játékos interakcióba lépjen a játék közös pénzalapjával [cite: 979-980].
    protected KozosKassza kassza; 
    protected T aktivJarmu;

    protected Jatekos(KozosKassza kassza) {
        this.kassza = kassza;
    }

    /**
     * Ha a játékos valamilyen tevékenységgel pénzt szerez, ez a függvény hívódik meg[cite: 982].
     */
    public void keres(int osszeg) {
        this.kassza.penzHozzaadas(osszeg);
    }

    public void leptetMindenJarmuvet(){
        for (T jarmu : jarmuvek) {
            leptet(jarmu);
        }
    }
    private void leptet(T j){
        aktivJarmu = j;
    }
}
