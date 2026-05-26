package allapot;

import halozat.Sav;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

/**
 * A legextrémebb téli útviszonyokat reprezentálja, ahol a normál forgalom
 * teljesen megbénul. Ebben az állapotban csak a speciális járművek 
 * (például hókotrók) képesek közlekedni.
 */
public class MelyHo extends Savallapot {

    /**
     * Konstruktor a MelyHo osztályhoz.
     */
    public MelyHo() {
        SkeletonLogger.create(this);
        SkeletonLogger.exit(this);
    }

    /**
     * Ellenőrzi, hogy a jármű befogadható-e a mély hó sávba.
     * Csak az a jármű léphet rá, amelyik alkalmas mély hóban közlekedni (pl. hókotró).
     * @param sav a sáv, amelybe a jármű szeretne befogadódni
     * @param jarmu a jármű, amely befogadódni szeretne
     * @return true, ha a jármű ráléphet, különben false
     */
    @Override
    public boolean befogad(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", sav, jarmu);
        
        boolean lephet = lepesTeszt(jarmu);
        
        SkeletonLogger.exit(lephet);
        return lephet;
    }

    /**
     * Jármű elengedésének hatásai a mély hó sávból.
     * Ebben az állapotban az elengedésnek nincs speciális mellékhatása.
     * @param sav A sáv, aminek az adott objektum a mély hó állapota
     * @param jarmu A jármű, melyet el akarunk engedni
     */
    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "elenged", sav, jarmu);
        SkeletonLogger.exit("void");
    }

    /**
     * Kezeli a hóesés esetét a mély hó sávon.
     * Mivel már eleve maximális a hóréteg (mély hó), a sáv állapota nem változik tovább.
     * @param sav A sáv, aminek az adott objektum a mély hó állapota
     */
    @Override
    public void hoesesEseten(Sav sav) {
        SkeletonLogger.enter(this, "hoesesEseten", sav);
        SkeletonLogger.exit("void");
    }

   /**
     * Frissíti a mély hó sáv állapotát az idő múlásával (olvadás).
     * A mély hó (3. szintű hóréteg) egy kör után megolvad, és SekélyHó állapotba (2. szintű vastagsággal) lép.
     * @param sav A sáv, aminek az adott objektum a mély hó állapota
     */
    @Override
    public void frissit(Sav sav) {
        SkeletonLogger.enter(this, "frissit", sav);
        
        // Logikai javítás: Mivel a mély hó 3. szintű havat jelent, 
        // egy kör olvadás után SekélyHóvá válik, de annak a vastagságát 
        // logikusan 2-re kell állítani, nem az alapértelmezett 1-re!
        SekelyHo sekelyHo = new SekelyHo();
        sekelyHo.setHoreteg(2); 
        sav.setAllapot(sekelyHo);

        SkeletonLogger.exit("void");
    }

    /**
     * Teszteli, hogy az adott járműnek szabad-e a mély hó sávra lépni.
     * Ehhez a jármű saját képességét (lephetMelyHora) kérdezi le.
     * @param jarmu A vizsgálandó jármű
     * @return true, ha a jármű képes mély hóban közlekedni, egyébként false
     */
    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        SkeletonLogger.enter(this, "lepesTeszt", jarmu);
        
        // Kulcsfontosságú függvény: eldönti, hogy a jármű rá tud-e lépni.
        boolean lephet = jarmu.lephetMelyHora();
        
        SkeletonLogger.exit(lephet);
        return lephet;
    }

    /**
     * Kezeli, ha a sáv sót kap.
     * Mély hóra a sózásnak nincs azonnali hatása.
     * @param sav A sáv, aminek az adott objektum a mély hó állapota
     */
    @Override
    public void sotKap(Sav sav) {
        SkeletonLogger.enter(this, "sotKap", sav);
        SkeletonLogger.exit("void");
    }

    /**
     * Megpróbálja megtisztítani a havat a sávból.
     * Sikeresen tiszta állapotba vált.
     * @param sav A sáv, aminek az adott objektum a mély hó állapota
     * @return true, mivel a hóeltakarítás mindenképp sikeres
     */
    @Override
    public boolean hoTisztit(Sav sav) {
        SkeletonLogger.enter(this, "hoTisztit", sav);

        sav.setAllapot(new Tiszta());

        SkeletonLogger.exit(true);
        return true;
    }

    /**
     * Megpróbálja megtisztítani a jeget a sávból.
     * Mély hóban nincs jég, így nem sikerül.
     * @param sav a tisztítandó sáv
     * @param olvad jelzi, hogy a jeget törjük, vagy olvasztjuk
     * @return false, mivel nincs jég
     */
    @Override
    public boolean jegTisztit(Sav sav,Boolean olvad) {
        SkeletonLogger.enter(this, "jegTisztit", sav, olvad);
        SkeletonLogger.exit(false);
        return false;
    }
    @Override
    public String printStat(String name) {
        return "MelyHo " + name;
    }
}