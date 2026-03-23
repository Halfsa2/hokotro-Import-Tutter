package vezerles;

import gazdasag.KozosKassza;
import halozat.Checkpoint;
import halozat.Csomopont;
import java.util.ArrayList;
import java.util.List;

public class VarosModell implements IJatekKezelo {

    private List<Csomopont> varosGraf; 
    private KozosKassza kassza; 

    public VarosModell(KozosKassza kassza) {
        this.varosGraf = new ArrayList<>();
        this.kassza = kassza;
    }

    @Override
    public void epit() {
        System.out.println("> varosmodell:VarosModell.epit()");
        System.out.println("<- void");
    }

    public void addCsomopont(Csomopont csp) {
        this.varosGraf.add(csp); 
    }

    @Override
    public void palyaFrissit() {
        System.out.println("> varosmodell:VarosModell.palyaFrissit()");
        for (Csomopont csp : varosGraf) {
            csp.frissit();
        }
        System.out.println("<- void");
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
        System.out.println("> varosmodell:VarosModell.havazas()");
        // Az Utszakasz lista helyett a varosGraf csomópontjain (pl. Sávokon) hívjuk a havazást
        for (Csomopont csp : varosGraf) {
            csp.hoesesEseten(); 
        }
        System.out.println("<- void");
    }

    @Override
    public List<Csomopont> legrovidebbUtvonal(Csomopont start, Csomopont cel) {
        return new ArrayList<>(); 
    }
}