package vezerles;

import java.util.HashMap;
import java.util.Map;
//retired (öregecske szegény, bepakoljuk majd valami otthonba)
public class SkeletonLogger {
    /**
     * Segéd osztály a szimuláció metódushívásainak és állapotváltozásainak naplózásához.
     * Statikus helper, amely depth-alapú behúzást használ a konzolos kimenetben.
     */
    private SkeletonLogger() {} // Privát konstruktor, hogy ne lehessen példányosítani
    private static int depth = 0;
    
    //Szótár az objektumok és a nevük tárolására, hogy szép logokat kapjunk
    private static final Map<Object, String> nevTarak = new HashMap<>();

    /**
     * Regisztrálja az objektumot emberi olvasható névvel a log céljára.
     * @param obj a logolandó objektum
     * @param nev a megjelenítendő név
     */
    public static void register(Object obj, String nev) {
        nevTarak.put(obj, nev);
    }

    /**
     * Visszaadja az objektum nevének reprezentációját (regisztrált név, primitív érték vagy default).
     * @param obj név lekérése
     * @return a logba kerülő név
     */
    public static String getNev(Object obj) {
        if (obj == null) return "null";
        
        // Először megnézzük, hogy van-e neve a nevTarakban
        if (nevTarak.containsKey(obj)) {
            return nevTarak.get(obj);
        }
        
        // A statikus értékek (int, string, boolean) értékének kiíratásához szükséges
        if (obj instanceof String || obj instanceof Integer || obj instanceof Boolean) {
            return obj.toString();
        }
        
        // 3. Biztonsági háló
        return "ismeretlen_" + obj.getClass().getSimpleName(); 
    }

    /**
     * Naplózza a metódushívás kezdetét (enter) és növeli a behúzást.
     * @param hivoObjektum a hívó objektum
     * @param metodusNev a metódus neve
     * @param parameterek metódusparaméterek
     */
    public static void enter(Object hivoObjektum, String metodusNev,Object... parameterek) {
        printIndent();
        
        String peldanyNev = getNev(hivoObjektum);
        String osztalyNev = hivoObjektum.getClass().getSimpleName();
        StringBuilder paramsStr = new StringBuilder("(");
        for (int i = 0; i < parameterek.length; i++) {
            paramsStr.append(getNev(parameterek[i]));
            if (i < parameterek.length - 1) paramsStr.append(", ");
        }
        paramsStr.append(")");
        // Összerakja a  formátumot, pl.: > s1:Sav.frissit()
        System.out.println("> " + peldanyNev + ":" + osztalyNev + "." + metodusNev + paramsStr.toString());
        depth++;
    }

    public static void exit(Object returnValue) {
        depth--;
        printIndent();
        
        if (returnValue == null) {
            System.out.println("<- null");
            return;
        }
        
        // Egyszerű típusok esetén a toString értékét írjuk ki
        if (returnValue instanceof String || returnValue instanceof Integer || returnValue instanceof Boolean) {
            System.out.println("<- " + returnValue.toString());
        }
        //objektumok esetén a nevTarakból próbáljuk meg kinyerni a nevet, hogy szép logunk legyen
        else {
            String nev = getNev(returnValue);
            String osztaly = returnValue.getClass().getSimpleName();
            System.out.println("<- " + nev + ":" + osztaly);
        }
    }
    // KIFEJEZETTEN KONSTRUKTOROKNAK: A "> new OsztalyNev()" formátumért
    public static void create(Object ujObjektum) {
        printIndent();
        System.out.println("> new " + ujObjektum.getClass().getSimpleName() + "()");
        depth++;
    }
    /**
     * Kiírja a behúzást az aktuális mélységnek megfelelően.
     */
    private static void printIndent() {
        for (int i = 0; i < depth; i++) {
            System.out.print("    ");
        }
    }
}
