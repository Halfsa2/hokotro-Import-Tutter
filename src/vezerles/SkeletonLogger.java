package vezerles;

import java.util.HashMap;
import java.util.Map;

public class SkeletonLogger {
    private SkeletonLogger() {} // Privát konstruktor, hogy ne lehessen példányosítani
    private static int depth = 0;
    
    //Szótár az objektumok és a nevük tárolására, hogy szép logokat kapjunk
    private static final Map<Object, String> nevTarak = new HashMap<>();

    // A Tesztelő osztályban ezzel adunk nevet az objektumoknak a teszt elején
    public static void register(Object obj, String nev) {
        nevTarak.put(obj, nev);
    }

    // Segédmetódus: megmondja, mi az objektum neve
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

    // AZ ÚJ ENTER METÓDUS: Most már kéri magát az objektumot is (this)!
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
    private static void printIndent() {
        for (int i = 0; i < depth; i++) {
            System.out.print("    ");
        }
    }
}
