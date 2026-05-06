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
    private int korokHoesesOta = 0;
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
            Sofor s = new Sofor("Sofor" + i, modell.getKassza());
            s.setJarmu(new Busz(modell.getSzabadCheckpoint(), modell.getSzabadCheckpoint(),s));
            jatekosok.add(s);
        }
        for( int i = 0; i < takaritokSzama; i++){
            Takarito t = new Takarito("Takarito" + i, modell.getKassza());
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
            sikeres = aktivJatekos.lep(cel);
            
            // --- INNEN TÖRÖLTÜK A korokHoesesOta LOGIKÁT! ---

            // Ha a játékos körének vége van, akkor következik a következő játékos, egyébként a következő járműve lép.
            if(aktivJatekos.isKorVege()) {
                nextJatekos();
            } else {
                aktivJatekos.nextJarmu();
            }
            
            autokKore();
            modell.palyaFrissit();
        }
        return sikeres;
    }
    public boolean lep(Jarmu jarmu, Csomopont cel){
        if(jatekVege) return false;
        //Megkerüljük a játék logikáját, az adott járművet direktben léptetjük.
        return jarmu.lep(cel);
    }
    public void nextJatekos() {
        if(jatekVege) return;

        if(aktivJatekos == null) {
            aktivJatekos = jatekosok.getFirst(); 
            aktivJatekos.korKezdodik(); 
            return;
        }

        int currentId = jatekosok.indexOf(aktivJatekos);

        // Ha az utolsó játékos lépett, újra az első jön, de ELTELT EGY KÖR!
        if(currentId == jatekosok.size() - 1) {
            currentId = -1;
            
            // --- IDŐ MÚLÁSA ---
            if (modell != null) {
                modell.tick();
            }
        }

        aktivJatekos = jatekosok.get(currentId + 1);
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

    public void setNezet(megjelenites.IJatekNezet nezet) {
        this.nezet = nezet;
    }

}