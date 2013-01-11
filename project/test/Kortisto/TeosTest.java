
package Kortisto;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TeosTest {
    
    private Teos teos;
    
    public TeosTest() {}
    
    @Before
    public void setUp() {
        teos = new Teos(1, "1112223334", "Abckiria", "Agricola, Mikael", 1542, "-");
        teos.lisaaHakusana("aapinen");
        teos.lisaaNide("001", 0, "testikokoelma");
    }
    
    @After
    public void tearDown() {
        
    }

    @Test
    public void testLisaaNide() {
        teos.lisaaNide("002", 0, "testikokoelma");
        ArrayList<Nide> testi = teos.getNiteet();
        boolean loytyi = false;
        for (Nide nide: testi) {
            if (nide.getViivakoodi().equals("002"))
                loytyi = true;
        }
        assertTrue(loytyi);
    }
    
    @Test
    public void testPoistaNide() {
        teos.lisaaNide("002", 0, "testikokoelma");
        try {
            teos.poistaNide("001");
        } catch (Exception ex) {
            System.out.println(ex);
            fail("Poikkeus napattu.");
        }
        ArrayList<Nide> testi = teos.getNiteet();
        boolean onnistui = true;
        for (Nide nide: testi) {
            if (nide.getViivakoodi().equals("001"))
                onnistui = false;
        }
        assertTrue(onnistui);
    }
    
    @Test
    public void testGetHakusanat() {
        ArrayList<String> testi = new ArrayList<String>();
        testi.add("aapinen");
        assertEquals(teos.getHakusanat(), testi);
    }

    @Test
    public void testLisaaHakusana() {
        teos.lisaaHakusana("raamattu");
        ArrayList<String> testi = teos.getHakusanat();
        boolean loytyi = false;
        for (String hakusana: testi) {
            if (hakusana.equals("raamattu"));
                loytyi = true;
        }
        assertTrue(loytyi);
    }
    
    @Test
    public void poistaHakusana() {
        teos.poistaHakusana("aapinen");
        ArrayList<String> testi = teos.getHakusanat();
        boolean loytyi = false;
        for (String hakusana: testi) {
            if (hakusana.equals("raamattu"));
                loytyi = true;
        }
        assertFalse(loytyi);
    }

    @Test
    public void testGetID() {
        assertEquals(teos.getID(), 1);
    }

    @Test
    public void testGetISBN() {
        assertEquals("1112223334", teos.getISBN());
    }

    @Test
    public void testSetID() {
        teos.setID(2);
        assertEquals(teos.getID(), 2);
    }

    @Test
    public void testGetNimi() {
        assertEquals(teos.getNimi(), "Abckiria");
    }

    @Test
    public void testSetNimi() {
        teos.setNimi("Se Wsi testamentti");
        assertEquals(teos.getNimi(), "Se Wsi testamentti");
    }

    @Test
    public void testGetTekija() {
        assertEquals(teos.getTekija(), "Agricola, Mikael");
    }

    @Test
    public void testSetTekija() {
        teos.setTekija("Kekkonen");
        assertEquals(teos.getTekija(), "Kekkonen");
    }

    @Test
    public void testGetVuosi() {
        assertEquals(teos.getVuosi(), 1542);
    }

    @Test
    public void testSetVuosi() {
        teos.setVuosi(1548);
        assertEquals(teos.getVuosi(), 1548);
    }

    @Test
    public void testGetKustantaja() {
        assertEquals(teos.getKustantaja(), "-");
    }

    @Test
    public void testSetKustantaja() {
        teos.setKustantaja("omakustanne");
        assertEquals(teos.getKustantaja(), "omakustanne");
    }
}
