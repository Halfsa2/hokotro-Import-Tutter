package halozat;
import java.util.ArrayList;
import java.util.List;

/**
 * A szimulációs hálózatban a kereszteződéseket összekötő éleket reprezentálja.
 */
public class Utszakasz {
    
    // Egy útszakasz fizikailag sávokból épül fel (kompozíció).
    protected List<Sav> savok; 

    public Utszakasz() {
        this.savok = new ArrayList<>();
    }

    public void addSav(Sav sav) {
        this.savok.add(sav);
    }

    /**
     * A havazás eseményének lekezelése az egész útszakaszon.
     * Végigiterál a hozzá tartozó sávokon, és mindegyiknek továbbítja az eseményt.
     */
    public void hoesesEseten() {
        for (Sav s : savok) {
            s.hoesesEseten();
        }
    }
}
