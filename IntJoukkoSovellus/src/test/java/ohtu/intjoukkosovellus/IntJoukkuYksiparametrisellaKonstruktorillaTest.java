
package ohtu.intjoukkosovellus;

import org.junit.Before;


public class IntJoukkuYksiparametrisellaKonstruktorillaTest extends IntJoukkoTest {
    
    @Before
    @Override
    public void setUp() {
        a = new IntJoukko(3);
        a.lisaa(10);
        a.lisaa(3);
    }
    
    // perii kaikki testit luokasta IntJoukkoTest
}
