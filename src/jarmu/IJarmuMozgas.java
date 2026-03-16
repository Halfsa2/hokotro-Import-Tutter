package jarmu;
import halozat.Csomopont;
/**
 * Az IJarmuMozgas egy interfész, amely a mozgásra képes entitások (járművek) alapvető viselkedését definiálja[cite: 905].
 * Fő felelőssége, hogy előírja azt a közös és kötelező műveletet, 
 * amellyel egy objektum a hálózat egyik csomópontjáról egy másikra tud lépni[cite: 906].
 */
public interface IJarmuMozgas {
    
    /**
     * Azt az eseményt modellezi, amikor a jármű megpróbál rálépni a paraméterként megadott 
     * célállomásra (egy Csomopont objektumra)[cite: 908].
     * * @param cel A cél csomópont, ahová a jármű lépni szeretne.
     * @return A logikai (boolean) visszatérési érték azt jelzi a hívó fél számára, 
     * hogy a lépés sikeres volt-e (például sikeres, ha a cél csomópont szabad volt, 
     * és sikertelen, ha foglalt vagy baleset történt rajta)[cite: 909].
     */
    boolean lep(Csomopont cel);
}
