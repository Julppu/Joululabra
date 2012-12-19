
package Kortisto;

import java.util.ArrayList;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LehtiTest {
    
    private Lehti lehti;
    
    public LehtiTest() {}
    
    @Before
    public void setUp() {
        lehti = new Lehti(1, "11223344", "Aku ankka", "Sanoma");
        lehti.lisaaNumero(22, 2011);
        lehti.lisaaHakusana("sarjakuva");
    }
    
    @After
    public void tearDown() {
        
    }

    @Test
    public void testLisaaNumero() {
        lehti.lisaaNumero(1, 2012);
        ArrayList<Numero> testi = lehti.getNumerot();
        boolean loytyi = false;
        for (Numero numero: testi) {
            if (numero.getVuosi() == 2012 && numero.getNumero() == 1)
                loytyi = true;
        }
        assertTrue(loytyi);
    }
    
    @Test
    public void testLisaaOlemassaOlevaNumero() {
        lehti.lisaaNumero(22, 2011);
        ArrayList<Numero> testi = lehti.getNumerot();
        boolean loytyi = false;
        for (Numero numero: testi) {
            if (numero.getVuosi() == 2012 && numero.getNumero() == 1)
                loytyi = true;
        }
        assertTrue(loytyi);
    }

    @Test
    public void testGetID() {
        assertEquals(lehti.getID(), 1);
    }

    @Test
    public void testSetID() {
        lehti.setID(2);
        assertEquals(lehti.getID(), 2);
    }

    @Test
    public void testGetISSN() {
        assertEquals(lehti.getISSN(), "11223344");
    }

    @Test
    public void testSetISSN() {
        lehti.setISSN("22114433");
        assertEquals(lehti.getISSN(), "22114433");
    }

    @Test
    public void testGetNimi() {
        assertEquals(lehti.getNimi(), "Aku ankka");
    }

    @Test
    public void testSetNimi() {
        lehti.setNimi("Roope-setä");
        assertEquals(lehti.getNimi(), "Roope-setä");
    }
    
    @Test
    public void testGetKustantaja() {
        assertEquals(lehti.getKustantaja(), "Sanoma");
    }

    @Test
    public void testSetKustantaja() {
        lehti.setKustantaja("WSOY");
        assertEquals(lehti.getKustantaja(), "WSOY");
    }

    @Test
    public void testGetHakusanat() {
        ArrayList<String> testi = new ArrayList();
        testi.add("sarjakuva");
        assertEquals(testi, lehti.getHakusanat());
    }
}
