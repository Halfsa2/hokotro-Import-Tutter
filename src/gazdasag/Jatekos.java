package gazdasag;
/**
 * A rendszerben lévő aktív szereplők általános reprezentációja [cite: 974-975].
 */
public abstract class Jatekos {
    
    // Lehetővé teszi, hogy a játékos interakcióba lépjen a játék közös pénzalapjával [cite: 979-980].
    protected KozosKassza kassza; 

    public Jatekos(KozosKassza kassza) {
        this.kassza = kassza;
    }

    /**
     * Ha a játékos valamilyen tevékenységgel pénzt szerez, ez a függvény hívódik meg[cite: 982].
     */
    public void keres(int osszeg) {
        this.kassza.penzHozzaadas(osszeg);
    }
}
