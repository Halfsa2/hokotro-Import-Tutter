package megjelenites;

import halozat.Csomopont;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import vezerles.IJatekVezerlo;

public class GameWindow extends JFrame implements IJatekNezet {
    private IJatekVezerlo vezerlo;
    private MapPanel mapPanel; // Itt hivatkozunk az új osztályunkra!
    private Map<Csomopont, Point> nodePositions; // A koordináták tárolója
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

        // 1. Koordináták kiosztása a modell csomópontjainak
        setupCoordinates();

        // 2. MapPanel példányosítása és hozzáadása az ablakhoz
        mapPanel = new MapPanel(vezerlo, nodePositions);
        add(mapPanel, BorderLayout.CENTER);

        // 3. Alsó vezérlőpult (Control Panel) hozzáadása
        JPanel controlPanel = new JPanel();
        infoLabel = new JLabel("Aktív játékos: - ");
        kasszaLabel = new JLabel("Kassza: 0 ZT ");

        JButton boltButton = new JButton("Bolt megnyitása");
        boltButton.setFocusable(false); // Nagyon fontos, hogy ne lopja el a fókuszt a billentyűzettől!
        boltButton.addActionListener(e -> megnyitBolt());
        
        JButton passzButton = new JButton("Kör vége / Passz");
        passzButton.setFocusable(false); // Fontos, hogy ne lopja el a fókuszt a billentyűzettől!
        passzButton.addActionListener(e -> {
            vezerlo.nextJatekos();
            frissit();
        });

        controlPanel.add(infoLabel);
        controlPanel.add(kasszaLabel);
        controlPanel.add(boltButton);
        controlPanel.add(passzButton);
        add(controlPanel, BorderLayout.SOUTH);
        
