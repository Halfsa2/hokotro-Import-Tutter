package allapot;
import halozat.Sav;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

public class Tiszta extends Savallapot {
    @Override
    public void befogad(Sav sav, Jarmu jarmu) {}
    
    @Override
    public void elenged(Sav sav, Jarmu jarmu) {}
    
    @Override
    public void hoesesEseten(Sav sav) {
        SkeletonLogger.enter(this, "hoesesEseten", sav);
        SekelyHo sekelyHo = new SekelyHo();
        SkeletonLogger.register(sekelyHo, "sekelyHo");
        sav.setAllapot(sekelyHo);
        SkeletonLogger.exit("void");
    }
    
    @Override
    public void frissit(Sav sav) {}
    
    @Override
    public boolean lepesTeszt(Jarmu jarmu) { return true; }
    
    @Override
    public void sotKap(Sav sav) {}
    
    @Override
    public boolean hoTisztit(Sav sav) { 
        SkeletonLogger.enter(this, "hoTisztit", sav);
        SkeletonLogger.exit(false);
        return false; 
    }
    
    @Override
    public boolean jegTisztit(Sav sav) { 
        SkeletonLogger.enter(this, "jegTisztit", sav);
        SkeletonLogger.exit(false);
        return false; 
    }
}