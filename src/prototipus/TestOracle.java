package prototipus;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import static prototipus.CommandInterpreter.nevTar;
import static prototipus.CommandInterpreter.reverseNevTar;

public class TestOracle {
    private File testFolder = new File("tests");

    public static void main(String[] args) {
        System.out.println("Test Oracle: A tesztek helyes működésének ellenőrzése");
        System.out.println("Parancsok: 'run <fájlnév>', 'run all', vagy 'exit' a kilépéshez.");

        // Létrehozunk egy példányt, hogy az objektum szintű változókat (testFolder) elérjük
        TestOracle oracle = new TestOracle();
        oracle.startListening();
    }

    /**
     * Várja és feldolgozza a konzolos utasításokat.
     */
    public void startListening() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            
            // Kilépési feltételek
            if (line.equalsIgnoreCase("exit") || line.equals("0")) {
                System.out.println("Kilépés...");
                break;
            }
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            String command = parts[0].toLowerCase();

            if (command.equals("run")) {
                if (parts.length > 1) {
                    if (parts[1].equalsIgnoreCase("all")) {
                        BatchRunTests();
                    } else {
                        RunTest(parts[1]);
                    }
                } else {
                    System.out.println("[HIBA] Kérlek add meg a teszt nevét, vagy használd a 'run all' parancsot!");
                }
            } else {
                System.out.println("[HIBA] Ismeretlen parancs. Használd a 'run' parancsot.");
            }
        }
        scanner.close();
    }

    /**
     * Egyetlen tesztfájl futtatása a CommandInterpreter segítségével.
     */
    public void RunTest(String testName) {
        System.out.println("Running test: " + testName);
        File containerFolder = new File(testFolder, testName);

        if (!containerFolder.exists()) {
            System.out.println("[HIBA] A tesztfájl nem található: " + containerFolder.getPath());
            return;
        }
        File inputFile = new File(containerFolder, "Bemenet.txt");
        try {
            // Megnyitjuk a tesztfájlt és átadjuk a parancsértelmezőnek
            Scanner fileScanner = new Scanner(inputFile);
            CommandInterpreter interpreter = new CommandInterpreter();
            nevTar.clear(); // Tisztítjuk a névtárakat, hogy ne legyenek maradványok előző tesztekből
            reverseNevTar.clear();
            CommandInterpreter.replyLog.clear();
            interpreter.start(fileScanner); // Ez végrehajtja a fájlban lévő összes parancsot
            
            System.out.println("--> Teszt véget ért: " + testName);
            assertTest(testName);
        } catch (FileNotFoundException e) {
            System.out.println("[HIBA] Fájl beolvasási hiba: " + e.getMessage());
        }
    }
    public void assertTest(String testName) {
        File containerFolder = new File(testFolder, testName);
        File expectedOutputFile = new File(containerFolder, "Kimenet.txt");
        if (!expectedOutputFile.exists()) {
            System.out.println("[HIBA] A várt kimenet fájl nem található: " + expectedOutputFile.getPath());
            return;
        }
        try{
            Scanner expectedScanner = new Scanner(expectedOutputFile);
            while(expectedScanner.hasNextLine()) {
                String expectedLine = expectedScanner.nextLine().trim();
                if (!CommandInterpreter.replyLog.contains(expectedLine)) {
                    System.out.println("[ASSERTION HIBA] Várt kimenet nem található a logban: " + expectedLine);
                    return;
                }
            }
            
        }catch(FileNotFoundException e){
            System.out.println("[HIBA] Várt kimenet fájl beolvasási hiba: " + e.getMessage());
            return;
        }
        System.out.println("[ASSERTION SUCCESS] Minden várt kimenet megtalálható a logban.");

    }

    /**
     * A 'tests' mappában található összes .txt fájl lefuttatása.
     */
    public void BatchRunTests() {
        System.out.println("Batch running all tests...");
        
        // Ellenőrizzük, hogy a mappa létezik-e
        if (!testFolder.exists() || !testFolder.isDirectory()) {
            System.out.println("[HIBA] A '" + testFolder.getName() + "' mappa nem található!");
            return;
        }

        // Fájlok listázása
        File[] files = testFolder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("A teszt mappa üres, nincsenek futtatható tesztek.");
            return;
        }
        
        // 1. Sorbarendezzük a tömböt a bennük lévő számok alapján
            Arrays.sort(files, Comparator.comparingInt(f -> {
                // A "\\D+" regex eltávolít minden nem-szám karaktert a névből
                String num = f.getName().replaceAll("\\D+", "");
                // Ha nincs benne szám, 0-nak vesszük, különben átalakítjuk Integer-ré
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }));
        
        // Végigmegyünk az összes kiterjesztésnek megfelelő fájlon
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("\n----------------------------------------");
                RunTest(file.getName());
            }
        }
        
        System.out.println("----------------------------------------");
        System.out.println("Minden teszt futtatása befejeződött.");
    }
}