
package Kortisto;

import Kortisto.KortistoOperaatiot.Hakukone;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KortistoTest {
    
    private Kortisto kortisto;
    private Hakukone hakukone;
    private boolean test;
    
    public KortistoTest() {
    }
    
    @Before
    public void setUp() {
        kortisto = new Kortisto();
        hakukone = new Hakukone();
        try {
            kortisto.lisaaTeos("0001", "Aapinen", "Aapiskukko", 2000, "WSOY");
            kortisto.lisaaTeos("0002", "Satukirja", "Satusetä", 1999, "Abloy");
            kortisto.lisaaLehti("0003", "Helsingin sanomat", "Sanoma");
            kortisto.lisaaNide(1, 0, "testikokoelma");
            kortisto.lisaaNide(2, 0, "testikokoelma");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testLisaaTeos() {
        test = false;
        try {
            kortisto.lisaaTeos("0004", "Miksi pitää tehdä JUnitia?", "Keijo Käpistelijä", 2012, "omakustanne");
        } catch (Exception ex) {}
        for (Teos teos: kortisto.getTeokset())
            if (teos.getISBN().equals("0004"))
                test = true;
        assertTrue(test);
    }
    
    @Test (expected=Exception.class)
    public void testLisaaOlemassaolevaTeos() throws Exception {
        kortisto.lisaaTeos("0001", "Aapinen", "Aapiskukko", 2000, "WSOY");
    }

    @Test
    public void testPoistaTeos() {
        test = false;
        kortisto.poistaTeos(2);
        for (Teos teos: kortisto.getTeokset())
            if (!teos.getISBN().equals("0002"))
                test = true;
        assertTrue(test);
    }

    @Test
    public void testLisaaNide() {
        test = false;
        kortisto.lisaaNide(1, 0, "testikokoelma");
        for (Teos teos: kortisto.getTeokset())
            for (Nide nide: teos.getNiteet())
                if (nide.getViivakoodi().equals("100001"))
                    test = true;
        assertTrue(test);
    }

    @Test
    public void testPoistaNide() {
        
    }
    
    @Test
    public void testPoistaOlematonNide() {
        
    }
    
    @Test
    public void testLisaaLehti() {
        
    }
    
    @Test  (expected=Exception.class)
    public void testLisaaOlemassaolevaLehti() throws Exception {
        kortisto.lisaaLehti("0003", "Helsingin sanomat", "Sanoma");
    }

    @Test
    public void testPoistaLehti() {
        
    }
    
    @Test
    public void testPoistaOlematonLehti() {
        
    }
    
    @Test
    public void testLisaaNumero() {
        
    }

    @Test
    public void testLisaaOlemassaOlevaNumero() {
        
    }

    @Test
    public void testPoistaNumero() {
        
    }
    
    @Test
    public void testPoistaOlematonNumero() {
        
    }
    
    @Test
    public void testGetTeokset() {
        
    }

    @Test
    public void testGetLehdet() {
        
    }

    @Test
    public void testLisaaKokoelma() {
        kortisto.lisaaKokoelma("uusi kokoelma");
        assertTrue(kortisto.getKokoelmat().contains("uusi kokoelma"));
    }

    @Test
    public void testPoistaKokoelma() {
        kortisto.lisaaKokoelma("uusi kokoelma");
        kortisto.poistaKokoelma("testikokoelma");
        assertTrue(!kortisto.getKokoelmat().contains("testikokoelma"));
    }

    @Test
    public void testGetKokoelmat() {
        ArrayList<String> testi = new ArrayList();
        testi.add("testikokoelma");
        assertEquals(testi, kortisto.getKokoelmat());
    }
}
