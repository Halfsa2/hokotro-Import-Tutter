package gazdasag;
/**
 * A játékosok közös perselye, amely a megszerzett Zúzmara Tallérokat tárolja.
 */
public class KozosKassza {
    
    private int penzosszeg; // A Zúzmara Tallérok aktuális egyenlege.

    public KozosKassza(int kezdetiOsszeg) {
        this.penzosszeg = kezdetiOsszeg;
    }

    /**
     * Hozzáadja a paraméterként kapott összeget a közös egyenleghez.
     */
    public void penzHozzaadas(int osszeg) {
        this.penzosszeg += osszeg;
    }

    /**
     * Levonja a megadott összeget a kasszából egy vásárlás során.
     */
    public boolean penzKivonas(int osszeg) {
        if (this.penzosszeg >= osszeg) {
            this.penzosszeg -= osszeg;
            return true; // Sikeres tranzakció
        }
        return false; // Nincs elég fedezet
    }
}
