package megjelenites;

import halozat.Csomopont;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import vezerles.IJatekVezerlo;

public class GameWindow extends JFrame implements IJatekNezet {
    private IJatekVezerlo vezerlo;
    private MapPanel mapPanel;
    private Map<Csomopont, Point> nodePositions;
    private JLabel infoLabel;
    private JLabel kasszaLabel;

    private int tileSize = 20; 
    private java.awt.event.MouseAdapter aktivKattintasKezelo = null;

    public GameWindow(IJatekVezerlo vezerlo) {
        this.vezerlo = vezerlo;
        this.nodePositions = new HashMap<>();
        
        setTitle("Zúzmaraváros - Hókotró Szimulátor");
        setSize(1024, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setupCoordinates();

        mapPanel = new MapPanel(vezerlo, nodePositions);
        add(mapPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        infoLabel = new JLabel("Aktív játékos: - ");
        kasszaLabel = new JLabel("Kassza: 0 ZT ");

        JButton boltButton = new JButton("Bolt megnyitása");
        boltButton.setFocusable(false);
        boltButton.addActionListener(e -> megnyitBolt());

        JButton csereButton = new JButton("Felszerelés cseréje");
        csereButton.setFocusable(false);
        csereButton.addActionListener(e -> cserelFelszerelest());

        JButton ujSoforButton = new JButton("Új Sofőr felvétele");
        ujSoforButton.setFocusable(false);
        ujSoforButton.addActionListener(e -> ujSoforHozzaadasa());
        
        JButton passzButton = new JButton("Passz");
        passzButton.setFocusable(false);
        passzButton.addActionListener(e -> {
            gazdasag.Jatekos<?> aktiv = vezerlo.getAktivJatekos();
            if (aktiv != null) {
                aktiv.nextJarmu(); 
            }
            vezerlo.nextJatekos();
            frissit();
        });

        controlPanel.add(infoLabel);
        controlPanel.add(kasszaLabel);
        controlPanel.add(boltButton);
        controlPanel.add(csereButton);
        controlPanel.add(ujSoforButton);
        controlPanel.add(passzButton);
        add(controlPanel, BorderLayout.SOUTH);
        
        setupKeyBindings();
    }

   private void setupKeyBindings() {
        InputMap im = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = this.getRootPane().getActionMap();

        java.util.function.BiConsumer<Integer, Integer> move = (dx, dy) -> {
            gazdasag.Jatekos<?> aktiv = vezerlo.getAktivJatekos();
            if (aktiv != null && aktiv.getAktivJarmu() != null) {
                halozat.Csomopont aktualis = aktiv.getAktivJarmu().getAktualisCsomopont();
                vezerles.VarosModell vModell = (vezerles.VarosModell) vezerlo.getVarosModell();
                
                int currX = -1, currY = -1;
                for(int x = 0; x < vModell.getSzelesseg(); x++) {
                    for(int y = 0; y < vModell.getMagassag(); y++) {
                        if(vModell.getCsomopont(x, y) == aktualis) { currX = x; currY = y; break; }
                    }
                }

                if (currX != -1 && currY != -1) {
                    halozat.Csomopont cel = vModell.getCsomopont(currX + dx, currY + dy);
                    
                    // HA CÉLÁLLOMÁSON VAGYUNK ÉS "FALNAK" MEGYÜNK (pl. Előre nyíl a pálya szélén)
                    // A gép automatikusan megkeresi a Checkpoint U-Turn kimenetét!
                    if (cel == null && aktualis instanceof halozat.Checkpoint) {
                        java.util.List<halozat.Csomopont> kimenetek = aktualis.getNext();
                        if(kimenetek != null && !kimenetek.isEmpty()) {
                            cel = kimenetek.get(0); 
                        }
                    }

                    if (cel != null && aktualis.getNext() != null && aktualis.getNext().contains(cel)) {
                        vezerlo.lep(cel);
                        frissit();
                    }
                }
            }
        };

        im.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        am.put("moveRight", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) { move.accept(1, 0); }
        });

        im.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        am.put("moveLeft", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) { move.accept(-1, 0); }
        });

        im.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        am.put("moveDown", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) { move.accept(0, 1); }
        });

        im.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        am.put("moveUp", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) { move.accept(0, -1); }
        });
    }

    @Override
    public void frissit() {
        gazdasag.Jatekos<?> aktiv = vezerlo.getAktivJatekos();
        if(aktiv != null) {
            String jatekosNev = aktiv.getNev();
            String jarmuNev = "Nincs gép";
            
            if (aktiv.getAktivJarmu() != null && aktiv.getAktivJarmu() instanceof jarmu.Hokotro) {
                jarmuNev = ((jarmu.Hokotro)aktiv.getAktivJarmu()).getNev();
            }
            
            infoLabel.setText("Aktív: " + jatekosNev + " (" + jarmuNev + ") ");
        }
        
        if(vezerlo.getVarosModell().getKassza() != null) {
            kasszaLabel.setText("Kassza: " + vezerlo.getVarosModell().getKassza().getPenzosszeg() + " ZT ");
        }
        
        kasszaLabel.revalidate();
        kasszaLabel.repaint();
        mapPanel.repaint();
    }

    private void setupCoordinates() {
        nodePositions.clear();
        vezerles.VarosModell vModell = (vezerles.VarosModell) vezerlo.getVarosModell();
        
        // --- KÖZÉPRE IGAZÍTÁS KISZÁMOLÁSA ---
        int mapPixelWidth = vModell.getSzelesseg() * tileSize;
        int mapPixelHeight = vModell.getMagassag() * tileSize;
        int offsetX = (1024 - mapPixelWidth) / 2; // Ablak szélessége mínusz a pálya szélessége
        int offsetY = (768 - mapPixelHeight) / 2; // Ablak magassága mínusz a pálya magassága

        for (int x = 0; x < vModell.getSzelesseg(); x++) {
            for (int y = 0; y < vModell.getMagassag(); y++) {
                halozat.Csomopont csp = vModell.getCsomopont(x, y);
                if (csp != null) {
                    nodePositions.put(csp, new java.awt.Point(offsetX + (x * tileSize), offsetY + (y * tileSize)));
                }
            }
        }
    }
    
    @Override
    public void uzenetKijelzese(String uzenet) {
        JOptionPane.showMessageDialog(this, uzenet, "Üzenet", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void jatekVege(String eredmeny) {
        JOptionPane.showMessageDialog(this, eredmeny, "Játék Vége!", JOptionPane.WARNING_MESSAGE);
    }

    private void megnyitBolt() {
        gazdasag.Jatekos<?> aktiv = vezerlo.getAktivJatekos();
        if (!(aktiv instanceof gazdasag.Takarito)) {
            uzenetKijelzese("Csak a Takarító vásárolhat a boltban!");
            return;
        }

        jarmu.Hokotro aktivHokotro = null;
        if (aktiv.getAktivJarmu() instanceof jarmu.Hokotro) {
            aktivHokotro = (jarmu.Hokotro) aktiv.getAktivJarmu();
        }

        JDialog boltAblak = new JDialog(this, "Zúzmaraváros Bolt és Hátizsák", true);
        boltAblak.setSize(750, 600);
        boltAblak.setLocationRelativeTo(this);
        boltAblak.setLayout(new BorderLayout());

        final gazdasag.Arucikk[] kivalasztottArucikk = {null};
        
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        JLabel kasszaBoltLabel = new JLabel();
        kasszaBoltLabel.setFont(new Font("Arial", Font.BOLD, 22));
        infoPanel.add(kasszaBoltLabel);
        boltAblak.add(infoPanel, BorderLayout.NORTH);

        JPanel tartalomPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        tartalomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel gombPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        JScrollPane boltScroll = new JScrollPane(gombPanel);
        boltScroll.setBorder(BorderFactory.createTitledBorder("Bolt Kínálata"));
        tartalomPanel.add(boltScroll);

        JTextArea inventoryArea = new JTextArea();
        inventoryArea.setEditable(false);
        inventoryArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        inventoryArea.setBackground(new Color(43, 43, 43));
        inventoryArea.setForeground(new Color(169, 183, 198));
        JScrollPane invScroll = new JScrollPane(inventoryArea);
        invScroll.setBorder(BorderFactory.createTitledBorder("Hátizsák (Inventori)"));
        tartalomPanel.add(invScroll);

        boltAblak.add(tartalomPanel, BorderLayout.CENTER);

        jarmu.Hokotro finalAktivHokotro = aktivHokotro;
        Runnable frissitBoltAdatok = () -> {
            try {
                kasszaBoltLabel.setText("Jelenlegi Kassza: " + vezerlo.getVarosModell().getKassza().getPenzosszeg() + " ZT");

                gazdasag.Jatekos<?> aktivJ = vezerlo.getAktivJatekos();
                if (aktivJ instanceof gazdasag.Takarito) {
                    gazdasag.Takarito takarito = (gazdasag.Takarito) aktivJ;
                    StringBuilder invText = new StringBuilder();
                    
                    invText.append("\n === ").append(takarito.getNev()).append(" FLOTTÁJA ===\n");

                    for (Object gep : takarito.getJarmuvek()) {
                        if (gep instanceof jarmu.Hokotro) {
                            jarmu.Hokotro h = (jarmu.Hokotro) gep;
                            
                            if (h == takarito.getAktivJarmu()) {
                                invText.append("\n [> ").append(h.getNev()).append(" (Épp Ebben Ülsz) <]\n");
                            } else {
                                invText.append("\n [ ").append(h.getNev()).append(" ]\n");
                            }

                            String aktivFej = "Alap Söprő";
                            if (h.getAktiv() != null) {
                                aktivFej = h.getAktiv().getClass().getSimpleName();
                                
                                if (h.getAktiv() instanceof felszereles.Kotrofej) {
                                    int toltet = ((felszereles.Kotrofej) h.getAktiv()).getToltet(); 
                                    if (toltet > 0 || aktivFej.equals("Soszoro") || aktivFej.equals("Sarkanyfej") || aktivFej.equals("ZuzalekSzoro")) {
                                        aktivFej += " (" + toltet + " egység)";
                                    }
                                }
                            }
                            invText.append("  * Felszerelve: ").append(aktivFej).append("\n");

                            invText.append("  * Raktárban:\n");
                            if (h.getBirtokolja() != null && !h.getBirtokolja().isEmpty()) {
                                for (Object f : h.getBirtokolja().values()) {
                                    String fejNev = f.getClass().getSimpleName();
                                    
                                    if (f instanceof felszereles.Kotrofej) {
                                        int toltet = ((felszereles.Kotrofej) f).getToltet(); 
                                        if (toltet > 0 || fejNev.equals("Soszoro") || fejNev.equals("Sarkanyfej") || fejNev.equals("ZuzalekSzoro")) {
                                            fejNev += " (" + toltet + " egység)";
                                        }
                                    }
                                    invText.append("    - ").append(fejNev).append("\n");
                                }
                            } else {
                                invText.append("    (Üres)\n");
                            }
                        }
                    }
                    inventoryArea.setText(invText.toString());
                } else {
                    inventoryArea.setText("\nNincs aktív takarító.");
                }
                
                boltAblak.getContentPane().revalidate();
                boltAblak.getContentPane().repaint();
                
            } catch (Exception ex) {
                System.out.println("Hiba a bolt frissítésekor: " + ex.getMessage());
            }
        };
        frissitBoltAdatok.run();

        JLabel kivalasztottLabel = new JLabel("Kiválasztva: Még semmi");
        kivalasztottLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        gombPanel.add(createBoltGomb("Hányófej (100 ZT)", gazdasag.Arucikk.HANYOFEJ, "hanyo", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Sószóró (150 ZT)", gazdasag.Arucikk.SOSZORO, "soszoro", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Sárkányfej (300 ZT)", gazdasag.Arucikk.SARKANYFEJ, "sarkanyfej", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Zúzalékszóró (200 ZT)", gazdasag.Arucikk.ZUZALEKSZORO, "zuzalekszoro", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Só [10 egység] (50 ZT)", gazdasag.Arucikk.SO, "so", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Kerozin [10 egység] (100 ZT)", gazdasag.Arucikk.KEROZIN, "kerozin", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Zúzalék [10 egység] (75 ZT)", gazdasag.Arucikk.ZUZALEK, "zuzalek", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Új Hókotró (500 ZT)", gazdasag.Arucikk.HOKOTRO, "hokotro", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Globális Felmelegedés (10000 ZT)", gazdasag.Arucikk.GLOBAL_WARMING, "global_warning", kivalasztottArucikk, kivalasztottLabel));

        JPanel alsoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton megveszGomb = new JButton("Kijelölt Tárgy Megvásárlása");
        megveszGomb.setFont(new Font("Arial", Font.BOLD, 16));
        
        megveszGomb.addActionListener(e -> {
            if (kivalasztottArucikk[0] != null) {
                try {
                    gazdasag.Arucikk mitVeszunk = kivalasztottArucikk[0];
                    
                    if (finalAktivHokotro != null && mitVeszunk != gazdasag.Arucikk.HOKOTRO && mitVeszunk != gazdasag.Arucikk.GLOBAL_WARMING) {
                        boolean vanHanyo = false;
                        boolean vanSoszoro = false;
                        boolean vanSarkanyfej = false;
                        boolean vanZuzalekSzoro = false;
                        int zuzalekSzoroToltet = 0;
                        
                        java.util.List<Object> osszesFej = new java.util.ArrayList<>();
                        if (finalAktivHokotro.getAktiv() != null) {
                            osszesFej.add(finalAktivHokotro.getAktiv());
                        }
                        if (finalAktivHokotro.getBirtokolja() != null) {
                            osszesFej.addAll(finalAktivHokotro.getBirtokolja().values());
                        }
                        
                        for (Object f : osszesFej) {
                            String nev = f.getClass().getSimpleName();
                            if (nev.equals("HanyoFej")) vanHanyo = true;
                            if (nev.equals("Soszoro")) vanSoszoro = true;
                            if (nev.equals("Sarkanyfej")) vanSarkanyfej = true;
                            if (nev.equals("ZuzalekSzoro")) {
                                vanZuzalekSzoro = true;
                                if (f instanceof felszereles.Kotrofej) {
                                    zuzalekSzoroToltet = ((felszereles.Kotrofej) f).getToltet();
                                }
                            }
                        }
                        
                        if (mitVeszunk == gazdasag.Arucikk.HANYOFEJ && vanHanyo) {
                            JOptionPane.showMessageDialog(boltAblak, "Már van Hányófej ezen a gépen!", "Tiltott Vásárlás", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (mitVeszunk == gazdasag.Arucikk.SOSZORO && vanSoszoro) {
                            JOptionPane.showMessageDialog(boltAblak, "Már van Sószóró ezen a gépen!", "Tiltott Vásárlás", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (mitVeszunk == gazdasag.Arucikk.SARKANYFEJ && vanSarkanyfej) {
                            JOptionPane.showMessageDialog(boltAblak, "Már van Sárkányfej ezen a gépen!", "Tiltott Vásárlás", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (mitVeszunk == gazdasag.Arucikk.ZUZALEKSZORO && vanZuzalekSzoro) {
                            JOptionPane.showMessageDialog(boltAblak, "Már van Zúzalékszóró ezen a gépen!", "Tiltott Vásárlás", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        
                        if (mitVeszunk == gazdasag.Arucikk.SO && !vanSoszoro) {
                            JOptionPane.showMessageDialog(boltAblak, "Nincs Sószóró a gépen, amibe a sót tölthetnéd!", "Tiltott Vásárlás", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (mitVeszunk == gazdasag.Arucikk.KEROZIN && !vanSarkanyfej) {
                            JOptionPane.showMessageDialog(boltAblak, "Nincs Sárkányfej a gépen, amibe a kerozint tölthetnéd!", "Tiltott Vásárlás", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if (mitVeszunk == gazdasag.Arucikk.ZUZALEK) {
                            if (!vanZuzalekSzoro) {
                                JOptionPane.showMessageDialog(boltAblak, "Nincs Zúzalékszóró a gépen, amibe a zúzalékot tölthetnéd!", "Tiltott Vásárlás", JOptionPane.WARNING_MESSAGE);
                                return;
                            } else if (zuzalekSzoroToltet >= 10) {
                                JOptionPane.showMessageDialog(boltAblak, "A Zúzalékszóró tartálya már tele van (10 egység)!", "Tele van", JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                        }
                    }

                    vezerlo.vasarol(mitVeszunk, finalAktivHokotro);
                    frissitBoltAdatok.run();
                    frissit();
                    
                    if (mitVeszunk == gazdasag.Arucikk.HOKOTRO) {
                        boltAblak.dispose(); // BEZÁRJUK A BOLTOT, hogy lássuk a térképet!
                        
                        String beirtNev = JOptionPane.showInputDialog(GameWindow.this, "Hogy hívják az új hókotrót?", "Névadás", JOptionPane.PLAIN_MESSAGE);
                        if (beirtNev == null || beirtNev.trim().isEmpty()) { beirtNev = "Hókotró 2000"; }
                        final String finalNev = beirtNev;

                        uzenetKijelzese("Kattints a térképen arra az ÜRES mezőre, ahova le akarod tenni!");
                        
                        varjKattintasra("Kattints az új Hókotró helyére...", 
                            csp -> !csp.foglalt(), // Bárhova leteheted, ami nem foglalt
                            celCsp -> {
                                gazdasag.Takarito aktivTakarito = (gazdasag.Takarito) vezerlo.getAktivJatekos();
                                for (Object gep : aktivTakarito.getJarmuvek()) { 
                                    if (gep instanceof jarmu.Hokotro) {
                                        jarmu.Hokotro h = (jarmu.Hokotro) gep;
                                        if (h.getAktualisCsomopont() == null) {
                                            h.setNev(finalNev); 
                                            if (celCsp.befogad(h)) { h.setAktualisCsomopont(celCsp); }
                                            break; 
                                        }
                                    }
                                }
                                uzenetKijelzese("Hókotró sikeresen lehelyezve!");
                                frissit();
                            });
                        return; // Kilépünk a bolt logikájából, a kattintás majd befejezi!
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(boltAblak, "Kivétel történt: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(boltAblak, "Előbb válassz ki egy árucikket a listából!");
            }
        });

        alsoPanel.add(kivalasztottLabel);
        alsoPanel.add(megveszGomb);
        boltAblak.add(alsoPanel, BorderLayout.SOUTH);

        boltAblak.setVisible(true);
    }

    private JButton createBoltGomb(String szoveg, gazdasag.Arucikk arucikk, String texturanev, gazdasag.Arucikk[] kivalasztott, JLabel label) {
        JButton gomb = new JButton(szoveg);
        Image img = TextureManager.getTexture(texturanev);
        if (img != null) {
            Image resizedImg = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            gomb.setIcon(new ImageIcon(resizedImg));
        }
        gomb.addActionListener(e -> {
            kivalasztott[0] = arucikk;
            label.setText("Kiválasztva: " + szoveg);
        });
        return gomb;
    }

    private void cserelFelszerelest() {
        gazdasag.Jatekos<?> aktiv = vezerlo.getAktivJatekos();
        if (!(aktiv instanceof gazdasag.Takarito)) {
            uzenetKijelzese("Csak a Takarító cserélhet felszerelést!");
            return;
        }

        if (!(aktiv.getAktivJarmu() instanceof jarmu.Hokotro)) {
            uzenetKijelzese("Nincs aktív hókotró kiválasztva!");
            return;
        }

        jarmu.Hokotro hokotro = (jarmu.Hokotro) aktiv.getAktivJarmu();
        
        if (hokotro.getBirtokolja() == null || hokotro.getBirtokolja().isEmpty()) {
            uzenetKijelzese("Nincs más felszerelés a gép raktárában!");
            return;
        }

        java.util.List<String> opciok = new java.util.ArrayList<>();
        for (Object f : hokotro.getBirtokolja().values()) {
            if (hokotro.getAktiv() != f) {
                opciok.add(f.getClass().getSimpleName());
            }
        }
        
        if (opciok.isEmpty()) {
            uzenetKijelzese("Nincs másik felszerelés a gép raktárában, amit feltehetnél!");
            return;
        }

        String[] valaszthatoTomb = opciok.toArray(new String[0]);
        String valasztas = (String) JOptionPane.showInputDialog(
                this,
                "Melyik felszerelést szeretnéd használni a(z) " + hokotro.getNev() + " gépen?",
                "Felszerelés cseréje",
                JOptionPane.QUESTION_MESSAGE,
                null,
                valaszthatoTomb,
                valaszthatoTomb[0]
        );

        if (valasztas != null) {
            felszereles.Kotrofej kivalasztottFej = hokotro.getFej(valasztas);
            if (kivalasztottFej != null) {
                hokotro.cserelFej(kivalasztottFej);
                uzenetKijelzese("Sikeresen felszerelted a következő fejet: " + valasztas);
                frissit(); 
            }
        }
    }

    private void ujSoforHozzaadasa() {
        String beirtNev = JOptionPane.showInputDialog(this, "Hogy hívják az új buszsofőrt?", "Új Sofőr Felvétele", JOptionPane.PLAIN_MESSAGE);
        if (beirtNev == null || beirtNev.trim().isEmpty()) return;

        uzenetKijelzese("Kattints a térképen egy START végállomásra (piros mező)!");

        varjKattintasra("Kattints a START állomásra (Checkpoint)...", 
            csp -> csp instanceof halozat.Checkpoint && !csp.foglalt(), 
            startCsp -> {
                uzenetKijelzese("Most kattints a CÉL állomásra!");
                
                varjKattintasra("Kattints a CÉL állomásra...", 
                    csp -> csp instanceof halozat.Checkpoint && csp != startCsp, 
                    celCsp -> {
                        try {
                            gazdasag.Sofor ujSofor = new gazdasag.Sofor(beirtNev, vezerlo.getVarosModell().getKassza());
                            jarmu.Busz ujBusz = new jarmu.Busz((halozat.Checkpoint) startCsp, (halozat.Checkpoint) celCsp, ujSofor);
                            ujSofor.setJarmu(ujBusz);
                            
                            if (startCsp.befogad(ujBusz)) ujBusz.setAktualisCsomopont(startCsp);
                            
                            vezerlo.addJatekos(ujSofor);
                            vezerlo.registerJatekos("Sofor"); 
                            // --- ÚJ VARÁZSLAT: Azonnal a Buszra ugrik a kör! ---
                            // Ezzel a ciklussal a gép automatikusan befejezi a jelenlegi játékos körét,
                            // és rögtön az új busznak adja az irányítást, Passz gomb megnyomása nélkül.
                            int loopGuard = 0;
                            while(vezerlo.getAktivJatekos() != ujSofor && loopGuard < 20) {
                                vezerlo.nextJatekos();
                                loopGuard++;
                            }
                            
                            uzenetKijelzese("Sikeresen felvetted a buszt! Azonnal indulhatsz is vele!");
                            frissit();
                        } catch (Exception ex) { ex.printStackTrace(); }
                    });
            });
    }

    private void varjKattintasra(String infoSzoveg, java.util.function.Predicate<halozat.Csomopont> validalo, java.util.function.Consumer<halozat.Csomopont> akcio) {
        infoLabel.setText(">>> " + infoSzoveg + " <<<");
        
        // Ha volt előző figyelő, azt levesszük
        if (aktivKattintasKezelo != null) {
            mapPanel.removeMouseListener(aktivKattintasKezelo);
        }

        aktivKattintasKezelo = new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                halozat.Csomopont kattintottCsp = null;
                
                // Megkeressük, melyik mezőre kattintott a játékos
                for (Map.Entry<halozat.Csomopont, Point> entry : nodePositions.entrySet()) {
                    Rectangle rect = new Rectangle(entry.getValue().x, entry.getValue().y, tileSize, tileSize);
                    if (rect.contains(e.getPoint())) {
                        kattintottCsp = entry.getKey();
                        break;
                    }
                }

                if (kattintottCsp != null && validalo.test(kattintottCsp)) {
                    // Ha jó helyre kattintott, levesszük a figyelőt és végrehajtjuk az akciót
                    mapPanel.removeMouseListener(this);
                    aktivKattintasKezelo = null;
                    akcio.accept(kattintottCsp);
                    frissit(); // Visszaállítja a normál infó szöveget
                } else {
                    uzenetKijelzese("Érvénytelen mező! Kérlek a megfelelő helyre kattints.");
                }
            }
        };
        
        mapPanel.addMouseListener(aktivKattintasKezelo);
    }
}