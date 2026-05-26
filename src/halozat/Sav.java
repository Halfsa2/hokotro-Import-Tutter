package halozat;

import allapot.Savallapot;
import allapot.Tiszta;
import jarmu.Jarmu;
import java.util.ArrayList;
import java.util.List;
import static prototipus.CommandInterpreter.reverseNevTar;
import vezerles.SkeletonLogger;

/**
 * A városi hálózat egy sávja, amelyben járművek haladhatnak, és állapotkezelést biztosít.
 * Felelős a rajta tartózkodó jármű nyilvántartásáért, az aktuális útviszonyok (Savallapot) 
 * kezeléséért, valamint a sózás és zúzalékszórás hatásainak követéséért.
 */
public class Sav extends Csomopont {
    /** Az útszakasz, amelyhez ez a sáv tartozik. */
    private Utszakasz utszakasz;
    /** A sáv aktuális fizikai állapota (pl. Tiszta, Jeges, SekelyHo). */
    private Savallapot allapot;
    /** A sávon jelenleg tartózkodó jármű (ha van). */
    private Jarmu jarmu;
    /** A sávból közvetlenül elérhető (következő) csomópontok listája. */
    private List<Csomopont> szomszedok = new ArrayList<>();
    
    // Privátra véve, Getter/Setter használatával a csomagok közti átjárhatóságért
    /** A sáv sózottságának idejét jelző számláló. */
    private int sozott = 0;
    /** A sáv zúzalékosságának idejét jelző számláló. */
    private int zuzalekos = 0;

    /**
     * Konstruktor, inicializálja a sáv alapértelmezett állapotát és szomszédait.
     * Inicializálja a sávot alapértelmezett, Tiszta állapottal és üres szomszédlistával.
     */
    public Sav() {
        SkeletonLogger.create(this);
        this.allapot = new Tiszta();
        this.szomszedok = new ArrayList<>();
        SkeletonLogger.exit(this);
    }

    /**
     * Hozzáad egy új kimeneti csomópontot a sávhoz, amire a járművek továbbhaladhatnak.
     * @param szomszed A hozzáadni kívánt szomszédos csomópont
     */
    public void addSzomszed(Csomopont szomszed) {
        SkeletonLogger.enter(this, "addSzomszed", szomszed);
        this.szomszedok.add(szomszed);
        SkeletonLogger.exit("void");
    }

    /**
     * Beállítja, hogy a sáv melyik útszakaszhoz tartozik.
     * @param utszakasz A befogadó útszakasz
     */
    public void setUtszakasz(Utszakasz utszakasz) {
        this.utszakasz = utszakasz;
    }

    /**
     * Visszaadja a sávot tartalmazó útszakaszt.
     * @return Az útszakasz objektum
     */
    public Utszakasz getUtszakasz() {
        return this.utszakasz;
    }

    /**
     * Beállítja a sáv új állapotát (pl. havazás vagy takarítás hatására).
     * @param allapot Az új Savallapot objektum
     */
    public void setAllapot(Savallapot allapot) {
        SkeletonLogger.enter(this, "setAllapot", allapot);
        this.allapot = allapot;
        SkeletonLogger.exit("void");
    }

    /**
     * Lekérdezi a sáv jelenlegi állapotát.
     * @return Az aktuális Savallapot
     */
    public Savallapot getAllapot() {
        return this.allapot;
    }

    // --- ÚJ GETTER/SETTER METÓDUSOK AZ ÁLLAPOTOK SZÁMÁRA ---


    /**
     * Megadja, hogy a sáv jelenleg fel van-e szórva zúzalékkal.
     * @return true, ha a zúzalék számláló nagyobb mint 0, különben false
     */
    public boolean isZuzalekos() {
        return this.zuzalekos > 0;
    }

    /**
     * Beállítja a sáv zúzalék-számlálóját.
     * @param zuzalekos A zúzalék szintje/ideje
     */
    public void setZuzalekos(int zuzalekos) {
        this.zuzalekos = zuzalekos;
    }

    /**
     * Lekérdezi a sáv sózottságának szintjét/idejét.
     * @return A sózottság mértéke
     */
    public int getSozott() {
        return this.sozott;
    }

