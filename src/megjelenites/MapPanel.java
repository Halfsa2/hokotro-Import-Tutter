package megjelenites;

import halozat.Csomopont;
import java.awt.*;
import java.util.Map;
import javax.swing.*;
import vezerles.IJatekVezerlo;

public class MapPanel extends JPanel {
    private IJatekVezerlo vezerlo;
    private Map<Csomopont, Point> nodePositions;
    private final int TILE_SIZE = 20; // A képeitek mérete

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

        // --- 1. KÖR: CSAK A PÁLYAELEMEK ÉS AZ IDŐJÁRÁS KIRAJZOLÁSA ---
        for (Map.Entry<Csomopont, Point> entry : nodePositions.entrySet()) {
            Csomopont csp = entry.getKey();
            Point p = entry.getValue();
            Image baseTexture = null;

            if (csp instanceof halozat.Sav) {
                halozat.Sav sav = (halozat.Sav) csp;
                if (sav.getUtszakasz() instanceof halozat.Alagut) {
                    baseTexture = TextureManager.getTexture("alagut");
                } else if (sav.getUtszakasz() instanceof halozat.Hid) {
                    baseTexture = TextureManager.getTexture("hid");
                } else {
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

            if (csp.foglalt()) {
                Image jarmuTexture = null;
                jarmu.Jarmu kirajzolandoJarmu = null;
                boolean latszik = true;

                if (csp instanceof halozat.Sav) {
                    halozat.Sav sav = (halozat.Sav) csp;
                    kirajzolandoJarmu = sav.getJarmu();
                    if (sav.getUtszakasz() instanceof halozat.Alagut) {
                        latszik = false;
                    }
                } else if (csp instanceof halozat.Checkpoint) {
                    java.util.List<jarmu.Jarmu> bentLevok = ((halozat.Checkpoint) csp).getJarmuvek();
                    if (bentLevok != null && !bentLevok.isEmpty()) {
                        kirajzolandoJarmu = bentLevok.get(0); 
                    }
                }

                if (latszik && kirajzolandoJarmu != null) {
                    if (kirajzolandoJarmu instanceof jarmu.Auto) {
                        jarmuTexture = TextureManager.getTexture("auto");
                    } else if (kirajzolandoJarmu instanceof jarmu.Busz) {
                        jarmuTexture = TextureManager.getTexture("busz");
                    } else if (kirajzolandoJarmu instanceof jarmu.Hokotro) {
                        jarmuTexture = TextureManager.getTexture("hokotro");
                    }

                    if (jarmuTexture != null) {
                        // ITT A VARÁZSLAT: A jármű marad 64-es méretű!
                        int vehicleSize = 64; 
                        
                        // Kiszámoljuk a középre igazítást (ha a jármű nagyobb, az offset negatív lesz, így kilóg a sávból)
                        int offsetX = (TILE_SIZE - vehicleSize) / 2;
                        int offsetY = (TILE_SIZE - vehicleSize) / 2;
                        
                        g.drawImage(jarmuTexture, p.x + offsetX, p.y + offsetY, vehicleSize, vehicleSize, null);
                    }
                }
            }
        }
    }
}