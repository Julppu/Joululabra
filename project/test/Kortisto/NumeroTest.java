
package Kortisto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NumeroTest {
    
    private Numero numero;
    
    public NumeroTest() {
        
    }
    
    @Before
    public void setUp() {
        numero = new Numero(1, 2012, 1);
    }
    
    @After
    public void tearDown() {
        
    }
    
    @Test
    public void testSetNumero() {
        numero.setNumero(2);
        assertEquals(numero.getNumero(), 2);
    }

    @Test
    public void testGetNumero() {
        assertEquals(numero.getNumero(), 1);
    }
    
    @Test
    public void testSetVuosi() {
        numero.setVuosi(2011);
        assertEquals(numero.getVuosi(), 2011);
    }

    @Test
    public void testGetVuosi() {
        assertEquals(numero.getVuosi(), 2012);
    }
}
