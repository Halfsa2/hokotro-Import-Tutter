package megjelenites;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;


/**
 * Ez az osztály felelős a játékban használt képek (textúrák) betöltéséért és tárolásáért.
 * A textúrák egy statikus map-ben vannak tárolva, ahol a kulcs egy könnyen megjegyezhető név, és az érték maga a betöltött Image objektum.
 */
public class TextureManager {
    /**
     * A textúrák tárolására szolgáló map
     */
    private static final Map<String, Image> textures = new HashMap<>();

    /**
     * Egy biztonságos segédmetódus, ami kiírja, ha pontosan melyik fájl hiányzik
     */
    private static void loadImg(String kulcs, String fajlNev) {
        String path = "resources/" + fajlNev;
        try {
            textures.put(kulcs, ImageIO.read(new File(path)));
        } catch (IOException e) {
            System.err.println("HIBA: Nem tudom beolvasni ezt a képet: " + path);
        }
    }

    /**
     * Betölti az összes szükséges textúrát.
     */
    public static void loadTextures() {
        System.out.println("Képek betöltése folyamatban...");
        
        // Pályaelemek és állapotok
        loadImg("tiszta", "tiszta.png");
        loadImg("sekely", "sekely.png");
        loadImg("mely", "mely.png");
        loadImg("jeges", "jeges.png");
        loadImg("zuzalekos", "zuzalekos.png");
        loadImg("alagut", "alagut.png");
        loadImg("hid", "hid.png");
        loadImg("keresztezodes", "keresztezodes4.png");
        loadImg("vegallomas", "vegallomas.png");

        // Járművek
        loadImg("auto", "auto.png");
        loadImg("busz", "busz.png");
        loadImg("hokotro", "hokotro.png");

        // Felszerelések és fogyóeszközök
        loadImg("hanyo", "hanyo.png");
        loadImg("jegtoro", "jegtoro.png");
        loadImg("sarkanyfej", "sarkanyfej.png");
        loadImg("sopro", "sopro.png");
        loadImg("soszoro", "soszoro.png");
        loadImg("zuzalekszoro", "zuzalekszoro.png");
        
        loadImg("kerozin", "kerozin.png");
        loadImg("so", "so.png");
        loadImg("zuzalek", "zuzalek.png");
        loadImg("zt", "ZT.png"); // Figyelj a nagybetűkre, ha a fájl neve is ZT.png!
        loadImg("global_warning", "globalwarning.png");

        System.out.println("Képek betöltése befejeződött!");
    }

    /**
     * Visszaadja a megadott nevű textúrát, vagy null-t, ha nem található.
     * @param name
     * @return a textúra, vagy null, ha nincs ilyen nevű textúra
     */
    public static Image getTexture(String name) {
        return textures.get(name);
    }
}