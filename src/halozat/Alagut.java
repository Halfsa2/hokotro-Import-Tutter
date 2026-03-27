package halozat;

import vezerles.SkeletonLogger;

public class Alagut extends Utszakasz {

    @Override
    public void havazikRa(Sav s) {
        SkeletonLogger.enter(this, "havazikRa",s);
        // Alagúton nem esik a hó, így nem hívjuk meg a sáv állapotát, csak simán visszatérünk
        SkeletonLogger.exit("void");
    }
}