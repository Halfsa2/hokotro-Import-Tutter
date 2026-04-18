package halozat;

import jarmu.Jarmu;
import java.util.List;

public abstract class Csomopont {

    /**
     * Általános csomópont absztrakt osztály a városi hálózaton.
     */
    public abstract void frissit();
    
    public abstract boolean befogad(Jarmu jarmu);
    
    public abstract void elenged(Jarmu jarmu);
    
    public abstract List<Csomopont> getNext();
    
    public abstract void balesetEseten();
    
    public abstract boolean foglalt();

    //Takarító metódusok. Alap esetben nem tesznek semmit, de a Sav osztály felülírja őket.
    public boolean jegTisztit(){
        return false;
    }
    public boolean hoTisztit(){
        return false;
    }
    public void soSzoras(){

    }
    public void zuzalekSzoras(){

    }
    public boolean zuzalekTisztit(){
        return false;
    }


    /**
     * Kezeli a hóesés eseményét az adott csomóponton.
     * Alapvetően nem tesz semmit, ha nincs felülírás.
     */
    public void hoesesEseten(){    }
}