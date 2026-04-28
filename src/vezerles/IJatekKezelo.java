package vezerles;
import gazdasag.KozosKassza;
import halozat.Checkpoint;
import halozat.Csomopont;
import java.util.List;

/**
 * A szimuláció és a játékmenet globális irányításához szükséges alapvető műveleteket tartalmazó interfész.
 * Biztosítja, hogy a játék motorja egy egységes felületen keresztül tudjon kommunikálni a modellel.
 */
public interface IJatekKezelo {
    
    /**
     * Előírja a játékbeli építkezési funkció megvalósítását[cite: 916].
     */
    void epit();
    
    /**
     * A teljes szimulációs játéktér (pálya) állapotának frissítését írja elő[cite: 916].
     */
    void palyaFrissit();
    
    /**
     * Egy olyan lekérdező funkciót definiál, amelynek feladata megkeresni és visszaadni 
     * egy éppen szabad (jármű által nem elfoglalt) ellenőrzőpontot a hálózatban[cite: 917].
     * * @return Egy szabad Checkpoint objektum.
     */
    Checkpoint getSzabadCheckpoint();
    
    /**
     * Egy globális időjárási esemény (havazás) elindítását deklarálja, amely a meghívásakor 
     * kihatással lesz a pálya útszakaszaira[cite: 918].
     */
    void havazas();
    
    /**
     * A város közös kasszájának elérését biztosító getter metódus, amely a játékosok pénzügyi tranzakcióihoz szükséges.
     * @return kassza a város közös kasszája
     */
    KozosKassza getKassza();

    /**
     * Előírja egy útvonaltervező szolgáltatás biztosítását, amely két megadott paraméter 
     * (start és cél csomópont) között megkeresi, és csomópontok listájaként visszaadja 
     * az optimális útvonalat[cite: 923].
     * @param start A kiindulási csomópont.
     * @param cel A cél csomópont.
     * @return Az optimális útvonalat alkotó csomópontok listája.
     */
    List<Csomopont> legrovidebbUtvonal(Csomopont start, Csomopont cel);
}
