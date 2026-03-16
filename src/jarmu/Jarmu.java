package jarmu;
import halozat.Csomopont;

/**
 * A város úthálózatán mozgó összes entitás közös absztrakt ősosztálya.
 */
public abstract class Jarmu implements IJarmuMozgas {
    
    // Azt tárolja, hogy a járműnek baleset esetén hány szimulációs körből kell kimaradnia
    protected int varakozik = 0; 
    
    // A jármű aktuális helyzete (kétirányú asszociáció a Csomoponttal)
    protected Csomopont aktualisCsomopont;

    /**
     * Ez a metódus felel a balesetek alapértelmezett következményeinek végrehajtásáért.
     * Alapértelmezésben a jármű ilyenkor leáll, és az általa elfoglalt sáv blokkolódik.
     */
    public void balesetetSzenved() {
        this.varakozik = 3; // A dokumentáció szerint a baleset miatti lezárás 3 körre érvényes
    }

    public Csomopont getAktualisCsomopont() {
        return aktualisCsomopont;
    }

    public void setAktualisCsomopont(Csomopont aktualisCsomopont) {
        this.aktualisCsomopont = aktualisCsomopont;
    }

    // Az IJarmuMozgas interfész megvalósítása, amit a leszármazottak definiálnak
    @Override
    public abstract boolean lep(Csomopont cel); 
}
