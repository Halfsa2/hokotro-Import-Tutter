package gazdasag;
import jarmu.Busz;
/**
 * Az a felhasználó, aki a buszt irányítja a szimulációban[cite: 1198].
 */
public class Sofor extends Jatekos {
    
    private Busz busz; // Egy Sofőrhöz pontosan egy Busz tartozhat [cite: 1204]

    public Sofor(KozosKassza kassza, Busz busz) {
        super(kassza);
        this.busz = busz;
    }
    
    public Busz getBusz() {
        return busz;
    }
}
