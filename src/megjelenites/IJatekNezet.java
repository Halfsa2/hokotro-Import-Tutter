package megjelenites;

public interface IJatekNezet{
    /**
     * Kijelzi a grafikus felhasználói felületen a kapott üzenetet.
     * @param uzenet
     */
    void uzenetKijelzese(String uzenet);
    /**
     * Jelzi a grafikus felhasználói felületnek a játék végét és az eredményt.
     * @param eredmeny
     */
    void jatekVege(String eredmeny);
    
    /**
     * Frissíti a játék megjelenítését, pl. a játék állapotának megjelenítésére.
     */
    void frissit();
}