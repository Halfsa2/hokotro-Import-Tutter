package halozat;
import jarmu.Jarmu;
import java.util.ArrayList;
import java.util.List;

/**
 * A hálózat speciális, végpontként szolgáló csomópontja.
 */
public class Checkpoint extends Csomopont {

    private Csomopont kimenet; 
    private List<Jarmu> varakozoJarmuvek; 

    public Checkpoint() {
        this.varakozoJarmuvek = new ArrayList<>();
    }

    public void setKimenet(Csomopont kimenet) {
        this.kimenet = kimenet;
    }

    // JAVÍTVA: void helyett boolean visszatérés
    @Override
    public boolean befogad(Jarmu jarmu) {
        System.out.println("\t\t> vegallomas1:Checkpoint.befogad(busz1:Busz)"); //log miatt
        this.varakozoJarmuvek.add(jarmu); 
        System.out.println("\t\t<- true"); //visszatérés logolása
        return true;
    }

    @Override
    public void elenged(Jarmu jarmu) {
        this.varakozoJarmuvek.remove(jarmu); 
    }

    @Override
    public void frissit() {
        // Checkpoint állapotának aktualizálása
    }

    @Override
    public List<Csomopont> getNext() {
        List<Csomopont> kimenetek = new ArrayList<>();
        if (kimenet != null) {
            kimenetek.add(kimenet);
        }
        return kimenetek;
    }

    @Override
    public void balesetEseten() {
        // Baleset esetén a Checkpointon
    }

    @Override
    public boolean foglalt() {
        // A te logikád alapján a Checkpoint valószínűleg sosem "foglalt" olyan értelemben, 
        // hogy ne tudna több járművet fogadni (mivel listája van), de itt ezt implementálhatod.
        return false;
    }

    // JAVÍTVA: Hiányzó hoesesEseten() metódus pótlása
    @Override
    public void hoesesEseten() {
    }
}