package halozat;

import static prototipus.CommandInterpreter.reverseNevTar;
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
    @Override
    public String printStat(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("Alagut ").append(name).append(": savok=");
        for (Sav s : this.savok) {
            sb.append(reverseNevTar.get(s));
            if(s != this.savok.getLast()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}