    /**
     * Beállítja a sáv sózottságának szintjét/idejét.
     * @param sozott A beállítani kívánt sózottsági szint
     */
    public void setSozott(int sozott) {
        this.sozott = sozott;
    }
    // --------------------------------------------------------

    /**
     * Frissíti a sáv állapotát, ami minden körben meghívódik.
     * Ha a sáv sózott, értesíti a belső állapotát a frissítésről (pl. jégolvasztás).
     */
    @Override
    public void frissit() {
        SkeletonLogger.enter(this, "frissit");
        if (this.sozott > 0 && allapot != null) {
            allapot.frissit(this);
        }
        
        SkeletonLogger.exit("void");
    }

    /**
     * Megpróbál befogadni egy járművet a sávba.
     * A befogadás sikeressége függ a sáv foglaltságától és a jelenlegi állapotától.
     * @param jarmu A sávra lépni kívánó jármű
     * @return true, ha a jármű sikeresen a sávra lépett, egyébként false
     */
    @Override
    public boolean befogad(Jarmu jarmu) {
        SkeletonLogger.enter(this, "befogad", jarmu);
        if (this.foglalt()) {
            SkeletonLogger.exit(false);
            return false;
        }
        boolean siker = false;
        if (this.allapot != null) {
            // Itt dől el (a State-ben), hogy jégre lép, letapossa a havat, stb.
            siker = this.allapot.befogad(this, jarmu);
        }
        if (siker) {
            this.jarmu = jarmu;
        }
        SkeletonLogger.exit(siker);
        return siker;
    }

