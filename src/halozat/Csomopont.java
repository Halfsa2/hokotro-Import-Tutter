package halozat;

import jarmu.Jarmu;
import java.util.List;

public abstract class Csomopont {
    
    public abstract void frissit();
    
    // MÓDOSÍTÁS: boolean visszatérési érték a Tell, don't ask elv miatt
    public abstract boolean befogad(Jarmu jarmu);
    
    public abstract void elenged(Jarmu jarmu);
    
    public abstract List<Csomopont> getNext();
    
    public abstract void balesetEseten();
    
    public abstract boolean foglalt();

    // MÓDOSÍTÁS: Új metódus az osztálydiagram alapján
    public abstract void hoesesEseten();
}