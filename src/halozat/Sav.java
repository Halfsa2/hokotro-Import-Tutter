package halozat;
import allapot.Savallapot;
import allapot.Tiszta;
import jarmu.Jarmu;
import java.util.ArrayList;
import java.util.List;

/**
 * A közlekedési hálózat egy irányított alapegysége.
 */
public class Sav extends Csomopont {
    
    private Savallapot allapot; // Az aktuális állapot (State minta) [cite: 1123]
    protected int sozott; // A sáv aktuális sózottsági szintjét tárolja [cite: 1125]
    private Jarmu rajtaAlloJarmu; // A sávon tartózkodó jármű [cite: 1127]
    private List<Csomopont> kovetkezoCsomopontok; // Navigálható irányok [cite: 1120]

    public Sav() {
        this.allapot = new Tiszta(); // Alapértelmezetten tiszta
        this.sozott = 0;
        this.kovetkezoCsomopontok = new ArrayList<>();
    }

    public void setAllapot(Savallapot s) {
        this.allapot = s; // [cite: 1143]
    }

    // --- Csomopont absztrakt metódusainak megvalósítása ---

    @Override
    public void befogad(Jarmu jarmu) {
        this.rajtaAlloJarmu = jarmu;
        this.allapot.befogad(this, jarmu); // Delegálás az állapotnak [cite: 1137]
    }

    @Override
    public void elenged(Jarmu jarmu) {
        this.rajtaAlloJarmu = null;
        this.allapot.elenged(this, jarmu); // Delegálás az állapotnak [cite: 1138]
    }

    @Override
    public void frissit() {
        if (this.sozott > 0) {
            this.sozott--; // Sózottság csökkenhet az idő múlásával [cite: 1131]
        }
        this.allapot.frissit(this);
    }

    @Override
    public List<Csomopont> getNext() {
        return this.kovetkezoCsomopontok; // [cite: 1139]
    }

    @Override
    public boolean foglalt() {
        return this.rajtaAlloJarmu != null; // [cite: 1127]
    }

    @Override
    public void balesetEseten() {
        // Baleset logikája (pl. sáv lezárása 3 körre) [cite: 1142]
        if (this.rajtaAlloJarmu != null) {
            this.rajtaAlloJarmu.balesetetSzenved();
        }
    }

    // --- Sáv specifikus metódusok ---

    public boolean lepesTeszt(Jarmu jarmu) {
        return this.allapot.lepesTeszt(jarmu); // [cite: 1140]
    }

    public void hoesesEseten() {
        // Ha sózott, nem esik le a hó, egyébként delegáljuk az állapotnak
        if (this.sozott == 0) {
            this.allapot.hoesesEseten(this); // [cite: 1141]
        }
    }

    public void soSzoras() {
        this.sozott = 9; // A só hatása 9 szimulációs körig tart [cite: 476, 1130]
        this.allapot.sotKap(this);
    }

    public boolean hoTisztit() {
        return this.allapot.hoTisztit(this); // [cite: 1129]
    }

    public boolean jegTisztit() {
        return this.allapot.jegTisztit(this); // [cite: 1128]
    }
}
