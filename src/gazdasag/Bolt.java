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
        int ar = getAr(termek);

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
        return false;
    }
}
