package halozat;

import jarmu.Jarmu;
import java.util.List;
import prototipus.IStatable;

public abstract class Csomopont implements IStatable {

    /**
     * Általános csomópont absztrakt osztály a városi hálózaton.
     */
    public abstract void frissit();
    
    public abstract boolean befogad(Jarmu jarmu);
    
    public abstract void elenged(Jarmu jarmu);
    
    public abstract List<Csomopont> getNext();
    
    public abstract void balesetEseten();
    
    public abstract boolean foglalt();

    /**
     * Kezeli a hóesés eseményét az adott csomóponton.
     * Alapvetően nem tesz semmit, ha nincs felülírás.
     */
    public void hoesesEseten(){    }
}