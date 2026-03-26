package gazdasag;
import felszereles.*;
import jarmu.Hokotro;
/**
 * A hely, ahol a közös kasszába megszerzett Zúzmara Tallérokat el lehet költeni [cite: 786-787].
 */
public class Bolt implements IMegvasarolhato {

    // Segédmetódus a termékek árainak meghatározására (példa árakkal)
    private int getAr(Arucikk termek) {
        switch (termek) {
            case HANYOFEJ: return 100;
            case SOSZORO: return 150;
            case SARKANYFEJ: return 300;
            case HOKOTRO: return 500;
            case SO: return 50;
            case KEROZIN: return 100;
            case GLOBAL_WARMING: return 10000;
            default: return 0;
        }
    }

    @Override
    public boolean vasarol(Arucikk termek, Takarito vevo, Hokotro gep) {
        //log: A Bolt.vasarol meghívása
        System.out.println("\t> bolt1:Bolt.vasarol(" + termek + ", takarito1: Takarito, hokotro1:Hokotro)");
        int ar = getAr(termek);

        // Meghívjuk a Takarító fizet() metódusát (ami majd meghívja a kasszát)
        boolean fizetesSikeres = vevo.fizet(ar);
        if (fizetesSikeres) {
            // Szkeleton szintű "létrehozás" és hozzáadás logolása a termék alapján
            if (termek == Arucikk.SARKANYFEJ) {
                System.out.println("\t\t> new ujFej: Sarkanyfej()");
                System.out.println("\t\t<- ujFej: Sarkanyfej");
                System.out.println("\t\t> hokotro1:Hokotro.addFej(ujFej: Sarkanyfej)");
                System.out.println("\t\t<- void");
            } 
            else if (termek == Arucikk.SO) {
                System.out.println("\t\t> takarito1:Takarito.soToltes(hokotro1: Hokotro)");
                System.out.println("\t\t<- void");
            }
            else if (termek == Arucikk.KEROZIN) {
                System.out.println("\t\t> takarito1:Takarito.kerozinToltes(hokotro1: Hokotro)");
                System.out.println("\t\t<- void");
            }
            else if (termek == Arucikk.HOKOTRO) {
                System.out.println("\t\t> new ujHokotro:Hokotro()");
                System.out.println("\t\t<- ujHokotro: Hokotro");
                // Itt a doksi szerint takarito1:Takarito.addHokotro hívás nincs a diagramon [cite: 194-209]
            }
            
            System.out.println("\t<- true");
            return true;
        } 
        else {
            // Nincs elég pénz (Sikertelen vásárlás use-case) [cite: 262-273]
            System.out.println("\t<- false");
            return false;
        }

        /* ez még nem kell
        // A bolt ellenőrzi, hogy a Takarító tud-e fizetni
        if (vevo.fizet(ar)) {
            // Ha a fizetés sikeres, létrehozza a terméket és odaadja a vevőnek/gépnek [cite: 792]
            switch (termek) {
                case HANYOFEJ:
                    gep.addFej(new Hanyofej());
                    break;
                case SOSZORO:
                    gep.addFej(new Soszoro(5)); // Például 5 egység sóval indul
                    break;
                case SARKANYFEJ:
                    gep.addFej(new Sarkanyfej(5)); // Például 5 egység kerozinnal indul
                    break;
                case HOKOTRO:
                    Hokotro ujGep = new Hokotro(vevo);
                    vevo.addHokotro(ujGep); // A Takarító listájához adódik [cite: 796]
                    break;
                case SO:
                    vevo.soToltes(gep);
                    break;
                case KEROZIN:
                    vevo.kerozinToltes(gep);
                    break;
                case GLOBAL_WARMING:
                    // A havazást megakadályozó eszköz megállítja a havazást [cite: 554-555]
                    System.out.println("Győzelem! A havazás elállt Zúzmaravárosban!");
                    // Itt kellene értesíteni a JátékKezelőt/VárosModellt a játék végéről
                    break;
            }
            return true;
        }
        
        // Nincs elég pénz
        System.out.println("Nincs elég Zúzmara Tallér a Közös Kasszában!");
        return false;*/
    } 
}
