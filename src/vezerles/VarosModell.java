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

public class VarosModell implements IJatekKezelo {

    private List<Csomopont> varosGraf;
    private KozosKassza kassza;
    private int tickSzamlalo = 0;

    // --- ÚJ: 2D-s Mátrix a térképhez ---
    private Csomopont[][] varosMatrix;
    private int szelesseg;
    private int magassag;

    public VarosModell() {
        SkeletonLogger.create(this);
        this.kassza = new KozosKassza(0);
        this.varosGraf = new ArrayList<>();
        SkeletonLogger.exit(this);
    }

    public VarosModell(KozosKassza kassza) {
        SkeletonLogger.create(this);
        this.varosGraf = new ArrayList<>();
        this.kassza = kassza;
        SkeletonLogger.exit(this);
    }

    // --- ÚJ METÓDUSOK A 2D RÁCSHOZ ---
    
    /**
     * Létrehozza az üres 2D-s rácsot a városnak.
     */
    public void initRacs(int szelesseg, int magassag) {
        this.szelesseg = szelesseg;
        this.magassag = magassag;
        this.varosMatrix = new Csomopont[szelesseg][magassag];
        this.varosGraf.clear(); // Tisztítjuk a régi listát, ha új pályát építünk
    }

    /**
     * Hozzáad egy csomópontot egy konkrét X, Y koordinátára.
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
     * Visszaadja az adott koordinátán lévő csomópontot.
     */
    public Csomopont getCsomopont(int x, int y) {
        if (x >= 0 && x < szelesseg && y >= 0 && y < magassag) {
            return varosMatrix[x][y];
        }
        return null; // Ha a pályán kívülre mutat
    }

    public int getSzelesseg() { return szelesseg; }
    public int getMagassag() { return magassag; }


    // --- RÉGI METÓDUSOK ---

    public KozosKassza getKassza() {
        return kassza;
    }

    @Override
    public void epit() {
        SkeletonLogger.enter(this, "epit");
        SkeletonLogger.exit("void");
    }

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

    // Visszafelé kompatibilitás miatt megmaradt
    public void addCsomopont(Csomopont csp) {
        SkeletonLogger.enter(this, "addCsomopont", csp);
        this.varosGraf.add(csp);
        SkeletonLogger.exit("void");
    }

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

    public List<Csomopont> getVarosGraf() {
        return this.varosGraf;
    }
}