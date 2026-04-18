package gazdasag;
import felszereles.Hanyofej;
import felszereles.Sarkanyfej;
import felszereles.Soszoro;
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
        return switch (termek) {
            case HANYOFEJ -> 100;
            case SOSZORO -> 150;
            case SARKANYFEJ -> 300;
            case HOKOTRO -> 500;
            case SO -> 50;
            case KEROZIN -> 100;
            case GLOBAL_WARMING -> 10000;
            default -> 0;
        };
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
            if (null != termek) switch (termek) {
                case SARKANYFEJ -> {
                    Sarkanyfej ujSarkanyfej = new Sarkanyfej(10);
                    gep.addFej(ujSarkanyfej);
                }
                case SO -> {
                    //TODO: A sószóró fej újratöltése a megfelelő mennyiségű sóval
                    vevo.soToltes(gep);
                }
                case KEROZIN -> {
                    //TODO: A sárkányfej újratöltése a megfelelő mennyiségű kerozinnal
                    vevo.kerozinToltes(gep);
                }
                case HOKOTRO -> {
                    Hokotro ujGep = new Hokotro(vevo);
                    vevo.addHokotro(ujGep);
                }
                case GLOBAL_WARMING -> {
                    // A havazást megakadályozó eszköz megállítja a havazást [cite: 554-555]
                    System.out.println("Győzelem! A havazás elállt Zúzmaravárosban!");
                    // A JatekVezerlo értesül a sikeres vásárlásról, és kezdeményezi a játék végét
                }
                case ZUZALEK ->{
                    //TODO: A zuzalék újratöltése a megfelelő mennyiséggel
                    vevo.zuzalekToltes(gep);
                }
                case HANYOFEJ -> {
                    Hanyofej ujHanyofej = new Hanyofej();
                    gep.addFej(ujHanyofej);
                }
                case SOSZORO -> {
                    Soszoro ujSoszoro = new Soszoro(10);
                    gep.addFej(ujSoszoro);
                }
                case ZUZALEKSZORO -> {
                    //TODO: Zúzalékszóró felszerelés hozzáadása a hókotróhoz (x db kővel)
                }
            }
            SkeletonLogger.exit(true);
                } 
        else {
            // Nincs elég pénz (Sikertelen vásárlás)
            SkeletonLogger.exit(false);
        }
        return fizetesSikeres;
    }
} 