    /**
     * Eltávolítja (elengedi) a járművet a sávról, és értesíti az állapotot a távozásról.
     * @param jarmu A távozó jármű
     */
    @Override
    public void elenged(Jarmu jarmu) {
        SkeletonLogger.enter(this, "elenged", jarmu);
        if (this.jarmu == jarmu) {
            this.jarmu = null;
            if (allapot != null) {
                allapot.elenged(this, jarmu);
            }
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Visszaadja a sávból elérhető szomszédos (következő) csomópontokat.
     * @return A szomszédos csomópontok listája
     */
    @Override
    public List<Csomopont> getNext() {
        return szomszedok;
    }

    /**
     * Lekérdezi az útszakaszon belül ettől a sávtól jobbra elhelyezkedő sávot.
     * Sávváltáshoz szükséges funkció.
     * @param sav A bázis sáv (általában önmaga)
     * @return A jobbra lévő sáv, vagy null, ha nincs ilyen (pl. legszélső sáv)
     */
    public Sav getJobbSzomszed(Sav sav) {
        SkeletonLogger.enter(this, "getJobbSzomszed", sav);
        Sav szomszed = null;
        if (this.utszakasz != null) {
            szomszed = this.utszakasz.getJobbSzomszed(this);
        }
        SkeletonLogger.exit(szomszed);
        return szomszed;
    }

    /**
     * Előzetesen teszteli, hogy egy adott jármű képes és jogosult-e erre a sávra lépni.
     * @param jarmu A vizsgálandó jármű
     * @return true, ha a sáv nem foglalt és az állapota megengedi a rálépést, egyébként false
     */
    public boolean lepesTeszt(Jarmu jarmu) {
        SkeletonLogger.enter(this, "lepesTeszt", jarmu);
        boolean teszt = false;
        if (allapot != null) {
            teszt = allapot.lepesTeszt(jarmu) && !this.foglalt(); 
        }
        SkeletonLogger.exit(teszt);
        return teszt;
    }

    /**
     * Kiváltja a baleset eseményét a sávon.
     * Ha van jármű a sávban, akkor az balesetet szenved.
     */
    @Override
    public void balesetEseten() {
        SkeletonLogger.enter(this, "balesetEseten");
        if (this.jarmu != null) {
            this.jarmu.balesetetSzenved(); // A baleset manuális előidézése (Teszt 50)
        }
        SkeletonLogger.exit("void");
    }

    /**
     * Lekérdezi, hogy a sáv foglalt-e.
     * @return true, ha tartózkodik jármű a sávon, egyébként false (Kapacitáskorlát biztosítása, Teszt 41)
     */
    @Override
    public boolean foglalt() {
        return (this.jarmu != null); // Kapacitáskorlát biztosítása (Teszt 41)
    }

    /**
     * Kezeli a havazás eseményét a sávon.
     * Ha a sáv nincs sózva, a hóesést a befogadó útszakasz vagy a belső állapot kezeli.
     * Ha a sáv sózva van, a só védi az utat, de a sómennyiség csökken 
     * A havazás a zúzalékot is fogyaszthatja.
     */
    @Override
    public void hoesesEseten() {
        SkeletonLogger.enter(this, "hoesesEseten");
        if (this.sozott == 0) {
            // NullPointerException védelem: ha nincs útszakaszban, egyből az állapotnak szólunk
            if (this.utszakasz != null) {
                this.utszakasz.havazikRa(this);
                
            } else {
                if (this.allapot != null) {
                    this.allapot.hoesesEseten(this);
                }
            }
            if(zuzalekos > 0) {
                    this.zuzalekos--;
            }
        } else {
            this.sozott--; // Teszt 47: A só védi a sávot a havazástól!
        }
        SkeletonLogger.exit("void");
    }
    /**
     * Továbbítja a jégtisztítási kérelmet az aktuális sávállapot felé.
     * @param olvad true ha olvasztjuk, false ha mechanikusan törjük a jeget
     * @return true, ha a tisztítás sikeres volt, egyébként false
     */
    public boolean jegTisztit(Boolean olvad) {
        SkeletonLogger.enter(this, "jegTisztit", olvad);
        boolean ret = false;
        if (allapot != null) {
            ret = allapot.jegTisztit(this, olvad);
        }
        SkeletonLogger.exit(ret);
        return ret;
    }

    /**
     * Továbbítja a hótisztítási kérelmet az aktuális sávállapot felé.
     * @return true, ha a takarítás sikeres volt, egyébként false
     */
    public boolean hoTisztit() {
        SkeletonLogger.enter(this, "hoTisztit");
        boolean ret = false;
        if (allapot != null) {
            ret = allapot.hoTisztit(this);
        }
        SkeletonLogger.exit(ret);
        return ret;
    }

    /**
     * Lesózza a sávot.
     * Továbbítja a sózás tényét az állapot felé, és beállítja a sózottság idejét 3-ra.
     */
    public void soSzoras() {
        SkeletonLogger.enter(this, "soSzoras");
        if (allapot != null) {
            allapot.sotKap(this);
        }
        this.sozott = 3;
        SkeletonLogger.exit("void");
    }

    /**
     * Felszórja a sávot zúzalékkal.
     * Értesíti az állapotot a szórásról, és beállítja a zúzalék élettartamát 3-ra.
     */
    public void zuzalekSzoras() {
        SkeletonLogger.enter(this, "zuzalekSzoras");
        if (allapot != null) {
            allapot.zuzalekSzoras(); 
        }
        this.zuzalekos = 3;
        SkeletonLogger.exit("void");
    }

    /**
     * Megpróbálja letakarítani a zúzalékot a sávról, ha van rajta.
     * @return true, ha volt rajta zúzalék és a takarítás sikeres, egyébként false
     */
    public boolean zuzalekTisztit(){
        SkeletonLogger.enter(this, "zuzalekTisztit");
        if (allapot != null) {
            allapot.zuzalekTisztit(this); 
        }
        if(this.zuzalekos > 0) {
            this.zuzalekos = 0;
            SkeletonLogger.exit(true);
            return true;
        }
        SkeletonLogger.exit(false);
        return false;
    }
    
    @Override
    public String printStat(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("Sav ").append(name).append(": allapot=").append(this.allapot.getClass().getSimpleName()).append(", sozott=").append(this.sozott).append(", zuzalekos=").append(this.zuzalekos).append(", jarmu=");
        if (this.jarmu != null) {
            sb.append(reverseNevTar.get(this.jarmu));
        }
        return sb.toString();
    }
/**
     * Getter a járműhöz.
     * Segítségével lekérdezhető a sávon tartózkodó jármű, pl. a grafikus megjelenítéshez (MapPanel).
     * @return A jelenleg a sávban lévő Jarmu objektum, vagy null ha üres
     */        
    public Jarmu getJarmu() {
        return this.jarmu;
    }
}
