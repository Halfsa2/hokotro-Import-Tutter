package vezerles;

import allapot.MelyHo;
import gazdasag.KozosKassza;
import halozat.Checkpoint;
import halozat.Csomopont;
import halozat.Sav;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * A város működéséért és a szimuláció logikájáért felelős központi osztály.
 * Nyilvántartja a hálózatot gráfként és 2D mátrixként is, kezeli a közös kasszát, 
 * irányítja az idő múlását (tick), az időjárást (havazás), és biztosítja 
 * az önvezető járművek számára az útvonalkeresést (BFS).
 */
public class VarosModell implements IJatekKezelo {

    /** A város topológiáját alkotó csomópontok lineáris listája. */
    private List<Csomopont> varosGraf;
    /** A globális közös kassza, amibe a játékosok a pénzt gyűjtik. */
    private KozosKassza kassza;
    /** A játékban eltelt időegységeket (köröket) mérő számláló. */
    private int tickSzamlalo = 0;

    // --- ÚJ: 2D-s Mátrix a térképhez ---
    /** A várost reprezentáló kétdimenziós rács a grafikus megjelenítéshez és koordináta-alapú eléréshez. */
    private Csomopont[][] varosMatrix;
    /** A 2D rács szélessége (oszlopok száma). */
    private int szelesseg;
    /** A 2D rács magassága (sorok száma). */
    private int magassag;

    /**
     * Alapértelmezett konstruktor.
     * Inicializálja a várost egy üres gráffal és egy 0 egyenlegű közös kasszával.
     */
    public VarosModell() {
        SkeletonLogger.create(this);
        this.kassza = new KozosKassza(0);
        this.varosGraf = new ArrayList<>();
        SkeletonLogger.exit(this);
    }
    /**
     * Paraméteres konstruktor a VarosModell osztályhoz.
     * @param kassza A város által használandó előre létrehozott közös kassza
     */
    public VarosModell(KozosKassza kassza) {
        SkeletonLogger.create(this);
        this.varosGraf = new ArrayList<>();
        this.kassza = kassza;
        SkeletonLogger.exit(this);
    }

    // --- ÚJ METÓDUSOK A 2D RÁCSHOZ ---
    
    /**
     * Létrehozza az üres 2D-s rácsot a városnak a megadott dimenziókkal.
     * Ha korábban volt már pálya építve, a gráfot kiüríti.
     * @param szelesseg A pálya szélessége (X tengely)
     * @param magassag A pálya magassága (Y tengely)
     */
    public void initRacs(int szelesseg, int magassag) {
        this.szelesseg = szelesseg;
        this.magassag = magassag;
        this.varosMatrix = new Csomopont[szelesseg][magassag];
        this.varosGraf.clear(); // Tisztítjuk a régi listát, ha új pályát építünk
    }

