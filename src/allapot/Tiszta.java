package allapot;

import java.util.Scanner;

import halozat.Sav;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

public class Tiszta extends Savallapot {

    public Tiszta() { /* Konstruktor  */
        
        SkeletonLogger.register(this, "tiszta");
        
    }

    @Override
    public boolean befogad(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", sav, jarmu);
        /*System.out.print("[?] Foglalt a cel sav? (I/N): ");
        java.util.Scanner sc = new java.util.Scanner(System.in);
        String valasz = sc.nextLine();
        
        if (valasz.equalsIgnoreCase("i")) {
            SkeletonLogger.exit(false);
            return false;
        } else {
            SkeletonLogger.exit(true);
            return true;
        }*/
        SkeletonLogger.exit(true);
        return true;
    }

    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "elenged", sav, jarmu);
        // Csak logoljuk a kilépést, tiszta sávnál nincs más dolgunk
        SkeletonLogger.exit("void");
    }

    @Override
    public void hoesesEseten(Sav sav) {
        SkeletonLogger.enter(this, "hoesesEseten", sav);
        SekelyHo sekelyHo = new SekelyHo();
        SkeletonLogger.register(sekelyHo, "sekelyHo");
        sav.setAllapot(sekelyHo);
        SkeletonLogger.exit("void");
    }

    @Override
    public void frissit(Sav sav) {
    }

    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        SkeletonLogger.enter(this, "lepesTeszt", jarmu);
        // Tiszta sávra rá lehet lépni
        SkeletonLogger.exit(true);
        return true;
    }

    @Override
    public void sotKap(Sav sav) {
        SkeletonLogger.enter(this, "sotKap", sav);
        SkeletonLogger.exit("void");
    }

    @Override
    public boolean hoTisztit(Sav sav) {
        SkeletonLogger.enter(this, "hoTisztit", sav);
        SkeletonLogger.exit(false);
        return false;
    }

    @Override
    public boolean jegTisztit(Sav sav) {
        SkeletonLogger.enter(this, "jegTisztit", sav);
        SkeletonLogger.exit(false);
        return false;
    }
}
