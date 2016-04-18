
package ohtu.intjoukkosovellus;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class IntJoukkoKaksiparametrisellaKonstruktorillaTest {
    IntJoukko a;
    IntJoukko b;

    @Before
    public void setUp() {
        a = new IntJoukko(1,2);
        a.lisaa(1);
        a.lisaa(2);

        b = new IntJoukko();
        b.lisaa(2);
        b.lisaa(3);

    }

    @Test
    public void leikkausToimii() {
        IntJoukko tulos = IntJoukko.leikkaus(a,b);
        IntJoukko oikeaTulos = new IntJoukko();
        oikeaTulos.lisaa(2);
        assertEquals(oikeaTulos.toString(), tulos.toString());
    }

    @Test
    public void erotusToimii() {
        IntJoukko tulos = IntJoukko.erotus(a,b);
        IntJoukko oikeaTulos = new IntJoukko();
        oikeaTulos.lisaa(1);
        assertEquals(oikeaTulos.toString(), tulos.toString());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void intJoukkoaEiVoiLuodaNegatiivisillaParametreilla() {
        new IntJoukko(-1);
    }
}
