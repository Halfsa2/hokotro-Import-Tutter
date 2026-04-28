package gazdasag;

import halozat.Csomopont;
import jarmu.IranyitottJarmu;
import java.util.List;

/**
 * A rendszerben lévő aktív szereplők általános reprezentációja [cite: 974-975].
 */
public abstract class Jatekos <T extends IranyitottJarmu> {
    protected List<T> jarmuvek;
    // Lehetővé teszi, hogy a játékos interakcióba lépjen a játék közös pénzalapjával [cite: 979-980].
    protected KozosKassza kassza; 
    protected T aktivJarmu;
    protected boolean korVege = false;

    protected Jatekos(KozosKassza kassza) {
        this.kassza = kassza;
        aktivJarmu = null; // Kezdetben nincs aktív jármű
    }

    /**
     * Ha a játékos valamilyen tevékenységgel pénzt szerez, ez a függvény hívódik meg[cite: 982].
     */
    public void keres(int osszeg) {
        this.kassza.penzHozzaadas(osszeg);
    }

    public void nextJarmu(){
        if(aktivJarmu == null){aktivJarmu = jarmuvek.get(0);return;}

        int currentId = jarmuvek.indexOf(aktivJarmu);
        if(currentId == jarmuvek.size()-1) {currentId = -1; korVege();}
        aktivJarmu = jarmuvek.get(currentId+1);
    }
    public boolean lep(Csomopont cel){
        if(aktivJarmu != null){
            return aktivJarmu.lep(cel);
        }
        return false;
    }
    public T getAktivJarmu() {
        return aktivJarmu;
    }
    public void korKezdodik(){
        korVege = false;
    }
    public void korVege(){
        korVege = true;
    }
    public boolean isKorVege() {
        return korVege;
    }
}
