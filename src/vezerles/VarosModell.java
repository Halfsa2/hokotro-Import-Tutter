package vezerles;
import gazdasag.KozosKassza;
import halozat.Checkpoint;
import halozat.Csomopont;
import halozat.Utszakasz;
import java.util.ArrayList;
import java.util.List;

/**
 * A szimuláció központi "Menedzser" osztálya, amely az egész várost összefogja [cite: 1296-1297].
 */
public class VarosModell implements IJatekKezelo {

    // A várost alkotó csomópontok listája (Gráf) [cite: 1302-1303].
    private List<Csomopont> varosGraf; 
    
    // Nyilvántartja a hálózat nagyobb logikai egységeit, az útszakaszokat [cite: 1304-1305].
    private List<Utszakasz> utszakaszok; 
    
    private KozosKassza kassza; // Hozzáférés a játék központi költségvetéséhez [cite: 1310]

    public VarosModell(KozosKassza kassza) {
        this.varosGraf = new ArrayList<>();
        this.utszakaszok = new ArrayList<>();
        this.kassza = kassza;
    }

    /**
     * Új infrastruktúra építését indítja el a játékban[cite: 1319].
     */
    @Override
    public void epit() {
        // Gráf, szakaszok, sávok és Checkpointok generálása
        System.out.println("Zúzmaraváros megépült!");
    }

    public void addCsomopont(Csomopont csp) {
        this.varosGraf.add(csp); // Dinamikusan bővíti az úthálózatot [cite: 1312]
    }

    public void addUtszakasz(Utszakasz szakasz) {
        this.utszakaszok.add(szakasz);
    }

    /**
     * A teljes szimulációs pálya időlépésenkénti (tick) frissítését koordinálja[cite: 1313].
     */
    @Override
    public void palyaFrissit() {
        // Végigiterál a gráf elemein, és meghívja a frissit() metódust [cite: 1314]
        for (Csomopont csp : varosGraf) {
            csp.frissit();
        }
    }

    /**
     * Lekérdezi a hálózatot, és visszaad egy olyan ellenőrzőpontot, amely szabad[cite: 1317].
     */
    @Override
    public Checkpoint getSzabadCheckpoint() {
        for (Csomopont csp : varosGraf) {
            if (csp instanceof Checkpoint && !csp.foglalt()) {
                return (Checkpoint) csp; // Visszaadja a szabad végpontot [cite: 1318]
            }
        }
        return null;
    }

    /**
     * Globális időjárási eseményt indít el az útszakaszokon[cite: 1315].
     */
    @Override
    public void havazas() {
        for (Utszakasz szakasz : utszakaszok) {
            szakasz.hoesesEseten(); // Kihat az egész városra [cite: 1315]
        }
    }

    /**
     * Útvonaltervező algoritmust futtat a várost reprezentáló gráfon (pl. BFS/Dijkstra)[cite: 1316].
     */
    @Override
    public List<Csomopont> legrovidebbUtvonal(Csomopont start, Csomopont cel) {
        // Ide kerül egy Pathfinding algoritmus (Breadth-First Search). 
        // A szkeletonhoz egyelőre elég egy üres listát visszaadni.
        System.out.println("Útvonaltervezés: " + start + " -> " + cel);
        return new ArrayList<>(); 
    }
}