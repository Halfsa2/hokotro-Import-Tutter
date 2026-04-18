package megjelenites;

public interface IJatekNezet{
    void uzenetKijelzese(String uzenet);
    void jatekVege(String eredmeny);
    
    //frissit() fgv a jatek megjelenitesenek frissitesere, pl. a jatek allapotanak megjelenitesere (ez csak később)
    void frissit();
}