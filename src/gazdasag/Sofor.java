package gazdasag;

import jarmu.Busz;
import java.util.ArrayList;
import vezerles.SkeletonLogger;

/**
 * Az a játékos (felhasználó), aki a buszt irányítja a szimulációban.
 * Célja, hogy a busszal sikeres fordulókat tegyen, és ezzel pénzt keressen a közös kasszába.
 */
public class Sofor extends Jatekos<Busz> {

    /**
     * Konstruktor a Sofőr osztályhoz.
     * @param kassza a közös kassza referenciája
     * @param busz a busz, amit a sofőr irányítani fog
     */
    public Sofor(KozosKassza kassza, Busz busz) {
        super(kassza);
        SkeletonLogger.create(this);
        
        this.jarmuvek = new ArrayList<>();
        this.jarmuvek.add(busz); // Egy Sofőrhöz pontosan egy Busz tartozhat
        
        SkeletonLogger.exit(this);
    }
    
    /**
     * Visszaadja a sofőr által irányított buszt.
     * @return a birtokolt busz objektum
     */
    public Busz getBusz() {
        // A naplózást egy egyszerű getternél elhagyhatjuk a tiszta konzol érdekében
        return this.jarmuvek.get(0);
    }
}