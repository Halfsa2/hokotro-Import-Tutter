package halozat;

import vezerles.SkeletonLogger;

public class Alagut extends Utszakasz {

    /**
     * Alagút esetén a hóesés hatás nem érvényesül.
     * @param s a vizsgált sáv
     */
    @Override
    public void havazikRa(Sav s) {
        SkeletonLogger.enter(this, "havazikRa",s);
        // Alagúton nem esik a hó, így nem hívjuk meg a sáv állapotát, csak simán visszatérünk
        SkeletonLogger.exit("void");
    }
}