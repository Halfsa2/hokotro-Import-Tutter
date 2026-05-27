package vezerles;

import gazdasag.Arucikk;
import gazdasag.IMegvasarolhato;
import gazdasag.Jatekos;
import halozat.Csomopont;
import jarmu.Auto;
import jarmu.Hokotro;
import jarmu.Jarmu;

/**
 * A játék vezérlését (Controller) összefogó interfész.
 * Deklarálja azokat a metódusokat, amelyeken keresztül a grafikus nézet (View) 
 * interakcióba léphet a logikával (Model), irányíthatja a játékosokat, 
 * a járműveket és az idő folyását a szimulációban.
 */
public interface IJatekVezerlo {
    /**
     * Inicializálja a játékot: felépíti a pályát, elhelyezi az utakat, 
     * a kezdőpontokat és az alapértelmezett önvezető járműveket.
     */
    void initJatek();
    
    /**
     * Megkísérli az aktív játékos soron lévő járművét a megadott célcsomópontra léptetni.
     * @param cel A csomópont, amire a játékos lépni kíván
     * @return true, ha a lépés sikeres volt, egyébként false
     */
    boolean lep(Csomopont cel);
    /**
     * CSAK TESZTELÉSHEZ HASZNÁLHATÓ
     * Megkísérel egy konkrét (általában nem játékos által irányított) járművet a célcsomópontra léptetni.
     * @param jarmu A léptetni kívánt jármű
     * @param cel A csomópont, ahová a jármű lépne
     * @return true, ha a lépés sikeres volt, egyébként false
     */
    boolean lep(Jarmu jarmu, Csomopont cel);
    
    /**
     * Lezárja az aktuális játékos körét, és a listában soron következő játékosnak adja az irányítást.
     */
    void nextJatekos();
    
    /**
     * Egy árucikk megvásárlását kezdeményezi a boltban az aktív játékos számára.
     * @param termek A megvásárolni kívánt árucikk (Arucikk enum)
     * @param gep A hókotró, amelyre a felszerelés kerül (ha releváns, egyébként null)
     */
    void vasarol(Arucikk termek, Hokotro gep);
    
    /**
     * Statisztikai céllal regisztrálja a rendszerben egy új játékos típusát.
     * @param tipus A játékos típusa (pl. "Sofor", "Takarito")
     */
    void registerJatekos(String tipus);
    
    /**
     * Hozzáad egy új játékos objektumot a játékhoz, bevonva őt a körökre osztott vezérlésbe.
     * @param jatekos A hozzáadandó játékos
     */
    void addJatekos(Jatekos<?> jatekos);
    
    /**
     * Hozzáad egy új, mesterséges intelligencia által vezérelt önvezető autót a városhoz.
     * @param auto A hozzáadandó autó
     */
    void addAuto(Auto auto);
    
    /**
     * Mesterségesen előre lépteti az időt a játékban megadott számú körrel.
     * Főként teszteléshez vagy időugratáshoz (Cheat) használatos.
     * @param korokSzama A léptetni kívánt körök (tickek) száma
     */
    public void tick(int korokSzama);
    
    /**
     * Lekérdezi a bolt interfészt, amelyen keresztül az árucikkek elérhetők.
     * @return A boltot reprezentáló IMegvasarolhato objektum
     */
    public IMegvasarolhato getBolt();

    /**
     * Lekérdezi a város és a hálózat állapotát kezelő modellt.
     * @return A városmodellt reprezentáló IJatekKezelo objektum
     */
    public IJatekKezelo getVarosModell();

    /**
     * Lekérdezi a jelenleg soron lévő (irányító) játékost.
     * @return Az aktív Jatekos objektum
     */
    public Jatekos<?> getAktivJatekos();

}
