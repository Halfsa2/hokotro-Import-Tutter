package allapot;

import halozat.Sav;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

public class Jeges extends Savallapot {

    public Jeges() { /* Konstruktor */
        SkeletonLogger.create(this);
        SkeletonLogger.register(this, "sekelyHo");
        SkeletonLogger.exit(this);
    }

    // A jeges állapot saját maga is nyilvántart egy ilyen értéket,
    // ami azt mutatja, hogy a jeges sáv mennyire sózott.
    public int sozott = 0;

    // jelzi, ha a sárkányfej hívta a jégtisztítást
    public static boolean sarkanfejOlvassza = false;

    @Override
    public boolean befogad(Sav sav, Jarmu jarmu) {
        // Lekezeli a jármű rálépését a jeges sávra.
        // A jármű megcsúszik és balesetet szenved a jégen.
        SkeletonLogger.enter(this, "befogad", sav, jarmu);
        jarmu.balesetetSzenved();
        SkeletonLogger.exit(true);
        return true; // Ráléphet, de balesetet szenved.
    }

    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
    }

    @Override
    public void hoesesEseten(Sav sav) {
        // Reagál arra az eseményre, ha a jeges útszakaszon havazni kezd.
        // Itt például vastagodhat a jégen lévő hóréteg, átválthat egy speciális
        // havas-jeges állapotba.
    }

    @Override
    public void frissit(Sav sav) {
        SkeletonLogger.enter(this, "frissit", sav);
        if (this.sozott <= 0) {
            Tiszta tiszta = new Tiszta();
            SkeletonLogger.register(tiszta, "tiszta");
            sav.setAllapot(tiszta);
            this.sozott--;
        }
        SkeletonLogger.exit("void");
    }

    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        return true;
    }

    @Override
    public void sotKap(Sav sav) {
        SkeletonLogger.enter(this, "sotKap", sav);
        this.sozott = 3;
        SkeletonLogger.exit("void");
    }

    @Override
    public boolean hoTisztit(Sav sav) {
        SkeletonLogger.enter(this, "hoTisztit", sav);
        SkeletonLogger.exit(false);
        return false;
    }

    @Override
    public boolean jegTisztit(Sav sav) {
        SkeletonLogger.enter(this, "jegTisztit", sav);

        if (sarkanfejOlvassza) {
            // Ha Sárkányfej (2-es teszt), akkor tisztára olvasztja
            Tiszta tiszta = new Tiszta();
            SkeletonLogger.register(tiszta, "tiszta1");
            sav.setAllapot(tiszta);
        } else {
            // Ha Jégtörő (9-es teszt), akkor marad egy réteg hó
            SekelyHo sekely = new SekelyHo();
            SkeletonLogger.register(sekely, "sekely1");
            sav.setAllapot(sekely);
        }

        SkeletonLogger.exit(true);
        return true;
    }
}