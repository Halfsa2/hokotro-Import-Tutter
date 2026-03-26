package gazdasag;
/**
 * A játékosok közös perselye, amely a megszerzett Zúzmara Tallérokat tárolja.
 */
public class KozosKassza {
    
    private int penzosszeg; // A Zúzmara Tallérok aktuális egyenlege.

    public KozosKassza(int kezdetiOsszeg) {
        this.penzosszeg = kezdetiOsszeg;
    }

    /**
     * Hozzáadja a paraméterként kapott összeget a közös egyenleghez.
     */
    public void penzHozzaadas(int osszeg) {
        System.out.println("\t> kassza:KozosKassza.penzHozzaadas(" + osszeg + ")"); // Szkeleton log kiírása
        this.penzosszeg += osszeg;
        System.out.println("\t<- void");
    }

    /**
     * Levonja a megadott összeget a kasszából egy vásárlás során.
     */
    public boolean penzKivonas(int osszeg) {
        System.out.println("\t\t\t> kassza:KozosKassza.penzKivonas(" + osszeg + ")");
        
        System.out.print("\t\t\t[?] Van elég pénz a közös kasszában a termékre? (i/n): ");
        java.util.Scanner sc = new java.util.Scanner(System.in);
        String valasz = sc.nextLine();
        
        if (valasz.equalsIgnoreCase("i")) {
            System.out.println("\t\t\t<- true");
            return true;
        } 
        else {
            System.out.println("\t\t\t<- false");
            return false;
        }
        /*
        if (this.penzosszeg >= osszeg) {
            this.penzosszeg -= osszeg;
            return true; // Sikeres tranzakció
        }
        return false; // Nincs elég fedezet*/
    }
}
