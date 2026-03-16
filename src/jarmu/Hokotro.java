package jarmu;
import java.util.ArrayList;
import java.util.List;
import felszereles.Kotrofej;
import halozat.Sav;
import gazdasag.Takarito;
import halozat.Csomopont;


/**
 * A Takarító által vezérelt munkagép, amely a sávok tisztításáért felel [cite: 879-880].
 */
public class Hokotro extends IranyitottJarmu {
    
    private Takarito tulajdonos; // Melyik játékoshoz tartozik a hókotró [cite: 890]
    private Kotrofej aktiv; // Az aktuálisan felszerelt, munkát végző eszköz [cite: 888]
    private List<Kotrofej> birtokolja; // A megvásárolt vagy alapfelszereltségként kapott fejek [cite: 887]

    public Hokotro(Takarito tulajdonos) {
        this.tulajdonos = tulajdonos;
        this.birtokolja = new ArrayList<>();
        // Alapesetben kapnia kell Söprő és Jégtörő fejet (ezt a inicializáláskor kell hozzáadni)
    }

    /**
     * Lecseréli a hókotróra jelenleg felszerelt kotrófejet[cite: 892].
     */
    public void cserelFej(Kotrofej ujFej) {
        if (this.birtokolja.contains(ujFej)) {
            this.aktiv = ujFej;
        }
    }

    /**
     * Az elérhető kotrófejek listáját bővíti[cite: 899].
     */
    public void addFej(Kotrofej ujFej) {
        this.birtokolja.add(ujFej); // [cite: 900]
    }

    /**
     * Elvégzi az adott sáv megtisztítását a felszerelt aktív kotrófejjel[cite: 893].
     */
    public boolean takarit(Sav s) {
        if (this.aktiv != null) {
            return this.aktiv.takarit(s); //
        }
        return false;
    }

    /**
     * Biztosítja a jármű sérthetetlenségét az ütközések esetén[cite: 894].
     * Szándékosan üresen van implementálva (nem csinál semmit)[cite: 894].
     */
    @Override
    public void balesetetSzenved() {
        // A Hókotró "elpusztíthatatlan", nem áll meg baleset esetén [cite: 883-884]
    }

    /**
     * A jármű mozgatása, és a lépés után fellépő speciális mellékhatások (takarítás) végrehajtása [cite: 901-902].
     */
    @Override
    public boolean lep(Csomopont celCsomopont) {
        if (!celCsomopont.foglalt()) {
            if (this.aktualisCsomopont != null) {
                this.aktualisCsomopont.elenged(this);
            }
            celCsomopont.befogad(this);
            this.aktualisCsomopont = celCsomopont;
            
            // Lépés után automatikusan megpróbál takarítani, ha sávon van
            if (celCsomopont instanceof Sav) {
                this.takarit((Sav) celCsomopont);
            }
            return true;
        }
        return false;
    }
}
