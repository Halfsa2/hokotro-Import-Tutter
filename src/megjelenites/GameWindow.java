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

    public GameWindow(IJatekVezerlo vezerlo) {
        this.vezerlo = vezerlo;
        this.nodePositions = new HashMap<>();
        
        setTitle("Zúzmaraváros - Hókotró Szimulátor");
        setSize(1024, 768);
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
        
        JButton passzButton = new JButton("Passz");
        passzButton.setFocusable(false);
        passzButton.addActionListener(e -> {
            // --- ITT A LÉNYEG! ÁTÜLÜNK A MÁSIK GÉPBE! ---
            gazdasag.Jatekos<?> aktiv = vezerlo.getAktivJatekos();
            if (aktiv != null) {
                aktiv.nextJarmu(); 
            }
            // -------------------------------------------
            vezerlo.nextJatekos();
            frissit();
        });

        controlPanel.add(infoLabel);
        controlPanel.add(kasszaLabel);
        controlPanel.add(boltButton);
        controlPanel.add(passzButton);
        add(controlPanel, BorderLayout.SOUTH);
        
        setupKeyBindings();
    }

    private void setupKeyBindings() {
        InputMap im = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = this.getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        am.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gazdasag.Jatekos<?> aktiv = vezerlo.getAktivJatekos();
                if (aktiv != null && aktiv.getAktivJarmu() != null) {
                    halozat.Csomopont aktualis = aktiv.getAktivJarmu().getAktualisCsomopont();
                    java.util.List<halozat.Csomopont> graf = vezerlo.getVarosModell().getVarosGraf();
                    
                    int index = graf.indexOf(aktualis);
                    if (index < graf.size() - 1) {
                        halozat.Csomopont cel = graf.get(index + 1);
                        vezerlo.lep(cel);
                        frissit();
                    }
                }
            }
        });

        im.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        am.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gazdasag.Jatekos<?> aktiv = vezerlo.getAktivJatekos();
                if (aktiv != null && aktiv.getAktivJarmu() != null) {
                    halozat.Csomopont aktualis = aktiv.getAktivJarmu().getAktualisCsomopont();
                    java.util.List<halozat.Csomopont> graf = vezerlo.getVarosModell().getVarosGraf();
                    
                    int index = graf.indexOf(aktualis);
                    if (index > 0) {
                        halozat.Csomopont cel = graf.get(index - 1);
                        vezerlo.lep(cel);
                        frissit();
                    }
                }
            }
        });
    }

    @Override
    public void frissit() {
        gazdasag.Jatekos<?> aktiv = vezerlo.getAktivJatekos();
        if(aktiv != null) {
            String jatekosNev = aktiv.getNev();
            String jarmuNev = "Nincs gép";
            
            // Lekérjük az aktuális járművet, és ha van neve, kiolvassuk
            if (aktiv.getAktivJarmu() != null && aktiv.getAktivJarmu() instanceof jarmu.Hokotro) {
                jarmuNev = ((jarmu.Hokotro)aktiv.getAktivJarmu()).getNev();
            }
            
            // Így fog kinézni: "Aktív: Takarito1 (Hókotró 2000) "
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
        int tileSize = 64; 
        java.util.List<halozat.Csomopont> graf = vezerlo.getVarosModell().getVarosGraf();
        int startX = 100;
        int startY = 300;
        for (int i = 0; i < graf.size(); i++) {
            halozat.Csomopont csp = graf.get(i);
            nodePositions.put(csp, new java.awt.Point(startX + (i * tileSize), startY));
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

        // --- ÁLLAPOT TÁROLÁSA: Mit jelölt ki a játékos? ---
        final gazdasag.Arucikk[] kivalasztottArucikk = {null};
        
        // --- 1. FELSŐ INFORMÁCIÓS PANEL ---
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        JLabel kasszaBoltLabel = new JLabel();
        kasszaBoltLabel.setFont(new Font("Arial", Font.BOLD, 22));
        infoPanel.add(kasszaBoltLabel);
        boltAblak.add(infoPanel, BorderLayout.NORTH);

        // --- 2. KÖZÉPSŐ PANEL (Bal: Bolt, Jobb: Inventori) ---
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

        // --- 3. ÉLŐ FRISSÍTŐ FUNKCIÓ (HIBATŰRŐ VERZIÓ) ---
        jarmu.Hokotro finalAktivHokotro = aktivHokotro;
        // --- 3. ÉLŐ FRISSÍTŐ FUNKCIÓ (OKOSÍTOTT FLOTTA INVENTORI) ---
        Runnable frissitBoltAdatok = () -> {
            try {
                kasszaBoltLabel.setText("Jelenlegi Kassza: " + vezerlo.getVarosModell().getKassza().getPenzosszeg() + " ZT");

                gazdasag.Jatekos<?> aktivJ = vezerlo.getAktivJatekos();
                if (aktivJ instanceof gazdasag.Takarito) {
                    gazdasag.Takarito takarito = (gazdasag.Takarito) aktivJ;
                    StringBuilder invText = new StringBuilder();
                    
                    invText.append("\n === ").append(takarito.getNev()).append(" FLOTTÁJA ===\n");

                    // Végigmegyünk a játékos ÖSSZES hókotróján!
                    for (Object gep : takarito.getJarmuvek()) {
                        if (gep instanceof jarmu.Hokotro) {
                            jarmu.Hokotro h = (jarmu.Hokotro) gep;
                            
                            // Kiírjuk a gép nevét
                            if (h == takarito.getAktivJarmu()) {
                                invText.append("\n [> ").append(h.getNev()).append(" (Épp Ebben Ülsz) <]\n");
                            } else {
                                invText.append("\n [ ").append(h.getNev()).append(" ]\n");
                            }

                            // AKTÍV FEJ VIZSGÁLATA
                            String aktivFej = "Alap Söprő";
                            if (h.getAktiv() != null) {
                                aktivFej = h.getAktiv().getClass().getSimpleName();
                                
                                // Töltöttség lekérdezése a te friss getToltet() metódusoddal!
                                if (h.getAktiv() instanceof felszereles.Kotrofej) {
                                    int toltet = ((felszereles.Kotrofej) h.getAktiv()).getToltet(); 
                                    if (toltet > 0 || aktivFej.equals("Soszoro") || aktivFej.equals("Sarkanyfej") || aktivFej.equals("ZuzalekSzoro")) {
                                        aktivFej += " (" + toltet + " egység)";
                                    }
                                }
                            }
                            invText.append("  * Felszerelve: ").append(aktivFej).append("\n");

                            // RAKTÁR VIZSGÁLATA (Zseb)
                            invText.append("  * Raktárban:\n");
                            if (h.getBirtokolja() != null && !h.getBirtokolja().isEmpty()) {
                                for (Object f : h.getBirtokolja().values()) {
                                    String fejNev = f.getClass().getSimpleName();
                                    
                                    // Zsebben lévő fej töltöttsége
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

        // --- ALSÓ PANEL ELŐKÉSZÍTÉSE (Kijelölés szövege) ---
        JLabel kivalasztottLabel = new JLabel("Kiválasztva: Még semmi");
        kivalasztottLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        // --- GOMBOK HOZZÁADÁSA ---
        gombPanel.add(createBoltGomb("Hányófej (100 ZT)", gazdasag.Arucikk.HANYOFEJ, "hanyo", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Sószóró (150 ZT)", gazdasag.Arucikk.SOSZORO, "soszoro", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Sárkányfej (300 ZT)", gazdasag.Arucikk.SARKANYFEJ, "sarkanyfej", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Zúzalékszóró (200 ZT)", gazdasag.Arucikk.ZUZALEKSZORO, "zuzalekszoro", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Só [10 egység] (50 ZT)", gazdasag.Arucikk.SO, "so", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Kerozin [10 egység] (100 ZT)", gazdasag.Arucikk.KEROZIN, "kerozin", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Zúzalék [10 egység] (75 ZT)", gazdasag.Arucikk.ZUZALEK, "zuzalek", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Új Hókotró (500 ZT)", gazdasag.Arucikk.HOKOTRO, "hokotro", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Globális Felmelegedés (10000 ZT)", gazdasag.Arucikk.GLOBAL_WARMING, "global_warning", kivalasztottArucikk, kivalasztottLabel));

        // --- 4. ALSÓ PANEL (MEGVESZ GOMB - BIZTONSÁGOS SZÁLKEZELÉSSEL) ---
        JPanel alsoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton megveszGomb = new JButton("Kijelölt Tárgy Megvásárlása");
        megveszGomb.setFont(new Font("Arial", Font.BOLD, 16));
        
        megveszGomb.addActionListener(e -> {
            if (kivalasztottArucikk[0] != null) {
                try {
                    gazdasag.Arucikk mitVeszunk = kivalasztottArucikk[0];
                    
                    // --- 1. JOGOSULTSÁG ÉS DUPLIKÁCIÓ ELLENŐRZÉSE ---
                    // Csak akkor vizsgáljuk a gép tartalmát, ha nem új hókotrót vagy globális felmelegedést vesz
                    // --- 1. JOGOSULTSÁG ÉS DUPLIKÁCIÓ ELLENŐRZÉSE ---
                    // Csak akkor vizsgáljuk a gép tartalmát, ha nem új hókotrót vagy globális felmelegedést vesz
                    if (finalAktivHokotro != null && mitVeszunk != gazdasag.Arucikk.HOKOTRO && mitVeszunk != gazdasag.Arucikk.GLOBAL_WARMING) {
                        boolean vanHanyo = false;
                        boolean vanSoszoro = false;
                        boolean vanSarkanyfej = false;
                        boolean vanZuzalekSzoro = false;
                        
                        // ITT HOZZUK LÉTRE A VÁLTOZÓT, AMIT HIÁNYOLT A RENDSZER:
                        int zuzalekSzoroToltet = 0;
                        
                        // Összeszedjük a gépen lévő aktív fejet és a zsebben lévőket is egy listába
                        java.util.List<Object> osszesFej = new java.util.ArrayList<>();
                        if (finalAktivHokotro.getAktiv() != null) {
                            osszesFej.add(finalAktivHokotro.getAktiv());
                        }
                        if (finalAktivHokotro.getBirtokolja() != null) {
                            osszesFej.addAll(finalAktivHokotro.getBirtokolja().values());
                        }
                        
                        // Megnézzük, mijünk van már meg, és mennyi ZÚZALÉK van benne!
                        for (Object f : osszesFej) {
                            String nev = f.getClass().getSimpleName();
                            if (nev.equals("HanyoFej")) vanHanyo = true;
                            if (nev.equals("Soszoro")) vanSoszoro = true;
                            if (nev.equals("Sarkanyfej")) vanSarkanyfej = true;
                            
                            if (nev.equals("ZuzalekSzoro")) {
                                vanZuzalekSzoro = true;
                                // Itt olvassuk ki a töltetet, hogy később vizsgálhassuk!
                                if (f instanceof felszereles.Kotrofej) {
                                    zuzalekSzoroToltet = ((felszereles.Kotrofej) f).getToltet();
                                }
                            }
                        }
                        
                        // --- A) Duplikált felszerelés tiltása ---
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
                        
                        // --- B) Töltőanyag tiltása megfelelő fej nélkül (Zúzaléknál limit is) ---
                        if (mitVeszunk == gazdasag.Arucikk.SO) {
                            if (!vanSoszoro) {
                                JOptionPane.showMessageDialog(boltAblak, "Nincs Sószóró a gépen, amibe a sót tölthetnéd!", "Tiltott Vásárlás", JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                        }
                        if (mitVeszunk == gazdasag.Arucikk.KEROZIN) {
                            if (!vanSarkanyfej) {
                                JOptionPane.showMessageDialog(boltAblak, "Nincs Sárkányfej a gépen, amibe a kerozint tölthetnéd!", "Tiltott Vásárlás", JOptionPane.WARNING_MESSAGE);
                                return;
                            }
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

                    // --- 2. VÁSÁRLÁS VÉGREHAJTÁSA (Ha idáig eljutott, minden rendben) ---
                    vezerlo.vasarol(mitVeszunk, finalAktivHokotro);
                    
                    // --- 3. ÚJ HÓKOTRÓ ELHELYEZÉSE ÉS ELNEVEZÉSE ---
                    if (mitVeszunk == gazdasag.Arucikk.HOKOTRO) {
                        
                        String beirtNev = JOptionPane.showInputDialog(boltAblak, "Hogy hívják az új hókotrót?", "Névadás", JOptionPane.PLAIN_MESSAGE);
                        if (beirtNev == null || beirtNev.trim().isEmpty()) { beirtNev = "Hókotró 2000"; }

                        java.util.List<halozat.Csomopont> graf = vezerlo.getVarosModell().getVarosGraf();
                        java.util.List<String> szabadNevek = new java.util.ArrayList<>();
                        java.util.List<halozat.Csomopont> szabadCsomopontok = new java.util.ArrayList<>();
                        
                        for (int i = 0; i < graf.size(); i++) {
                            halozat.Csomopont csp = graf.get(i);
                            if (!csp.foglalt()) { 
                                szabadNevek.add("Csomópont " + i);
                                szabadCsomopontok.add(csp);
                            }
                        }
                        
                        if (szabadNevek.isEmpty()) {
                            JOptionPane.showMessageDialog(boltAblak, "Nincs szabad hely a pályán az új gépnek!", "Hiba", JOptionPane.ERROR_MESSAGE);
                        } else {
                            String[] valaszthatoTomb = szabadNevek.toArray(new String[0]);
                            String valasztas = (String) JOptionPane.showInputDialog(boltAblak,
                                    "Hova szeretnéd letenni a(z) " + beirtNev + " nevű gépet?", "Új Hókotró Elhelyezése",
                                    JOptionPane.QUESTION_MESSAGE, null, valaszthatoTomb, valaszthatoTomb[0]);
                                    
                            if (valasztas != null) {
                                int valasztottIndex = szabadNevek.indexOf(valasztas);
                                halozat.Csomopont celCsomopont = szabadCsomopontok.get(valasztottIndex);
                                
                                gazdasag.Takarito aktivTakarito = (gazdasag.Takarito) vezerlo.getAktivJatekos();
                                for (Object gep : aktivTakarito.getJarmuvek()) { 
                                    if (gep instanceof jarmu.Hokotro) {
                                        jarmu.Hokotro h = (jarmu.Hokotro) gep;
                                        if (h.getAktualisCsomopont() == null) {
                                            h.setNev(beirtNev); 
                                            if (celCsomopont.befogad(h)) { h.setAktualisCsomopont(celCsomopont); }
                                            break; 
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // ----------------------------------------------
                    
                    // 2. GUI frissítése biztonságos szálon
                    SwingUtilities.invokeLater(() -> {
                        frissit(); 
                        frissitBoltAdatok.run(); 
                        
                        kivalasztottArucikk[0] = null;
                        kivalasztottLabel.setText("Vásárlás sikeres! Válassz következőt...");
                    });
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
}