        // Billentyűzet figyelésének beállítása
        setupKeyBindings();
    }

    private void setupKeyBindings() {
        InputMap im = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = this.getRootPane().getActionMap();

        // JOBBRA LÉPÉS
        im.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        am.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gazdasag.Jatekos<?> aktiv = vezerlo.getAktivJatekos();
                if (aktiv != null && aktiv.getAktivJarmu() != null) {
                    halozat.Csomopont aktualis = aktiv.getAktivJarmu().getAktualisCsomopont();
                    java.util.List<halozat.Csomopont> graf = vezerlo.getVarosModell().getVarosGraf();
                    
                    int index = graf.indexOf(aktualis);
                    if (index < graf.size() - 1) { // Ha van tőle jobbra lévő elem
                        halozat.Csomopont cel = graf.get(index + 1);
                        vezerlo.lep(cel); // A modell úgyis validálja, hogy létezik-e az út!
                        frissit();
                    }
                }
            }
        });

        // BALRA LÉPÉS
        im.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        am.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                gazdasag.Jatekos<?> aktiv = vezerlo.getAktivJatekos();
                if (aktiv != null && aktiv.getAktivJarmu() != null) {
                    halozat.Csomopont aktualis = aktiv.getAktivJarmu().getAktualisCsomopont();
                    java.util.List<halozat.Csomopont> graf = vezerlo.getVarosModell().getVarosGraf();
                    
                    int index = graf.indexOf(aktualis);
                    if (index > 0) { // Ha van tőle balra lévő elem
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
        if(vezerlo.getAktivJatekos() != null) {
            infoLabel.setText("Aktív: " + vezerlo.getAktivJatekos().getClass().getSimpleName() + " ");
        }
        if(vezerlo.getVarosModell().getKassza() != null) {
            kasszaLabel.setText("Kassza: " + vezerlo.getVarosModell().getKassza().getPenzosszeg() + " ZT ");
        }
        mapPanel.repaint();
    }

     private void setupCoordinates() {
        nodePositions.clear();
        int tileSize = 64; // A képeitek mérete
        
        // Lekérjük a modellből a pályát
        java.util.List<halozat.Csomopont> graf = vezerlo.getVarosModell().getVarosGraf();
        
        int startX = 100; // Bal margó
        int startY = 300; // Fentről lefelé pozíció
        
        // Végigmegyünk a listán és egymás mellé rakjuk őket
        for (int i = 0; i < graf.size(); i++) {
            halozat.Csomopont csp = graf.get(i);
            nodePositions.put(csp, new java.awt.Point(startX + (i * tileSize), startY));
        }
    }
    
    @Override
    public void uzenetKijelzese(String uzenet) {
        // Egy felugró ablakban megjelenítjük a játékosnak szánt üzenetet
        JOptionPane.showMessageDialog(this, uzenet, "Üzenet", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void jatekVege(String eredmeny) {
        // Felugró ablak a játék végén (pl. Global Warming megvásárlásakor)
        JOptionPane.showMessageDialog(this, eredmeny, "Játék Vége!", JOptionPane.WARNING_MESSAGE);
    }
    // ... Többi implementált metódus (uzenetKijelzese, jatekVege) ...

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
        // Egy 1 elemű tömbbel oldjuk meg, hogy a gombok módosíthassák az értékét
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

        // --- 3. ÉLŐ FRISSÍTŐ FUNKCIÓ ---
        jarmu.Hokotro finalAktivHokotro = aktivHokotro;
        Runnable frissitBoltAdatok = () -> {
            kasszaBoltLabel.setText("Jelenlegi Kassza: " + vezerlo.getVarosModell().getKassza().getPenzosszeg() + " ZT");

            if (finalAktivHokotro != null) {
                StringBuilder invText = new StringBuilder();
                invText.append("\n === FELSZERELÉSEK ===\n\n");

                String aktivFej = finalAktivHokotro.getAktiv() != null ? finalAktivHokotro.getAktiv().getClass().getSimpleName() : "Nincs (Alap Söprő)";
                invText.append(" [*] Felszerelve: ").append(aktivFej).append("\n\n");

                invText.append(" === RAKTÁRBAN (Zsebben) ===\n\n");
                if (finalAktivHokotro.getBirtokolja() != null && !finalAktivHokotro.getBirtokolja().isEmpty()) {
                    for (felszereles.Kotrofej f : finalAktivHokotro.getBirtokolja().values()) {
                        invText.append("  - ").append(f.getClass().getSimpleName()).append("\n");
                    }
                } else {
                    invText.append("  (Nincs extra felszerelés)\n");
                }
                inventoryArea.setText(invText.toString());
            } else {
                inventoryArea.setText("\nNincs aktív hókotró.");
            }
        };

        frissitBoltAdatok.run(); 

        // --- ALSÓ PANEL ELŐKÉSZÍTÉSE (Kijelölés szövege) ---
        JLabel kivalasztottLabel = new JLabel("Kiválasztva: Még semmi");
        kivalasztottLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        // --- GOMBOK HOZZÁADÁSA ---
        // Figyeld meg: most a 'kivalasztottArucikk' memóriát és a 'kivalasztottLabel'-t is átadjuk!
        gombPanel.add(createBoltGomb("Hányófej (100 ZT)", gazdasag.Arucikk.HANYOFEJ, "hanyo", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Sószóró (150 ZT)", gazdasag.Arucikk.SOSZORO, "soszoro", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Sárkányfej (300 ZT)", gazdasag.Arucikk.SARKANYFEJ, "sarkanyfej", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Zúzalékszóró (200 ZT)", gazdasag.Arucikk.ZUZALEKSZORO, "zuzalekszoro", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Só [10 egység] (50 ZT)", gazdasag.Arucikk.SO, "so", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Kerozin [10 egység] (100 ZT)", gazdasag.Arucikk.KEROZIN, "kerozin", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Zúzalék [10 egység] (75 ZT)", gazdasag.Arucikk.ZUZALEK, "zuzalek", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Új Hókotró (500 ZT)", gazdasag.Arucikk.HOKOTRO, "hokotro", kivalasztottArucikk, kivalasztottLabel));
        gombPanel.add(createBoltGomb("Globális Felmelegedés (10000 ZT)", gazdasag.Arucikk.GLOBAL_WARMING, "global_warning", kivalasztottArucikk, kivalasztottLabel));

        // --- 4. ALSÓ PANEL (MEGVESZ GOMB) ---
        JPanel alsoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton megveszGomb = new JButton("Kijelölt Tárgy Megvásárlása");
        megveszGomb.setFont(new Font("Arial", Font.BOLD, 16));
        
        megveszGomb.addActionListener(e -> {
            if (kivalasztottArucikk[0] != null) {
                // 1. Lefuttatjuk a vásárlást a modellben
                vezerlo.vasarol(kivalasztottArucikk[0], finalAktivHokotro);
                
                // 2. Frissítjük a fő ablakot (a hátteret)
                frissit(); 
                
                // 3. KRITIKUS LÉPÉS: Frissítjük a Bolt ablakának saját kijelzőit is!
                // Ez fogja újra lekérni a pénzt és az inventorit, majd beírni a JTextArea-ba.
                frissitBoltAdatok.run(); 
                
                // Opcionális: Visszaállítjuk a kijelölést, hogy látszódjon a sikeres vétel
                kivalasztottArucikk[0] = null;
                kivalasztottLabel.setText("Vásárlás sikeres! Válassz következőt...");
            } else {
                JOptionPane.showMessageDialog(boltAblak, "Előbb válassz ki egy árucikket a listából!");
            }
        });

        alsoPanel.add(kivalasztottLabel);
        alsoPanel.add(megveszGomb);
        boltAblak.add(alsoPanel, BorderLayout.SOUTH);

        boltAblak.setVisible(true);
    }

    // --- AZ ÚJRAÍRT SEGÉDMETÓDUS ---
    private JButton createBoltGomb(String szoveg, gazdasag.Arucikk arucikk, String texturanev, gazdasag.Arucikk[] kivalasztott, JLabel label) {
        JButton gomb = new JButton(szoveg);
        
        Image img = TextureManager.getTexture(texturanev);
        if (img != null) {
            Image resizedImg = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            gomb.setIcon(new ImageIcon(resizedImg));
        }

        // Ez a gomb már NEM vásárol! Csak eltárolja a döntést, és kiírja alulra!
        gomb.addActionListener(e -> {
            kivalasztott[0] = arucikk; // Eltároljuk a választást
            label.setText("Kiválasztva: " + szoveg); // Kiírjuk a Megvesz gomb mellé
        });
        
        return gomb;
    }
}