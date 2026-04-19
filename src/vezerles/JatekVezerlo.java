package vezerles;

import gazdasag.Arucikk;
import gazdasag.IMegvasarolhato;
import gazdasag.Jatekos;
import gazdasag.Takarito;
import halozat.Csomopont;
import jarmu.Auto;
import jarmu.Hokotro;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import megjelenites.IJatekNezet;

public class JatekVezerlo {
    private IJatekNezet nezet;
    private IJatekKezelo modell;
    private IMegvasarolhato bolt;
    private HashMap<Auto,List<Csomopont>> autoUtvonalak;
    private List<Jatekos<?>> jatekosok;
    private boolean jatekVege = false;
    //Az első körben esik a hó, hogy legyen valami a pályán.
    private int korokHoesesOta = 999;
    private Jatekos<?> aktivJatekos;

    public JatekVezerlo(IJatekNezet nezet, IJatekKezelo modell, IMegvasarolhato bolt) {
        this.nezet = nezet;
        this.modell = modell;
        this.bolt = bolt;
        this.jatekosok = new ArrayList<>();
        autoUtvonalak = new HashMap<>();
        
    }
    public void initJatek(){
        //TODO: autók hozzáadása, játékosok járműveinek beállítása, modell inicializálása, stb.
    }
    public void lep(Csomopont cel){
        if(jatekVege) return;
        if(aktivJatekos != null){
            //Itt nem számít a lépés eredménye, ha sikertelen a lépés, akkor is tovább lépünk a következő játékosra/járműre.
            aktivJatekos.lep(cel);
            //Ha a játékos körének vége van, akkor következik a következő játékos, egyébként a következő járműve lép.
            if(aktivJatekos.isKorVege()) {nextJatekos();}
            else{aktivJatekos.nextJarmu();}
        }
    }
    public void nextJatekos(){
        if(jatekVege) return;
        //minden 3. körben leesik a hó.
        if(korokHoesesOta >=2) {modell.havazas();korokHoesesOta = 0;}else{korokHoesesOta++;}

        //Ha ez lesz az első kör a játékban, akkor beállítjuk az első játékost aktívnak és NEM lépnek még az autók
        if(aktivJatekos == null){aktivJatekos = jatekosok.getFirst(); aktivJatekos.korKezdodik(); return;}

        //Ha nem ez az első kör, akkor megszerezzük a jelenlegi játékos ID-jét
        int currentId = jatekosok.indexOf(aktivJatekos);

        //Ha az utolsó játékos volt legutóbb, akkor az első jön
        if(currentId == jatekosok.size()-1) currentId = -1;
        
        //autók lépnek még a kör vége előtt
        autokKore();
        //só hatása most érvényesül (első körben úgysem lesz semmi, ami miatt frissíteni kéne, így nem baj, hogy ez az egyik return után van)
        modell.palyaFrissit();

        //hivatalosan a kör vége, kövi játékos következik.
        aktivJatekos = jatekosok.get(currentId+1);
        aktivJatekos.korKezdodik();
    }
    private void autokKore(){
        for (Auto auto : autoUtvonalak.keySet()) {
            autoKore(auto);
        }
    }
    private void autoKore(Auto auto){
        List<Csomopont> utvonal = autoUtvonalak.get(auto);
        int nextIndex = utvonal.indexOf(auto.getAktualisCsomopont())+1;
        if(nextIndex >= utvonal.size()||nextIndex <= 0){
            //TODO: visszafordul ha célba ért (újraszámolás)
            return;
        }
        Csomopont next = utvonal.get(nextIndex);
        auto.lep(next);
    }
    public void vasarol(Arucikk termek, Hokotro gep){
        //Mivel ezt a grafikus interfész is kezelni fogja, így nem gond az instanceof szerintem.
        if(aktivJatekos instanceof Takarito t){
            if(bolt.vasarol(termek, t, gep)){
                if(termek == Arucikk.GLOBAL_WARMING){
                    jatekVege();
                }
                nezet.uzenetKijelzese("Sikeres vásárlás!");
            }else{
                nezet.uzenetKijelzese("Sikertelen vásárlás!");
            }
        }
    }
    public void addJatekos(Jatekos<?> jatekos){
        jatekosok.add(jatekos);
    }
    public void addAuto(Auto auto){
        //hozzáadunk egy autót és kiszámolunk egy legrövidebb útvonalat az autó start és cél végpontjai között. 
        autoUtvonalak.put(auto, (modell.legrovidebbUtvonal(auto.getStart(), auto.getCel())));
    }
    private void jatekVege(){
        jatekVege = true;
        nezet.jatekVege("Játék vége!");
    }
    
}