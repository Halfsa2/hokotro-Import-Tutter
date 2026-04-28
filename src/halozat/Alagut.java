package halozat;

import vezerles.SkeletonLogger;

public class Alagut extends Utszakasz {

    public Alagut() {
        super(); // Meghívja az Utszakasz konstruktorát (inicializálja a sávok listáját)
        SkeletonLogger.create(this);
        SkeletonLogger.exit(this);
    }

    /**
     * Alagút esetén a hóesés hatás nem érvényesül.
     * @param s a vizsgált sáv
     */
    @Override
    public void havazikRa(Sav s) {
        SkeletonLogger.enter(this, "havazikRa", s);
        // Alagúton nem esik a hó, így nem hívjuk meg a sáv állapotát, 
        // ezzel megvédve a bent lévő sávokat a hófelhalmozódástól. (Teszt 38)
        SkeletonLogger.exit("void");
    }
}