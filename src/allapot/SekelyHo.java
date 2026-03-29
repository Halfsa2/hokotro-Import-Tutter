package allapot;
import halozat.Sav;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

public class SekelyHo extends Savallapot {
    protected int horeteg = 1; 
    protected int nyomvonal = 0; 

    public SekelyHo() { 
        SkeletonLogger.create(this);
        SkeletonLogger.register(this, "sekelyHo");
        SkeletonLogger.exit(this);
    }

    @Override
    public void befogad(Sav sav, Jarmu jarmu) {
        nyomvonal++; 
        if (nyomvonal >= 3) {
            sav.setAllapot(new Jeges()); 
        }
    }

    @Override
    public void elenged(Sav sav, Jarmu jarmu) {}

    @Override
    public void hoesesEseten(Sav sav) {
        horeteg++;
        if (horeteg >= 3) {
            sav.setAllapot(new MelyHo()); 
        }
    }

    @Override
    public void frissit(Sav sav) {}

    @Override
    public boolean lepesTeszt(Jarmu jarmu) { return true; }

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