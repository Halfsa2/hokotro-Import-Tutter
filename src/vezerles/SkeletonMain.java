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
/**
 * A szimuláció indító osztálya, amely menüvezérelt módon teszteseteket futtat.
 */
public class SkeletonMain {

    /**
     * A program belépési pontja. Felhasználói menüt jelenít meg és teszteseteket futtat.
     * @param args parancssori argumentumok (jelenleg nem használtak)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean fut = true;

        System.out.println("==========================================================");
        System.out.println("  Zúzmaraváros Szimuláció   ");
        System.out.println("==========================================================");

        while (fut) {
            // Innentől Anna
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

            // Innentől Noémi
            System.out.println("33. Autó sikeresen sávot vált");
            System.out.println("34. Autó balesete jégen");
            System.out.println("35. Sikeres érkezés checkpointra");
            System.out.println("36. Foglalt Checkpoint célállomás");

            System.out.println("0.  Kilépés");
            System.out.print("\nVálasztás: ");

            String valasz = scanner.nextLine();

            if (valasz.equals("0")) {
                fut = false;
                continue;
            }

            int tesztSzam;
            try {
                tesztSzam = Integer.parseInt(valasz);
            } catch (NumberFormatException ex) {
                System.out.println("Érvénytelen választás! Kérlek számot adj meg.");
                continue;
            }

            //Sav sav = new Sav();
            //Hokotro hokotro = new Hokotro(null);
            boolean sikeresTakaritas = false;
            boolean sikeresTranzakcio = false; // vásárlások sikerességére

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
                    System.out
                            .println("[ USE-CASE: Sikertelen takarítás hányófejjel (Jég eltávolításának kísérlete) ]");
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

                // Noemi
                case "33":
                    System.out.println("[ USE-CASE: Autó sikeresen sávot vált]");
                    autoSikeresenSavotValt();
                    break;
                case "34":
                    System.out.println("[ USE-CASE: Autó balesete jégen ]");
                    autoBaleseteJegen();
                    break;
                case "35":
                    System.out.println("[ USE-CASE: Sikeres érkezés checkpointra ]");
                    sikeresErkezesCheckpointra();
                    break;
                case "36":
                    System.out.println("[ USE-CASE: Foglalt Checkpoint célállomás ]");
                    foglaltCheckpoint();
                    break;
                default:
                    System.out.println("Érvénytelen választás! Kérlek 0 és 36 közötti számot adj meg.");
                    continue;
            }
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

    // Anna
    /**
     * Teszt: sárkányfejjel történő hótakarítás sekély hó esetén.
     * @return true, ha a takarítás és pénz hozzáadás sikeres
     */
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
        if (siker)
            kassza.penzHozzaadas(5);
        return siker;
    }

    /**
     * Teszt: sárkányfejjel történő jégtörés sekély hórétegen.
     * @return true, ha a jégtörés és pénz hozzáadás sikeres
     */
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

        //Jeges.sarkanfejOlvassza = true;
        boolean siker = hokotro1.takarit(sav1);
        //Jeges.sarkanfejOlvassza = false;
        if (siker)
            kassza.penzHozzaadas(5);
        return siker;
    }

    /**
     * Teszt: sikertelen sárkányfej használat kerozin hiány miatt.
     * @return true, ha a próbálkozás sikertelen (nincs kerozin)
     */
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

    /**
     * Teszt: sószóróval történő sikeres hótakarítás.
     * @return true, ha a takarítás és pénz hozzáadás sikeres
     */
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
        if (siker)
            kassza.penzHozzaadas(5);
        return siker;
    }

    /**
     * Teszt: sikertelen sószórás sóhiány miatt.
     * @return true, ha a próbálkozás sikertelen
     */
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

    /**
     * Teszt: sikeres söprőfejes takarítás, ha van szomszédos sáv.
     * @return true, ha a takarítás sikeres és pénz hozzáadás megtörténik
     */
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
        SkeletonLogger.register(szomszedos.getAllapot(), "tiszta2");
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
        if (siker)
            kassza.penzHozzaadas(5);
        return siker;
    }

    /**
     * Teszt: sikeres söprőfejes takarítás, ha nincsen szomszédos sáv.
     * @return true, ha a takarítás sikeres és pénz hozzáadás megtörténik
     */
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
        if (siker)
            kassza.penzHozzaadas(5);
        return siker;
    }

    /**
     * Teszt: sikertelen söprőfejes takarítás tiszta sávon.
     * @return true, ha a próbálkozás sikertelen
     */
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

    /**
     * Teszt: sikeres jégtörés takarítás esetén.
     * @return true, ha a jégtörés sikeres és pénz hozzáadás megtörténik
     */
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
        if (siker)
            kassza.penzHozzaadas(5);
        return siker;
    }

    /**
     * Teszt: sikertelen jégtörés sekély havon.
     * @return true, ha a próbálkozás sikertelen
     */
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

    /**
     * Teszt: sikeres hányófejes takarítás mély hóban.
     * @return true, ha a takarítás és pénz hozzáadás sikeres
     */
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
        if (siker)
            kassza.penzHozzaadas(5);
        return siker;
    }

    /**
     * Teszt: sikertelen hányófej alkalmazása jégen.
     * @return true, ha a próbálkozás sikertelen
     */
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

    // Kata
    /**
     * Teszt: busz sikeres célba érkezése egy checkpointnál.
     * @return true, ha a busz sikeresen fordul és pénz hozzáadás történik
     */
    public static boolean buszfordulo() {
        KozosKassza kassza = new KozosKassza(0);
        SkeletonLogger.register(kassza, "kassza");
        Checkpoint vegallomas = new Checkpoint();
        SkeletonLogger.register(vegallomas, "vegallomas");
        Busz busz = new Busz(vegallomas, vegallomas, null);
        SkeletonLogger.register(busz, "busz");

        boolean siker = busz.lep(vegallomas);
        if (siker) {
            System.out.print("> [Sikeres érkezés] ");
            kassza.penzHozzaadas(100);
        }
        return siker;
    }

    /**
     * Teszt: sárkányfej vásárlása boltban.
     * @return true, ha vásárlás sikeres
     */
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

    /**
     * Teszt: sikertelen vásárlás pénzhiány miatt.
     * @return true, ha vásárlás nem történik meg
     */
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

    /**
     * Teszt: só vásárlása boltban.
     * @return true, ha vásárlás sikeres
     */
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

    /**
     * Teszt: kerozin vásárlása boltban.
     * @return true, ha vásárlás sikeres
     */
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

    /**
     * Teszt: hókotró vásárlása boltban.
     * @return true, ha vásárlás sikeres
     */
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

    /**
     * Teszt: globális felmelegedés vásárlása boltban.
     * @return true, ha vásárlás sikeres
     */
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
    /**
     * Teszt: hóesés tiszta, sózatlan útszakaszon.
     */
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

    /**
     * Teszt: hóesés tiszta, sózott útszakaszon.
     */
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

    /**
     * Teszt: hóesés alagútban.
     */
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

    /**
     * Teszt: kereszteződés frissítése a városmodellben.
     */
    public static void keresztezodesFrissitese() {
        VarosModell vM = new VarosModell();
        SkeletonLogger.register(vM, "vM");
        Keresztezodes keresztezodes = new Keresztezodes();
        SkeletonLogger.register(keresztezodes, "keresztezodes");
        vM.addCsomopont(keresztezodes);
        vM.palyaFrissit();
    }

    /**
     * Teszt: hófelhalmozódás szimulációja.
     */
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

    /**
     * Teszt: hó jeggé tömörülése, járművek és sávok megjelenítésével.
     */
    public static void hoJeggeTomorulese() {
        Sav aktualis = new Sav();
        Sav cel = new Sav();
        SekelyHo sh = new SekelyHo();
        Busz busz1 = new Busz(null, null, null);
        aktualis.setAllapot(sh);
        aktualis.befogad(busz1);
        busz1.setAktualisCsomopont(aktualis);
        SkeletonLogger.register(aktualis, "aktualis");
        SkeletonLogger.register(cel, "cel");
        SkeletonLogger.register(sh, "sekelyho");
        SkeletonLogger.register(busz1, "busz1");
        busz1.lep(cel);
    }

    /**
     * Teszt: mély hó miatt járhatatlan sáv.
     */
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

    /**
     * Teszt: só hatása tiszta sávon.
     */
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

    /**
     * Teszt: só hatása sekély havas sávon, 2 hóréteggel.
     */
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

    /**
     * Teszt: só hatása sekély havas sávon, 1 hóréteggel.
     */
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
        s1.soSzoras();

        vM.palyaFrissit();
    }

    /**
     * Teszt: só hatása mély havas sávon.
     */
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

    /**
     * Teszt: só hatása jeges sávon, ahol kevesebb mint 2 sózási kör van.
     */
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
    /**
     * Teszt: só hatása jeges sávon, ahol pontosan 2 sózási kör van hátra.
     */
    public static void soHatasaJegesSavonPontosan2() {
        VarosModell vM = new VarosModell();
        Sav s1 = new Sav();
        Jeges jeges = new Jeges();
        jeges.sozott = 1;
        SkeletonLogger.register(vM, "vM");
        SkeletonLogger.register(s1, "s1");
        SkeletonLogger.register(jeges, "jeges");
        vM.addCsomopont(s1);
        s1.soSzoras();
        s1.setAllapot(jeges);
        vM.palyaFrissit();
    }

    /**
     * Teszt: az autó sikeresen sávot vált egy üres cél sávba.
     */
    public static void autoSikeresenSavotValt() {
        Checkpoint startCp = new Checkpoint();
        SkeletonLogger.register(startCp, "startCp");
        Checkpoint celCp = new Checkpoint();
        SkeletonLogger.register(celCp, "celCp");

        jarmu.Auto auto1 = new jarmu.Auto(startCp, celCp);
        SkeletonLogger.register(auto1, "auto1");

        Sav aktualisSav = new Sav();
        SkeletonLogger.register(aktualisSav, "aktualisSav");
        Sav celSav = new Sav();
        SkeletonLogger.register(celSav, "celSav");

        Tiszta tiszta1 = new Tiszta();
        SkeletonLogger.register(tiszta1, "tiszta1");
        Tiszta tiszta2 = new Tiszta();
        SkeletonLogger.register(tiszta2, "tiszta2");

        aktualisSav.setAllapot(tiszta2);
        celSav.setAllapot(tiszta1);
        aktualisSav.befogad(auto1);
        auto1.setAktualisCsomopont(aktualisSav);

        auto1.lep(celSav);
    }

    /**
     * Teszt: autó balesete, amikor jeges cél sávra lép.
     */
    public static void autoBaleseteJegen() {
        Checkpoint cp = new Checkpoint();
        Auto auto1 = new Auto(cp, cp);
        SkeletonLogger.register(auto1, "auto1");

        Sav aktualisSav = new Sav();
        SkeletonLogger.register(aktualisSav, "aktualisSav");

        Sav celSav = new Sav();
        SkeletonLogger.register(celSav, "celSav");

        Jeges jeges1 = new Jeges();
        SkeletonLogger.register(jeges1, "jeges1");

        celSav.setAllapot(jeges1);

        aktualisSav.befogad(auto1);
        auto1.setAktualisCsomopont(aktualisSav);

        auto1.lep(celSav);
    }

    /**
     * Teszt: az autó eléri a checkpointra történő célba érkezést.
     */
    public static void sikeresErkezesCheckpointra() {
        Checkpoint cel = new Checkpoint();
        SkeletonLogger.register(cel, "cel");

        Auto auto1 = new Auto(null, cel);
        SkeletonLogger.register(auto1, "auto1");

        Sav aktualisSav = new Sav();
        SkeletonLogger.register(aktualisSav, "aktualisSav");
        Tiszta tiszta1 = new Tiszta();
        SkeletonLogger.register(tiszta1, "tiszta1");

        aktualisSav.setAllapot(tiszta1);
        aktualisSav.befogad(auto1);
        auto1.setAktualisCsomopont(aktualisSav);

        auto1.lep(cel);
    }

    /**
     * Teszt: foglalt checkpoint megakadályozza, hogy az autó célba érjen.
     */
    public static void foglaltCheckpoint() {
        Checkpoint cel = new Checkpoint();
        SkeletonLogger.register(cel, "cel");

        Auto dummyAuto = new Auto(null, null);
        SkeletonLogger.register(dummyAuto, "dummyAuto");
        cel.befogad(dummyAuto);

        Auto auto1 = new Auto(null, cel);
        SkeletonLogger.register(auto1, "auto1");

        Sav aktualisSav = new Sav();
        SkeletonLogger.register(aktualisSav, "aktualisSav");
        aktualisSav.befogad(auto1);
        auto1.setAktualisCsomopont(aktualisSav);

        auto1.lep(cel);
    }
}