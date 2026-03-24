package vezerles;

import allapot.*;
import felszereles.*;
import halozat.Sav;
import halozat.Utszakasz;
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
                    System.out.println("[ USE-CASE: Sikertelen takarítás hányófejjel (Jég eltávolításának kísérlete) ]");
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

                default:
                    System.out.println("Érvénytelen választás! Kérlek 0 és 12 közötti számot adj meg.");
                    continue; 
            }

            System.out.println("----------------------------------------------------------");
            if (sikeresTakaritas) {
                System.out.println(">>> EREDMÉNY: A takarítás megtörtént");
            } else {
                System.out.println(">>> EREDMÉNY: Nem történt takarítás");
            }
            System.out.println("----------------------------------------------------------");
        }

        System.out.println("\n--- Szimuláció Vége ---");
        scanner.close();
    }
}