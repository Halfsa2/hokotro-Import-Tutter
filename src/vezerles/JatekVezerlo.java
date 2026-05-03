package vezerles;

import gazdasag.Arucikk;
import gazdasag.IMegvasarolhato;
import gazdasag.Jatekos;
import gazdasag.Sofor;
import gazdasag.Takarito;
import halozat.Csomopont;
import jarmu.Auto;
import jarmu.Busz;
import jarmu.Hokotro;
import jarmu.Jarmu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import megjelenites.IJatekNezet;

public class JatekVezerlo implements IJatekVezerlo {
    private IJatekNezet nezet;
    private IJatekKezelo modell;
    private IMegvasarolhato bolt;
    private HashMap<Auto,List<Csomopont>> autoUtvonalak;
    private int takaritokSzama = 0;
    private int soforokSzama = 0;
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
        modell.epit();
        for(int i = 0; i < soforokSzama; i++){
            Sofor s = new Sofor(modell.getKassza());
            s.setJarmu(new Busz(modell.getSzabadCheckpoint(), modell.getSzabadCheckpoint(),s));
            jatekosok.add(s);
        }
        for( int i = 0; i < takaritokSzama; i++){
            Takarito t = new Takarito(modell.getKassza());
            jatekosok.add(t);
        }
        // x mennyiségű autó létrehozása, hozzáadása a modellhez (startra léptetés) és útvonaltervezése
        
        modell.havazas();
        nextJatekos(); // Beállítja az első játékost aktívnak

    }
    public boolean lep(Csomopont cel){
        if(jatekVege) return false;
        boolean sikeres = false;
        if(aktivJatekos != null){
            //Itt nem számít a lépés eredménye, ha sikertelen a lépés, akkor is tovább lépünk a következő játékosra/járműre.
            sikeres = aktivJatekos.lep(cel);
            
            //minden 3. lépésnél leesik a hó.
            if(korokHoesesOta >=2) {modell.havazas();korokHoesesOta = 0;}else{korokHoesesOta++;}
            //Ha a játékos körének vége van, akkor következik a következő játékos, egyébként a következő járműve lép.
            if(aktivJatekos.isKorVege()) {nextJatekos();}
            else{aktivJatekos.nextJarmu();}
            //autók lépnek még a lépés vége előtt
            autokKore();
            //só hatása most érvényesül (első lépésben úgysem lesz semmi, ami miatt frissíteni kéne, így nem baj, hogy ez az első lépésnél nem teljesül)
            modell.palyaFrissit();
        }
        return sikeres;
    }
    public boolean lep(Jarmu jarmu, Csomopont cel){
        if(jatekVege) return false;
        //Megkerüljük a játék logikáját, az adott járművet direktben léptetjük.
        return jarmu.lep(cel);
    }
    public void nextJatekos(){
        if(jatekVege) return;

        //Ha ez lesz az első kör a játékban, akkor beállítjuk az első játékost aktívnak és NEM lépnek még az autók
        if(aktivJatekos == null){aktivJatekos = jatekosok.getFirst(); aktivJatekos.korKezdodik(); return;}

        //Ha nem ez az első kör, akkor megszerezzük a jelenlegi játékos ID-jét
        int currentId = jatekosok.indexOf(aktivJatekos);

        //Ha az utolsó játékos volt legutóbb, akkor az első jön
        if(currentId == jatekosok.size()-1) currentId = -1;

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
    @Override
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
    @Override
    public void registerJatekos(String tipus){
        if(tipus.equals("Sofor")){
            soforokSzama++;
        }else if(tipus.equals("Takarito")){
            takaritokSzama++;
        }
    }
    @Override
    public void addJatekos(Jatekos<?> jatekos){
        jatekosok.add(jatekos);
    }
    @Override
    public void addAuto(Auto auto){
        //hozzáadunk egy autót és kiszámolunk egy legrövidebb útvonalat az autó start és cél végpontjai között. 
        autoUtvonalak.put(auto, (modell.legrovidebbUtvonal(auto.getStart(), auto.getCel())));
    }
    private void jatekVege(){
        jatekVege = true;
        nezet.jatekVege("Játék vége!");
    }
    @Override
    public void tick(int korokSzama) {
        if(jatekVege) return;
        for (int i = 0; i < korokSzama; i++) {
            if(korokHoesesOta >=2) {modell.havazas();korokHoesesOta = 0;}else{korokHoesesOta++;}
            autokKore(); 
            modell.palyaFrissit();
        }
    }

    @Override
    public IMegvasarolhato getBolt() {
        //Ez a getter csak a prototípus csaló parancsának használatához van, hogy elérjük a boltot a CommandInterpreterből.
        //Nem szabadna egyébként használni, mert így kikerüljük a játék logikáját.
        return this.bolt;
    }
    @Override
    public IJatekKezelo getVarosModell() {
        //Ez a getter csak a prototípus csaló parancsának használatához van, hogy elérjük a város modellt a CommandInterpreterből.
        //Nem szabadna egyébként használni, mert így kikerüljük a játék logikáját.
        return this.modell;
    }
    @Override
    public Jatekos<?> getAktivJatekos() {
        //Ez a getter csak a prototípus csaló parancsának használatához van, hogy elérjük az aktív játékost a CommandInterpreterből.
        //Nem szabadna egyébként használni, mert így kikerüljük a játék logikáját.
        return this.aktivJatekos;
    }
}