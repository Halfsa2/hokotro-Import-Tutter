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
    public abstract boolean befogad(Sav sav, Jarmu jarmu);

    /**
     * Elengedi a járművet a sávból az aktuális állapotban.
     * @param sav a sáv, amelyből a jármű elengedésre kerül
     * @param jarmu a jármű, amely elengedésre kerül
     */
    public abstract void elenged(Sav sav, Jarmu jarmu);

    /**
     * Kezeli a hóesés eseményét a sávon az aktuális állapotban.
     * @param sav a sáv, amelyen hóesés történik
     */
    public abstract void hoesesEseten(Sav sav);

    /**
     * Frissíti a sáv állapotát az aktuális állapotban.
     * (Pl.: jég olvadása, hóréteg csökkenése, ha sóztak)
     * @param sav a frissítendő sáv
     */
    public abstract void frissit(Sav sav);

    /**
     * Teszteli, hogy a jármű ráléphet-e a sávra az aktuális állapotban.
     * (Segédmetódus a befogad() döntéséhez)
     * @param jarmu a jármű, amely tesztelni szeretne
     * @return true, ha ráléphet, különben false
     */
    public abstract boolean lepesTeszt(Jarmu jarmu);

    /**
     * Kezeli, ha a sáv sót kap az aktuális állapotban.
     * @param sav a sáv, amely sót kap
     */
    public abstract void sotKap(Sav sav);

    /**
     * Megpróbálja megtisztítani a havat a sávból az aktuális állapotban.
     * @param sav a tisztítandó sáv
     * @return true, ha sikerült (volt mit tisztítani), különben false
     */
    public abstract boolean hoTisztit(Sav sav);

    /**
     * Megpróbálja megtisztítani a jeget a sávból az aktuális állapotban.
     * @param sav a tisztítandó sáv
     * @return true, ha sikerült (volt jég), különben false
     */
    public abstract boolean jegTisztit(Sav sav);
}