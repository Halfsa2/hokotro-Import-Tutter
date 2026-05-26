package gazdasag;

import halozat.Csomopont;
import jarmu.IranyitottJarmu;
import java.util.ArrayList;
import java.util.List;
import prototipus.IStatable;
/**
 * A játékosokat reprezentáló absztrakt ősosztály.
 * Felelős a játékoshoz tartozó járművek kezeléséért, az éppen aktív jármű vezérléséért,
 * valamint a közös kasszával való interakcióért.
 * * @param <T> A játékos által irányított jármű típusa, amelynek az IranyitottJarmu osztályból kell származnia.
 */
public abstract class Jatekos <T extends IranyitottJarmu> implements IStatable {
    /** A játékos birtokában lévő járművek listája. */
    protected List<T> jarmuvek;
    /** A közös kassza, amelybe a játékos a bevételeit gyűjti. */
    protected KozosKassza kassza; 
    /** A játékos éppen soron lévő, aktív járműve. */
    protected T aktivJarmu;
    /** Jelzi, hogy a játékos befejezte-e az aktuális körét. */
    protected boolean korVege = false;
    /** A játékos neve vagy azonosítója. */
    private final String nev;

    /**
     * Konstruktor a Jatekos osztályhoz.
     * @param nev A játékos neve
     * @param kassza A közös kassza, amit a játékos használ
     */
    protected Jatekos(String nev,KozosKassza kassza) {
        this.nev = nev;
        this.kassza = kassza;
        this.jarmuvek = new ArrayList<>();
        this.aktivJarmu = null; 
    }
    /**
     * A játékos pénzt keres, amelyet hozzáad a közös kasszához.
     * @param osszeg A kasszához adandó pénzösszeg
     */
    public void keres(int osszeg) {
        this.kassza.penzHozzaadas(osszeg);
    }

    /**
     * Kicseréli az aktív járművet a listában a következőre.
     * Ha nincs jármű, vagy az utolsó jármű is befejezte a lépését, 
     * a játékos köre véget ér.
     */
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

    /**
     * Megpróbálja az aktív járművet a megadott cél csomópontra léptetni.
     * @param cel A cél csomópont, ahová a jármű lépni szeretne
     * @return true, ha a lépés sikeres volt, egyébként false
     */
    public boolean lep(Csomopont cel){
        if(aktivJarmu != null){
            return aktivJarmu.lep(cel);
        }
        return false;
    }

    /**
     * Inicializálja a játékos állapotát az új kör kezdetén.
     * A kör vége jelzőt hamisra állítja, és kiválasztja az első járművet aktívnak.
     */
    public void korKezdodik(){
        korVege = false;
        // Amikor a kör kezdődik, azonnal kiválasztjuk az első járművet (hogy ne ragadjon be a busz!)
        if (!jarmuvek.isEmpty()) {
            aktivJarmu = jarmuvek.get(0);
        } else {
            aktivJarmu = null;
        }
    }
    /**
     * Visszaadja a jelenleg aktív (soron lévő) járművet.
     * @return Az aktív jármű referenciája
     */
    public T getAktivJarmu() { return aktivJarmu; }
    /**
     * Manuálisan befejezi a játékos körét.
     */
    public void korVege() { korVege = true; }
    /**
     * Lekérdezi, hogy a játékos köre véget ért-e.
     * @return true, ha a kör véget ért, egyébként false
     */
    public boolean isKorVege() { return korVege; }
    /**
     * Visszaadja a játékoshoz tartozó összes jármű listáját.
     * @return A járművek listája
     */
    public List<T> getJarmuvek() { return this.jarmuvek; }
    /**
     * Visszaadja a játékos nevét.
     * @return A játékos neve
     */
    public String getNev() { return this.nev; }
}