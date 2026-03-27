package allapot;
import halozat.Sav;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;
/**
 * Vékonyabb hóréteg, belső állapottal (hóvastagság, nyomvonal). [cite: 1168, 1169]
 */
public class SekelyHo extends Savallapot {
    protected int horeteg = 1; // [cite: 1174]
    protected int nyomvonal = 0; // [cite: 1176]

    public SekelyHo() { /* Konstruktor [cite: 1173] */ 
        SkeletonLogger.create(this);
        SkeletonLogger.register(this, "sekelyHo");
        SkeletonLogger.exitCreate(this);
    }
    @Override
    public void befogad(Sav sav, Jarmu jarmu) {
        nyomvonal++; // Járművek letapossák a havat [cite: 1183]
        if (nyomvonal >= 3) {
            sav.setAllapot(new Jeges()); // 3 áthaladás után jég képződik [cite: 500]
        }
    }

    @Override
    public void elenged(Sav sav, Jarmu jarmu) { /* Távozás adminisztrálása [cite: 1184] */ }

    @Override
    public void hoesesEseten(Sav sav) {
        horeteg++;
        if (horeteg >= 3) {
            sav.setAllapot(new MelyHo()); // 3 réteg után Mély Hó [cite: 1186]
        }
    }

    @Override
    public void frissit(Sav sav) { /* ... [cite: 1187] */ }

    @Override
    public boolean lepesTeszt(Jarmu jarmu) { return true; /* Még járható [cite: 1190] */ }

    @Override
    public void sotKap(Sav sav) {
        horeteg--;
        if (horeteg <= 0) {
            sav.setAllapot(new Tiszta()); // Só hatására elolvad [cite: 1192]
        }
    }

    @Override
    public boolean hoTisztit(Sav sav) {
        sav.setAllapot(new Tiszta()); // Takarítás után tiszta lesz [cite: 1193]
        return true; 
    }

    @Override
    public boolean jegTisztit(Sav sav) { return false; /* Nincs jég [cite: 1195] */ }
}
