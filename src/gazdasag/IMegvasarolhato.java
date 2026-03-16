package gazdasag;

import jarmu.Hokotro;

/**
 * A szimulációban a vásárlási folyamatokat határozza meg[cite: 927].
 */
public interface IMegvasarolhato {
    /**
     * A vásárlás folyamatának logikáját írja elő[cite: 936].
     * @return Igaz, ha a tranzakció sikeres volt [cite: 938-939].
     */
    boolean vasarol(Arucikk termek, Takarito vevo, Hokotro gep); 
}
