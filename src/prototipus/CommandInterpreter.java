package prototipus;

import allapot.Jeges;
import allapot.MelyHo;
import allapot.SekelyHo;
import allapot.Tiszta;
import felszereles.Hanyofej;
import felszereles.Jegtoro;
import felszereles.Sarkanyfej;
import felszereles.Sopro;
import felszereles.Soszoro;
import felszereles.ZuzalekSzoro;
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
import jarmu.Hokotro;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import vezerles.IJatekVezerlo;

public class CommandInterpreter {
    IJatekVezerlo jatekVezerlo;
    public static final Map<String, IStatable> nevTar = new HashMap<>();
    public static final Map<IStatable, String> reverseNevTar = new HashMap<>();


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

            processCommand(line);
        }
    }

    /**
     * Egyetlen parancssor feldolgozása.
     */
    private void processCommand(String line) {
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
                default:
                    printError("Ismeretlen parancs: " + command);
            }
        } catch (IllegalArgumentException e) {
            printError("Hibás paraméterezés: " + e.getMessage());
        } catch (Exception e) {
            printFailed("Váratlan hiba történt a parancs végrehajtása közben.");
        }
    }

    // ==========================================
    // PARANCS KEZELŐ FÜGGVÉNYEK (VÁZLATOK)
    // ==========================================

    private void handleTick(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("A 'tick' parancs egyetlen paramétert vár: a körök számát");
        }
        jatekVezerlo.tick(Integer.parseInt(args[0]));
    }

    private void handleLoad(String[] args) {
        //TODO
    }

    private void handleSave(String[] args) {
       //TODO
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
                break;
            case "melyho":
                MelyHo melyho = new MelyHo();
                nevTar.put(args[1], melyho);
                reverseNevTar.put(melyho, args[1]);
                break;
            case "sekelyho":
                SekelyHo sekelyHo = new SekelyHo();
                nevTar.put(args[1], sekelyHo);
                reverseNevTar.put(sekelyHo, args[1]);   
                break;
            case "tiszta":
                Tiszta tiszta = new Tiszta();
                nevTar.put(args[1], tiszta);
                reverseNevTar.put(tiszta, args[1]);
                break;
            case "hanyofej":
                Hanyofej hanyofej = new Hanyofej();
                nevTar.put(args[1], hanyofej);
                reverseNevTar.put(hanyofej, args[1]);
                break;
            case "jegtoro":
                Jegtoro jegtoro = new Jegtoro();
                nevTar.put(args[1], jegtoro);
                reverseNevTar.put(jegtoro, args[1]);
                break;
            case "sarkanyfej":
                Sarkanyfej sarkanyfej = new Sarkanyfej(Integer.parseInt(args[2]));
                nevTar.put(args[1], sarkanyfej);
                reverseNevTar.put(sarkanyfej, args[1]);
                break;
            case "sopro":
                Sopro sopro = new Sopro();
                nevTar.put(args[1], sopro);
                reverseNevTar.put(sopro, args[1]);
                break;
            case "soszoro":
                Soszoro soszoro = new Soszoro(Integer.parseInt(args[2]));
                nevTar.put(args[1], soszoro);
                reverseNevTar.put(soszoro, args[1]);
                break;
            case "zuzalekszoro":
                ZuzalekSzoro zuzalekszoro = new ZuzalekSzoro(Integer.parseInt(args[2]));
                nevTar.put(args[1], zuzalekszoro);
                reverseNevTar.put(zuzalekszoro, args[1]);
                break;
            case "bolt":
                Bolt bolt = new Bolt();
                nevTar.put(args[1], bolt);
                reverseNevTar.put(bolt, args[1]);
                break;
            case "kassza":
                KozosKassza kassza = new KozosKassza(Integer.parseInt(args[2]));
                nevTar.put(args[1], kassza);
                reverseNevTar.put(kassza, args[1]);
                break;
            case "sofor":
                KozosKassza kSofor = nevTar.get(args[2]) instanceof KozosKassza ? (KozosKassza) nevTar.get(args[2]) : null;
                if(kSofor == null){
                    throw new IllegalArgumentException("A 'sofor' létrehozásához egy érvényes közös kassza szükséges paraméterként.");
                }
                Sofor sofor = new Sofor(kSofor);
                nevTar.put(args[1], sofor);
                reverseNevTar.put(sofor, args[1]);
                break;
            case "takarito":
                KozosKassza kTakarito = nevTar.get(args[2]) instanceof KozosKassza ? (KozosKassza) nevTar.get(args[2]) : null;
                if(kTakarito == null){
                    throw new IllegalArgumentException("A 'takarito' létrehozásához egy érvényes közös kassza szükséges paraméterként.");
                }
                Takarito takarito = new Takarito(kTakarito);
                nevTar.put(args[1], takarito);
                reverseNevTar.put(takarito, args[1]);
                break;
            case "alagut":
                Alagut alagut = new Alagut();
                nevTar.put(args[1], alagut);
                reverseNevTar.put(alagut, args[1]);
                break;
            case "checkpoint":
                Checkpoint checkpoint = new Checkpoint();
                nevTar.put(args[1], checkpoint);
                reverseNevTar.put(checkpoint, args[1]);
                break;
            case "keresztezodes":
                Keresztezodes keresztezodes = new Keresztezodes();
                nevTar.put(args[1], keresztezodes);
                reverseNevTar.put(keresztezodes, args[1]);
                break;
            case "sav":
                Sav sav = new Sav();
                nevTar.put(args[1], sav);
                reverseNevTar.put(sav, args[1]);
                break;
            case "utszakasz":
                Utszakasz utszakasz = new Utszakasz();
                nevTar.put(args[1], utszakasz);
                reverseNevTar.put(utszakasz, args[1]);
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
                break;
            case "busz":
                Checkpoint buszStart = nevTar.get(args[2]) instanceof Checkpoint ? (Checkpoint) nevTar.get(args[2]) : null;
                Checkpoint buszCel = nevTar.get(args[3]) instanceof Checkpoint ? (Checkpoint) nevTar.get(args[3]) : null;
                Sofor buszSofor = nevTar.get(args[4]) instanceof Sofor ? (Sofor) nevTar.get(args[4]) : null;
                if(buszStart == null || buszCel == null || buszSofor == null){
                    throw new IllegalArgumentException("A 'busz' létrehozásához érvényes kiindulási és cél checkpoint, valamint egy sofőr szükséges paraméterként.");
                }
                jarmu.Busz busz = new jarmu.Busz(buszStart, buszCel, buszSofor);
                nevTar.put(args[1], busz);
                reverseNevTar.put(busz, args[1]);
                break;
            case "hokotro":
                Takarito vezeto = nevTar.get(args[2]) instanceof Takarito ? (Takarito) nevTar.get(args[2]) : null;
                if(vezeto == null){
                    throw new IllegalArgumentException("A 'hokotro' létrehozásához egy érvényes takarító szükséges paraméterként.");
                }
                Hokotro hokotro = new Hokotro(vezeto);
                nevTar.put(args[1], hokotro);
                reverseNevTar.put(hokotro, args[1]);
                break;
            default:
                throw new IllegalArgumentException("Ismeretlen típus a 'create' parancsban: " + args[0]);
       }
    }

    private void handleStep(String[] args) {
        Csomopont cel = nevTar.get(args[0]) instanceof Csomopont ? (Csomopont) nevTar.get(args[0]) : null;
        if(cel == null){
            throw new IllegalArgumentException("A 'step' parancs első paraméterének egy érvényes csomópontnak kell lennie.");
        }
        jatekVezerlo.lep(cel);
    }

    private void handleStat(String[] args) {
       nevTar.get(args[0]).printStat(args[0]);
    }

    // (A többi handleX függvény hasonló felépítéssel rendelkezne...)
    private void handleAddMoney(String[] args) { /* ... */ printOk("Pénz hozzáadva."); }
    private void handleAddJarmu(String[] args) { /* ... */ printOk("Jármű hozzáadva."); }
    private void handleSetState(String[] args) { /* ... */ printOk("Állapot beállítva."); }
    private void handleSetNyomvonal(String[] args) { /* ... */ printOk("Nyomvonal beállítva."); }
    private void handleSetSomennyiseg(String[] args) { /* ... */ printOk("Sómennyiség beállítva."); }
    private void handleSetKerozinmennyiseg(String[] args) { /* ... */ printOk("Kerozin beállítva."); }
    private void handleSetStartPoint(String[] args) { /* ... */ printOk("Kezdőpont beállítva."); }
    private void handleSetEndPoint(String[] args) { /* ... */ printOk("Végpont beállítva."); }
    private void handleSozas(String[] args) { /* ... */ printOk("Sózás sikeres."); }
    private void handleZuzalekszoras(String[] args) { /* ... */ printOk("Zúzalékszórás sikeres."); }
    private void handleConnect(String[] args) { /* ... */ printOk("Kapcsolat létrehozva."); }
    private void handleBuy(String[] args) { /* ... */ printOk("Vásárlás sikeres."); }
    private void handleFejcsere(String[] args) { /* ... */ printOk("Fejcsere sikeres."); }
    private void handleAddSav(String[] args) { /* ... */ printOk("Sáv hozzáadva."); }
    private void handleSetZuzalekmennyiseg(String[] args) { /* ... */ printOk("Zúzalék beállítva."); }


    // ==========================================
    // KIMENETI FORMÁZÓ FÜGGVÉNYEK (SPECIFIKÁCIÓ ALAPJÁN)
    // ==========================================

    private void printOk(String message) {
        System.out.println("[OK] " + message);
    }

    private void printFailed(String message) {
        System.out.println("[FAILED] " + message);
    }

    private void printError(String message) {
        System.out.println("[ERROR] " + message);
    }
}