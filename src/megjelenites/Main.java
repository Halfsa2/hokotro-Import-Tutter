package megjelenites;

import gazdasag.Bolt;
import gazdasag.KozosKassza;
import gazdasag.Takarito;
import halozat.Csomopont;
import jarmu.Hokotro;
import java.util.List;
import vezerles.JatekVezerlo;
import vezerles.VarosModell;

public class Main {
    public static void main(String[] args) {
        // 1. Textúrák betöltése
        TextureManager.loadTextures();

        // 2. Fő rendszerek inicializálása
        KozosKassza kassza = new KozosKassza(1000);
        VarosModell modell = new VarosModell(kassza);
        Bolt bolt = new Bolt();

        JatekVezerlo vezerlo = new JatekVezerlo(null, modell, bolt);

        // 3. Játékosok regisztrálása (1 db Takarító)
        vezerlo.registerJatekos("Takarito");

        // 4. A VÁROS FELÉPÍTÉSE (Kereszteződés, AI autók, Start/Cél pontok)
        vezerlo.initJatek();

        // 5. Kezdőcsomag a Takarítónak (adunk egy hókotrót, hogy ne üres kézzel induljon)
        gazdasag.Jatekos<?> aktiv = vezerlo.getAktivJatekos();
        if (aktiv instanceof Takarito) {
            Takarito takarito = (Takarito) aktiv;
            Hokotro kezdoGep = new Hokotro(takarito);
            kezdoGep.setNev("Kezdő Hókotró");
            takarito.addHokotro(kezdoGep);
            takarito.nextJarmu(); // Beültetjük a gépbe

            // Megkeresünk egy üres Checkpointot a pályán, és letesszük oda
            List<Csomopont> graf = modell.getVarosGraf();
            for (Csomopont csp : graf) {
                if (csp instanceof halozat.Checkpoint && !csp.foglalt()) {
                    if (csp.befogad(kezdoGep)) {
                        kezdoGep.setAktualisCsomopont(csp);
                    }
                    break;
                }
            }
        }

        // 6. Grafikus felület indítása
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameWindow ablak = new GameWindow(vezerlo);
            vezerlo.setNezet(ablak);
            ablak.setVisible(true);
            ablak.frissit(); 
        });
    }
}