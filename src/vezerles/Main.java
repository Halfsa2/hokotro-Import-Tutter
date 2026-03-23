package vezerles;

import allapot.*;
import felszereles.*;
import halozat.Sav;
import halozat.Utszakasz;
import jarmu.Hokotro;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean fut = true;

        System.out.println("==================================================");
        System.out.println("  Zúzmaraváros Szimuláció - Kotrófej Use-Case-ek  ");
        System.out.println("==================================================");

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
            System.out.println("12. Sikertelen takarítás hányófejjel (jég eltávolításának kísérlete)");
            System.out.println("0.  Kilépés");
            System.out.print("\nVálasztás: ");

            String valasz = scanner.nextLine();

            if (valasz.equals("0")) {
                fut = false;
                continue;
            }

            Sav sav = new Sav();
            Hokotro hokotro = new Hokotro(null); 

            switch (valasz) {
                case "1":
                    System.out.println("\n[ 1. Sikeres takarítás sárkányfejjel (havat tisztít) ]");
                    sav.setAllapot(new SekelyHo());
                    Sarkanyfej sarkany1 = new Sarkanyfej(5);
                    hokotro.addFej(sarkany1);
                    hokotro.cserelFej(sarkany1);
                    hokotro.lep(sav);
                    break;

                case "2":
                    System.out.println("\n[ 2. Sikeres takarítás sárkányfejjel (jeget tisztít) ]");
                    sav.setAllapot(new Jeges());
                    Sarkanyfej sarkany2 = new Sarkanyfej(5);
                    hokotro.addFej(sarkany2);
                    hokotro.cserelFej(sarkany2);
                    hokotro.lep(sav);
                    break;

                case "3":
                    System.out.println("\n[ 3. Sikertelen takarítás sárkányfejjel (nincs kerozin) ]");
                    sav.setAllapot(new SekelyHo());
                    Sarkanyfej sarkany3 = new Sarkanyfej(0); 
                    hokotro.addFej(sarkany3);
                    hokotro.cserelFej(sarkany3);
                    hokotro.lep(sav);
                    break;

                case "4":
                    System.out.println("\n[ 4. Sikeres takarítás sószóróval ]");
                    sav.setAllapot(new Jeges());
                    Soszoro soszoro1 = new Soszoro(5);
                    hokotro.addFej(soszoro1);
                    hokotro.cserelFej(soszoro1);
                    hokotro.lep(sav);
                    break;

                case "5":
                    System.out.println("\n[ 5. Sikertelen takarítás sószóróval (nincs só) ]");
                    sav.setAllapot(new Jeges());
                    Soszoro soszoro2 = new Soszoro(0); 
                    hokotro.addFej(soszoro2);
                    hokotro.cserelFej(soszoro2);
                    hokotro.lep(sav);
                    break;

                case "6":
                    System.out.println("\n[ 6. Sikeres takarítás jégtörővel (jeget tisztít) ]");
                    sav.setAllapot(new Jeges());
                    Jegtoro jegtoro1 = new Jegtoro();
                    hokotro.addFej(jegtoro1);
                    hokotro.cserelFej(jegtoro1);
                    hokotro.lep(sav);
                    break;

                case "7":
                    System.out.println("\n[ 7. Sikertelen takarítás jégtörővel (hó eltávolításának kísérlete) ]");
                    sav.setAllapot(new SekelyHo());
                    Jegtoro jegtoro2 = new Jegtoro();
                    hokotro.addFej(jegtoro2);
                    hokotro.cserelFej(jegtoro2);
                    hokotro.lep(sav);
                    break;

                case "8":
                    System.out.println("\n[ 8. Sikeres hóeltakarítás söprő fejjel (van szomszédos sáv) ]");
                    Utszakasz u1 = new Utszakasz();
                    Sav sav1 = new Sav(); sav1.setAllapot(new SekelyHo());
                    Sav sav2 = new Sav(); sav2.setAllapot(new Tiszta());
                    u1.addSav(sav1); 
                    u1.addSav(sav2);
                    
                    Sopro sopro1 = new Sopro();
                    hokotro.addFej(sopro1);
                    hokotro.cserelFej(sopro1);
                    hokotro.lep(sav1);
                    break;

                case "9":
                    System.out.println("\n[ 9. Sikeres hóeltakarítás söprő fejjel (nincs szomszédos sáv) ]");
                    Utszakasz u2 = new Utszakasz();
                    Sav savEgymaga = new Sav(); savEgymaga.setAllapot(new SekelyHo());
                    u2.addSav(savEgymaga);
                    
                    Sopro sopro2 = new Sopro();
                    hokotro.addFej(sopro2);
                    hokotro.cserelFej(sopro2);
                    hokotro.lep(savEgymaga);
                    break;

                case "10":
                    System.out.println("\n[ 10. Sikertelen takarítás söprővel (jég eltávolításának kísérlete) ]");
                    sav.setAllapot(new Jeges());
                    Sopro sopro3 = new Sopro();
                    hokotro.addFej(sopro3);
                    hokotro.cserelFej(sopro3);
                    hokotro.lep(sav);
                    break;

                case "11":
                    System.out.println("\n[ 11. Sikeres takarítás hányófejjel (havat tisztít) ]");
                    sav.setAllapot(new SekelyHo());
                    Hanyofej hanyo1 = new Hanyofej();
                    hokotro.addFej(hanyo1);
                    hokotro.cserelFej(hanyo1);
                    hokotro.lep(sav);
                    break;

                case "12":
                    System.out.println("\n[ 12. Sikertelen takarítás hányófejjel (jég eltávolításának kísérlete) ]");
                    sav.setAllapot(new Jeges());
                    Hanyofej hanyo2 = new Hanyofej();
                    hokotro.addFej(hanyo2);
                    hokotro.cserelFej(hanyo2);
                    hokotro.lep(sav);
                    break;

                default:
                    System.out.println("Érvénytelen választás! Kérlek 0 és 12 közötti számot adj meg.");
            }
        }

        System.out.println("\n--- Szimuláció Vége ---");
        scanner.close();
    }
}