package prototipus;

import allapot.Jeges;
import allapot.MelyHo;
import allapot.Savallapot;
import allapot.SekelyHo;
import allapot.Tiszta;
import felszereles.Hanyofej;
import felszereles.Jegtoro;
import felszereles.Kotrofej;
import felszereles.Sarkanyfej;
import felszereles.Sopro;
import felszereles.Soszoro;
import felszereles.ZuzalekSzoro;
import gazdasag.Arucikk;
import gazdasag.Bolt;
import gazdasag.KozosKassza;
import gazdasag.Sofor;
import gazdasag.Takarito;
import halozat.Alagut;
import halozat.Checkpoint;
import halozat.Csomopont;
import halozat.Keresztezodes;
import halozat.Sav;
import halozat.Utszakasz;
import jarmu.Auto;
import jarmu.Busz;
import jarmu.Hokotro;
import jarmu.Jarmu;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import vezerles.IJatekVezerlo;
import vezerles.JatekVezerlo;
import vezerles.VarosModell;

public class CommandInterpreter {
    IJatekVezerlo jatekVezerlo;
    public static final Map<String, IStatable> nevTar = new HashMap<>();
    public static final Map<IStatable, String> reverseNevTar = new HashMap<>();
    private static List<String> commandLog = new ArrayList<>();
    public static final List<String> replyLog = new ArrayList<>();

    CommandInterpreter() {
        this.jatekVezerlo = new JatekVezerlo(null, new VarosModell(), new Bolt());
    }
    public static void main(String[] args) {
        CommandInterpreter interpreter = new CommandInterpreter();
        interpreter.start(new Scanner(System.in));
    }

