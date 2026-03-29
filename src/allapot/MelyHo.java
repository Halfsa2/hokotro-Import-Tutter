package allapot;

import halozat.Sav;
import jarmu.Hokotro;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

/**
 * A legextrémebb téli útviszonyokat reprezentálja, ahol a normál forgalom
 * teljesen megbénul .
 */
public class MelyHo extends Savallapot {

    public MelyHo() { /* Konstruktor [cite: 1173] */
        SkeletonLogger.create(this);
        SkeletonLogger.register(this, "melyho");
        SkeletonLogger.exit(this);
    }

    @Override
    public boolean befogad(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", jarmu);
        SkeletonLogger.exit(false);
        return false;
    }

    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
    }

    @Override
    public void hoesesEseten(Sav sav) {
        // Lekezeli a havazás eseményét. Mivel a sáv már "mély hó" állapotban van,
        // marad a MelyHo állapotban [cite: 1082-1083].
    }

    @Override
    public void frissit(Sav sav) {
        SkeletonLogger.enter(this, "frissit", sav);
        System.out.println("                > melyHo:MelyHo.horeteg--");
        SekelyHo sekelyHo = new SekelyHo();
        SkeletonLogger.register(sekelyHo, "sekelyHo");
        sav.setAllapot(sekelyHo);

        SkeletonLogger.exit("void");
    }

    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        // Kulcsfontosságú függvény, amely eldönti, hogy a jármű rá tud-e lépni a sávra.
        // A mély hó miatt ez egy sima Auto esetén hamis, míg egy Hokotro számára igaz
        // [cite: 1085-1090].
        if (jarmu instanceof Hokotro) {
            return true;
        }
        return false;
    }

    @Override
    public void sotKap(Sav sav) {
        SkeletonLogger.enter(this, "sotKap", sav);
        SkeletonLogger.exit("void");
    }

    @Override
    public boolean hoTisztit(Sav sav) {
        SkeletonLogger.enter(this, "hoTisztit", sav);

        Tiszta tiszta = new Tiszta();
        SkeletonLogger.register(tiszta, "tiszta1");
        sav.setAllapot(tiszta);

        SkeletonLogger.exit(true);
        return true;
    }

    @Override
    public boolean jegTisztit(Sav sav) {
        SkeletonLogger.enter(this, "jegTisztit", sav);
        SkeletonLogger.exit(false);
        return false;
    }
}