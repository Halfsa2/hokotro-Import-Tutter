package halozat;
/**
 * A városi közlekedési hálózat egy speciális, fedett útszakasza[cite: 755].
 */
public class Alagut extends Utszakasz {

    /**
     * Polimorfizmus révén az alagút is megkapja a havazás hívást, de üresen fut le.
     * Így a belső sávjain nem halmozódik fel hóréteg [cite: 755-762].
     */
    @Override
    public void hoesesEseten() {
        // Ne csinálj semmit! Az alagútban sosincs hó.
    }
}
