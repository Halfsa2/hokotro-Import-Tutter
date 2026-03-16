package jarmu;
import halozat.Csomopont;
/**
 * Az IranyitottJarmu egy absztrakt osztály a modellben. 
 * Olyan járműveket reprezentál, amelyeket speciális logika irányít, 
 * és a tevékenységükkel (például hóeltakarítás vagy utasok szállítása) 
 * pénzt tudnak gyűjteni a KozosKassza számára[cite: 947].
 */
public abstract class IranyitottJarmu extends Jarmu {

    /**
     * Ez a metódus felelős a jármű mozgatásához szükséges vezérlésért 
     * egy megadott cél csomópont irányába[cite: 951]. 
     * További feladata a lépés után fellépő speciális mellékhatásokat 
     * (például takarítás) hajtja végre[cite: 952].
     * * Mivel absztrakt, a leszármazottaknak (Busz, Hokotro) 
     * kötelező megvalósítaniuk a saját specifikus logikájukkal.
     *
     * @param cel A csomópont, ahová a jármű lépni próbál.
     * @return Igaz, ha a lépés sikeres volt.
     */
    @Override
    public abstract boolean lep(Csomopont cel);

}
