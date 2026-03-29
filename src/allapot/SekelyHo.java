package allapot;

import java.util.Scanner;

import halozat.Sav;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

public class SekelyHo extends Savallapot {

    protected int horeteg = 1;
    protected int nyomvonal = 0;

    public SekelyHo() {
        SkeletonLogger.create(this);
        SkeletonLogger.register(this, "sekelyHo");
        SkeletonLogger.exit(this);
    }

    public void setHoreteg(int horeteg) {
        this.horeteg = horeteg;
    }

    @Override
    public boolean befogad(Sav sav, Jarmu jarmu) {
        nyomvonal++;
        if (nyomvonal >= 3) {
            sav.setAllapot(new Jeges());
        }
        return true;
    }

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

    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        return true;
        /* Még járható [cite: 1190] */ }

    @Override
    public void sotKap(Sav sav) {
        horeteg--;
        // if (horeteg <= 0) {
        // sav.setAllapot(new Tiszta()); // Só hatására elolvad [cite: 1192]
        // }
    }

    @Override
    public boolean hoTisztit(Sav sav) {
        SkeletonLogger.enter(this, "hoTisztit", sav);

        Tiszta tiszta = new Tiszta();
        SkeletonLogger.register(tiszta, "tiszta1");
        sav.setAllapot(tiszta);

        SkeletonLogger.exit(true);
        return true;
    }

    @Override
    public boolean jegTisztit(Sav sav) {
        SkeletonLogger.enter(this, "jegTisztit", sav);
        SkeletonLogger.exit(false);
        return false;
    }
}
