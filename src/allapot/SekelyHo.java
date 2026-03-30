package allapot;

import halozat.Sav;
import jarmu.Jarmu;
import java.util.Scanner;
import vezerles.SkeletonLogger;

/**
 * A sekély hó állapotát reprezentáló osztály.
 * Ez az állapot azt jelzi, hogy a sávon van egy kevés hó, de még járható.
 */
public class SekelyHo extends Savallapot {

    /**
     * A hórétegek száma a sávon.
     */
    protected int horeteg = 1;

    /**
     * A járművek által hagyott nyomvonalak száma.
     */
    protected int nyomvonal = 0;

    /**
     * Konstruktor a SekelyHo osztályhoz.
     * Inicializálja a sekély hó állapotot és regisztrálja a SkeletonLogger-ben.
     */
    public SekelyHo() {
        SkeletonLogger.create(this);
        SkeletonLogger.register(this, "sekelyHo");
        SkeletonLogger.exit(this);
    }

    /**
     * Beállítja a hórétegek számát.
     * @param horeteg az új hórétegek száma
     */
    public void setHoreteg(int horeteg) {
        this.horeteg = horeteg;
    }

    /**
     * Ellenőrzi, hogy a jármű befogadható-e a sekély hó sávba.
     * Növeli a nyomvonalat, és ha 3 vagy több, jeges állapotba vált.
     * @param sav a sáv, amelybe a jármű szeretne befogadódni
     * @param jarmu a jármű, amely befogadódni szeretne
     * @return mindig true
     */
    @Override
    public boolean befogad(Sav sav, Jarmu jarmu) {
        nyomvonal++;
        if (nyomvonal >= 3) {
            sav.setAllapot(new Jeges());
        }
        return true;
    }

    /**
     * Elengedi a járművet a sekély hó sávból.
     * Növeli a nyomvonalat, és ha 3 vagy több, jeges állapotba vált.
     * @param sav a sáv, amelyből a jármű elengedésre kerül
     * @param jarmu a jármű, amely elengedésre kerül
     */
    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "elenged", jarmu);
        System.out.println("                > sekelyho:SekelyHo.nyomvonal++");
        this.nyomvonal++;
        System.out.print("                [?] nyomvonal >= 3? (I/N): ");
        Scanner sc = new Scanner(System.in);
        Jeges ujJeges = null;
        if (sc.nextLine().equalsIgnoreCase("I")) {
            ujJeges = new Jeges();
            SkeletonLogger.register(ujJeges, "jeges");
        }
        SkeletonLogger.exit("void");
        if (ujJeges != null) {
            sav.setAllapot(ujJeges);
        }
    }

    /**
     * Kezeli a hóesés esetét a sekély hó sávon.
     * Növeli a hóréteget, és ha 3 vagy több, mély hó állapotba vált.
     * @param sav a sáv, amelyen hóesés történik
     */
    @Override
    public void hoesesEseten(Sav sav) {
        SkeletonLogger.enter(this, "hoesesEseten", sav);
        System.out.println("                > sekelyho:SekelyHo.horeteg++");
        this.horeteg++;
        System.out.print("            [?] horeteg >= 3?: ");
        Scanner sc = new Scanner(System.in);
        MelyHo mh = null;
        if (sc.nextLine().equalsIgnoreCase("I")) {
            mh = new MelyHo();
            SkeletonLogger.register(mh, "melyho");
        }
        SkeletonLogger.exit("void");
        if (mh != null) {
            sav.setAllapot(mh);
        }

    }

    /**
     * Frissíti a sekély hó sáv állapotát.
     * Csökkenti a hóréteget, és ha 0 vagy kevesebb, tiszta állapotba vált.
     * @param sav a frissítendő sáv
     */
    @Override
    public void frissit(Sav sav) {
        SkeletonLogger.enter(this, "frissit", sav);
        System.out.println("                > sekelyHo:SekelyHo.horeteg--");
        this.horeteg--;
        if (horeteg <= 0) {
            Tiszta tiszta = new Tiszta();
            SkeletonLogger.register(tiszta, "tiszta");
            sav.setAllapot(tiszta);
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Teszteli, hogy a jármű ráléphet-e a sekély hó sávra.
     * Mindig lehetséges.
     * @param jarmu a jármű, amely tesztelni szeretne
     * @return mindig true
     */
    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        return true;
        /* Még járható [cite: 1190] */ }

    /**
     * Kezeli, ha a sáv sót kap.
     * Csökkenti a hóréteget.
     * @param sav a sáv, amely sót kap
     */
    @Override
    public void sotKap(Sav sav) {
        horeteg--;
        // if (horeteg <= 0) {
        // sav.setAllapot(new Tiszta()); // Só hatására elolvad [cite: 1192]
        // }
    }

    /**
     * Megpróbálja megtisztítani a havat a sávból.
     * Sikeresen tiszta állapotba vált.
     * @param sav a tisztítandó sáv
     * @return true, mivel sikerül
     */
    @Override
    public boolean hoTisztit(Sav sav) {
        SkeletonLogger.enter(this, "hoTisztit", sav);

        Tiszta tiszta = new Tiszta();
        SkeletonLogger.register(tiszta, "tiszta1");
        sav.setAllapot(tiszta);

        SkeletonLogger.exit(true);
        return true;
    }

    /**
     * Megpróbálja megtisztítani a jeget a sávból.
     * Sekély hóban nincs jég, így nem sikerül.
     * @param sav a tisztítandó sáv
     * @return false, mivel nincs jég
     */
    @Override
    public boolean jegTisztit(Sav sav) {
        SkeletonLogger.enter(this, "jegTisztit", sav);
        SkeletonLogger.exit(false);
        return false;
    }
}
