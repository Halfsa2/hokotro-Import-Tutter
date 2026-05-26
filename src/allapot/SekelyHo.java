package allapot;

import halozat.Sav;
import jarmu.Jarmu;
import vezerles.SkeletonLogger;

/**
 * A sekély hó állapotát reprezentáló osztály.
 * Ez az állapot azt jelzi, hogy a sávon van egy kevés hó, de még járható.
 * Ha sok jármű halad át rajta, jéggé tömörödhet, további havazás esetén pedig mély hóvá válhat.
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
     * Jelzi, hogy van-e zúzalék a sávon, ami megakadályozza a hó jéggé tömörödését.
     */
    private boolean zuzalekos = false; 
    
    /**
     * Konstruktor a SekelyHo osztályhoz. Létrehozáskor naplózza a létrejöttét.
     */
    public SekelyHo() {
        SkeletonLogger.create(this);
        SkeletonLogger.exit(this);
    }

    /**
     * Beállítja a hóréteg vastagságát.
     * @param horeteg Az új hóréteg vastagsága
     */
    public void setHoreteg(int horeteg) {
        this.horeteg = horeteg;
    }

    /**
     * Ellenőrzi, hogy a jármű befogadható-e a sekély hó sávba.
     * Minden jármű ráléphet a sekély hóra.
     * @param sav A sáv, aminek az adott objektum a sekély hó állapota
     * @param jarmu A jármű, ami a sávra akar lépni
     * @return true, mivel a sekély hóba minden jármű befogadható
     */
    @Override
    public boolean befogad(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", sav, jarmu);
        SkeletonLogger.exit(true);
        return true;
    }

    /**
     * Jármű elengedésének hatásai a sekély hó sávból.
     * Ha nincs a sávon zúzalék, a jármű kerekei letapossák a havat (nyomvonal nő).
     * Ha a nyomvonal eléri a 3-at, a hó jéggé tömörödik (Jeges állapotba vált).
     * @param sav A sáv, aminek az adott objektum a sekély hó állapota
     * @param jarmu A jármű, melyet el akarunk engedni
     */
    @Override
    public void elenged(Sav sav, Jarmu jarmu) {
        SkeletonLogger.enter(this, "elenged", sav, jarmu);
        if(!zuzalekos){
            this.nyomvonal++; // A jármű kerekei letapossák a havat
            if (this.nyomvonal >= 3) {
                sav.setAllapot(new Jeges()); // Jéggé tömörödik
            }
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Kezeli a hóesés esetét a sekély hó sávon.
     * Növeli a hóréteget, és ha az eléri a 3-at, a sáv mély hó (MelyHo) állapotba vált.
     * @param sav A sáv, aminek az adott objektum a sekély hó állapota
     */
    @Override
    public void hoesesEseten(Sav sav) {
        SkeletonLogger.enter(this, "hoesesEseten", sav);
        
        this.horeteg++;
        if (this.horeteg >= 3) {
            sav.setAllapot(new MelyHo());
        }

        SkeletonLogger.exit("void");
    }
    /**
     * Frissíti a sekély hó sáv állapotát (Só hatásának szimulálása).
     * Csökkenti a hóréteget, és ha elfogy (0 vagy kevesebb), a sáv Tiszta állapotba kerül.
     * @param sav A sáv, aminek az adott objektum a sekély hó állapota
     */
    @Override
    public void frissit(Sav sav) {
        SkeletonLogger.enter(this, "frissit", sav);
        // A napos idő szimulálása
        this.horeteg--;
        if (this.horeteg <= 0) {
            sav.setAllapot(new Tiszta());
        }
        
        SkeletonLogger.exit("void");
    }
    /**
     * Teszteli, hogy az adott járműnek szabad-e a sekély hó sávra lépni.
     * @param jarmu A vizsgálandó jármű
     * @return true, mivel a sekély hó nem akadályozza meg a járművek rálépését
     */
    @Override
    public boolean lepesTeszt(Jarmu jarmu) {
        SkeletonLogger.enter(this, "lepesTeszt", jarmu);
        SkeletonLogger.exit(true);
        return true; 
    }
    /**
     * Kezeli, ha a sáv sót kap.
     * Sekély hó esetén a sózásnak nincs azonnali, állapotot megváltoztató hatása.
     * @param sav A sáv, aminek az adott objektum a sekély hó állapota
     */
    @Override
    public void sotKap(Sav sav) {
        SkeletonLogger.enter(this, "sotKap", sav);
        SkeletonLogger.exit("void");
    }
    /**
     * Megpróbálja megtisztítani a havat a sávról
     * A tisztítás hatására a sáv Tiszta állapotba kerül.
     * @param sav A sáv, aminek az adott objektum a sekély hó állapota
     * @return true, mivel a hóeltakarítás sikeres
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
     * Mivel ez az állapot sekély hó (és nem jég), a jégtisztítás nem értelmezett.
     * @param sav A tisztítandó sáv
     * @param olvad Jelzi, hogy a jeget olvasztjuk (true) vagy törjük (false)
     * @return false, mivel nincs jég, amit le lehetne takarítani
     */
    @Override
    public boolean jegTisztit(Sav sav, Boolean olvad) {
        SkeletonLogger.enter(this, "jegTisztit", sav, olvad);
        SkeletonLogger.exit(false);
        return false;
    }
    @Override
    public String printStat(String name) {
        return "SekelyHo " + name + ": horeteg=" + this.horeteg + ", nyomvonal=" + this.nyomvonal + ", zuzalekos=" + this.zuzalekos;
    }
    /**
     * Kezeli azt az esetet, amikor a sávot zúzalékkal szórják fel.
     * A zúzalék megvédi a sávot a letaposástól és eltünteti a korábbi nyomvonalat.
     */
    @Override
    public void zuzalekSzoras() {
        SkeletonLogger.enter(this, "zuzalekSzoras");
        this.zuzalekos = true; // A zúzalék szórása megvédi a sávot a letaposástól
        this.nyomvonal = 0; // A zúzalék szórása eltünteti a nyomvonalat
        SkeletonLogger.exit("void");
    }
    /**
     * Megpróbálja eltakarítani a zúzalékot a sávról.
     * @param sav A sáv, aminek az adott objektum a sekély hó állapota
     * @return true, ha volt zúzalék és sikerült eltávolítani, egyébként false
     */
    @Override
    public boolean zuzalekTisztit(Sav sav) {
        SkeletonLogger.enter(this, "zuzalekTisztit", sav);
        if(zuzalekos){
            zuzalekos = false; // A zúzalék eltávolítása lehetővé teszi a letaposást
            SkeletonLogger.exit(true);
            return true;
        }
        SkeletonLogger.exit(false);
        return false; // Nem volt zúzalék, így nem történt tisztítás
    }



    /**
     * CSAK A PROTOTÍPUS CSALÓ PARANCSÁNAK HASZNÁLATÁHOZ!
     * Beállítja a sávon található nyomvonalak számát.
     * @param nyomvonal A beállítani kívánt nyomvonal értéke
     */
    public void setNyomvonal(int nyomvonal) {
        this.nyomvonal = nyomvonal;
    }
}