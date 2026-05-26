package gazdasag;

import felszereles.*;
import jarmu.Hokotro;
import prototipus.CommandInterpreter;
import prototipus.IStatable;
import vezerles.SkeletonLogger;

/**
 * A hely, ahol a közös kasszába megszerzett Zúzmara Tallérokat el lehet költeni.
 */
public class Bolt implements IMegvasarolhato, IStatable {

    /**
     * Meghatározza a termékek árait a prototípus specifikáció alapján.
     * @param termek A termék enum értéke, amelynek az árát kívánjuk lekérdezni.
     */
    private int getAr(Arucikk termek) {
        return switch (termek) {
            case HANYOFEJ -> 100;
            case SOSZORO -> 150;
            case SARKANYFEJ -> 300;
            case ZUZALEKSZORO -> 200;
            case HOKOTRO -> 500;
            case SO -> 50;
            case KEROZIN -> 100;
            case ZUZALEK -> 75;
            case GLOBAL_WARMING -> 10000;
            default -> 0;
        };
    }

    /**
     * A vásárlást lebonyolító metódus.
     * @param termek A vásárolni kívánt termék
     * @param vevo Az a takarító, aki a vásárlást kezdeményezte
     * @param gep A vevő azon hókotrója, melyre az adott terméket vásárolni akarja (ha olyan terméket vásárol, amely nem hókotró fejlesztés, akkor null értéket kap)
     * @return A vásárlás sikeressége
     */
    @Override
    public boolean vasarol(Arucikk termek, Takarito vevo, Hokotro gep) {
        SkeletonLogger.enter(this, "vasarol", termek, vevo, gep);
        
        int ar = getAr(termek);
        boolean fizetesSikeres = vevo.fizet(ar); // Levonás a közös kasszából
        
        if (fizetesSikeres) {
            switch (termek) {
                case HANYOFEJ -> gep.addFej(new Hanyofej());
                case SOSZORO -> gep.addFej(new Soszoro(10));
                case SARKANYFEJ -> gep.addFej(new Sarkanyfej(10));
                case ZUZALEKSZORO -> gep.addFej(new ZuzalekSzoro(10));
                case SO -> vevo.soToltes(gep);
                case KEROZIN -> vevo.kerozinToltes(gep);
                case ZUZALEK -> vevo.zuzalekToltes(gep);
                case HOKOTRO -> vevo.addHokotro(new Hokotro(vevo));
                case GLOBAL_WARMING -> {
                    System.out.println("Győzelem! A havazás elállt Zúzmaravárosban!");
                    CommandInterpreter.replyLog.add("Győzelem! A havazás elállt Zúzmaravárosban!"); // Ez a log üzenet jelzi a győzelmet a teszteléshez
                    // A visszatérési érték (true) jelzi a JatekVezerlonek, hogy vége a játéknak
                }
            }
            SkeletonLogger.exit(true);
            return true;
        } else {
            SkeletonLogger.exit(false);
            return false;
        }
    }
    @Override
    public String printStat(String name) {
        return "Bolt " + name;
    }
}