package vezerles;
import felszereles.*;
import gazdasag.*;
import halozat.*;
import jarmu.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- Zúzmaraváros Szimuláció Inicializálása ---");

        // 1. Pénzügyi és központi rendszerek felállítása
        KozosKassza kassza = new KozosKassza(1000); // Induló tőke
        VarosModell varos = new VarosModell(kassza); // A központi menedzser [cite: 1296-1297]

        // 2. A hálózat (Gráf) építése
        Checkpoint startCp = new Checkpoint();
        Checkpoint celCp = new Checkpoint();
        Sav sav1 = new Sav();
        Sav sav2 = new Sav();

        // 3. Kapcsolatok (élek) kialakítása
        startCp.setKimenet(sav1); // A startpontból a sav1-re vezet az út
        sav1.getNext().add(sav2); // A sav1-ből a sav2-re
        sav2.getNext().add(celCp); // A sav2-ből a célba

        // 4. Hozzáadás a VárosModellhez
        varos.addCsomopont(startCp);
        varos.addCsomopont(sav1);
        varos.addCsomopont(sav2);
        varos.addCsomopont(celCp);

        Utszakasz foUtca = new Utszakasz();
        foUtca.addSav(sav1);
        foUtca.addSav(sav2);
        varos.addUtszakasz(foUtca); // A VárosModell így tudja majd havaztatni a sávokat [cite: 1304-1305]

        // 5. Szereplők inicializálása
        Auto auto = new Auto(startCp, celCp);
        startCp.befogad(auto);
        auto.setAktualisCsomopont(startCp);

        System.out.println("A város megépült, az autó a startvonalon áll!");
        System.out.println("----------------------------------------------\n");

        // --- SZIMULÁCIÓS KÖRÖK ---

        // 1. KÖR: Tiszta út, az autó lép egyet
        System.out.println("[1. KÖR] Az időjárás tiszta.");
        varos.palyaFrissit(); // Pálya időzítőjének frissítése [cite: 1313-1314]
        
        boolean lepes1 = auto.lep(sav1);
        System.out.println("Autó rálép az 1. sávra: " + (lepes1 ? "SIKERES" : "SIKERTELEN"));

        // 2. KÖR: Extrém Havazás (3 réteg hó = Mély Hó)
        System.out.println("\n[2. KÖR] Hatalmas hóvihar csap le Zúzmaravárosra!");
        varos.havazas(); 
        varos.havazas(); 
        varos.havazas(); // 3 réteg hó után a sávok átváltanak MelyHo állapotba [cite: 1082-1083, 1185-1186]

        varos.palyaFrissit();

        // 3. KÖR: Az autó megpróbál haladni a mély hóban
        System.out.println("\n[3. KÖR] Az autó megpróbál továbbmenni a 2. sávra...");
        
        // Itt manuálisan ellenőrizzük a lépéstesztet, ahogy a dokumentáció előírja
        boolean tudLepni = sav2.lepesTeszt(auto); // A MelyHo állapot itt false-t fog adni az Autóra [cite: 1085-1090]
        
        if (tudLepni) {
            boolean lepes2 = auto.lep(sav2);
            System.out.println("Autó továbbhaladt: " + lepes2);
        } else {
            System.out.println("Autó elakadt! A sáv járhatatlan a mély hó miatt.");
        }

        // 4. KÖR: Megérkezik a Hókotró a megmentésre!
        System.out.println("\n[4. KÖR] A Takarító kiküld egy Hókotrót!");
        Takarito takarito = new Takarito(kassza);
        Hokotro hokotro = new Hokotro(takarito);
        
        Kotrofej ujSopro = new Sopro(); 
        hokotro.addFej(ujSopro); // Felszereljük a söprő fejet
        hokotro.cserelFej(ujSopro); // Aktívvá tesszük az átadott referencia alapján
        
        // A hókotró "leugrik" a 2. sávra (a valóságban végigmenne az úton, de a teszt kedvéért ide tesszük)
        sav2.befogad(hokotro);
        hokotro.setAktualisCsomopont(sav2);
        
        // A hókotró letakarítja a 2. sávot
        boolean takaritasSikeres = hokotro.takarit(sav2);
        System.out.println("Hókotró letakarította a 2. sávot: " + (takaritasSikeres ? "SIKERES" : "SIKERTELEN"));
        
        // 5. KÖR: Az autó újra próbálkozik
        System.out.println("\n[5. KÖR] Az autó ismét megpróbál haladni...");
        tudLepni = sav2.lepesTeszt(auto); // A takarítás után a sáv ismét Tiszta állapotú
        
        if (tudLepni) {
            // Mivel a hókotró még a sávon van, foglalt lesz!
            System.out.println("Sáv foglalt-e? " + sav2.foglalt());
            boolean lepes3 = auto.lep(sav2);
            System.out.println("Autó rálép a 2. sávra: " + (lepes3 ? "SIKERES" : "SIKERTELEN (Foglalt)"));
        }
        
        System.out.println("\n--- Szimuláció Vége ---");
    }
}
