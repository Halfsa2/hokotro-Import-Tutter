package vezerles;

import allapot.*;
import felszereles.*;
import gazdasag.*;
import halozat.Alagut;
import halozat.Checkpoint;
import halozat.Keresztezodes;
import halozat.Sav;
import halozat.Utszakasz;
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
            //Innentől Anna
            System.out.println("\n--- Válassz egy tesztesetet! ---");
            System.out.println("1.  Sikeres takarítás sárkányfejjel (havat tisztít)");
            System.out.println("2.  Sikeres takarítás sárkányfejjel (Jeget tisztít)");
            System.out.println("3.  Sikertelen sárkányfej használat (nincs kerozin)");
            System.out.println("4.  Sikeres takarítás sószóró fejjel (Pénzkeresés)");
            System.out.println("5.  Sikertelen sószóró használat (nincs só)");
            System.out.println("6.  Sikeres hóeltakarítás söprő fejjel (van szomszédos sáv)");
            System.out.println("7.  Sikeres hóeltakarítás söprő fejjel (nincs szomszédos sáv)");
            System.out.println("8.  Sikertelen takarítás tiszta sávon (Nincs pénzkeresés)");
            System.out.println("9.  Sikeres jégtörés (Pénzkeresés)");
            System.out.println("10. Sikertelen jégtörés sekély havon (Nincs pénzkeresés)");
            System.out.println("11. Sikeres hóeltakarítás hányófejjel (Pénzkeresés)");
            System.out.println("12. Sikertelen takarítás hányófejjel (Jég eltávolításának kísérlete)");
            
            //Innentől Kata 
            System.out.println("13. Sikeres buszforduló (Pénzkeresés)");
            System.out.println("14. Vásárlás a boltban (Sárkányfej)");
            System.out.println("15. Sikertelen vásárlás a boltban (nincs elég pénz)");
            System.out.println("16. Fogyóeszköz vásárlása a boltban (Só)");
            System.out.println("17. Fogyóeszköz vásárlása a boltban (Kerozin)");
            System.out.println("18. Új Hókotró vásárlása a boltban");
            System.out.println("19. Globális felmelegedés vásárlása a boltban");
            
            //Innentől Alex
            System.out.println("20. Hóesés tiszta, sózatlan útszakaszon");
            System.out.println("21. Hóesés tiszta, sózott útszakaszon");
            System.out.println("22. Hóesés alagúton");
            System.out.println("23. Kereszteződés frissítése");
            
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
            boolean sikeresTranzakcio = false; //vásárlások sikerességére

            System.out.println("\n----------------------------------------------------------");

            switch (valasz) {
                case "1":
                    System.out.println("[ USE-CASE: Sikeres takarítás sárkányfejjel (havat tisztít) ]");
                    sikeresTakaritas = sikeresSarkanyfejHavatTisztit();
                    break;
                case "2":
                    System.out.println("[ USE-CASE: Sikeres takarítás sárkányfejjel (Jeget tisztít) ]");
                    sikeresTakaritas = sikeresSarkanyfejJegetTisztit();
                    break;
                case "3":
                    System.out.println("[ USE-CASE: Sikertelen sárkányfej használat (nincs kerozin) ]");
                    sikeresTakaritas = sikertelenSarkanyfejNincsKerozin();
                    break;
                case "4":
                    System.out.println("[ USE-CASE: Sikeres takarítás sószóró fejjel (Pénzkeresés) ]");
                    sikeresTakaritas = sikeresSoszoro();
                    break;
                case "5":
                    System.out.println("[ USE-CASE: Sikertelen sószóró használat (nincs só) ]");
                    sikeresTakaritas = sikertelenSoszoroNincsSo();
                    break;
                case "6":
                    System.out.println("[ USE-CASE: Sikeres hóeltakarítás söprő fejjel (van szomszédos sáv) ]");
                    sikeresTakaritas = sikeresSoproVanSzomszedos();
                    break;
                case "7":
                    System.out.println("[ USE-CASE: Sikeres hóeltakarítás söprő fejjel (nincs szomszédos sáv) ]");
                    sikeresTakaritas = sikeresSoproNincsSzomszedos();
                    break;
                case "8":
                    System.out.println("[ USE-CASE: Sikertelen takarítás tiszta sávon (Nincs pénzkeresés) ]");
                    sikeresTakaritas = sikertelenSoproTisztaSav();
                    break;
                case "9":
                    System.out.println("[ USE-CASE: Sikeres jégtörés (Pénzkeresés) ]");
                    sikeresTakaritas = sikeresJegtores();
                    break;
                case "10":
                    System.out.println("[ USE-CASE: Sikertelen jégtörés sekély havon (Nincs pénzkeresés) ]");
                    sikeresTakaritas = sikertelenJegtoresSekelyHavon();
                    break;
                case "11":
                    System.out.println("[ USE-CASE: Sikeres hóeltakarítás hányófejjel (Pénzkeresés) ]");
                    sikeresTakaritas = sikeresHanyofej();
                    break;
                case "12":
                    System.out.println("[ USE-CASE: Sikertelen takarítás hányófejjel (Jég eltávolításának kísérlete) ]");
                    sikeresTakaritas = sikertelenHanyofejJeg();
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
                
                //Alex
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
                default:
                    System.out.println("Érvénytelen választás! Kérlek 0 és 12 közötti számot adj meg.");
                    continue; 
            }
            System.out.println("----------------------------------------------------------");
            int tesztSzam = Integer.parseInt(valasz);
            if (tesztSzam >= 1 && tesztSzam <= 12) {
                // Csak a takarítós teszteknél írjuk ki ezt
                if (sikeresTakaritas) {
                    System.out.println(">>> EREDMÉNY: A takarítás megtörtént");
                } 
                else {
                    System.out.println(">>> EREDMÉNY: Nem történt takarítás");
                }
            } 
            else if (tesztSzam == 13) {
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
                } 
                else {
                    System.out.println(">>> EREDMÉNY: A vásárlás sikertelen");
                }
            }
            System.out.println("----------------------------------------------------------");
            System.out.println("\n[?] --- Nyomj ENTER-t a folytatáshoz ---");
            try {
                System.in.read();
            } 
            catch (Exception e) {
            }
        }

        System.out.println("\n--- Szimuláció Vége ---");
        scanner.close();
    }
    // Anna
    public static boolean sikeresSarkanyfejHavatTisztit() {
        KozosKassza kassza = new KozosKassza(0);
        SkeletonLogger.register(kassza, "kassza");
        Sav sav1 = new Sav();
        SkeletonLogger.register(sav1, "sav1");
        Hokotro hokotro1 = new Hokotro(null);
        SkeletonLogger.register(hokotro1, "hokotro1");

        SekelyHo sekelyho1 = new SekelyHo();
        SkeletonLogger.register(sekelyho1, "sekelyho1");
        sav1.setAllapot(sekelyho1);

        Sarkanyfej sarkanyfej1 = new Sarkanyfej(5);
        SkeletonLogger.register(sarkanyfej1, "sarkanyfej1");
        hokotro1.addFej(sarkanyfej1);
        hokotro1.cserelFej(sarkanyfej1);

        boolean siker = hokotro1.takarit(sav1);
        if (siker) kassza.penzHozzaadas(5);
        return siker;
    }

    public static boolean sikeresSarkanyfejJegetTisztit() {
        KozosKassza kassza = new KozosKassza(0);
        SkeletonLogger.register(kassza, "kassza");
        Sav sav1 = new Sav();
        SkeletonLogger.register(sav1, "sav1");
        Hokotro hokotro1 = new Hokotro(null);
        SkeletonLogger.register(hokotro1, "hokotro1");

        Jeges jeges1 = new Jeges();
        SkeletonLogger.register(jeges1, "jeges1");
        sav1.setAllapot(jeges1);

        Sarkanyfej sarkanyfej1 = new Sarkanyfej(5);
        SkeletonLogger.register(sarkanyfej1, "sarkanyfej1");
        hokotro1.addFej(sarkanyfej1);
        hokotro1.cserelFej(sarkanyfej1);

        Jeges.sarkanfejOlvassza = true;
        boolean siker = hokotro1.takarit(sav1);
        Jeges.sarkanfejOlvassza = false;
        if (siker) kassza.penzHozzaadas(5);
        return siker;
    }

    public static boolean sikertelenSarkanyfejNincsKerozin() {
        Sav sav1 = new Sav();
        SkeletonLogger.register(sav1, "sav1");
        Hokotro hokotro1 = new Hokotro(null);
        SkeletonLogger.register(hokotro1, "hokotro1");

        SekelyHo sekelyho1 = new SekelyHo();
        SkeletonLogger.register(sekelyho1, "sekelyho1");
        sav1.setAllapot(sekelyho1);

        Sarkanyfej sarkanyfej1 = new Sarkanyfej(0);
        SkeletonLogger.register(sarkanyfej1, "sarkanyfej1");
        hokotro1.addFej(sarkanyfej1);
        hokotro1.cserelFej(sarkanyfej1);

        return hokotro1.takarit(sav1);
    }

    public static boolean sikeresSoszoro() {
        KozosKassza kassza = new KozosKassza(0);
        SkeletonLogger.register(kassza, "kassza");
        Sav sav1 = new Sav();
        SkeletonLogger.register(sav1, "sav1");
        Hokotro hokotro1 = new Hokotro(null);
        SkeletonLogger.register(hokotro1, "hokotro1");

        SekelyHo sekelyho1 = new SekelyHo();
        SkeletonLogger.register(sekelyho1, "sekelyho1");
        sav1.setAllapot(sekelyho1);

        Soszoro soszoro1 = new Soszoro(5);
        SkeletonLogger.register(soszoro1, "soszoro1");
        hokotro1.addFej(soszoro1);
        hokotro1.cserelFej(soszoro1);

        boolean siker = hokotro1.takarit(sav1);
        if (siker) kassza.penzHozzaadas(5);
        return siker;
    }

    public static boolean sikertelenSoszoroNincsSo() {
        Sav sav1 = new Sav();
        SkeletonLogger.register(sav1, "sav1");
        Hokotro hokotro1 = new Hokotro(null);
        SkeletonLogger.register(hokotro1, "hokotro1");

        SekelyHo sekelyho1 = new SekelyHo();
        SkeletonLogger.register(sekelyho1, "sekelyho1");
        sav1.setAllapot(sekelyho1);

        Soszoro soszoro1 = new Soszoro(0);
        SkeletonLogger.register(soszoro1, "soszoro1");
        hokotro1.addFej(soszoro1);
        hokotro1.cserelFej(soszoro1);

        return hokotro1.takarit(sav1);
    }

    public static boolean sikeresSoproVanSzomszedos() {
        KozosKassza kassza = new KozosKassza(0);
        SkeletonLogger.register(kassza, "kassza");
        Sav sav1 = new Sav();
        SkeletonLogger.register(sav1, "sav1");
        Hokotro hokotro1 = new Hokotro(null);
        SkeletonLogger.register(hokotro1, "hokotro1");

        SekelyHo sekelyho1 = new SekelyHo();
        SkeletonLogger.register(sekelyho1, "sekelyho1");
        sav1.setAllapot(sekelyho1);

        Sav szomszedos = new Sav();
        SkeletonLogger.register(szomszedos, "szomszedos");
        Utszakasz utszakasz = new Utszakasz();
        SkeletonLogger.register(utszakasz, "utszakasz");
        utszakasz.addSav(sav1);
        utszakasz.addSav(szomszedos);
        sav1.setUtszakasz(utszakasz);

        Sopro sopro1 = new Sopro();
        SkeletonLogger.register(sopro1, "sopro1");
        hokotro1.addFej(sopro1);
        hokotro1.cserelFej(sopro1);

        boolean siker = hokotro1.takarit(sav1);
        if (siker) kassza.penzHozzaadas(5);
        return siker;
    }

    public static boolean sikeresSoproNincsSzomszedos() {
        KozosKassza kassza = new KozosKassza(0);
        SkeletonLogger.register(kassza, "kassza");
        Sav sav1 = new Sav();
        SkeletonLogger.register(sav1, "sav1");
        Hokotro hokotro1 = new Hokotro(null);
        SkeletonLogger.register(hokotro1, "hokotro1");

        SekelyHo sekelyho1 = new SekelyHo();
        SkeletonLogger.register(sekelyho1, "sekelyho1");
        sav1.setAllapot(sekelyho1);

        Utszakasz utszakasz = new Utszakasz();
        SkeletonLogger.register(utszakasz, "utszakasz");
        utszakasz.addSav(sav1); // Csak 1 sáv
        sav1.setUtszakasz(utszakasz);

        Sopro sopro1 = new Sopro();
        SkeletonLogger.register(sopro1, "sopro1");
        hokotro1.addFej(sopro1);
        hokotro1.cserelFej(sopro1);

        boolean siker = hokotro1.takarit(sav1);
        if (siker) kassza.penzHozzaadas(5);
        return siker;
    }

    public static boolean sikertelenSoproTisztaSav() {
        Sav sav1 = new Sav();
        SkeletonLogger.register(sav1, "sav1");
        Hokotro hokotro1 = new Hokotro(null);
        SkeletonLogger.register(hokotro1, "hokotro1");

        Tiszta tiszta1 = new Tiszta();
        SkeletonLogger.register(tiszta1, "tiszta1");
        sav1.setAllapot(tiszta1);

        Sopro sopro1 = new Sopro();
        SkeletonLogger.register(sopro1, "sopro1");
        hokotro1.addFej(sopro1);
        hokotro1.cserelFej(sopro1);

        return hokotro1.takarit(sav1);
    }

    public static boolean sikeresJegtores() {
        KozosKassza kassza = new KozosKassza(0);
        SkeletonLogger.register(kassza, "kassza");
        Sav sav1 = new Sav();
        SkeletonLogger.register(sav1, "sav1");
        Hokotro hokotro1 = new Hokotro(null);
        SkeletonLogger.register(hokotro1, "hokotro1");

        Jeges jeges1 = new Jeges();
        SkeletonLogger.register(jeges1, "jeges1");
        sav1.setAllapot(jeges1);

        Jegtoro jegtoro1 = new Jegtoro();
        SkeletonLogger.register(jegtoro1, "jegtoro1");
        hokotro1.addFej(jegtoro1);
        hokotro1.cserelFej(jegtoro1);

        boolean siker = hokotro1.takarit(sav1);
        if (siker) kassza.penzHozzaadas(5);
        return siker;
    }

    public static boolean sikertelenJegtoresSekelyHavon() {
        Sav sav1 = new Sav();
        SkeletonLogger.register(sav1, "sav1");
        Hokotro hokotro1 = new Hokotro(null);
        SkeletonLogger.register(hokotro1, "hokotro1");

        SekelyHo sekely1 = new SekelyHo();
        SkeletonLogger.register(sekely1, "sekely1");
        sav1.setAllapot(sekely1);

        Jegtoro jegtoro1 = new Jegtoro();
        SkeletonLogger.register(jegtoro1, "jegtoro1");
        hokotro1.addFej(jegtoro1);
        hokotro1.cserelFej(jegtoro1);

        return hokotro1.takarit(sav1);
    }

    public static boolean sikeresHanyofej() {
        KozosKassza kassza = new KozosKassza(0);
        SkeletonLogger.register(kassza, "kassza");
        Sav sav1 = new Sav();
        SkeletonLogger.register(sav1, "sav1");
        Hokotro hokotro1 = new Hokotro(null);
        SkeletonLogger.register(hokotro1, "hokotro1");

        MelyHo melyho1 = new MelyHo();
        SkeletonLogger.register(melyho1, "melyho1");
        sav1.setAllapot(melyho1);

        Hanyofej hanyofej1 = new Hanyofej();
        SkeletonLogger.register(hanyofej1, "hanyofej1");
        hokotro1.addFej(hanyofej1);
        hokotro1.cserelFej(hanyofej1);

        boolean siker = hokotro1.takarit(sav1);
        if (siker) kassza.penzHozzaadas(5);
        return siker;
    }

    public static boolean sikertelenHanyofejJeg() {
        Sav sav1 = new Sav();
        SkeletonLogger.register(sav1, "sav1");
        Hokotro hokotro1 = new Hokotro(null);
        SkeletonLogger.register(hokotro1, "hokotro1");

        Jeges jeges1 = new Jeges();
        SkeletonLogger.register(jeges1, "jeges1");
        sav1.setAllapot(jeges1);

        Hanyofej hanyofej1 = new Hanyofej();
        SkeletonLogger.register(hanyofej1, "hanyofej1");
        hokotro1.addFej(hanyofej1);
        hokotro1.cserelFej(hanyofej1);

        return hokotro1.takarit(sav1);
    }   
    //Kata
    public static boolean buszfordulo() {
        KozosKassza kassza = new KozosKassza(0); 
        SkeletonLogger.register(kassza, "kassza");
        Checkpoint vegallomas = new Checkpoint();
        SkeletonLogger.register(vegallomas, "vegallomas");
        Busz busz = new Busz(vegallomas, vegallomas);
        SkeletonLogger.register(busz, "busz");
        
        boolean siker = busz.lep(vegallomas);
        if(siker) {
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
    
    //Alex
    public static void hoesesTisztaUtszakaszonSozatlan(){
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
    public static void hoesesTisztaUtszakaszonSozott(){
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
    public static void hoesesAlaguton(){
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
    public static void keresztezodesFrissitese(){
        VarosModell vM = new VarosModell();
        SkeletonLogger.register(vM, "vM");
        Keresztezodes keresztezodes = new Keresztezodes();
        SkeletonLogger.register(keresztezodes, "keresztezodes");
        vM.addCsomopont(keresztezodes);
        vM.palyaFrissit();
    }
}