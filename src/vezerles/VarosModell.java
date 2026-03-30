package vezerles;

import gazdasag.KozosKassza;
import halozat.Checkpoint;
import halozat.Csomopont;
import java.util.ArrayList;
import java.util.List;

public class VarosModell implements IJatekKezelo {

    private List<Csomopont> varosGraf;
    private KozosKassza kassza;

    /**
     * Alapértelmezett konstruktor: új, üres városi gráfot hoz létre és üres pénztárcát inicializál.
     */
    public VarosModell() {
        kassza = new KozosKassza(0);
        varosGraf = new ArrayList<>();
    }

    /**
     * Konstruktor meglévő közös kasszával.
     * @param kassza a város közös kasszája
     */
    public VarosModell(KozosKassza kassza) {
        this.varosGraf = new ArrayList<>();
        this.kassza = kassza;
    }

    @Override
    public void epit() {
        // Város pálya felépítése, inicializáció (implementálandó szimulációban)
    }

    public void addCsomopont(Csomopont csp) {
        /**
         * Hozzáad egy csomópontot a város gráfhoz.
         * @param csp a hozzáadandó csomópont
         */
        this.varosGraf.add(csp);
    }

    @Override
    public void palyaFrissit() {
        SkeletonLogger.enter(this, "palyaFrissit");
        for (Csomopont csp : varosGraf) {
            csp.frissit();
        }
        SkeletonLogger.exit("void");
    }

    @Override
    public Checkpoint getSzabadCheckpoint() {
        /**
         * Kikeresi az első szabad (foglaltlan) checkpointot a városi gráfban.
         * @return szabad Checkpoint objektum vagy null, ha nincs szabad
         */
        for (Csomopont csp : varosGraf) {
            if (csp instanceof Checkpoint && !csp.foglalt()) {
                return (Checkpoint) csp;
            }
        }
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

    @Override
    public List<Csomopont> legrovidebbUtvonal(Csomopont start, Csomopont cel) {
        /**
         * Egyszerű sablon: legrovidebb útvonal számítása (még nem implementált).
         * @param start a kezdő csomópont
         * @param cel a cél csomópont
         * @return jelenleg üres lista (később optimális útvonalat kell adjon)
         */
        return new ArrayList<>();
    }
}