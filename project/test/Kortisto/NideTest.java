
package Kortisto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NideTest {
    
    private Nide nide;
    
    public NideTest() {
    }
    
    @Before
    public void setUp() {
        nide = new Nide(1, "001", 0, "testikokoelma");
    }
    
    @After
    public void tearDown() {
        
    }

    @Test
    public void testSetViivakoodi() {
        nide.setViivakoodi("002");
        assertEquals(nide.getViivakoodi(), "002");
    }

    @Test
    public void testGetViivakoodi() {
        assertEquals(nide.getViivakoodi(), "001");
    }

    @Test
    public void testGetID() {
        assertEquals(nide.getID(), 1);
    }
    
    @Test
    public void testGetLainaAika() {
        assertEquals(nide.getLainaAika(), 0);
    }

    @Test
    public void testSetLainaAika() {
        nide.setLainaAika(14);
        assertEquals(nide.getLainaAika(), 14);
    }

    @Test
    public void testGetKokoelma() {
        assertEquals(nide.getKokoelma(), "testikokoelma");
    }

    @Test
    public void testSetKokoelma() {
        nide.setKokoelma("toinen testikokoelma");
        assertEquals(nide.getKokoelma(), "toinen testikokoelma");
    }
}
