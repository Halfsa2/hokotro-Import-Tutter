package halozat;
import java.util.List;
import jarmu.Jarmu;

/**
 * A város alapegysége, az úthálózat egy absztrakt eleme[cite: 841].
 * Mivel absztrakt osztály, sosem példányosítjuk közvetlenül[cite: 843].
 */
public abstract class Csomopont {
    
    /**
     * Absztrakt metódus. A szimulációs idő múlására reagáló metódus, amely lehetővé teszi 
     * a csomópontok állapotának aktualizálását (például környezeti hatások, havazás vagy 
     * akadályok eltűnésének kezelését az egyes leszármazottaknál)[cite: 853].
     */
    public abstract void frissit();
    
    /**
     * Absztrakt metódus. Ennek a megvalósítása teszi lehetővé a leszármazottaknál, 
     * hogy egy jármű szabályosan ráléphessen az adott csomópontra és adminisztrálja 
     * annak megérkezését[cite: 854].
     */
    public abstract void befogad(Jarmu jarmu);
    
    /**
     * Absztrakt metódus. Ennek segítségével valósítható meg a jármű távozásának regisztrálása, 
     * amint az letér a csomópontról, ezáltal felszabadítva azt a további forgalom számára[cite: 855].
     */
    public abstract void elenged(Jarmu jarmu);
    
    /**
     * Absztrakt metódus. Visszaadja a Csomopont-ból közvetlenül elérhető következő 
     * csomópontok listáját. Ezt használhatják a járművek a továbbindulás lehetséges 
     * irányainak lekérdezésére[cite: 856, 857].
     */
    public abstract List<Csomopont> getNext();
    
    /**
     * Metódus, amely előírja a balesetek lokális lekezelését. Célja, hogy ha az adott 
     * csomóponton baleset történik, a leszármazott osztályok (pl. Sav) megvalósítsák a 
     * szükséges adminisztrációt, például az útszakasz lezárását[cite: 858].
     */
    public abstract void balesetEseten();
    
    /**
     * Lekérdezi, hogy a Csomopont az adott pillanatban szabad-e[cite: 859].
     * @return Logikai értékkel tér vissza (igaz, ha tartózkodik rajta jármű, és hamis, ha nem)[cite: 860].
     */
    public abstract boolean foglalt();
}
