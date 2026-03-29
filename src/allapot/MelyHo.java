package allapot;
import halozat.Sav;
import jarmu.Hokotro;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

public class MelyHo extends Savallapot {

    @Override
    public void befogad(Sav sav, Jarmu jarmu) {}

    @Override
    public void elenged(Sav sav, Jarmu jarmu) {}

    @Override
    public void hoesesEseten(Sav sav) {}

    @Override
    public void frissit(Sav sav) {}

    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
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