package gazdasag;

import jarmu.Busz;
import static prototipus.CommandInterpreter.reverseNevTar;
import vezerles.SkeletonLogger;

/**
 * Az a játékos (felhasználó), aki a buszt irányítja a szimulációban.
 * Célja, hogy a busszal sikeres fordulókat tegyen, és ezzel pénzt keressen a közös kasszába.
 */
public class Sofor extends Jatekos<Busz> {

    /**
     * Konstruktor a Sofőr osztályhoz.
     * @param nev a sofőr neve
     * @param kassza a közös kassza referenciája
     */
    public Sofor(String nev, KozosKassza kassza) {
        super(nev, kassza);
        SkeletonLogger.create(this);
        SkeletonLogger.exit(this);
    }

    /**
     * Beállítja a sofőr buszát a paraméterként megadottra, amennyiben még nincsen neki.
     * Ha már van busza a sofőrnek akkor nem szabad tudnia cserélni.
     * @param busz
     */
    public void setJarmu(Busz busz) {
        SkeletonLogger.enter(this, "setJarmu", busz);
        if(!jarmuvek.isEmpty()){return;} 
        this.jarmuvek.add(busz);
        
        // Azonnal beültetjük a buszba!
        if (this.aktivJarmu == null) {
            this.aktivJarmu = busz;
        }
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