package megjelenites;

import halozat.Csomopont;
import java.awt.*;
import java.util.Map;
import javax.swing.*;
import vezerles.IJatekVezerlo;

/**
 * A játék térképének grafikus megjelenítéséért felelős egyedi Swing panel.
 * Ez az osztály végzi a csomópontok (utak, kereszteződések), az időjárási viszonyok 
 * (hó, jég), valamint a járművek (hókotró, busz, autó) tényleges képernyőre rajzolását.
 */
public class MapPanel extends JPanel {
    /** A játék menetét irányító vezérlő. */
    private IJatekVezerlo vezerlo;
    /** A csomópontok és a hozzájuk tartozó (x, y) képernyő-koordináták leképezése. */
    private Map<Csomopont, Point> nodePositions;
    /** A pályaelemek alapértelmezett mérete pixelben. */
    private final int TILE_SIZE = 20;

    /**
     * Konstruktor a MapPanel osztályhoz. 
     * Megkapja a vezérlőt és a csomópontok kiszámolt koordinátáit a GameWindow-tól.
     * @param vezerlo A játék logikáját kezelő vezérlő
     * @param nodePositions A csomópontok elhelyezkedését tartalmazó Map
     */    
    public MapPanel(IJatekVezerlo vezerlo, Map<Csomopont, Point> nodePositions) {
        this.vezerlo = vezerlo;
        this.nodePositions = nodePositions;
        
        // Fekete vagy sötétszürke háttérszín, ha nem fedné le mindenhol a pálya
        setBackground(Color.DARK_GRAY); 
    }

    /**
     * Felülírt metódus a komponens egyéni kirajzolásához.
     * Két fő fázisban dolgozik: 
     * 1. A pályaelemek (utak, alagutak, hidak) és az állapotok (hóréteg, jég, zúzalék) kirajzolása.
     * 2. A járművek rárajzolása a pályára, figyelembe véve az extra szabályokat (pl. alagútban nem látszanak).
     * @param g A rajzoláshoz használt Graphics objektum
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // --- 1. KÖR: CSAK A PÁLYAELEMEK ÉS AZ IDŐJÁRÁS KIRAJZOLÁSA ---
        for (Map.Entry<Csomopont, Point> entry : nodePositions.entrySet()) {
            Csomopont csp = entry.getKey();
            Point p = entry.getValue();
            Image baseTexture = null;

            // 1. Megnézzük a mező típusát
            if (csp instanceof halozat.Sav) {
                halozat.Sav sav = (halozat.Sav) csp;
                
                // Az Alagút mindig alagút marad (oda nem esik hó)
                if (sav.getUtszakasz() instanceof halozat.Alagut) {
                    baseTexture = TextureManager.getTexture("alagut");
                } else {
                    // Ha nem alagút (tehát Sima út vagy Híd), lekérjük az állapotát!
                    allapot.Savallapot allapot = sav.getAllapot();
                    
                    if (allapot instanceof allapot.Tiszta) {
                        // Ha TISZTA, megnézzük, hogy híd-e. Ha igen, híd textúra, ha nem, sima aszfalt.
                        if (sav.getUtszakasz() instanceof halozat.Hid) {
                            baseTexture = TextureManager.getTexture("hid");
                        } else {
                            baseTexture = TextureManager.getTexture("tiszta");
                        }
                    } 
                    // Ha viszont esett rá a hó, a hó textúrák befedik a hidat és az utat is!
                    else if (allapot instanceof allapot.SekelyHo) {
                        baseTexture = TextureManager.getTexture("sekely");
                    } else if (allapot instanceof allapot.MelyHo) {
                        baseTexture = TextureManager.getTexture("mely");
                    } else if (allapot instanceof allapot.Jeges) {
                        baseTexture = TextureManager.getTexture("jeges");
                    }
                }
            } else if (csp instanceof halozat.Keresztezodes) {
                baseTexture = TextureManager.getTexture("tiszta"); 
            } else if (csp instanceof halozat.Checkpoint) {
                baseTexture = TextureManager.getTexture("vegallomas");
            }

            if (baseTexture != null) {
                // A SÁVOKAT A KISEBB MÉRETTEL RAJZOLJUK (pl. 40x40)
                g.drawImage(baseTexture, p.x, p.y, TILE_SIZE, TILE_SIZE, null);
            }

            if (csp instanceof halozat.Sav && ((halozat.Sav) csp).isZuzalekos()) {
                Image zuzalekLayer = TextureManager.getTexture("zuzalekos");
                g.drawImage(zuzalekLayer, p.x, p.y, TILE_SIZE, TILE_SIZE, null);
            }
        }

       // --- 2. KÖR: JÁRMŰVEK KIRAJZOLÁSA (Hogy mindig legfelül legyenek!) ---
        for (Map.Entry<Csomopont, Point> entry : nodePositions.entrySet()) {
            Csomopont csp = entry.getKey();
            Point p = entry.getValue();

            Image jarmuTexture = null;
            jarmu.Jarmu kirajzolandoJarmu = null;
            boolean latszik = true;

            // 1. Megnézzük, milyen típusú mezőn vagyunk, és van-e rajta jármű
            if (csp instanceof halozat.Sav) {
                halozat.Sav sav = (halozat.Sav) csp;
                if (sav.getJarmu() != null) {
                    kirajzolandoJarmu = sav.getJarmu();
                    // Alagútban láthatatlan
                    if (sav.getUtszakasz() instanceof halozat.Alagut) {
                        latszik = false;
                    }
                }
            } 
            else if (csp instanceof halozat.Checkpoint) {
                java.util.List<jarmu.Jarmu> bentLevok = ((halozat.Checkpoint) csp).getJarmuvek();
                if (bentLevok != null && !bentLevok.isEmpty()) {
                    kirajzolandoJarmu = bentLevok.get(0); 
                }
            } 
            else if (csp instanceof halozat.Keresztezodes) {
                java.util.List<jarmu.Jarmu> bentLevok = ((halozat.Keresztezodes) csp).getJarmuvek();
                if (bentLevok != null && !bentLevok.isEmpty()) {
                    // Mivel a Kereszteződésbe több autó is befér egyszerre, a legutoljára 
                    // belépett (legfelső) járművet rajzoljuk ki, hogy biztosan lásd a sajátodat!
                    kirajzolandoJarmu = bentLevok.get(bentLevok.size() - 1);
                }
            }

            // 2. Tényleges kirajzolás, ha van mit
            if (latszik && kirajzolandoJarmu != null) {
                if (kirajzolandoJarmu instanceof jarmu.Auto) {
                    jarmuTexture = TextureManager.getTexture("auto");
                } else if (kirajzolandoJarmu instanceof jarmu.Busz) {
                    jarmuTexture = TextureManager.getTexture("busz");
                } else if (kirajzolandoJarmu instanceof jarmu.Hokotro) {
                    jarmuTexture = TextureManager.getTexture("hokotro");
                }

                if (jarmuTexture != null) {
                    // A jármű mérete marad a szép nagy (64 pixel)
                    int vehicleSize = 64; 
                    
                    // Középre igazítás a sávokon (kilógás engedélyezése)
                    int offsetX = (TILE_SIZE - vehicleSize) / 2;
                    int offsetY = (TILE_SIZE - vehicleSize) / 2;
                    
                    g.drawImage(jarmuTexture, p.x + offsetX, p.y + offsetY, vehicleSize, vehicleSize, null);
                }
            }
        }
    }
}