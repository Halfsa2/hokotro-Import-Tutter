package megjelenites;

import halozat.Csomopont;
import java.awt.*;
import java.util.Map;
import javax.swing.*;
import vezerles.IJatekVezerlo;

public class MapPanel extends JPanel {
    private IJatekVezerlo vezerlo;
    private Map<Csomopont, Point> nodePositions;
    private final int TILE_SIZE = 64; // A képeitek mérete

    // Konstruktor: megkapja a vezérlőt és a koordinátákat a GameWindow-tól
    public MapPanel(IJatekVezerlo vezerlo, Map<Csomopont, Point> nodePositions) {
        this.vezerlo = vezerlo;
        this.nodePositions = nodePositions;
        
        // Fekete vagy sötétszürke háttérszín, ha nem fedné le mindenhol a pálya
        setBackground(Color.DARK_GRAY); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Map.Entry<Csomopont, Point> entry : nodePositions.entrySet()) {
            Csomopont csp = entry.getKey();
            Point p = entry.getValue();
            Image baseTexture = null;

            // 1. Alapréteg (sávok, állapotok) meghatározása
            if (csp instanceof halozat.Sav) {
                halozat.Sav sav = (halozat.Sav) csp;
                
                // ELŐSZÖR megnézzük, hogy a sáv alagútban van-e!
                if (sav.getUtszakasz() instanceof halozat.Alagut) {
                    baseTexture = TextureManager.getTexture("alagut");
                } 
                // Ha nem alagút, akkor megnézzük az állapotát (hó, jég, stb.)
                else {
                    allapot.Savallapot allapot = sav.getAllapot();
                    if (allapot instanceof allapot.Tiszta) {
                        baseTexture = TextureManager.getTexture("tiszta");
                    } else if (allapot instanceof allapot.SekelyHo) {
                        baseTexture = TextureManager.getTexture("sekely");
                    } else if (allapot instanceof allapot.MelyHo) {
                        baseTexture = TextureManager.getTexture("mely");
                    } else if (allapot instanceof allapot.Jeges) {
                        baseTexture = TextureManager.getTexture("jeges");
                    }
                }
            } else if (csp instanceof halozat.Keresztezodes) {
                baseTexture = TextureManager.getTexture("keresztezodes");
            } else if (csp instanceof halozat.Checkpoint) {
                baseTexture = TextureManager.getTexture("vegallomas");
            }

            // Kép kirajzolása
            if (baseTexture != null) {
                g.drawImage(baseTexture, p.x, p.y, TILE_SIZE, TILE_SIZE, null);
            }

            // 2. Extra réteg: Zúzalék (ha van)
            if (csp instanceof halozat.Sav && ((halozat.Sav) csp).isZuzalekos()) {
                Image zuzalekLayer = TextureManager.getTexture("zuzalekos");
                g.drawImage(zuzalekLayer, p.x, p.y, TILE_SIZE, TILE_SIZE, null);
            }

            // 3. JÁRMŰVEK KIRAJZOLÁSA (HA FOGLALT A CSOMÓPONT)
            if (csp.foglalt()) {
                Image jarmuTexture = null;

                // Megnézzük, hogy Sávon állunk-e, és lekérjük a rajta lévő járművet
                if (csp instanceof halozat.Sav) {
                    halozat.Sav sav = (halozat.Sav) csp;
                    jarmu.Jarmu j = sav.getJarmu(); // FONTOS: Ehhez kell a getJarmu() a Sav.java-ba!

                    // Eldöntjük, melyik képet töltsük be a jármű típusa alapján
                    if (j instanceof jarmu.Auto) {
                        jarmuTexture = TextureManager.getTexture("auto");
                    } else if (j instanceof jarmu.Busz) {
                        jarmuTexture = TextureManager.getTexture("busz");
                    } else if (j instanceof jarmu.Hokotro) {
                        jarmuTexture = TextureManager.getTexture("hokotro");
                    }
                }
                
                // TODO később: Ugyanezt megcsinálni Checkpoint és Keresztezodes esetére is!

                // Jármű tényleges kirajzolása (eltolva, hogy az aszfalt közepén legyen)
                if (jarmuTexture != null) {
                    int offset = 8; 
                    int vehicleSize = TILE_SIZE - (offset * 2);
                    g.drawImage(jarmuTexture, p.x + offset, p.y + offset, vehicleSize, vehicleSize, null);
                }
            }
        }
    }
}