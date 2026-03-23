package halozat;
import jarmu.Jarmu;
import java.util.ArrayList;
import java.util.List;

/**
 * Olyan csomópont, ahol több útvonal találkozik, és elosztja a forgalmat.
 */
public class Keresztezodes extends Csomopont {

    private List<Csomopont> kimenetek;
    private List<Jarmu> bentLevoJarmuvek;

    public Keresztezodes() {
        this.kimenetek = new ArrayList<>();
        this.bentLevoJarmuvek = new ArrayList<>();
    }

    public void addKimenet(Csomopont csp) {
        this.kimenetek.add(csp);
    }

    // JAVÍTVA: void helyett boolean visszatérés
    @Override
    public boolean befogad(Jarmu jarmu) {
        System.out.println("> keresztezodes:Keresztezodes.befogad(jarmu)");
        
        // Ha nem akarjuk, hogy végtelen sok jármű legyen benne, itt lehetne ellenőrizni a foglaltságot.
        // Mivel a kereszteződés a korábbi kódod alapján listát használt, egyelőre mindig beengedjük:
        this.bentLevoJarmuvek.add(jarmu); 
        
        System.out.println("<- true");
        return true; 
    }

    @Override
    public void elenged(Jarmu jarmu) {
        this.bentLevoJarmuvek.remove(jarmu); 
    }

    @Override
    public void frissit() {
        // A szimulációs idő múlására reagáló kód
    }

    @Override
    public List<Csomopont> getNext() {
        return this.kimenetek;
    }

    @Override
    public void balesetEseten() {
        // Kereszteződésbeli baleset logikája
    }

    @Override
    public boolean foglalt() {
        // Visszaadja, hogy van-e benne jármű
        return !this.bentLevoJarmuvek.isEmpty();
    }

    // JAVÍTVA: Hiányzó hoesesEseten() metódus pótlása
    @Override
    public void hoesesEseten() {
        System.out.println("> keresztezodes:Keresztezodes.hoesesEseten()");
        // A dokumentáció alapján a kereszteződésre nem hat a havazás, így üresen hagyjuk
        System.out.println("<- void");
    }
}