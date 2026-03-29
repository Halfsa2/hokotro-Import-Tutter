package vezerles;

import allapot.*;
import felszereles.*;
import gazdasag.*;
import halozat.Alagut;
import halozat.Checkpoint;
import halozat.Keresztezodes;
import halozat.Sav;
import halozat.Utszakasz;
import jarmu.Auto;
import jarmu.Busz;
import jarmu.Hokotro;
import java.util.Scanner;

// így futtatjátok:
//javac -cp . src/**/*.java -d bin
//java -cp bin vezerles.Main
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean fut = true;

        System.out.println("==========================================================");
        System.out.println("  Zúzmaraváros Szimuláció - Kotrófej Szkeleton Tesztek    ");
        System.out.println("==========================================================");

        while (fut) {
            System.out.println("\n--- Válassz egy tesztesetet! ---");
            System.out.println("1.  Sikeres takarítás sárkányfejjel (havat tisztít)");
            System.out.println("2.  Sikeres takarítás sárkányfejjel (jeget tisztít)");
            System.out.println("3.  Sikertelen takarítás sárkányfejjel (nincs kerozin)");
            System.out.println("4.  Sikeres takarítás sószóróval");
            System.out.println("5.  Sikertelen takarítás sószóróval (nincs só)");
            System.out.println("6.  Sikeres takarítás jégtörővel (jeget tisztít)");
            System.out.println("7.  Sikertelen takarítás jégtörővel (hó eltávolításának kísérlete)");
            System.out.println("8.  Sikeres hóeltakarítás söprő fejjel (van szomszédos sáv)");
            System.out.println("9.  Sikeres hóeltakarítás söprő fejjel (nincs szomszédos sáv)");
            System.out.println("10. Sikertelen takarítás söprővel (jég eltávolításának kísérlete)");
            System.out.println("11. Sikeres takarítás hányófejjel (havat tisztít)");
            System.out.println("12. Sikertelen takarítás hányófejjel (Jég eltávolításának kísérlete)");

            // Innentől Kata
            System.out.println("13. Sikeres buszforduló (Pénzkeresés)");
            System.out.println("14. Vásárlás a boltban (Sárkányfej)");
            System.out.println("15. Sikertelen vásárlás a boltban (nincs elég pénz)");
            System.out.println("16. Fogyóeszköz vásárlása a boltban (Só)");
            System.out.println("17. Fogyóeszköz vásárlása a boltban (Kerozin)");
            System.out.println("18. Új Hókotró vásárlása a boltban");
            System.out.println("19. Globális felmelegedés vásárlása a boltban");

            // Innentől Alex
            System.out.println("20. Hóesés tiszta, sózatlan útszakaszon");
            System.out.println("21. Hóesés tiszta, sózott útszakaszon");
            System.out.println("22. Hóesés alagúton");
            System.out.println("23. Kereszteződés frissítése");

            // Bibi
            System.out.println("24. Hófelhalmozódás");
            System.out.println("25. Hó jeggé tömörülése");
            System.out.println("26. MelyHo jarhatatlan");
            System.out.println("27. Só hatása tiszta sávon");
            System.out.println("28. Só hatása sekély havas sávon, 2 hóréteggel");
            System.out.println("29. Só hatása sekély havas savon, 1 hóréteggel");
            System.out.println("30. Só hatása mély havas savon");
            System.out.println("31. Só hatása jeges sávon, kevesebb, mint 2 köre sózva");
            System.out.println("32. Só hatása jeges sávon, pontosan 2 köre sózva");

            System.out.println("0.  Kilépés");
            System.out.print("\nVálasztás: ");

            String valasz = scanner.nextLine();

            if (valasz.equals("0")) {
                fut = false;
                continue;
            }

            Sav sav = new Sav();
            Hokotro hokotro = new Hokotro(null);
            boolean sikeresTakaritas = false;
            boolean sikeresTranzakcio = false; // vásárlások sikerességére

            System.out.println("\n----------------------------------------------------------");

            switch (valasz) {
                case "1":
                    System.out.println("[ USE-CASE: Sikeres takarítás sárkányfejjel (havat tisztít) ]");
                    System.out.print("[?] Van elég kerozin a sárkányfej tartályában? (I/N): ");
                    int kerozin1 = scanner.nextLine().equalsIgnoreCase("I") ? 5 : 0;
                    System.out.print("[?] Havat tisztít? (I/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("I")) {
                        sav.setAllapot(new SekelyHo());
                    } else {
                        sav.setAllapot(new Jeges());
                    }
                    Sarkanyfej sarkany1 = new Sarkanyfej(kerozin1);
                    hokotro.addFej(sarkany1);
                    hokotro.cserelFej(sarkany1);
                    sikeresTakaritas = hokotro.takarit(sav);
                    break;

                case "2":
                    System.out.println("[ USE-CASE: Sikeres takarítás sárkányfejjel (jeget tisztít) ]");
                    System.out.print("[?] Van elég kerozin a sárkányfej tartályában? (I/N): ");
                    int kerozin2 = scanner.nextLine().equalsIgnoreCase("I") ? 5 : 0;
                    System.out.print("[?] Jeget tisztít? (I/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("I")) {
                        sav.setAllapot(new Jeges());
                    } else {
                        sav.setAllapot(new SekelyHo());
                    }
                    Sarkanyfej sarkany2 = new Sarkanyfej(kerozin2);
                    hokotro.addFej(sarkany2);
                    hokotro.cserelFej(sarkany2);
                    sikeresTakaritas = hokotro.takarit(sav);
                    break;

                case "3":
                    System.out.println("[ USE-CASE: Sikertelen takarítás sárkányfejjel (nincs kerozin) ]");
                    System.out.print("[?] Van elég kerozin a sárkányfej tartályában? (I/N): ");
                    int kerozin3 = scanner.nextLine().equalsIgnoreCase("I") ? 5 : 0;
                    sav.setAllapot(new SekelyHo());
                    Sarkanyfej sarkany3 = new Sarkanyfej(kerozin3);
                    hokotro.addFej(sarkany3);
                    hokotro.cserelFej(sarkany3);
                    sikeresTakaritas = hokotro.takarit(sav);
                    break;

                case "4":
                    System.out.println("[ USE-CASE: Sikeres takarítás sószóróval ]");
                    System.out.print("[?] Van elég só a sószóró tartályában? (I/N): ");
                    int so4 = scanner.nextLine().equalsIgnoreCase("I") ? 5 : 0;
                    sav.setAllapot(new Jeges());
                    Soszoro soszoro4 = new Soszoro(so4);
                    hokotro.addFej(soszoro4);
                    hokotro.cserelFej(soszoro4);
                    sikeresTakaritas = hokotro.takarit(sav);
                    break;

                case "5":
                    System.out.println("[ USE-CASE: Sikertelen takarítás sószóróval (nincs só) ]");
                    System.out.print("[?] Van elég só a sószóró tartályában? (I/N): ");
                    int so5 = scanner.nextLine().equalsIgnoreCase("I") ? 5 : 0;
                    sav.setAllapot(new Jeges());
                    Soszoro soszoro5 = new Soszoro(so5);
                    hokotro.addFej(soszoro5);
                    hokotro.cserelFej(soszoro5);
                    sikeresTakaritas = hokotro.takarit(sav);
                    break;

                case "6":
                    System.out.println("[ USE-CASE: Sikeres takarítás jégtörővel (jeget tisztít) ]");
                    System.out.print("[?] Jeget tisztít? (I/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("I")) {
                        sav.setAllapot(new Jeges());
                    } else {
                        sav.setAllapot(new SekelyHo());
                    }
                    Jegtoro jegtoro6 = new Jegtoro();
                    hokotro.addFej(jegtoro6);
                    hokotro.cserelFej(jegtoro6);
                    sikeresTakaritas = hokotro.takarit(sav);
                    break;

                case "7":
                    System.out.println("[ USE-CASE: Sikertelen takarítás jégtörővel (hó eltávolításának kísérlete) ]");
                    System.out.print("[?] Jeget tisztít? (I/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("I")) {
                        sav.setAllapot(new Jeges());
                    } else {
                        sav.setAllapot(new SekelyHo());
                    }
                    Jegtoro jegtoro7 = new Jegtoro();
                    hokotro.addFej(jegtoro7);
                    hokotro.cserelFej(jegtoro7);
                    sikeresTakaritas = hokotro.takarit(sav);
                    break;

                case "8":
                    System.out.println("[ USE-CASE: Sikeres hóeltakarítás söprő fejjel (van szomszédos sáv) ]");
                    System.out.print("[?] Havat tisztít? (I/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("I")) {
                        sav.setAllapot(new SekelyHo());
                    } else {
                        sav.setAllapot(new Jeges());
                    }
                    System.out.print("[?] Van szomszédos sáv? (I/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("I")) {
                        Utszakasz u8 = new Utszakasz();
                        u8.addSav(sav);
                        u8.addSav(new Sav());
                        sav.setUtszakasz(u8);
                    }
                    Sopro sopro8 = new Sopro();
                    hokotro.addFej(sopro8);
                    hokotro.cserelFej(sopro8);
                    sikeresTakaritas = hokotro.takarit(sav);
                    break;

                case "9":
                    System.out.println("[ USE-CASE: Sikeres hóeltakarítás söprő fejjel (nincs szomszédos sáv) ]");
                    System.out.print("[?] Havat tisztít? (I/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("I")) {
                        sav.setAllapot(new SekelyHo());
                    } else {
                        sav.setAllapot(new Jeges());
                    }
                    System.out.print("[?] Van szomszédos sáv? (I/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("I")) {
                        Utszakasz u9 = new Utszakasz();
                        u9.addSav(sav);
                        u9.addSav(new Sav());
                        sav.setUtszakasz(u9);
                    } else {
                        Utszakasz u9 = new Utszakasz();
                        u9.addSav(sav);
                        sav.setUtszakasz(u9);
                    }
                    Sopro sopro9 = new Sopro();
                    hokotro.addFej(sopro9);
                    hokotro.cserelFej(sopro9);
                    sikeresTakaritas = hokotro.takarit(sav);
                    break;

                case "10":
                    System.out.println("[ USE-CASE: Sikertelen takarítás söprővel (jég eltávolításának kísérlete) ]");
                    System.out.print("[?] Havat tisztít? (I/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("N")) {
                        sav.setAllapot(new Jeges());
                    } else {
                        sav.setAllapot(new SekelyHo());
                    }
                    Sopro sopro10 = new Sopro();
                    hokotro.addFej(sopro10);
                    hokotro.cserelFej(sopro10);
                    sikeresTakaritas = hokotro.takarit(sav);
                    break;

                case "11":
                    System.out.println("[ USE-CASE: Sikeres takarítás hányófejjel (havat tisztít) ]");
                    System.out.print("[?] Havat tisztít? (I/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("I")) {
                        sav.setAllapot(new MelyHo());
                    } else {
                        sav.setAllapot(new Jeges());
                    }
                    Hanyofej hanyofej11 = new Hanyofej();
                    hokotro.addFej(hanyofej11);
                    hokotro.cserelFej(hanyofej11);
                    sikeresTakaritas = hokotro.takarit(sav);
                    break;

                case "12":
                    System.out
                            .println("[ USE-CASE: Sikertelen takarítás hányófejjel (Jég eltávolításának kísérlete) ]");
                    System.out.print("[?] Havat tisztít? (I/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("N")) {
                        sav.setAllapot(new Jeges());
                    } else {
                        sav.setAllapot(new MelyHo());
                    }
                    Hanyofej hanyofej12 = new Hanyofej();
                    hokotro.addFej(hanyofej12);
                    hokotro.cserelFej(hanyofej12);
                    sikeresTakaritas = hokotro.takarit(sav);
                    break;

                // Kata tesztjei (Refaktorálva Alex mintájára)
                case "13":
                    System.out.println("[ USE-CASE: Sikeres buszforduló (Pénzkeresés) ]");
                    sikeresTranzakcio = buszfordulo();
                    break;
                case "14":
                    System.out.println("[ USE-CASE: Vásárlás a boltban (Sárkányfej) ]");
                    sikeresTranzakcio = vasarlasSarkanyfej();
                    break;
                case "15":
                    System.out.println("[ USE-CASE: Sikertelen vásárlás a boltban (nincs elég pénz) ]");
                    sikeresTranzakcio = sikertelenVasarlas();
                    break;
                case "16":
                    System.out.println("[ USE-CASE: Fogyóeszköz vásárlása a boltban (Só) ]");
                    sikeresTranzakcio = vasarlasSo();
                    break;
                case "17":
                    System.out.println("[ USE-CASE: Fogyóeszköz vásárlása a boltban (Kerozin) ]");
                    sikeresTranzakcio = vasarlasKerozin();
                    break;
                case "18":
                    System.out.println("[ USE-CASE: Új Hókotró vásárlása a boltban ]");
                    sikeresTranzakcio = vasarlasHokotro();
                    break;
                case "19":
                    System.out.println("[ USE-CASE: Globális felmelegedés vásárlása a boltban ]");
                    sikeresTranzakcio = vasarlasGlobalisFelmelegedes();
                    break;

                // Alex
                case "20":
                    System.out.println("[ USE-CASE: Hóesés tiszta, sózatlan útszakaszon ]");
                    hoesesTisztaUtszakaszonSozatlan();
                    break;
                case "21":
                    System.out.println("[ USE-CASE: Hóesés tiszta, sózott útszakaszon ]");
                    hoesesTisztaUtszakaszonSozott();
                    break;
                case "22":
                    System.out.println("[ USE-CASE: Hóesés alagúton ]");
                    hoesesAlaguton();
                    break;
                case "23":
                    System.out.println("[ USE-CASE: Kereszteződés frissítése ]");
                    keresztezodesFrissitese();
                    break;
                case "24":
                    System.out.println("[ USE-CASE: Hófelhalmozódás ]");
                    horetegFelhalmozodas();
                    break;
                case "25":
                    System.out.println("[ USE-CASE: Hó jeggé tömörülése ]");
                    hoJeggeTomorulese();
                    break;
                case "26":
                    System.out.println("[ USE-CASE: MelyHo jarhatatlan ]");
                    melyHoJarhatatlan();
                    break;
                case "27":
                    System.out.println("[ USE-CASE: Só hatása tiszta sávon ]");
                    soHatasTisztaSavon();
                    break;
                case "28":
                    System.out.println("[ USE-CASE: Só hatása sekély havas sávon, 2 hóréteggel ]");
                    soHatasSekelyHavasSavon2();
                    break;
                case "29":
                    System.out.println("[ USE-CASE: Só hatása sekély havas savon, 1 hóréteggel ]");
                    soHatasSekelyHavasSavon1();
                    break;
                case "30":
                    System.out.println("[ USE-CASE: Só hatása mély havas savon ]");
                    soHatasaMelyHavasSavon();
                    break;
                case "31":
                    System.out.println("USE-CASE: Só hatása jeges sávon, kevesebb, mint 2 köre sózva");
                    soHatasaJegesSavonKevesebbMint2();
                    break;
                case "32":
                    System.out.println("USE-CASE: Só hatása jeges sávon, pontosan 2 köre sózva");
                    soHatasaJegesSavonPontosan2();
                    break;

                default:
                    System.out.println("Érvénytelen választás! Kérlek 0 és 32 közötti számot adj meg.");
                    continue;
            }
            System.out.println("----------------------------------------------------------");
            int tesztSzam = Integer.parseInt(valasz);
            if (tesztSzam >= 1 && tesztSzam <= 12) {
                // Csak a takarítós teszteknél írjuk ki ezt
                if (sikeresTakaritas) {
                    System.out.println(">>> EREDMÉNY: A takarítás megtörtént");
                } else {
                    System.out.println(">>> EREDMÉNY: Nem történt takarítás");
                }
            } else if (tesztSzam == 13) {
                if (sikeresTranzakcio) {
                    System.out.println(">>> EREDMÉNY: A buszforduló sikeresen megtörtént");
                } else {
                    System.out.println(">>> EREDMÉNY: A buszforduló nem tud megtörténni");
                }
            }
            // Ha 14-19-es vásárlásaid futottak:
            else if (tesztSzam >= 14 && tesztSzam <= 19) {
                if (sikeresTranzakcio) {
                    System.out.println(">>> EREDMÉNY: A vásárlás sikeresen megtörtént");
                } else {
                    System.out.println(">>> EREDMÉNY: A vásárlás sikertelen");
                }
            }
            System.out.println("----------------------------------------------------------");
            System.out.println("\n[?] --- Nyomj ENTER-t a folytatáshoz ---");
            try {
                System.in.read();
            } catch (Exception e) {
            }
        }

        System.out.println("\n--- Szimuláció Vége ---");
        scanner.close();
    }

    // Kata
    public static boolean buszfordulo() {
        KozosKassza kassza = new KozosKassza(0);
        SkeletonLogger.register(kassza, "kassza");
        Checkpoint vegallomas = new Checkpoint();
        SkeletonLogger.register(vegallomas, "vegallomas");
        Busz busz = new Busz(vegallomas, vegallomas);
        SkeletonLogger.register(busz, "busz");

        boolean siker = busz.lep(vegallomas);
        if (siker) {
            System.out.print("> [Sikeres érkezés] ");
            kassza.penzHozzaadas(100);
        }
        return siker;
    }

    public static boolean vasarlasSarkanyfej() {
        KozosKassza kassza = new KozosKassza(1000);
        SkeletonLogger.register(kassza, "kassza");
        Bolt bolt = new Bolt();
        SkeletonLogger.register(bolt, "bolt");
        Takarito takarito = new Takarito(kassza);
        SkeletonLogger.register(takarito, "takarito");
        Hokotro hokotro = new Hokotro(takarito);
        SkeletonLogger.register(hokotro, "hokotro");

        return bolt.vasarol(Arucikk.SARKANYFEJ, takarito, hokotro);
    }

    public static boolean sikertelenVasarlas() {
        KozosKassza kassza = new KozosKassza(50);
        SkeletonLogger.register(kassza, "kassza");
        Bolt bolt = new Bolt();
        SkeletonLogger.register(bolt, "bolt");
        Takarito takarito = new Takarito(kassza);
        SkeletonLogger.register(takarito, "takarito");
        Hokotro hokotro = new Hokotro(takarito);
        SkeletonLogger.register(hokotro, "hokotro");

        return bolt.vasarol(Arucikk.SARKANYFEJ, takarito, hokotro);
    }

    public static boolean vasarlasSo() {
        KozosKassza kassza = new KozosKassza(1000);
        SkeletonLogger.register(kassza, "kassza");
        Bolt bolt = new Bolt();
        SkeletonLogger.register(bolt, "bolt");
        Takarito takarito = new Takarito(kassza);
        SkeletonLogger.register(takarito, "takarito");
        Hokotro hokotro = new Hokotro(takarito);
        SkeletonLogger.register(hokotro, "hokotro");

        return bolt.vasarol(Arucikk.SO, takarito, hokotro);
    }

    public static boolean vasarlasKerozin() {
        KozosKassza kassza = new KozosKassza(1000);
        SkeletonLogger.register(kassza, "kassza");
        Bolt bolt = new Bolt();
        SkeletonLogger.register(bolt, "bolt");
        Takarito takarito = new Takarito(kassza);
        SkeletonLogger.register(takarito, "takarito");
        Hokotro hokotro = new Hokotro(takarito);
        SkeletonLogger.register(hokotro, "hokotro");

        return bolt.vasarol(Arucikk.KEROZIN, takarito, hokotro);
    }

    public static boolean vasarlasHokotro() {
        KozosKassza kassza = new KozosKassza(1000);
        SkeletonLogger.register(kassza, "kassza");
        Bolt bolt = new Bolt();
        SkeletonLogger.register(bolt, "bolt");
        Takarito takarito = new Takarito(kassza);
        SkeletonLogger.register(takarito, "takarito");
        Hokotro hokotro = new Hokotro(takarito);
        SkeletonLogger.register(hokotro, "hokotro");

        return bolt.vasarol(Arucikk.HOKOTRO, takarito, hokotro);
    }

    public static boolean vasarlasGlobalisFelmelegedes() {
        KozosKassza kassza = new KozosKassza(10000);
        SkeletonLogger.register(kassza, "kassza");
        Bolt bolt = new Bolt();
        SkeletonLogger.register(bolt, "bolt");
        Takarito takarito = new Takarito(kassza);
        SkeletonLogger.register(takarito, "takarito");
        Hokotro hokotro = new Hokotro(takarito);
        SkeletonLogger.register(hokotro, "hokotro");

        return bolt.vasarol(Arucikk.GLOBAL_WARMING, takarito, hokotro);
    }

    // Alex
    public static void hoesesTisztaUtszakaszonSozatlan() {
        VarosModell vM = new VarosModell();
        SkeletonLogger.register(vM, "vM");
        Utszakasz utszakasz = new Utszakasz();
        SkeletonLogger.register(utszakasz, "utszakasz");
        Sav s1 = new Sav();
        SkeletonLogger.register(s1, "s1");
        vM.addCsomopont(s1);
        utszakasz.addSav(s1);
        Tiszta tiszta = new Tiszta();
        SkeletonLogger.register(tiszta, "tiszta");
        s1.setAllapot(tiszta);
        vM.havazas();
    }

    public static void hoesesTisztaUtszakaszonSozott() {
        VarosModell vM = new VarosModell();
        SkeletonLogger.register(vM, "vM");
        Utszakasz utszakasz = new Utszakasz();
        SkeletonLogger.register(utszakasz, "utszakasz");
        Sav s1 = new Sav();
        SkeletonLogger.register(s1, "s1");
        vM.addCsomopont(s1);
        utszakasz.addSav(s1);
        Tiszta tiszta = new Tiszta();
        SkeletonLogger.register(tiszta, "tiszta");
        s1.setAllapot(tiszta);
        s1.soSzoras();
        vM.havazas();
    }

    public static void hoesesAlaguton() {
        VarosModell vM = new VarosModell();
        SkeletonLogger.register(vM, "vM");
        Alagut alagut = new Alagut();
        SkeletonLogger.register(alagut, "alagut");
        Sav s1 = new Sav();
        SkeletonLogger.register(s1, "s1");
        vM.addCsomopont(s1);
        alagut.addSav(s1);
        Tiszta tiszta = new Tiszta();
        SkeletonLogger.register(tiszta, "tiszta");
        s1.setAllapot(tiszta);
        vM.havazas();
    }

    public static void keresztezodesFrissitese() {
        VarosModell vM = new VarosModell();
        SkeletonLogger.register(vM, "vM");
        Keresztezodes keresztezodes = new Keresztezodes();
        SkeletonLogger.register(keresztezodes, "keresztezodes");
        vM.addCsomopont(keresztezodes);
        vM.palyaFrissit();
    }

    public static void horetegFelhalmozodas() {
        VarosModell vm = new VarosModell();
        Utszakasz u = new Utszakasz();
        Sav s1 = new Sav();
        SekelyHo sh = new SekelyHo();
        s1.setUtszakasz(u);
        u.addSav(s1);
        s1.setAllapot(sh);
        vm.addCsomopont(s1);
        SkeletonLogger.register(vm, "vm");
        SkeletonLogger.register(u, "utszakasz");
        SkeletonLogger.register(s1, "s1");
        SkeletonLogger.register(sh, "sekelyho");
        vm.havazas();
    }

    public static void hoJeggeTomorulese() {
        Sav aktualis = new Sav();
        Sav cel = new Sav();
        SekelyHo sh = new SekelyHo();
        Busz busz1 = new Busz(null, null);
        aktualis.setAllapot(sh);
        aktualis.befogad(busz1);
        busz1.setAktualisCsomopont(aktualis);
        SkeletonLogger.register(aktualis, "aktualis");
        SkeletonLogger.register(cel, "cel");
        SkeletonLogger.register(sh, "sekelyho");
        SkeletonLogger.register(busz1, "busz1");
        busz1.lep(cel);
    }

    public static void melyHoJarhatatlan() {
        Checkpoint cp1 = new Checkpoint();
        Checkpoint cp2 = new Checkpoint();
        Sav s1 = new Sav();
        MelyHo melyho = new MelyHo();
        Auto a = new Auto(cp2, cp1);
        s1.setAllapot(melyho);
        SkeletonLogger.register(s1, "cel");
        SkeletonLogger.register(melyho, "melyHo");
        SkeletonLogger.register(a, "a");
        a.lep(s1);
    }

    public static void soHatasTisztaSavon() {
        VarosModell vM = new VarosModell();
        Sav s1 = new Sav();
        Tiszta tiszta = new Tiszta();
        vM.addCsomopont(s1);
        s1.setAllapot(tiszta);
        SkeletonLogger.register(vM, "vM");
        SkeletonLogger.register(s1, "s1");
        SkeletonLogger.register(tiszta, "tiszta");
        vM.palyaFrissit();
    }

    public static void soHatasSekelyHavasSavon2() {
        VarosModell vM = new VarosModell();
        Sav s1 = new Sav();
        SekelyHo sekelyHo = new SekelyHo();

        // Állítsuk 3-ra:
        // - 1 elmegy a sotKap-nál (marad 2)
        // - 1 elmegy a frissit-nél (marad 1) -> ÍGY NEM LESZ 0!
        sekelyHo.setHoreteg(3);

        SkeletonLogger.register(vM, "vM");
        SkeletonLogger.register(s1, "s1");
        SkeletonLogger.register(sekelyHo, "sekelyHo");

        vM.addCsomopont(s1);

        // Ez levon egyet (3 -> 2)
        sekelyHo.sotKap(s1);

        s1.setAllapot(sekelyHo);

        // Ez meghívja a frissit-et, ami levon még egyet (2 -> 1)
        // Mivel 1 != 0, az if nem fut le, a log tiszta marad!
        vM.palyaFrissit();
    }

    public static void soHatasSekelyHavasSavon1() {
        VarosModell vM = new VarosModell();
        Sav s1 = new Sav();
        SekelyHo sekelyHo = new SekelyHo();
        Tiszta tisztaMinta = new Tiszta();

        sekelyHo.setHoreteg(1); // 1-ről indulunk

        SkeletonLogger.register(vM, "vM");
        SkeletonLogger.register(s1, "s1");
        SkeletonLogger.register(sekelyHo, "sekelyHo");
        SkeletonLogger.register(tisztaMinta, "tiszta");

        vM.addCsomopont(s1);
        s1.setAllapot(sekelyHo);

        // Csak EGYIKET használd a kettő közül, hogy ne fogyjon el a hó túl korán!
        // sekelyHo.sotKap(s1); // Ezt hagyd ki, ha a soSzoras-t használod
        s1.soSzoras();

        vM.palyaFrissit();
    }

    public static void soHatasaMelyHavasSavon() {
        VarosModell vM = new VarosModell();
        Sav s1 = new Sav();
        MelyHo melyHo = new MelyHo();
        SkeletonLogger.register(vM, "vM");
        SkeletonLogger.register(s1, "s1");
        SkeletonLogger.register(melyHo, "melyHo");
        vM.addCsomopont(s1);
        s1.setAllapot(melyHo);
        s1.soSzoras();
        vM.palyaFrissit();
    }

    public static void soHatasaJegesSavonKevesebbMint2() {
        VarosModell vM = new VarosModell();
        Sav s1 = new Sav();
        Jeges jeges = new Jeges();
        jeges.sozott = 3;
        SkeletonLogger.register(vM, "vM");
        SkeletonLogger.register(s1, "s1");
        SkeletonLogger.register(jeges, "jeges");
        vM.addCsomopont(s1);
        s1.setAllapot(jeges);
        s1.soSzoras();
        vM.palyaFrissit();
    }

    public static void soHatasaJegesSavonPontosan2() {
        VarosModell vM = new VarosModell();
        Sav s1 = new Sav();
        Jeges jeges = new Jeges();
        jeges.sozott = 1;
        SkeletonLogger.register(vM, "vM");
        SkeletonLogger.register(s1, "s1");
        SkeletonLogger.register(jeges, "jeges");
        vM.addCsomopont(s1);
        s1.setAllapot(jeges);
        s1.soSzoras();
        vM.palyaFrissit();
    }
}