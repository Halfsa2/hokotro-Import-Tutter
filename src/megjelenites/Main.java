package megjelenites;
import gazdasag.Bolt;
import gazdasag.KozosKassza;
import gazdasag.Takarito;
import halozat.Sav;
import jarmu.Hokotro;
import vezerles.JatekVezerlo;
import vezerles.VarosModell;

public class Main {
    public static void main(String[] args) {
        TextureManager.loadTextures();

        KozosKassza kassza = new KozosKassza(1000);
        VarosModell modell = new VarosModell(kassza);
        Bolt bolt = new Bolt();

        Sav s1 = new Sav();
        Sav s2 = new Sav();
        Sav s3 = new Sav();
        s2.hoesesEseten(); // Havazzuk be a középsőt

        // FONTOS: Összekötjük a sávokat, hogy a jármű tudjon haladni rajtuk!
        s1.addSzomszed(s2);
        s2.addSzomszed(s3);

        // ... és vissza, hogy tudjunk tolatni a teszt során!
        s2.addSzomszed(s1);
        s3.addSzomszed(s2);

        modell.addCsomopont(s1);
        modell.addCsomopont(s2);
        modell.addCsomopont(s3);


        JatekVezerlo vezerlo = new JatekVezerlo(null, modell, bolt);

        // JÁTÉKOS LÉTREHOZÁSA (Autó helyett Takarítót csinálunk, hogy mi irányíthassunk)
        Takarito takarito = new Takarito("Takarito1", kassza);
        Hokotro hokotro = new Hokotro(takarito);
        takarito.addHokotro(hokotro);
        
        vezerlo.addJatekos(takarito);
        vezerlo.nextJatekos(); // Ő lesz a soron lévő (aktív) játékos

        takarito.nextJarmu();

        s1.befogad(hokotro); // Felrakjuk a pályára az 1. sávra
        hokotro.setAktualisCsomopont(s1); // Beállítjuk a helyzetét

        javax.swing.SwingUtilities.invokeLater(() -> {
            GameWindow ablak = new GameWindow(vezerlo);
            vezerlo.setNezet(ablak);
            ablak.setVisible(true);
            ablak.frissit(); 
        });
    }
}