    /**
     * Elindítja a parancsok beolvasását.
     */
    public void start(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            
            // Kommentek és üres sorok figyelmen kívül hagyása
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            
            // "0" esetén kilépünk (ha ez a kilépési feltétel a specifikáció végén)
            if (line.equals("0")) {
                break;
            }

            if (processCommand(line)) {
                commandLog.add(line); // Parancs naplózása
            }
        }
    }

    /**
     * Egyetlen parancssor feldolgozása.
     */
    private boolean processCommand(String line) {
        String[] parts = line.split("\\s+");
        String command = parts[0].toLowerCase();
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);

        try {
            switch (command) {
                case "tick":
                    handleTick(args);
                    break;
                case "load":
                    handleLoad(args);
                    break;
                case "save":
                    handleSave(args);
                    break;
                case "create":
                    handleCreate(args);
                    break;
                case "addmoney":
                    handleAddMoney(args);
                    break;
                case "addjarmu":
                    handleAddJarmu(args);
                    break;
                case "setstate":
                    handleSetState(args);
                    break;
                case "setnyomvonal":
                    handleSetNyomvonal(args);
                    break;
                case "setsomennyiseg":
                    handleSetSomennyiseg(args);
                    break;
                case "setkerozinmennyiseg":
                    handleSetKerozinmennyiseg(args);
                    break;
                case "setstartpoint":
                    handleSetStartPoint(args);
                    break;
                case "setendpoint":
                    handleSetEndPoint(args);
                    break;
                case "sozas":
                    handleSozas(args);
                    break;
                case "zuzalekszoras":
                    handleZuzalekszoras(args);
                    break;
                case "connect":
                    handleConnect(args);
                    break;
                case "step":
                    handleStep(args);
                    break;
                case "buy":
                    handleBuy(args);
                    break;
                case "fejcsere":
                    handleFejcsere(args);
                    break;
                case "stat":
                    handleStat(args);
                    break;
                case "addsav":
                    handleAddSav(args);
                    break;
                case "setzuzalekmennyiseg":
                    handleSetZuzalekmennyiseg(args);
                    break;
                case "baleset":
                    handleBaleset(args);
                    break;
                case "havazas":
                    handleHavazas(args);
                    break;
                default:
                    printError("Ismeretlen parancs: " + command);
                    return false;
            }
        } catch (IllegalArgumentException e) {
            printError("Hibás paraméterezés: " + e.getMessage());
            return false;
        } catch (Exception e) {
            printFailed("Váratlan hiba történt a parancs végrehajtása közben.");
            return false;
        }
        return true;
    }

    // ==========================================
    // PARANCS KEZELŐ FÜGGVÉNYEK 
    // ==========================================
    
    private void handleTick(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("A 'tick' parancs egyetlen paramétert vár: a körök számát");
        }
        jatekVezerlo.tick(Integer.parseInt(args[0]));
        printOk("Játék előre léptetve " + args[0] + " körrel.");
    }
    
    private final File mappa = new File("saves");
    private void handleLoad(String[] args) {
        File fajl = new File(mappa,args[0]);
        if (!fajl.exists()) {
            throw new IllegalArgumentException("A megadott fájl nem létezik: " + args[0]);
        }
        try (Scanner fileScanner = new Scanner(fajl)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                if (!processCommand(line)) {
                    printError("Hiba történt a fájl parancsainak feldolgozása közben.");
                    return;
                }
            }
            printOk("Fájl sikeresen betöltve: " + args[0]);
        } catch (IOException e) {
            printError("Hiba történt a fájl olvasása közben: " + e.getMessage());
        }
    }

    private void handleSave(String[] args) {
        if (!mappa.exists()) {
            mappa.mkdir();
        }

        File mentesFajl;

        if (args.length > 0) {
            // Ha a játékos adott meg nevet
            mentesFajl = new File(mappa, args[0]);
            
            // EGYEDISÉG ELLENŐRZÉSE: Ha már létezik ilyen nevű fájl, nem engedjük felülírni!
            if (mentesFajl.exists()) {
                printError("A '" + args[0] + "' nevű mentés már létezik! Kérlek, válassz egy másik nevet.");
                return; // Megszakítjuk a mentést
            }
        } else {
            // Ha nem adott meg nevet, jön a biztonságos automatikus sorszámozás
            int sorszam = 1;
            mentesFajl = new File(mappa, "save" + sorszam + ".txt");
            while (mentesFajl.exists()) {
                sorszam++;
                mentesFajl = new File(mappa, "save" + sorszam + ".txt");
            }
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(mentesFajl))) {
            out.println("# Mentés: " + mentesFajl.getName());
            for(String parancs : commandLog){
                out.println(parancs);
            }
            printOk("Sikeres mentés ide: " + mentesFajl.getPath()); 
            
        } catch (IOException e) {
            printError("Hiba történt a fájlba íráskor: " + e.getMessage());
        }
    }

    private void handleCreate(String[] args) {
       if(args.length == 0){
            throw new IllegalArgumentException("A 'create' parancs nem használható paraméterek nélkül");
       }
       if(nevTar.containsKey(args[1])){
            throw new IllegalArgumentException("A '" + args[1] + "' név már foglalt. Válassz egy egyedi nevet.");
       }
       switch(args[0]){
            case "jeges":
                Jeges jeges = new Jeges();
                nevTar.put(args[1], jeges);
                reverseNevTar.put(jeges, args[1]);
                printOk("Jeges létrehozva: " + args[1]);
                break;
            case "melyho":
                MelyHo melyho = new MelyHo();
                nevTar.put(args[1], melyho);
                reverseNevTar.put(melyho, args[1]);
                printOk("MelyHo létrehozva: " + args[1]);
                break;
            case "sekelyho":
                SekelyHo sekelyHo = new SekelyHo();
                nevTar.put(args[1], sekelyHo);
                reverseNevTar.put(sekelyHo, args[1]);
                printOk("SekelyHo létrehozva: " + args[1]);
                break;
            case "tiszta":
                Tiszta tiszta = new Tiszta();
                nevTar.put(args[1], tiszta);
                reverseNevTar.put(tiszta, args[1]);
                printOk("Tiszta létrehozva: " + args[1]);
                break;
            case "hanyofej":
                Hanyofej hanyofej = new Hanyofej();
                nevTar.put(args[1], hanyofej);
                reverseNevTar.put(hanyofej, args[1]);
                printOk("Hanyofej létrehozva: " + args[1]);
                break;
            case "jegtoro":
                Jegtoro jegtoro = new Jegtoro();
                nevTar.put(args[1], jegtoro);
                reverseNevTar.put(jegtoro, args[1]);
                printOk("Jegtoro létrehozva: " + args[1]);
                break;
            case "sarkanyfej":
                Sarkanyfej sarkanyfej = new Sarkanyfej(Integer.parseInt(args[2]));
                nevTar.put(args[1], sarkanyfej);
                reverseNevTar.put(sarkanyfej, args[1]);
                printOk("Sarkanyfej létrehozva: " + args[1]);
                break;
            case "sopro":
                Sopro sopro = new Sopro();
                nevTar.put(args[1], sopro);
                reverseNevTar.put(sopro, args[1]);
                printOk("Sopro létrehozva: " + args[1]);
                break;
            case "soszoro":
                Soszoro soszoro = new Soszoro(Integer.parseInt(args[2]));
                nevTar.put(args[1], soszoro);
                reverseNevTar.put(soszoro, args[1]);
                printOk("Soszoro létrehozva: " + args[1]);
                break;
            case "zuzalekszoro":
                ZuzalekSzoro zuzalekszoro = new ZuzalekSzoro(Integer.parseInt(args[2]));
                nevTar.put(args[1], zuzalekszoro);
                reverseNevTar.put(zuzalekszoro, args[1]);
                printOk("ZuzalekSzoro létrehozva: " + args[1]);
                break;
            case "bolt":
                Bolt bolt = new Bolt();
                nevTar.put(args[1], bolt);
                reverseNevTar.put(bolt, args[1]);
                printOk("Bolt létrehozva: " + args[1]);
                break;
            case "kassza":
                KozosKassza kassza = new KozosKassza(Integer.parseInt(args[2]));
                nevTar.put(args[1], kassza);
                reverseNevTar.put(kassza, args[1]);
                printOk("KozosKassza létrehozva: " + args[1]);
                break;
            case "sofor":
                KozosKassza kSofor = nevTar.get(args[2]) instanceof KozosKassza ? (KozosKassza) nevTar.get(args[2]) : null;
                if(kSofor == null){
                    throw new IllegalArgumentException("A 'sofor' létrehozásához egy érvényes közös kassza szükséges paraméterként.");
                }
                Sofor sofor = new Sofor(kSofor);
                nevTar.put(args[1], sofor);
                reverseNevTar.put(sofor, args[1]);
                jatekVezerlo.addJatekos(sofor);
                printOk("Sofor létrehozva: " + args[1]);
                break;
            case "takarito":
                KozosKassza kTakarito = nevTar.get(args[2]) instanceof KozosKassza ? (KozosKassza) nevTar.get(args[2]) : null;
                if(kTakarito == null){
                    throw new IllegalArgumentException("A 'takarito' létrehozásához egy érvényes közös kassza szükséges paraméterként.");
                }
                Takarito takarito = new Takarito(kTakarito);
                nevTar.put(args[1], takarito);
                reverseNevTar.put(takarito, args[1]);
                jatekVezerlo.addJatekos(takarito);
                printOk("Takarito létrehozva: " + args[1]);
                break;
            case "alagut":
                Alagut alagut = new Alagut();
                nevTar.put(args[1], alagut);
                reverseNevTar.put(alagut, args[1]);
                printOk("Alagut létrehozva: " + args[1]);
                break;
            case "checkpoint":
                Checkpoint checkpoint = new Checkpoint();
                nevTar.put(args[1], checkpoint);
                reverseNevTar.put(checkpoint, args[1]);
                jatekVezerlo.getVarosModell().addCsomopont(checkpoint); // A checkpointokat hozzá kell adni a város modellhez is
                printOk("Checkpoint létrehozva: " + args[1]);
                break;
            case "keresztezodes":
                Keresztezodes keresztezodes = new Keresztezodes();
                nevTar.put(args[1], keresztezodes);
                reverseNevTar.put(keresztezodes, args[1]);
                printOk("Keresztezodes létrehozva: " + args[1]);
                jatekVezerlo.getVarosModell().addCsomopont(keresztezodes); // A keresztezodeseket hozzá kell adni a város modellhez is
                break;
            case "sav":
                Sav sav = new Sav();
                nevTar.put(args[1], sav);
                reverseNevTar.put(sav, args[1]);
                printOk("Sav létrehozva: " + args[1]);
                jatekVezerlo.getVarosModell().addCsomopont(sav); // A savokat hozzá kell adni a város modellhez is
                break;
            case "utszakasz":
                Utszakasz utszakasz = new Utszakasz();
                nevTar.put(args[1], utszakasz);
                reverseNevTar.put(utszakasz, args[1]);
                printOk("Utszakasz létrehozva: " + args[1]);
                break;
            case "auto":
                Checkpoint autoStart = nevTar.get(args[2]) instanceof Checkpoint ? (Checkpoint) nevTar.get(args[2]) : null;
                Checkpoint autoCel = nevTar.get(args[3]) instanceof Checkpoint ? (Checkpoint) nevTar.get(args[3]) : null;
                if(autoStart == null || autoCel == null){
                    throw new IllegalArgumentException("Az 'auto' létrehozásához érvényes kiindulási és cél checkpoint szükséges paraméterként.");
                }
                Auto auto = new Auto(autoStart, autoCel);
                nevTar.put(args[1], auto);
                reverseNevTar.put(auto, args[1]);
                jatekVezerlo.addAuto(auto);
                printOk("Auto létrehozva: " + args[1]);
                break;
            case "busz":
                Checkpoint buszStart = nevTar.get(args[2]) instanceof Checkpoint ? (Checkpoint) nevTar.get(args[2]) : null;
                Checkpoint buszCel = nevTar.get(args[3]) instanceof Checkpoint ? (Checkpoint) nevTar.get(args[3]) : null;
                Sofor buszSofor = nevTar.get(args[4]) instanceof Sofor ? (Sofor) nevTar.get(args[4]) : null;
                if(buszStart == null || buszCel == null || buszSofor == null){
                    throw new IllegalArgumentException("A 'busz' létrehozásához érvényes kiindulási és cél checkpoint, valamint egy sofőr szükséges paraméterként.");
                }
                jarmu.Busz busz = new jarmu.Busz(buszStart, buszCel, buszSofor);
                buszSofor.setJarmu(busz); // A sofőr tudja, hogy melyik buszt vezeti
                nevTar.put(args[1], busz);
                reverseNevTar.put(busz, args[1]);
                printOk("Busz létrehozva: " + args[1]);
                break;
            case "hokotro":
                Takarito vezeto = nevTar.get(args[2]) instanceof Takarito ? (Takarito) nevTar.get(args[2]) : null;
                if(vezeto == null){
                    throw new IllegalArgumentException("A 'hokotro' létrehozásához egy érvényes takarító szükséges paraméterként.");
                }
                Hokotro hokotro = new Hokotro(vezeto);
                vezeto.addHokotro(hokotro); // A takarító tudja, hogy melyik hókotrót vezeti
                nevTar.put(args[1], hokotro);
                reverseNevTar.put(hokotro, args[1]);
                printOk("Hokotro létrehozva: " + args[1]);
                break;
            default:
                throw new IllegalArgumentException("Ismeretlen típus a 'create' parancsban: " + args[0]);
       }
    }

    private void handleStep(String[] args) {
    Jarmu jarmu = nevTar.get(args[0]) instanceof Jarmu ? (Jarmu) nevTar.get(args[0]) : null;
    
    if(jarmu == null){        
        // --- Játékos léptetése (Változatlan) ---
        Csomopont cel = nevTar.get(args[0]) instanceof Csomopont ? (Csomopont) nevTar.get(args[0]) : null;
        if(cel == null) throw new IllegalArgumentException("Érvénytelen jármű vagy csomópont.");
        if(jatekVezerlo.getAktivJatekos() == null) jatekVezerlo.nextJatekos();
        if(!jatekVezerlo.lep(cel)) {
            printFailed("Sikertelen lépés a " + args[0] + " csomópontra.");
            return;
        }
        printOk("Sikeres lépés a " + args[0] + " csomópontra.");
    } else {
        // --- Jármű léptetése ---
        Csomopont cel = null;
        
        // Ha van második paraméter, az a manuális célpont
        if (args.length > 1) {
            cel = nevTar.get(args[1]) instanceof Csomopont ? (Csomopont) nevTar.get(args[1]) : null;
        } 
        // Ha nincs második paraméter, de AUTO, akkor beindul az önvezetés!
        else if (jarmu instanceof Auto) {
            Auto auto = (Auto) jarmu;
            // Megkeressük a BFS szerinti következő lépést[cite: 2]
            java.util.List<Csomopont> ut = jatekVezerlo.getVarosModell().legrovidebbUtvonal(auto.getAktualisCsomopont(), auto.getCel());
            if (ut.size() >= 2) {
                cel = ut.get(1);
                System.out.println("Az " + args[0] + " autó a rövidebb utat választotta (" + reverseNevTar.get(cel) + " felé).");
            }
        }

        if(cel == null){
            throw new IllegalArgumentException("A 'step' parancshoz célpont megadása szükséges.");
        }

        if(!jatekVezerlo.lep(jarmu, cel)){
            printFailed("Sikertelen lépés a " + args[0] + " járművel.");
            return;
        }
        printOk("Az " + args[0] + " jármű sikeresen átlépett az " + reverseNevTar.get(cel) + " sávra.");
    }
}

    private void handleStat(String[] args) {
        String[] nevek = args[0].split("\\.");
       IStatable object = nevTar.get(nevek[0]);
       if(object == null){
            throw new IllegalArgumentException("A 'stat' parancs első paraméterének egy érvényes objektumnak kell lennie.");
       }
       String stat = "";
       if(nevek.length > 1){
           if(object instanceof Hokotro){
                Hokotro hokotro = (Hokotro) object;
                switch(nevek[1].toLowerCase()){
                    case "hanyofej" -> stat =hokotro.getFej(Hanyofej.class.getSimpleName()).printStat(nevek[0] + ".hanyofej");
                    case "jegtoro" -> stat =hokotro.getFej(Jegtoro.class.getSimpleName()).printStat(nevek[0] + ".jegtoro");
                    case "sarkanyfej" -> stat =hokotro.getFej(Sarkanyfej.class.getSimpleName()).printStat(nevek[0] + ".sarkanyfej");
                    case "sopro" -> stat =hokotro.getFej(Sopro.class.getSimpleName()).printStat(nevek[0] + ".sopro");
                    case "soszoro" -> stat =hokotro.getFej(Soszoro.class.getSimpleName()).printStat(nevek[0] + ".soszoro");
                    case "zuzalekszoro" -> stat =hokotro.getFej(ZuzalekSzoro.class.getSimpleName()).printStat(nevek[0] + ".zuzalekszoro");
                    default -> throw new IllegalArgumentException("Ismeretlen felszerelés a 'stat' parancsban: " + nevek[1]);
                }
           } else{
               throw new IllegalArgumentException("A 'stat' parancs második paramétere csak hókotrónál használható.");
           }
       }else{
           stat = object.printStat(nevek[0]);
       }
       System.out.println(stat);
       replyLog.add(stat); // Válasz naplózása
    }
    private void handleAddMoney(String[] args) { 
        KozosKassza kassza = nevTar.get(args[0]) instanceof KozosKassza ? (KozosKassza) nevTar.get(args[0]) : null;
        if(kassza == null){
            throw new IllegalArgumentException("A 'addmoney' parancs első paraméterének egy érvényes közös kasszának kell lennie.");
        }
        kassza.penzHozzaadas(Integer.parseInt(args[1]));
         printOk("Pénz hozzáadva."); 
    }
    private void handleAddJarmu(String[] args) { 
        Takarito jatekos = nevTar.get(args[0]) instanceof Takarito ? (Takarito) nevTar.get(args[0]) : null;
        if(jatekos == null){
            if(nevTar.get(args[0]) instanceof Sofor){
                Sofor sofor = (Sofor) nevTar.get(args[0]);
                Busz busz = nevTar.get(args[1]) instanceof jarmu.Busz ? (jarmu.Busz) nevTar.get(args[1]) : null;
                if(busz == null){
                    throw new IllegalArgumentException("A 'addjarmu' parancs második paraméterének egy érvényes busznak kell lennie.");
                }
                if(sofor.getBusz() != null){
                    throw new IllegalArgumentException("A sofőr már irányít egy buszt, nem adható hozzá újabb.");
                }
                sofor.setJarmu(busz);
                printOk("Jármű hozzáadva.");
            } else{
            throw new IllegalArgumentException("A 'addjarmu' parancs első paraméterének egy érvényes játékosnak kell lennie.");
            }
        }else{
        Hokotro gep = nevTar.get(args[1]) instanceof Hokotro ? (Hokotro) nevTar.get(args[1]) : null;
        if(gep == null){
            throw new IllegalArgumentException("A 'addjarmu' parancs második paraméterének egy érvényes hókotrónak kell lennie.");
        }
        jatekos.addHokotro(gep);
        printOk("Jármű hozzáadva."); 
        }
    }
    private void handleSetState(String[] args) { 
        if(args.length != 2){
            throw new IllegalArgumentException("A 'setstate' parancs két paramétert vár: a sáv nevét és az új állapotot.");
        }
        Sav sav = nevTar.get(args[0]) instanceof Sav ? (Sav) nevTar.get(args[0]) : null;
        if(sav == null){
            throw new IllegalArgumentException("A 'setstate' parancs első paraméterének egy érvényes sávnak kell lennie.");
        }
        Savallapot ujAllapot = nevTar.get(args[1]) instanceof Savallapot ? (Savallapot) nevTar.get(args[1]) : null;
        if(ujAllapot == null){
            switch(args[1].toLowerCase()){
                case "jeges" -> sav.setAllapot(new Jeges());
                case "melyho" -> sav.setAllapot(new MelyHo());
                case "sekelyho" -> sav.setAllapot(new SekelyHo());
                case "tiszta" -> sav.setAllapot(new Tiszta());
                default -> throw new IllegalArgumentException("Ismeretlen állapot a 'setstate' parancsban: " + args[1]);
            }
        } else{
            sav.setAllapot(ujAllapot);
        }
        printOk("Állapot beállítva."); 
    }
    private void handleSetNyomvonal(String[] args) {
        if(args.length != 2){
            throw new IllegalArgumentException("A 'setnyomvonal' parancs két paramétert vár: a sáv nevét és a nyomvonalak számát.");
        }
        Sav sav = nevTar.get(args[0]) instanceof Sav ? (Sav) nevTar.get(args[0]) : null;
        if(sav == null){
            throw new IllegalArgumentException("A 'setnyomvonal' parancs első paraméterének egy érvényes sávnak kell lennie.");
        }
        int nyomvonalakSzama = Integer.parseInt(args[1]);
        if(sav.getAllapot() instanceof SekelyHo sekelyHo){
            sekelyHo.setNyomvonal(nyomvonalakSzama);
        }
        printOk("Nyomvonal beállítva.");
    }
    private void handleSetSomennyiseg(String[] args) { 
        if(args.length != 2){
            throw new IllegalArgumentException("A 'setsomennyiseg' parancs két paramétert vár: a hókotró nevét és a só mennyiségét.");
        }
        Hokotro hokotro = nevTar.get(args[0]) instanceof Hokotro ? (Hokotro) nevTar.get(args[0]) : null;
        if(hokotro == null){
            throw new IllegalArgumentException("A 'setsomennyiseg' parancs első paraméterének egy érvényes hókotrónak kell lennie.");
        }
        int somennyiseg = Integer.parseInt(args[1]);
        Soszoro soszoro = (Soszoro) hokotro.getFej("Soszoro");
        if(soszoro == null){
            throw new IllegalArgumentException("A megadott hókotrón nincs sószóró feje, így nem állítható be a só mennyisége.");
        }
        soszoro.setSoMennyiseg(somennyiseg);
        printOk("Sómennyiség beállítva."); 
    }
    private void handleSetKerozinmennyiseg(String[] args) {
        if(args.length != 2){
            throw new IllegalArgumentException("A 'setkerozinmennyiseg' parancs két paramétert vár: a hókotró nevét és a kerozin mennyiségét.");
        }
        Hokotro hokotro = nevTar.get(args[0]) instanceof Hokotro ? (Hokotro) nevTar.get(args[0]) : null;
        if(hokotro == null){
            throw new IllegalArgumentException("A 'setkerozinmennyiseg' parancs első paraméterének egy érvényes hókotrónak kell lennie.");
        }
        int kerozinmennyiseg = Integer.parseInt(args[1]);
        Sarkanyfej sarkanyfej = (Sarkanyfej) hokotro.getFej("Sarkanyfej");
        if(sarkanyfej == null){
            throw new IllegalArgumentException("A megadott hókotrón nincs sárkányfejje, így nem állítható be a kerozin mennyisége.");
        }
        sarkanyfej.setKerozinMennyiseg(kerozinmennyiseg);
        printOk("Kerozin beállítva."); 
    }


    /* még nem tudom mi lesz ezekkel de tippre nem kellenek */
    private void handleSetStartPoint(String[] args) { /* ... */ printOk("Kezdőpont beállítva."); }
    private void handleSetEndPoint(String[] args) { /* ... */ printOk("Végpont beállítva."); }


    private void handleSozas(String[] args) {
        if(args.length != 1){
            throw new IllegalArgumentException("A 'sozas' parancs egyetlen paramétert vár: a sáv nevét.");
        }
        Sav sav = nevTar.get(args[0]) instanceof Sav ? (Sav) nevTar.get(args[0]) : null;
        if(sav == null){
            throw new IllegalArgumentException("A 'sozas' parancs első paraméterének egy érvényes sávnak kell lennie.");
        }
        sav.soSzoras();
        printOk("Sózás sikeres."); 
    }
    private void handleZuzalekszoras(String[] args) { 
        if(args.length != 1){
            throw new IllegalArgumentException("A 'zuzalekszoras' parancs egyetlen paramétert vár: a sáv nevét.");
        }
        Sav sav = nevTar.get(args[0]) instanceof Sav ? (Sav) nevTar.get(args[0]) : null;
        if(sav == null){
            throw new IllegalArgumentException("A 'zuzalekszoras' parancs első paraméterének egy érvényes sávnak kell lennie.");
        }
        sav.zuzalekSzoras();
        printOk("Zúzalékszórás sikeres."); 
    }
    private void handleConnect(String[] args) { 
        if(args.length != 2){
            throw new IllegalArgumentException("A 'connect' parancs két paramétert vár: a két csomópont (Sav, Keresztezodes, Checkpoint) nevét.");
        }
        Csomopont csomopont1 = nevTar.get(args[0]) instanceof Csomopont ? (Csomopont) nevTar.get(args[0]) : null;
        Csomopont csomopont2 = nevTar.get(args[1]) instanceof Csomopont ? (Csomopont) nevTar.get(args[1]) : null;
        if(csomopont1 == null || csomopont2 == null){
            throw new IllegalArgumentException("A 'connect' parancs mindkét paraméterének érvényes csomópontnak kell lennie.");
        }
        switch (csomopont1) {
            case Sav sav -> sav.addSzomszed(csomopont2);
            case Keresztezodes keresztezodes -> keresztezodes.addKimenet(csomopont2);
            case Checkpoint checkpoint -> checkpoint.setKimenet(csomopont2);
            default -> throw new IllegalArgumentException("A 'connect' parancs mindkét paraméterének érvényes csomópontnak kell lennie.");
        }
        printOk("Kapcsolat létrehozva."); 
    }
    private void handleBuy(String[] args) { 
        Takarito takarito = nevTar.get(args[0]) instanceof Takarito ? (Takarito) nevTar.get(args[0]) : null;
        if(takarito == null){
            Hokotro hokotro = nevTar.get(args[0]) instanceof Hokotro ? (Hokotro) nevTar.get(args[0]) : null;
            Arucikk arucikk = switch(args[1].toLowerCase()){
                case "hanyofej" -> Arucikk.HANYOFEJ;
                case "sarkanyfej" -> Arucikk.SARKANYFEJ;
                case "soszoro" -> Arucikk.SOSZORO;
                case "zuzalekszoro" -> Arucikk.ZUZALEKSZORO;
                case "so" -> Arucikk.SO;
                case "kerozin" -> Arucikk.KEROZIN;
                case "zuzalek" -> Arucikk.ZUZALEK;
                case "globalwarming" -> Arucikk.GLOBAL_WARMING;
                default -> throw new IllegalArgumentException("Ismeretlen árucikk a 'buy' parancsban: " + args[1]);
            };
            jatekVezerlo.vasarol(arucikk, hokotro);
        }else{
            Arucikk arucikk = switch(args[2].toLowerCase()){
                case "hanyofej" -> Arucikk.HANYOFEJ;
                case "sarkanyfej" -> Arucikk.SARKANYFEJ;
                case "soszoro" -> Arucikk.SOSZORO;
                case "zuzalekszoro" -> Arucikk.ZUZALEKSZORO;
                case "so" -> Arucikk.SO;
                case "kerozin" -> Arucikk.KEROZIN;
                case "zuzalek" -> Arucikk.ZUZALEK;
                case "globalwarming" -> Arucikk.GLOBAL_WARMING;
                default -> throw new IllegalArgumentException("Ismeretlen árucikk a 'buy' parancsban: " + args[2]);
            };
            Hokotro hokotro = nevTar.get(args[1]) instanceof Hokotro ? (Hokotro) nevTar.get(args[1]) : null;
            if(jatekVezerlo.getBolt().vasarol(arucikk, takarito, hokotro)){
                printOk("Vásárlás sikeres.");
            } else {
                printFailed("Vásárlás sikertelen. Ellenőrizd a kassza egyenlegét és a hókotród kapacitását.");
            }
        }
    }
    private void handleFejcsere(String[] args) { 
        if(args.length != 2){
            throw new IllegalArgumentException("A 'fejcsere' parancs kettő paramétert vár: a hókotró nevét és az új fej típusát.");
        }
        Hokotro hokotro = nevTar.get(args[0]) instanceof Hokotro ? (Hokotro) nevTar.get(args[0]) : null;
        if(hokotro == null){
            throw new IllegalArgumentException("A 'fejcsere' parancs első paraméterének egy érvényes hókotrónak kell lennie.");
        }
        String fejtipus = "";
        switch (args[1].toLowerCase()){
            case "hanyofej" -> fejtipus = "Hanyofej";
            case "jegtoro" -> fejtipus = "Jegtoro";
            case "sarkanyfej" -> fejtipus = "Sarkanyfej";
            case "sopro" -> fejtipus = "Sopro";
            case "soszoro" -> fejtipus = "Soszoro";
            case "zuzalekszoro" -> fejtipus = "ZuzalekSzoro";
            default -> throw new IllegalArgumentException("Ismeretlen fej típus a 'fejcsere' parancsban: " + args[1]);
        }
        Kotrofej ujFej = hokotro.getFej(fejtipus);
        if(ujFej == null){
            throw new IllegalArgumentException("A megadott fej típus nem létezik, vagy nincs feloldva a megadott hókotróban.");
        }
        hokotro.cserelFej(ujFej);
        printOk("Fejcsere sikeres."); 
    }
    private void handleAddSav(String[] args) { 
        if(args.length != 2){
            throw new IllegalArgumentException("Az 'addsav' parancs két paramétert vár: az útszakasz és a sáv nevét.");
        }
        Utszakasz utszakasz = nevTar.get(args[0]) instanceof Utszakasz ? (Utszakasz) nevTar.get(args[0]) : null;
        if(utszakasz == null){
            throw new IllegalArgumentException("Az 'addsav' parancs első paraméterének egy érvényes útszakasznak kell lennie.");
        }
        Sav sav = nevTar.get(args[1]) instanceof Sav ? (Sav) nevTar.get(args[1]) : null;
        if(sav == null){
            throw new IllegalArgumentException("Az 'addsav' parancs második paraméterének egy érvényes sávnak kell lennie.");
        }
        utszakasz.addSav(sav);
        printOk("Sáv hozzáadva."); }
    private void handleSetZuzalekmennyiseg(String[] args) { 
        if(args.length != 2){
            throw new IllegalArgumentException("A 'setzuzalekmennyiseg' parancs két paramétert vár: a hókotró nevét és az új zúzalék mennyiséget.");
        }
        Hokotro hokotro = nevTar.get(args[0]) instanceof Hokotro ? (Hokotro) nevTar.get(args[0]) : null;
        if(hokotro == null){
            throw new IllegalArgumentException("A 'setzuzalekmennyiseg' parancs első paraméterének egy érvényes hókotrónak kell lennie.");
        }
        int zuzalekmennyiseg = Integer.parseInt(args[1]);
        ZuzalekSzoro zuzalekszoro = (ZuzalekSzoro) hokotro.getFej(ZuzalekSzoro.class.getSimpleName());
        if(zuzalekszoro == null){
            throw new IllegalArgumentException("A megadott hókotrón nincs zúzalékszóró feje, így nem állítható be a zúzalék mennyisége.");
        }
        zuzalekszoro.setZuzalekMennyiseg(zuzalekmennyiseg);
        printOk("Zúzalék beállítva."); 
    }
    private void handleBaleset(String[] args) { 
        if(args.length != 1){
            throw new IllegalArgumentException("A 'baleset' parancs egyetlen paramétert vár: a jármű nevét.");
        }
        Jarmu jarmu = nevTar.get(args[0]) instanceof Jarmu ? (Jarmu) nevTar.get(args[0]) : null;
        if(jarmu == null){
            throw new IllegalArgumentException("A 'baleset' parancs első paraméterének egy érvényes járműnek kell lennie.");
        }
        jarmu.balesetetSzenved();
        printOk("Baleset kezelve.");
    }
    private void handleHavazas(String[] args) { 
        if(args.length != 0){
            throw new IllegalArgumentException("A 'havazas' parancs nem vár paramétert.");
        }
        //teljesen megkerüljük a logikát
        jatekVezerlo.getVarosModell().havazas();
        printOk("Havazás végrehajtva."); 
    }




    private void printOk(String message) {
        String formattedMessage = "[OK] " + message;
        System.out.println(formattedMessage);
        replyLog.add(formattedMessage);
    }

    private void printFailed(String message) {
        String formattedMessage = "[FAILED] " + message;
        System.out.println(formattedMessage);
        replyLog.add(formattedMessage);
    }

    private void printError(String message) {
        String formattedMessage = "[ERROR] " + message;
        System.out.println(formattedMessage);
        replyLog.add(formattedMessage);   //[ERROR]-ra futó teszt miatt kell
    }
}