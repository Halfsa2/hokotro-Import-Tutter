package allapot;
import halozat.Sav;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

public class Jeges extends Savallapot {

    public int sozott = 0; 

    //jelzi, ha a sárkányfej hívta a jégtisztítást
    public static boolean sarkanfejOlvassza = false;

    @Override
    public void befogad(Sav sav, Jarmu jarmu) {
        jarmu.balesetetSzenved();
    }

    @Override
    public void elenged(Sav sav, Jarmu jarmu) {}

    @Override
    public void hoesesEseten(Sav sav) {}

    @Override
    public void frissit(Sav sav) {
        if (sozott > 0) {
            sozott--;
            if (sozott == 0) {
                sav.setAllapot(new Tiszta());
            }
        }
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