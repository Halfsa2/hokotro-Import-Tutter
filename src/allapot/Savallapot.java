package allapot;
import halozat.Sav;
import jarmu.Jarmu;
/**
 * A State (Állapot) tervezési minta központi eleme. 
 * Előírja, hogyan kell az egyes állapotoknak reagálniuk az eseményekre.
 */
public abstract class Savallapot {
    public abstract void befogad(Sav sav, Jarmu jarmu); // [cite: 1151]
    public abstract void elenged(Sav sav, Jarmu jarmu); // [cite: 1152]
    public abstract void hoesesEseten(Sav sav); // [cite: 1153]
    public abstract void frissit(Sav sav); // [cite: 1158]
    public abstract boolean lepesTeszt(Jarmu jarmu); // [cite: 1159]
    public abstract void sotKap(Sav sav); // [cite: 1160]
    public abstract boolean hoTisztit(Sav sav); // [cite: 1161]
    public abstract boolean jegTisztit(Sav sav); // [cite: 1163]
}