    /**
     * Hozzáad egy csomópontot egy konkrét (X, Y) koordinátára a 2D rácsban, 
     * és beteszi a lineáris gráfba is, ha még nincs benne.
     * @param x Az X koordináta (oszlop)
     * @param y Az Y koordináta (sor)
     * @param csp A lehelyezendő Csomopont objektum
     */
    public void addCsomopont(int x, int y, Csomopont csp) {
        SkeletonLogger.enter(this, "addCsomopont", csp);
        if (x >= 0 && x < szelesseg && y >= 0 && y < magassag) {
            this.varosMatrix[x][y] = csp;
            
            // Csak akkor adjuk a listához, ha még nincs benne (duplikáció elkerülése)
            if (!this.varosGraf.contains(csp)) {
                this.varosGraf.add(csp); 
            }
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Visszaadja az adott (X, Y) koordinátán lévő csomópontot.
     * @param x Keresett X koordináta
     * @param y Keresett Y koordináta
     * @return A csomópont objektum, vagy null, ha a koordináta a pályán kívül esik, vagy a cella üres
     */
    public Csomopont getCsomopont(int x, int y) {
        if (x >= 0 && x < szelesseg && y >= 0 && y < magassag) {
            return varosMatrix[x][y];
        }
        return null; // Ha a pályán kívülre mutat
    }

    /**
     * Lekérdezi a városrács szélességét.
     * @return A rács szélessége
     */
    public int getSzelesseg() { return szelesseg; }
    /**
     * Lekérdezi a városrács magasságát.
     * @return A rács magassága
     */
    public int getMagassag() { return magassag; }


    // --- RÉGI METÓDUSOK ---

    /**
     * Lekérdezi a városhoz tartozó közös kasszát.
     * @return A KozosKassza objektum
     */
    public KozosKassza getKassza() {
        return kassza;
    }

    /**
     * Elméleti metódus a város építésének indítására. 
     * Jelenleg a pályát máshol hozzuk létre, így ez üres.
     */
    @Override
    public void epit() {
        SkeletonLogger.enter(this, "epit");
        SkeletonLogger.exit("void");
    }

    /**
     * CSAK TESZTELÉSHEZ HASZNÁLHATÓ
     * Lépteti a város globális idejét (1 tick).
     * Frissíti a pályát (olvadás), és minden 3. tickben havazást idéz elő a városban.
     */
    @Override
    public void tick() {
        SkeletonLogger.enter(this, "tick");
        tickSzamlalo++;
        System.out.println("Eltelt egy tick... (Jelenlegi idő: " + tickSzamlalo + ")");
        
        if (tickSzamlalo % 3 == 0) {
            System.out.println("Eltelt 3 időegység: Újabb hó hullik a városra!");
            this.havazas(); 
        }
        
        palyaFrissit();
        SkeletonLogger.exit("void");
    }

    /**
     * Visszafelé kompatibilitás miatt megmaradt metódus.
     * Csak a lineáris gráfhoz ad hozzá egy csomópontot (koordináta nélkül).
     * @param csp A hozzáadni kívánt csomópont
     */
    public void addCsomopont(Csomopont csp) {
        SkeletonLogger.enter(this, "addCsomopont", csp);
        this.varosGraf.add(csp);
        SkeletonLogger.exit("void");
    }

    /**
     * Meghívja minden csomóponton a frissítést (idő múlásának hatásai, pl. sózás lejárata, jégolvadás).
     */
    @Override
    public void palyaFrissit() {
        SkeletonLogger.enter(this, "palyaFrissit");
        // Itt mostmár ellenőrizzük a null-t is, mert a mátrix üres cellái null-ok!
        for (Csomopont csp : varosGraf) {
            if (csp != null) {
                csp.frissit();
            }
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Keres a hálózatban egy szabad, jármű által nem foglalt Checkpointot.
     * @return Egy szabad Checkpoint objektum, vagy null, ha nincs ilyen
     */
    @Override
    public Checkpoint getSzabadCheckpoint() {
        SkeletonLogger.enter(this, "getSzabadCheckpoint");
        for (Csomopont csp : varosGraf) {
            if (csp != null && csp instanceof Checkpoint && !csp.foglalt()) {
                SkeletonLogger.exit(csp);
                return (Checkpoint) csp;
            }
        }
        SkeletonLogger.exit(null);
        return null;
    }

    /**
     * Havazást idéz elő a város összes csomópontján.
     */
    @Override
    public void havazas() {
        SkeletonLogger.enter(this, "havazas");
        for (Csomopont csp : varosGraf) {
            if (csp != null) {
                csp.hoesesEseten();
            }
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Megkeresi a legrövidebb útvonalat a kezdő és cél csomópont között az önvezető autók számára.
     * Az alapértelmezett viselkedés, hogy megpróbálja kikerülni a mély havat.
     * @param start A kiindulási csomópont
     * @param cel A cél csomópont
     * @return Az útvonalat alkotó csomópontok listája (a start csomóponttal kezdve)
     */
@Override
public List<Csomopont> legrovidebbUtvonal(Csomopont start, Csomopont cel) {
    SkeletonLogger.enter(this, "legrovidebbUtvonal", start, cel);
    
    // A-TERV: Keresünk egy utat úgy, hogy szigorúan kikerüljük a havat
    List<Csomopont> utvonal = bfsKereses(start, cel, true);
    /*
    // B-TERV: Ha nincs végig tiszta út, keresünk egyet a hó figyelmen kívül hagyásával
    if (utvonal.isEmpty()) {
        utvonal = bfsKereses(start, cel, false);
    }
    */
    
    SkeletonLogger.exit("lista");
    return utvonal;
}

/**
     * Szélességi keresés (BFS) algoritmussal megkeresi a legrövidebb utat két pont között.
     * @param start A kiindulási csomópont
     * @param cel A cél csomópont
     * @param keruldAHavat Ha true, az algoritmus élből elutasítja azokat a sávokat, ahol mély hó van
     * @return A legrövidebb útvonal listája. Ha nincs elérhető út, üres listát ad vissza.
     */
private List<Csomopont> bfsKereses(Csomopont start, Csomopont cel, boolean keruldAHavat) {
    List<Csomopont> utvonal = new ArrayList<>();
    if (start == null || cel == null) return utvonal;
    

    if (start.equals(cel)) {
        utvonal.add(start);
        return utvonal;
    }

    Queue<Csomopont> sor = new LinkedList<>();
    Map<Csomopont, Csomopont> szuloMap = new HashMap<>(); 
    sor.add(start);
    szuloMap.put(start, null); 
    boolean megtalaltuk = false;

    while (!sor.isEmpty()) {
        Csomopont aktualis = sor.poll();
        if (aktualis.equals(cel)) {
            megtalaltuk = true;
            break;
        }
        
        List<Csomopont> szomszedok = aktualis.getNext();
        if (szomszedok != null) {
            for (Csomopont szomszed : szomszedok) {
                if (szomszed != null && !szuloMap.containsKey(szomszed)) {
                    
                    // Itt döntjük el, hogy kikerüljük-e a havat
                    if (keruldAHavat && szomszed instanceof Sav sav && sav.getAllapot() instanceof MelyHo) {
                        continue; 
                    }
                    
                    szuloMap.put(szomszed, aktualis); 
                    sor.add(szomszed);
                }
            }
        }
    }

    if (megtalaltuk) {
        Csomopont lepes = cel;
        while (lepes != null) {
            utvonal.add(lepes);
            lepes = szuloMap.get(lepes); 
        }
        Collections.reverse(utvonal);
    }
    return utvonal;
}

    /**
     * Visszaadja a város összes csomópontját tartalmazó lineáris gráfot.
     * @return A csomópontok listája
     */
    public List<Csomopont> getVarosGraf() {
        return this.varosGraf;
    }
}