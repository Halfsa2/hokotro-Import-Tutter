package vezerles;

import gazdasag.Arucikk;
import gazdasag.IMegvasarolhato;
import gazdasag.Jatekos;
import gazdasag.Takarito;
import halozat.Csomopont;
import jarmu.Auto;
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
    private int korokHoesesOta = 0;
    private Jatekos<?> aktivJatekos;

    public JatekVezerlo(IJatekNezet nezet, IJatekKezelo modell, IMegvasarolhato bolt) {
        this.nezet = nezet;
        this.modell = modell;
        this.bolt = bolt;
        this.jatekosok = new ArrayList<>();
        autoUtvonalak = new HashMap<>();
    }
    
    @Override
    public void initJatek(){
        vezerles.VarosModell vModell = (vezerles.VarosModell) modell;
        
        // --- 1. A PÁLYA MÉRETE ÉS AZ UTAK HELYZETE ---
        int W = 30; // 40-ről 30-ra csökkent (5 csempés távolságok miatt)
        int H = 35; 
        vModell.initRacs(W, H);

        // Az utak kezdő koordinátái (Mindegyik között pont 5 mező távolság van!)
        int[] vX = {4, 13, 22}; 
        int[] hY = {2, 11, 20, 29}; 

        java.util.Map<String, halozat.Utszakasz> sliceMap = new java.util.HashMap<>();

        // --- 2. A PÁLYA MEZŐINEK LÉTREHOZÁSA ---
        for(int x = 0; x < W; x++) {
            for(int y = 0; y < H; y++) {
                
                int rX = -1; for(int i=0; i<vX.length; i++) if(x >= vX[i] && x < vX[i]+4) rX = i;
                int rY = -1; for(int i=0; i<hY.length; i++) if(y >= hY[i] && y < hY[i]+4) rY = i;

                if (rX == -1 && rY == -1) continue; // Üres fű/hó

                Csomopont csp;
                // Kereszteződés
                if (rX != -1 && rY != -1) {
                    csp = new halozat.Keresztezodes();
                } 
                // Checkpointok a pálya szélén (1x4 és 4x1)
                else if ((rX != -1 && (y == 0 || y == H-1)) || (rY != -1 && (x == 0 || x == W-1))) {
                    csp = new halozat.Checkpoint();
                } 
                // Sima utak, Hidak, Alagutak
                else {
                    boolean isTunnel = false, isBridge = false;
                    
                    // Pozíciók (hozzáigazítva a rövidebb blokkokhoz)
                    if (rY == -1) { // Függőleges szakasz
                        if (rX == 0 && y == 8) isTunnel = true;
                        if (rX == 0 && y == 26) isTunnel = true;
                        if (rX == 1 && y == 8) isBridge = true;
                        if (rX == 1 && y == 17) isTunnel = true;
                        if (rX == 2 && y == 8) isTunnel = true;
                        if (rX == 2 && y == 17) isBridge = true;
                    } else if (rX == -1) { // Vízszintes szakasz
                        if (rY == 2 && x == 19) isBridge = true; // Középre igazítva
                        if (rY == 3 && x == 10) isTunnel = true; // Középre igazítva
                    }

                    String key = (rX != -1) ? "V_" + rX + "_" + y : "H_" + x + "_" + rY;
                    halozat.Utszakasz u = sliceMap.get(key);
                    if (u == null) {
                        u = isTunnel ? new halozat.Alagut() : (isBridge ? new halozat.Hid() : new halozat.Utszakasz());
                        sliceMap.put(key, u);
                    }
                    
                    csp = new halozat.Sav();
                    ((halozat.Sav)csp).setUtszakasz(u);
                    u.addSav((halozat.Sav)csp);
                }
                vModell.addCsomopont(x, y, csp);
            }
        }

        // --- 3. TOPOLÓGIA ÉS SÁVVÁLTÁS ÖSSZEKÖTÉSE ---
        for(int x = 0; x < W; x++) {
            for(int y = 0; y < H; y++) {
                Csomopont akt = vModell.getCsomopont(x, y);
                if (akt == null) continue;

                int rX = -1; for(int i=0; i<vX.length; i++) if(x >= vX[i] && x < vX[i]+4) rX = i;
                int rY = -1; for(int i=0; i<hY.length; i++) if(y >= hY[i] && y < hY[i]+4) rY = i;

                // Vízszintes haladás
                if (rY != -1) {
                    int lane = y - hY[rY]; 
                    if ((lane == 0 || lane == 1) && x > 0) kapcsol(akt, vModell.getCsomopont(x-1, y)); // Nyugat
                    if ((lane == 2 || lane == 3) && x < W-1) kapcsol(akt, vModell.getCsomopont(x+1, y)); // Kelet
                    
                    if (lane == 0) kapcsol(akt, vModell.getCsomopont(x, y+1));
                    if (lane == 1) { kapcsol(akt, vModell.getCsomopont(x, y-1)); kapcsol(akt, vModell.getCsomopont(x, y+1)); }
                    if (lane == 2) { kapcsol(akt, vModell.getCsomopont(x, y-1)); kapcsol(akt, vModell.getCsomopont(x, y+1)); }
                    if (lane == 3) kapcsol(akt, vModell.getCsomopont(x, y-1));
                }

                // Függőleges haladás
                if (rX != -1) {
                    int lane = x - vX[rX]; 
                    if ((lane == 0 || lane == 1) && y > 0) kapcsol(akt, vModell.getCsomopont(x, y-1)); // Észak
                    if ((lane == 2 || lane == 3) && y < H-1) kapcsol(akt, vModell.getCsomopont(x, y+1)); // Dél
                    
                    if (lane == 0) kapcsol(akt, vModell.getCsomopont(x+1, y));
                    if (lane == 1) { kapcsol(akt, vModell.getCsomopont(x-1, y)); kapcsol(akt, vModell.getCsomopont(x+1, y)); }
                    if (lane == 2) { kapcsol(akt, vModell.getCsomopont(x-1, y)); kapcsol(akt, vModell.getCsomopont(x+1, y)); }
                    if (lane == 3) kapcsol(akt, vModell.getCsomopont(x-1, y));
                }

                // Visszafordítók a Checkpointokon
                if (akt instanceof halozat.Checkpoint) {
                    if (x == 0 && rY != -1) {
                        if (y - hY[rY] == 0) kapcsol(akt, vModell.getCsomopont(0, y+3));
                        if (y - hY[rY] == 1) kapcsol(akt, vModell.getCsomopont(0, y+1));
                    }
                    if (x == W-1 && rY != -1) {
                        if (y - hY[rY] == 2) kapcsol(akt, vModell.getCsomopont(W-1, y-1));
                        if (y - hY[rY] == 3) kapcsol(akt, vModell.getCsomopont(W-1, y-3));
                    }
                    if (y == 0 && rX != -1) {
                        if (x - vX[rX] == 0) kapcsol(akt, vModell.getCsomopont(x+3, 0));
                        if (x - vX[rX] == 1) kapcsol(akt, vModell.getCsomopont(x+1, 0));
                    }
                    if (y == H-1 && rX != -1) {
                        if (x - vX[rX] == 2) kapcsol(akt, vModell.getCsomopont(x-1, H-1));
                        if (x - vX[rX] == 3) kapcsol(akt, vModell.getCsomopont(x-3, H-1));
                    }
                }
            }
        }

        // --- 4. JÁTÉKOSOK ÉS AUTÓK ---
        //for(int i = 0; i < takaritokSzama; i++) {
       //     jatekosok.add(new gazdasag.Takarito("Takarito" + i, modell.getKassza()));
       // }

        // 1. Autó (Északi vízszintes út: Nyugatról Keletre)
        halozat.Checkpoint start1 = (halozat.Checkpoint)vModell.getCsomopont(0, 4);  
        halozat.Checkpoint cel1 = (halozat.Checkpoint)vModell.getCsomopont(W-1, 4);
        jarmu.Auto a1 = new jarmu.Auto(start1, cel1);
        if(start1.befogad(a1)) a1.setAktualisCsomopont(start1);
        addAuto(a1);

        // 2. Autó (Középső függőleges út: Északról Délre - rX=1, sáv=16)
        halozat.Checkpoint start2 = (halozat.Checkpoint)vModell.getCsomopont(16, 0);  
        halozat.Checkpoint cel2 = (halozat.Checkpoint)vModell.getCsomopont(16, H-1);
        jarmu.Auto a2 = new jarmu.Auto(start2, cel2);
        if(start2.befogad(a2)) a2.setAktualisCsomopont(start2);
        addAuto(a2);

        // 3. Autó (Déli vízszintes út: Keletről Nyugatra - rY=3, sáv=29)
        halozat.Checkpoint start3 = (halozat.Checkpoint)vModell.getCsomopont(W-1, 29);  
        halozat.Checkpoint cel3 = (halozat.Checkpoint)vModell.getCsomopont(0, 29);
        jarmu.Auto a3 = new jarmu.Auto(start3, cel3);
        if(start3.befogad(a3)) a3.setAktualisCsomopont(start3);
        addAuto(a3);

        //modell.havazas();
        //nextJatekos(); 
    }

    public void startElsoKor() {
            modell.havazas(); // Leesik az első adag hó
            nextJatekos();    // Megkapja az első játékos az irányítást
        }

    private void kapcsol(Csomopont honnan, Csomopont hova) {
        if (honnan == null || hova == null) return;
        if (honnan instanceof halozat.Sav) ((halozat.Sav)honnan).addSzomszed(hova);
        if (honnan instanceof halozat.Checkpoint) ((halozat.Checkpoint)honnan).addSzomszed(hova);
        if (honnan instanceof halozat.Keresztezodes) ((halozat.Keresztezodes)honnan).addKimenet(hova);
    }

    @Override
   public boolean lep(Csomopont cel){
        if(jatekVege) return false;
        boolean sikeres = false;
        
        if(aktivJatekos != null){
            sikeres = aktivJatekos.lep(cel);
            
            if (sikeres) {
                // ELŐSZÖR levonjuk a lépést (járművet váltunk)
                aktivJatekos.nextJarmu();
                
                // UTÁNA ellenőrizzük, hogy elfogyott-e minden jármű lépése!
                if(aktivJatekos.isKorVege()) {
                    nextJatekos();
                }
                
                autokKore();
                modell.palyaFrissit();
            } else {
                nezet.uzenetKijelzese("Hoppá! Ide nem tudsz lépni. (Foglalt, vagy túl mély a hó)");
            }
        }
        return sikeres;
    }

    @Override
    public boolean lep(Jarmu jarmu, Csomopont cel){
        if(jatekVege) return false;
        return jarmu.lep(cel);
    }

    @Override
    public void nextJatekos() {
        if(jatekVege) return;
        if(aktivJatekos == null) {
            aktivJatekos = jatekosok.getFirst(); 
            aktivJatekos.korKezdodik(); 
            return;
        }

        int currentId = jatekosok.indexOf(aktivJatekos);
        if(currentId == jatekosok.size() - 1) {
            currentId = -1;
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
        Csomopont aktualis = auto.getAktualisCsomopont();
        
        // Megnézzük, hol állunk az útvonalban
        int nextIndex = utvonal.indexOf(aktualis) + 1;
        
        // --- HA CÉLBA ÉRT (vagy elfogyott az út) ---
        if(nextIndex >= utvonal.size() || nextIndex <= 0){
            // Megkeressük az új célt: ha a 'cel'-nél van, menjen a 'start'-hoz, és fordítva
            // (Ez feltételezi, hogy az Auto osztályodban vannak getStart() és getCel() getterek)
            Csomopont ujCel = (aktualis == auto.getCel()) ? auto.getStart() : auto.getCel();
            
            // Újratervezzük az utat a túlsó végállomásig
            List<Csomopont> ujUtvonal = modell.legrovidebbUtvonal(aktualis, ujCel);
            autoUtvonalak.put(auto, ujUtvonal);
            
            // Ebben a körben megfordul, a következőben már indul is vissza
            return;
        }
        
        // --- HALADÁS ---
        Csomopont kovetkezo = utvonal.get(nextIndex);
        
        // Csak akkor lép, ha az út tiszta (nincs rajta hó vagy másik autó)
        // Ha nem tud lépni, ott marad (dugó), amíg fel nem szabadul az út
        auto.lep(kovetkezo);
    }

    @Override
    public void vasarol(Arucikk termek, Hokotro gep){
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
        if(tipus.equals("Sofor")) soforokSzama++;
        else if(tipus.equals("Takarito")) takaritokSzama++;
    }

    @Override
    public void addJatekos(Jatekos<?> jatekos){
        jatekosok.add(jatekos);
    }

    @Override
    public void addAuto(Auto auto){
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
    public IMegvasarolhato getBolt() { return this.bolt; }
    @Override
    public IJatekKezelo getVarosModell() { return this.modell; }
    @Override
    public Jatekos<?> getAktivJatekos() { return this.aktivJatekos; }
    public void setNezet(megjelenites.IJatekNezet nezet) { this.nezet = nezet; }
}