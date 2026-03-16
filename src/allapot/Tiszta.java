package allapot;
import halozat.Sav;
import jarmu.Jarmu;
/**
 * Tiszta, akadálymentes útfelület. [cite: 1239]
 */
public class Tiszta extends Savallapot {
    @Override
    public void befogad(Sav sav, Jarmu jarmu) { /* Normál érkezés [cite: 1246] */ }
    
    @Override
    public void elenged(Sav sav, Jarmu jarmu) { /* Normál távozás [cite: 1247] */ }
    
    @Override
    public void hoesesEseten(Sav sav) {
        // Havazás esetén átvált Sekély Hó állapotba [cite: 1249]
        sav.setAllapot(new SekelyHo());
    }
    
    @Override
    public void frissit(Sav sav) { /* Nincs drasztikus változás [cite: 1251] */ }
    
    @Override
    public boolean lepesTeszt(Jarmu jarmu) { return true; /* Mindenki ráléphet [cite: 1253] */ }
    
    @Override
    public void sotKap(Sav sav) { /* Növeli a sózottságot a kontextusban [cite: 1255] */ }
    
    @Override
    public boolean hoTisztit(Sav sav) { return false; /* Nincs hó [cite: 1260] */ }
    
    @Override
    public boolean jegTisztit(Sav sav) { return false; /* Nincs jég [cite: 1262] */ }
}
