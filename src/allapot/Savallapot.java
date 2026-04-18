package allapot;

import halozat.Sav;
import jarmu.Jarmu;

/**
 * A State (Állapot) tervezési minta központi eleme.
 * Előírja, hogyan kell az egyes állapotoknak reagálniuk az eseményekre.
 */
public abstract class Savallapot {

    /**
     * Ellenőrzi, hogy a jármű befogadható-e a sávba az aktuális állapotban.
     * @param sav a sáv, amelybe a jármű szeretne befogadódni
     * @param jarmu a jármű, amely befogadódni szeretne
     * @return true, ha befogadható, különben false
     */
    public abstract boolean befogad(Sav sav, Jarmu jarmu); // [cite: 1151]

    /**
     * Elengedi a járművet a sávból az aktuális állapotban.
     * @param sav a sáv, amelyből a jármű elengedésre kerül
     * @param jarmu a jármű, amely elengedésre kerül
     */
    public abstract void elenged(Sav sav, Jarmu jarmu); // [cite: 1152]

    /**
     * Kezeli a hóesés eseményét a sávon az aktuális állapotban.
     * @param sav a sáv, amelyen hóesés történik
     */
    public abstract void hoesesEseten(Sav sav); // [cite: 1153]

    /**
     * Frissíti a sáv állapotát az aktuális állapotban.
     * @param sav a frissítendő sáv
     */
    public abstract void frissit(Sav sav); // [cite: 1158]

    /**
     * Teszteli, hogy a jármű ráléphet-e a sávra az aktuális állapotban.
     * @param jarmu a jármű, amely tesztelni szeretne
     * @return true, ha ráléphet, különben false
     */
    public abstract boolean lepesTeszt(Jarmu jarmu); // [cite: 1159]

    /**
     * Kezeli, ha a sáv sót kap az aktuális állapotban.
     * @param sav a sáv, amely sót kap
     */
    public abstract void sotKap(Sav sav); // [cite: 1160]

    /**
     * Megpróbálja megtisztítani a havat a sávból az aktuális állapotban.
     * @param sav a tisztítandó sáv
     * @return true, ha sikerült, különben false
     */
    public abstract boolean hoTisztit(Sav sav); // [cite: 1161]

    /**
     * Megpróbálja megtisztítani a jeget a sávból az aktuális állapotban.
     * @param sav a tisztítandó sáv
     * @param olvad jelzi, hogy a jeget törjük, vagy olvasztjuk
     * @return true, ha sikerült, különben false
     */
    public abstract boolean jegTisztit(Sav sav, Boolean olvad); // [cite: 1163]

    /**
      * Megpróbálja megtisztítani a zuzalékot a sávból az aktuális állapotban.
      * @param sav a tisztítandó sáv
      * @return true, ha sikerült, különben false
      */
    public boolean zuzalekTisztit(Sav sav) {
        return false;
    }
    public void zuzalekSzoras() {}
}
