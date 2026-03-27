package vezerles;

import java.util.HashMap;
import java.util.Map;

public class SkeletonLogger {
    private SkeletonLogger() {} // Privát konstruktor, hogy ne lehessen példányosítani
    private static int depth = 0;
    
    //Szótár az objektumok és a nevük tárolására, hogy szép logokat kapjunk
    private static Map<Object, String> nevTarak = new HashMap<>();

    // A Tesztelő osztályban ezzel adunk nevet az objektumoknak a teszt elején
    public static void register(Object obj, String nev) {
        nevTarak.put(obj, nev);
    }

    // Segédmetódus: megmondja, mi az objektum neve
    public static String getNev(Object obj) {
        if (obj == null) return "null";
        
        // 1. Ha regisztráltuk (pl. s1, b1, alagut)
        if (nevTarak.containsKey(obj)) {
            return nevTarak.get(obj);
        }
        
        // A statikus értékek kiíratásához szükséges
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
        // Összerakja a tökéletes formátumot: > s1:Sav.frissit()
        System.out.println("> " + peldanyNev + ":" + osztalyNev + "." + metodusNev + paramsStr.toString());
        depth++;
    }

    public static void exit(String returnValue) {
        depth--;
        printIndent();
        System.out.println("<- " + returnValue);
    }
    // KIFEJEZETTEN KONSTRUKTOROKNAK: A "> new OsztalyNev()" formátumért
    public static void create(Object ujObjektum) {
        printIndent();
        System.out.println("> new " + ujObjektum.getClass().getSimpleName() + "()");
        depth++;
    }

    // VISSZATÉRÉS A KONSTRUKTORBÓL: "<- nev:OsztalyNev" formátum
    public static void exitCreate(Object ujObjektum) {
        depth--;
        printIndent();
        System.out.println("<- " + getNev(ujObjektum) + ":" + ujObjektum.getClass().getSimpleName());
    }
    private static void printIndent() {
        for (int i = 0; i < depth; i++) {
            System.out.print("    ");
        }
    }
}
