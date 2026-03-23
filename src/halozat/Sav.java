package halozat;

import allapot.Savallapot;
import allapot.Tiszta; // <-- JAVÍTVA a helyes csomagra!
import jarmu.Jarmu;     // <-- JAVÍTVA: beimportáljuk a Tiszta állapotot is!
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sav extends Csomopont {
    
    private Utszakasz utszakasz;
    private Savallapot allapot;
    private Jarmu jarmu;
    private List<Csomopont> szomszedok = new ArrayList<>();
    protected int sozott = 0;

    // JAVÍTVA: Konstruktor, ami beállítja az alapértelmezett állapotot
    public Sav() {
        this.allapot = new Tiszta();
        this.szomszedok = new ArrayList<>();
    }

    public void setUtszakasz(Utszakasz utszakasz) {
        this.utszakasz = utszakasz;
    }

    public void setAllapot(Savallapot allapot) {
        System.out.println("> sav:Sav.setAllapot(allapot)");
        this.allapot = allapot;
        System.out.println("<- void");
    }

    public Savallapot getAllapot() {
        return this.allapot;
    }

    @Override
    public void frissit() {
        System.out.println("> sav:Sav.frissit()");
        if (this.sozott > 0) {
            this.sozott--; // Sózottság csökkenhet az idő múlásával
        }
        if (allapot != null) {
            allapot.frissit(this);
        }
        System.out.println("<- void");
    }

    // TELL, DON'T ASK LOGIKA
    @Override
    public boolean befogad(Jarmu jarmu) {
        System.out.println("> sav:Sav.befogad(jarmu)");
        if (this.foglalt()) {
            System.out.println("<- false");
            return false; // Foglalt, elutasítjuk a lépést
        }
        this.jarmu = jarmu;
        System.out.println("<- true");
        return true; // Sikeres rálépés
    }

    @Override
    public void elenged(Jarmu jarmu) {
        System.out.println("> sav:Sav.elenged(jarmu)");
        if (this.jarmu == jarmu) {
            this.jarmu = null;
        }
        System.out.println("<- void");
    }

    @Override
    public List<Csomopont> getNext() {
        return szomszedok;
    }

    public boolean lepesTeszt(Jarmu jarmu) {
        System.out.println("> sav:Sav.lepesTeszt(jarmu)");
        boolean teszt = false;
        if (allapot != null) {
            teszt = allapot.lepesTeszt(jarmu);
        }
        System.out.println("<- " + teszt);
        return teszt;
    }

    @Override
    public void balesetEseten() {
        System.out.println("> sav:Sav.balesetEseten()");
        // Baleset logikája
        if (this.jarmu != null) {
            // this.jarmu.balesetetSzenved(); // Ha van ilyen metódus a Jarmu-ben
        }
        System.out.println("<- void");
    }

    @Override
    public boolean foglalt() {
        System.out.println("> sav:Sav.foglalt()");
        boolean isFoglalt = (this.jarmu != null);
        System.out.println("<- " + isFoglalt);
        return isFoglalt;
    }

    // DOUBLE DISPATCH INDÍTÁSA
    @Override
    public void hoesesEseten() {
        System.out.println("> sav:Sav.hoesesEseten()");
        
        // Szkeletonos teszteléshez megkérdezzük a felhasználót
        Scanner scanner = new Scanner(System.in);
        System.out.print("[?] Sózott a sáv? (I/N): ");
        String valasz = scanner.nextLine();
        
        if (valasz.equalsIgnoreCase("N")) {
            if (this.utszakasz != null) {
                // Szólunk az útszakasznak, hogy esik a hó
                this.utszakasz.havazikRa(this);
            }
        }
        System.out.println("<- void");
    }

    public boolean jegTisztit() {
        System.out.println("> sav:Sav.jegTisztit()");
        boolean ret = (allapot != null) && allapot.jegTisztit(this);
        System.out.println("<- " + ret);
        return ret;
    }

    public boolean hoTisztit() {
        System.out.println("> sav:Sav.hoTisztit()");
        boolean ret = (allapot != null) && allapot.hoTisztit(this);
        System.out.println("<- " + ret);
        return ret;
    }

    public void soSzoras() {
        System.out.println("> sav:Sav.soSzoras()");
        if (allapot != null) {
            allapot.sotKap(this);
        }
        System.out.println("<- void");
    }
}