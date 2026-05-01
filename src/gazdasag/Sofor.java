package gazdasag;

import jarmu.Busz;
import java.util.ArrayList;
import static prototipus.CommandInterpreter.reverseNevTar;
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
    public Sofor(KozosKassza kassza) {
        super(kassza);
        SkeletonLogger.create(this);
        
        this.jarmuvek = new ArrayList<>();
        
        SkeletonLogger.exit(this);
    }
    public void setJarmu(Busz busz) {
        SkeletonLogger.enter(this, "setJarmu", busz);
        if(!jarmuvek.isEmpty()){return;} // A sofőr csak egy buszt irányíthat, így ha már van egy, nem adunk hozzá újat
        this.jarmuvek.add(busz);
        SkeletonLogger.exit("void");
    }
    
    /**
     * Visszaadja a sofőr által irányított buszt.
     * @return a birtokolt busz objektum
     */
    public Busz getBusz() {
        // A naplózást egy egyszerű getternél elhagyhatjuk a tiszta konzol érdekében
        return this.jarmuvek.get(0);
    }
    @Override
    public String printStat(String name) {
        return"Sofor "+ name + ": busz=" + (jarmuvek.isEmpty() ? "nincs" : reverseNevTar.get(jarmuvek.get(0)));
    }
}