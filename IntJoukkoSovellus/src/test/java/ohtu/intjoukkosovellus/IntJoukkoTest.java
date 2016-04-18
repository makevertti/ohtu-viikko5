package ohtu.intjoukkosovellus;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IntJoukkoTest {

    IntJoukko a;
    IntJoukko b;

    @Before
    public void setUp() {
        a = new IntJoukko();
        a.lisaa(10);
        a.lisaa(3);

        b = new IntJoukko();
        b.lisaa(6);
        b.lisaa(4);
    }

    @Test
    public void lukujaLisattyMaara() {
        a.lisaa(4);
        assertEquals(3, a.mahtavuus());
    }

    @Test
    public void samaLukuMeneeJoukkoonVaanKerran() {
        a.lisaa(10);
        a.lisaa(3);
        assertEquals(2, a.mahtavuus());
    }

    @Test
    public void vainLisatytLuvutLoytyvat() {
        assertTrue(a.kuuluuJoukkoon(10));
        assertFalse(a.kuuluuJoukkoon(5));
        assertTrue(a.kuuluuJoukkoon(3));
    }

    @Test
    public void poistettuEiOleEnaaJoukossa() {
        a.poista(3);
        assertFalse(a.kuuluuJoukkoon(3));
        assertEquals(1, a.mahtavuus());
    }
    
    @Test
    public void palautetaanOikeaTaulukko() {
        int[] odotettu = {3, 55, 99};
        
        a.lisaa(55);
        a.poista(10);
        a.lisaa(99);

        int[] vastaus = a.toIntArray();
        Arrays.sort(vastaus);
        assertArrayEquals(odotettu, vastaus);
    }
    
    
    @Test
    public void toimiiKasvatuksenJalkeen(){
        int[] lisattavat = {1,2,4,5,6,7,8,9,11,12,13,14};
        for (int luku : lisattavat) {
            a.lisaa(luku);
        }
        assertEquals(14, a.mahtavuus());
        assertTrue(a.kuuluuJoukkoon(11));
        a.poista(11);
        assertFalse(a.kuuluuJoukkoon(11));
        assertEquals(13, a.mahtavuus());
    }
    
    @Test
    public void toStringToimii(){
        assertEquals("{10, 3}", a.toString());
    }
    
    @Test
    public void toStringToimiiYhdenKokoiselleJoukolla(){
        a = new IntJoukko();
        a.lisaa(1);
        assertEquals("{1}", a.toString());
    }

    @Test
    public void toStringToimiiTyhjallaJoukolla(){
        a = new IntJoukko();
        assertEquals("{}", a.toString());
    }     
}
