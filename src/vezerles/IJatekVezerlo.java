package vezerles;

import gazdasag.Arucikk;
import gazdasag.Jatekos;
import halozat.Csomopont;
import jarmu.Auto;
import jarmu.Hokotro;

public interface IJatekVezerlo {
    void initJatek();
    
    void lep(Csomopont cel);
    
    void nextJatekos();
    
    void vasarol(Arucikk termek, Hokotro gep);
    
    void registerJatekos(String tipus);
    
    void addJatekos(Jatekos<?> jatekos);
    
    void addAuto(Auto auto);
    
    public void tick(int korokSzama);
}
