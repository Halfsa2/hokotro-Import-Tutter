package gazdasag;
import jarmu.Busz;
import java.util.ArrayList;
/**
 * Az a felhasználó, aki a buszt irányítja a szimulációban[cite: 1198].
 */
public class Sofor extends Jatekos<Busz> {

    public Sofor(KozosKassza kassza, Busz busz) {
        super(kassza);
        this.jarmuvek = new ArrayList<>();
        this.jarmuvek.add(busz);// Egy Sofőrhöz pontosan egy Busz tartozhat [cite: 1204]
    }
    
    public Busz getBusz() {
        return jarmuvek.get(0);
    }
}
