package gazdasag;

import halozat.Csomopont;
import jarmu.IranyitottJarmu;
import java.util.ArrayList;
import java.util.List;
import prototipus.IStatable;

public abstract class Jatekos <T extends IranyitottJarmu> implements IStatable {
    protected List<T> jarmuvek;
    protected KozosKassza kassza; 
    protected T aktivJarmu;
    protected boolean korVege = false;
    private String nev;

    protected Jatekos(String nev,KozosKassza kassza) {
        this.nev = nev;
        this.kassza = kassza;
        this.jarmuvek = new ArrayList<>();
        this.aktivJarmu = null; 
    }

    public void keres(int osszeg) {
        this.kassza.penzHozzaadas(osszeg);
    }

    public void nextJarmu(){
        if (jarmuvek.isEmpty()) {
            korVege();
            return;
        }
        if(aktivJarmu == null){
            aktivJarmu = jarmuvek.get(0);
            return;
        }

        int currentId = jarmuvek.indexOf(aktivJarmu);
        // Ha az utolsó jármű is lépett, vége a körnek!
        if(currentId >= jarmuvek.size()-1) {
            korVege();
            aktivJarmu = jarmuvek.get(0); // Visszaállítjuk az elsőre a következő körhöz
        } else {
            // Egyébként jön a következő jármű
            aktivJarmu = jarmuvek.get(currentId+1);
        }
    }

    public boolean lep(Csomopont cel){
        if(aktivJarmu != null){
            return aktivJarmu.lep(cel);
        }
        return false;
    }

    public void korKezdodik(){
        korVege = false;
        // Amikor a kör kezdődik, azonnal kiválasztjuk az első járművet (hogy ne ragadjon be a busz!)
        if (!jarmuvek.isEmpty()) {
            aktivJarmu = jarmuvek.get(0);
        } else {
            aktivJarmu = null;
        }
    }

    public T getAktivJarmu() { return aktivJarmu; }
    public void korVege() { korVege = true; }
    public boolean isKorVege() { return korVege; }
    public List<T> getJarmuvek() { return this.jarmuvek; }
    public String getNev() { return this.nev; }
}