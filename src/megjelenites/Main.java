package megjelenites;

import gazdasag.Bolt;
import gazdasag.KozosKassza;
import vezerles.JatekVezerlo;
import vezerles.VarosModell;

public class Main {
    public static void main(String[] args) {
        // 1. Textúrák betöltése
        TextureManager.loadTextures();

        // 2. Fő rendszerek inicializálása (1000 ZT kezdőtőkével a közös kasszában)
        KozosKassza kassza = new KozosKassza(1000);
        VarosModell modell = new VarosModell(kassza);
        Bolt bolt = new Bolt();

        // Inicializáljuk a vezérlőt (a nézetet még null-ra hagyjuk, mert a GameWindow később jön létre)
        JatekVezerlo vezerlo = new JatekVezerlo(null, modell, bolt);

        // 3. A VÁROS FELÉPÍTÉSE (Létrehozza a 30x35-ös rácsot, utakat, kereszteződéseket és az AI autókat)
        vezerlo.initJatek();

        // FONTOS: Kivettük a fix játékos regisztrációt és a "Kezdő Hókotró" bedrótozását,
        // mert ezt a felugró ablakok fogják elintézni!

        // 4. Grafikus felület indítása és a dinamikus beállítás elindítása
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameWindow ablak = new GameWindow(vezerlo);
            vezerlo.setNezet(ablak);
            
            // Megjelenítjük a játékteret
            ablak.setVisible(true);
            ablak.frissit(); 
            
            // --- AZ ÚJ VARÁZSLAT ---
            // Amint megnyílik az ablak, azonnal elindul a láncolt kérdezőrendszer:
            // Hány játékos? -> Nevek? -> Szerepek? -> Kattints a térképre a lehelyezéshez!
            ablak.inditJatekBeallitas();
        });
    }
}