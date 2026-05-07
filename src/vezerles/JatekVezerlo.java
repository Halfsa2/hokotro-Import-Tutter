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

public void initJatek(){
        vezerles.VarosModell vModell = (vezerles.VarosModell) modell;
        int W = 40; // Pálya szélessége
        int H = 35; // Pálya magassága
        vModell.initRacs(W, H);

        // Az utak kezdő koordinátái (4 sáv szélesek lesznek)
        int[] vX = {4, 18, 32}; 
        int[] hY = {2, 11, 20, 29}; 

        java.util.Map<String, halozat.Utszakasz> sliceMap = new java.util.HashMap<>();

        // --- 1. A PÁLYA MEZŐINEK LÉTREHOZÁSA ---
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
                // Sima utak, Hidak, Alagutak (Csak 1 mezőnyiek!)
                else {
                    boolean isTunnel = false, isBridge = false;
                    
                    // Pozíciók a rajzod alapján (Y vagy X koordináta a kereszteződések között)
                    if (rY == -1) { // Függőleges szakasz
                        if (rX == 0 && y == 8) isTunnel = true;
                        if (rX == 0 && y == 26) isTunnel = true;
                        if (rX == 1 && y == 8) isBridge = true;
                        if (rX == 1 && y == 17) isTunnel = true;
                        if (rX == 2 && y == 8) isTunnel = true;
                        if (rX == 2 && y == 17) isBridge = true;
                    } else if (rX == -1) { // Vízszintes szakasz
                        if (rY == 2 && x == 26) isBridge = true;
                        if (rY == 3 && x == 12) isTunnel = true;
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

        // --- 2. TOPOLÓGIA ÉS SÁVVÁLTÁS ÖSSZEKÖTÉSE ---
        for(int x = 0; x < W; x++) {
            for(int y = 0; y < H; y++) {
                Csomopont akt = vModell.getCsomopont(x, y);
                if (akt == null) continue;

                int rX = -1; for(int i=0; i<vX.length; i++) if(x >= vX[i] && x < vX[i]+4) rX = i;
                int rY = -1; for(int i=0; i<hY.length; i++) if(y >= hY[i] && y < hY[i]+4) rY = i;

                // Vízszintes haladás (2 Nyugat, 2 Kelet)
                if (rY != -1) {
                    int lane = y - hY[rY]; // 0-3 sáv index
                    if ((lane == 0 || lane == 1) && x > 0) kapcsol(akt, vModell.getCsomopont(x-1, y)); // Nyugat
                    if ((lane == 2 || lane == 3) && x < W-1) kapcsol(akt, vModell.getCsomopont(x+1, y)); // Kelet
                    
                    if (lane == 0) kapcsol(akt, vModell.getCsomopont(x, y+1));
                    if (lane == 1) { kapcsol(akt, vModell.getCsomopont(x, y-1)); kapcsol(akt, vModell.getCsomopont(x, y+1)); }
                    if (lane == 2) { kapcsol(akt, vModell.getCsomopont(x, y-1)); kapcsol(akt, vModell.getCsomopont(x, y+1)); }
                    if (lane == 3) kapcsol(akt, vModell.getCsomopont(x, y-1));
                }

                // Függőleges haladás (2 Észak, 2 Dél)
                if (rX != -1) {
                    int lane = x - vX[rX]; // 0-3 sáv index
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

       // --- 3. JÁTÉKOSOK ÉS AUTÓK ---
        for(int i = 0; i < takaritokSzama; i++) {
            jatekosok.add(new gazdasag.Takarito("Takarito" + i, modell.getKassza()));
        }

        // 1. Autó (Északi vízszintes út: Nyugatról Keletre)
        halozat.Checkpoint start1 = (halozat.Checkpoint)vModell.getCsomopont(0, 4);  
        halozat.Checkpoint cel1 = (halozat.Checkpoint)vModell.getCsomopont(W-1, 4);
        jarmu.Auto a1 = new jarmu.Auto(start1, cel1);
        if(start1.befogad(a1)) a1.setAktualisCsomopont(start1);
        addAuto(a1);

        // 2. Autó (Középső függőleges út: Északról Délre)
        halozat.Checkpoint start2 = (halozat.Checkpoint)vModell.getCsomopont(20, 0);  
        halozat.Checkpoint cel2 = (halozat.Checkpoint)vModell.getCsomopont(20, H-1);
        jarmu.Auto a2 = new jarmu.Auto(start2, cel2);
        if(start2.befogad(a2)) a2.setAktualisCsomopont(start2);
        addAuto(a2);

        // 3. Autó (Déli vízszintes út: Keletről Nyugatra)
        halozat.Checkpoint start3 = (halozat.Checkpoint)vModell.getCsomopont(W-1, 29);  
        halozat.Checkpoint cel3 = (halozat.Checkpoint)vModell.getCsomopont(0, 29);
        jarmu.Auto a3 = new jarmu.Auto(start3, cel3);
        if(start3.befogad(a3)) a3.setAktualisCsomopont(start3);
        addAuto(a3);

        modell.havazas();
        nextJatekos(); 
    }

    private void kapcsol(Csomopont honnan, Csomopont hova) {
        if (honnan == null || hova == null) return;
        if (honnan instanceof halozat.Sav) ((halozat.Sav)honnan).addSzomszed(hova);
        if (honnan instanceof halozat.Checkpoint) ((halozat.Checkpoint)honnan).addSzomszed(hova);
        if (honnan instanceof halozat.Keresztezodes) ((halozat.Keresztezodes)honnan).addKimenet(hova);
    }


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

    public boolean lep(Jarmu jarmu, Csomopont cel){
        if(jatekVege) return false;
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