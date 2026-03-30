package gazdasag;
import jarmu.Hokotro;
import vezerles.SkeletonLogger;
/**
 * A hely, ahol a közös kasszába megszerzett Zúzmara Tallérokat el lehet költeni [cite: 786-787].
 */
public class Bolt implements IMegvasarolhato {
    /**
     * Segédmetódus a termékek árainak meghatározására.
     * @param termek a megvásárolni kívánt termék
     * @return a termék ára Zúzmara Tallérban
     */
    // Segédmetódus a termékek árainak meghatározására 
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
    /**
     * Megvalósítja a vásárlási logikát a boltban.
     * A vevő (takarító) kifizet egy terméket a közös kasszából, majd a termék hatását alkalmazza.
     * @param termek a megvásárolni kívánt termék
     * @param vevo a vásárlást indító takarító
     * @param gep a vásárolt termékhez kapcsolódó hókotró
     * @return true, ha a vásárlás sikeres volt, különben false
     */
    @Override
    public boolean vasarol(Arucikk termek, Takarito vevo, Hokotro gep) {
        SkeletonLogger.enter(this, "vasarol", termek, vevo, gep);
        int ar = getAr(termek);
        boolean fizetesSikeres = vevo.fizet(ar); //takarító fizet metódusa
        
        if (fizetesSikeres) {
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
            }
            SkeletonLogger.exit(true);
            return true;
        } 
        else {
            // Nincs elég pénz (Sikertelen vásárlás)
            SkeletonLogger.exit(false);
            return false;
        }
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

