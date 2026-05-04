package vezerles;

import gazdasag.KozosKassza;
import halozat.Checkpoint;
import halozat.Csomopont;
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

    /**
     * Alapértelmezett konstruktor: új, üres városi gráfot hoz létre és üres pénztárcát inicializál.
     */
    public VarosModell() {
        SkeletonLogger.create(this);
        this.kassza = new KozosKassza(0);
        this.varosGraf = new ArrayList<>();
        SkeletonLogger.exit(this);
    }
    /**
     * Visszaadja a város közös kasszáját, amely a játékosok pénzügyi tranzakcióihoz szükséges.
     * @return kassza a város közös kasszája
     */
    public KozosKassza getKassza() {
        return kassza;
    }
    /**
     * Konstruktor meglévő közös kasszával.
     * @param kassza a város közös kasszája
     */
    public VarosModell(KozosKassza kassza) {
        SkeletonLogger.create(this);
        this.varosGraf = new ArrayList<>();
        this.kassza = kassza;
        SkeletonLogger.exit(this);
    }

    @Override
    public void epit() {
        SkeletonLogger.enter(this, "epit");
        // A manuális építést majd a parancsértelmező (Main) végzi a create és connect parancsokkal
        SkeletonLogger.exit("void");
    }

    /**
     * Hozzáad egy csomópontot a város gráfhoz.
     * @param csp a hozzáadandó csomópont
     */
    public void addCsomopont(Csomopont csp) {
        SkeletonLogger.enter(this, "addCsomopont", csp);
        this.varosGraf.add(csp);
        SkeletonLogger.exit("void");
    }

    @Override
    public void palyaFrissit() {
        SkeletonLogger.enter(this, "palyaFrissit");
        for (Csomopont csp : varosGraf) {
            csp.frissit();
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Kikeresi az első szabad (foglaltlan) checkpointot a városi gráfban.
     * @return szabad Checkpoint objektum vagy null, ha nincs szabad
     */
    @Override
    public Checkpoint getSzabadCheckpoint() {
        SkeletonLogger.enter(this, "getSzabadCheckpoint");
        for (Csomopont csp : varosGraf) {
            if (csp instanceof Checkpoint && !csp.foglalt()) {
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
            csp.hoesesEseten();
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Legrovidebb útvonal számítása két csomópont között (BFS Algoritmus).
     * @param start a kezdő csomópont
     * @param cel a cél csomópont
     * @return Az útvonalat alkotó csomópontok listája
     */
    /*
    Az autó kiindul a Start pontból. Megkérdezi a szomszédait (pl. Sáv1, Sáv2), és felírja őket egy listára.
    Ezután megnézi Sáv1 szomszédait, majd Sáv2 szomszédait, és így tovább, hullámokban terjedve a hálózaton.
    Mivel egy Map-ben (szótárban) felírja, hogy melyik sávra melyik előző sávról lépett rá,
    amikor megtalálja a Célt, egyszerűen csak "visszagöngyölíti" a szálat a céltól a startig,
    megfordítja a listát, és kész is a tökéletes útvonal!
    */
    @Override
    public List<Csomopont> legrovidebbUtvonal(Csomopont start, Csomopont cel) {
        SkeletonLogger.enter(this, "legrovidebbUtvonal", start, cel);
        
        List<Csomopont> utvonal = new ArrayList<>();
        
        if (start == null || cel == null) {
            SkeletonLogger.exit("ures_lista");
            return utvonal;
        }

        // 1. Ha a start és a cél megegyezik
        if (start.equals(cel)) {
            utvonal.add(start);
            SkeletonLogger.exit("lista");
            return utvonal;
        }

        // 2. BFS inicializálása
        Queue<Csomopont> sor = new LinkedList<>();
        Map<Csomopont, Csomopont> szuloMap = new HashMap<>(); // Nyilvántartja, honnan jöttünk
        
        sor.add(start);
        szuloMap.put(start, null); // A start csomópontnak nincs "szülője"
        
        boolean megtalaltuk = false;

        // 3. Keresés
        while (!sor.isEmpty()) {
            Csomopont aktualis = sor.poll();
            
            // Ha elértük a célt, leállítjuk a keresést
            if (aktualis.equals(cel)) {
                megtalaltuk = true;
                break;
            }
            
            // Szomszédok bejárása
            List<Csomopont> szomszedok = aktualis.getNext();
            if (szomszedok != null) {
                for (Csomopont szomszed : szomszedok) {
                    // Ha a szomszédot még nem vizsgáltuk meg (nincs benne a map-ben)
                    if (szomszed != null && !szuloMap.containsKey(szomszed)) {
                        szuloMap.put(szomszed, aktualis); // Megjegyezzük, hogy az "aktualis"-ból léptünk ide
                        sor.add(szomszed);
                    }
                }
            }
        }

        // 4. Útvonal visszafejtése
        if (megtalaltuk) {
            Csomopont lepes = cel;
            while (lepes != null) {
                utvonal.add(lepes);
                lepes = szuloMap.get(lepes); // Visszalépünk a szülőre
            }
            // Mivel a céltól haladtunk a start felé, a listát meg kell fordítani!
            Collections.reverse(utvonal);
        }

        SkeletonLogger.exit("lista");
        return utvonal;
    }

    public List<Csomopont> getVarosGraf() {
        return this.varosGraf;
    }
}