package vezerles;

import gazdasag.KozosKassza;
import halozat.Checkpoint;
import halozat.Csomopont;
import java.util.ArrayList;
import java.util.List;

public class VarosModell implements IJatekKezelo {

    private List<Csomopont> varosGraf; 
    private KozosKassza kassza; 

    public VarosModell(){
        kassza = new KozosKassza(0);
        varosGraf = new ArrayList<>();
    }
    public VarosModell(KozosKassza kassza) {
        this.varosGraf = new ArrayList<>();
        this.kassza = kassza;
    }

    @Override
    public void epit() {
    }

    public void addCsomopont(Csomopont csp) {
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
        return new ArrayList<>(); 
    }
}