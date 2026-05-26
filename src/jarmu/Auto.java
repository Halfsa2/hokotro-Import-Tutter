package jarmu;

import halozat.Checkpoint;
import halozat.Csomopont;
import halozat.Sav;
import java.util.List;
import static prototipus.CommandInterpreter.reverseNevTar;
import vezerles.SkeletonLogger;

/**
 * A városban közlekedő, alapszintű, önvezető jármű.
 */
public class Auto extends Jarmu {

    /** Az autó kiindulási pozíciója (nem változik a játék során). */
    private final Checkpoint start; 
    /** Az autó célállomása (nem változik a játék során). */
    private final Checkpoint cel; 
    /** Irányjelző: true, ha a cél felé tart, false, ha vissza a start felé. */
    private boolean oda; 

    /**
     * Konstruktor az Auto osztályhoz.
     * Beállítja az autó kezdőpontját, célját, és az alapértelmezett haladási irányt.
     * @param start A kiindulási Checkpoint
     * @param cel A cél Checkpoint
     */
    public Auto(Checkpoint start, Checkpoint cel) {
        SkeletonLogger.create(this);
        this.start = start;
        this.cel = cel;
        this.oda = true; // Alapértelmezetten az első cél felé tart
        SkeletonLogger.exit(this);
    }
    /**
     * Lekérdezi, hogy az autó éppen az elsődleges célja felé tart-e.
     * @return true, ha a cél felé tart, false, ha visszafelé (start pont felé)
     */
    public boolean getOda(){
        return oda;
    }
    /**
     * Beállítja az autó haladási irányát (cél felé vagy vissza).
     * @param oda Az új irány (true = cél felé, false = start felé)
     */
    public void setOda(boolean oda) {
        this.oda = oda;
    }

    /**
     * Ez a metódus felelős az autó tényleges mozgásáért és a sávváltási (kikerülési) logikáért.
     * Ellenőrzi a várakozási büntetést (pl. baleset után), validálja a topológiát, 
     * majd megpróbál rálépni a cél csomópontra. Ha az foglalt, sávváltással próbálkozik.
     * @param celCsomopont A csomópont, ahová az autó lépni szeretne
     * @return true, ha a lépés (vagy kikerülés) sikeres volt, egyébként false
     */
    @Override
    public boolean lep(Csomopont celCsomopont) {
        SkeletonLogger.enter(this, "lep", celCsomopont);
        
        // 1. Várakozás (büntetés) ellenőrzése baleset után (Teszt 53)
        if (this.varakozik > 0) {
            this.varakozik--; // A büntetés csökken minden próbálkozásnál/körnél
            SkeletonLogger.exit(false);
            return false;
        }
        
        // 2. Szomszédság validációja: Csak topológiailag elérhető szomszédra léphet
        if (this.aktualisCsomopont != null) {
            List<Csomopont> szomszedok = this.aktualisCsomopont.getNext();
            if (szomszedok == null || !szomszedok.contains(celCsomopont)) {
                SkeletonLogger.exit(false);
                return false;
            }
        }

        // 3. Befogadás megkísérlése az új csomóponton
        if (celCsomopont.befogad(this)) {
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            this.aktualisCsomopont = celCsomopont;
            SkeletonLogger.exit(true);
            return true;
        }else {
            if(aktualisCsomopont instanceof Sav sav){
                Sav szomszedSav = sav.getUtszakasz().getBalSzomszed(sav) == null? sav.getUtszakasz().getJobbSzomszed(sav) : sav.getUtszakasz().getBalSzomszed(sav);
                if(szomszedSav != null && szomszedSav.befogad(this)){
                    if (this.aktualisCsomopont != null) {
                        this.aktualisCsomopont.elenged(this);
                    }
                    this.aktualisCsomopont = szomszedSav;
                    SkeletonLogger.exit(true);
                    return true;
                }
            }else{
                if(celCsomopont instanceof Sav sav){
                    Sav szomszedSav = sav.getUtszakasz().getBalSzomszed(sav) == null? sav.getUtszakasz().getJobbSzomszed(sav) : sav.getUtszakasz().getBalSzomszed(sav);
                    for(Csomopont szomszed : aktualisCsomopont.getNext()){
                        if(szomszed.getNext().contains(szomszedSav) && szomszed.befogad(this)){
                                if (this.aktualisCsomopont != null) {
                                    this.aktualisCsomopont.elenged(this);
                                }
                                this.aktualisCsomopont = szomszed;
                                SkeletonLogger.exit(true);
                                return true;
                            }
                    }
                }
            }
        }

        
        // Ha a befogadás elutasítva (pl. foglalt a Checkpoint)
        SkeletonLogger.exit(false);
        return false;
    }

    /**
     * Önvezető mozgás: lekéri a VarosModell-től a legrövidebb utat, 
     * és rálép a következő csomópontra.
     * @param vm A város modellje, amely az útvonalat számolja
     * @return true, ha sikerült a lépés
     */
    public boolean onvezetoLep(vezerles.VarosModell vm) {
        // Lekérjük a teljes útvonalat BFS-sel
        java.util.List<halozat.Csomopont> utvonal = vm.legrovidebbUtvonal(this.aktualisCsomopont, this.cel);
        
        // Az útvonal első eleme az aktuális helyünk, a második a következő lépés
        if (utvonal.size() >= 2) {
            halozat.Csomopont kovetkezo = utvonal.get(1);
            return this.lep(kovetkezo);
        }
        return false;
    }
    
    public Checkpoint getStart(){
        return start;
    }
    
    public Checkpoint getCel(){
        return cel;
    }
   @Override
    public String printStat(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("Auto ").append(name).append(": aktualisCsomopont=");
        if (this.aktualisCsomopont != null) {
            sb.append(aktualisCsomopont != null? reverseNevTar.get(this.aktualisCsomopont) : "null");
        }
        sb.append(", start=").append(reverseNevTar.get(this.start));
        sb.append(", cel=").append(reverseNevTar.get(this.cel));
        sb.append(", varakozik=").append(varakozik);
        return sb.toString();
    }
}