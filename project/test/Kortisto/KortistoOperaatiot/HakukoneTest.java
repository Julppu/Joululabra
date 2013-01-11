/**
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

package Kortisto.KortistoOperaatiot;

import Kortisto.Kortisto;
import Kortisto.KortistoOperaatiot.Hakukone;
import Kortisto.Lehti;
import Kortisto.Nide;
import Kortisto.Teos;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HakukoneTest {
    
    private Hakukone hakukone;
    private Kortisto kortisto;
    
    public HakukoneTest() {
    }
    
    @Before
    public void setUp() {
        hakukone = new Hakukone();
        kortisto = new Kortisto();
        try {
            kortisto.lisaaTeos("0001", "Aapinen", "Aapiskukko", 2010, "WSOY");
            kortisto.lisaaTeos("0002", "Aapinen", "Aapiskukko", 2010, "WSOY");
            kortisto.lisaaTeos("0003", "Raamattu", "Jeesus", 0, "Jessen kirjuritoimisto");
            kortisto.lisaaLehti("0004", "Helsingin Sanomat", "Sanoma");
            kortisto.lisaaLehti("0005", "Iltasanomat", "Sanoma");
            kortisto.lisaaNide(1, 14, "testikokoelma");
            kortisto.lisaaNide(1, 14, "testikokoelma");
            kortisto.lisaaNide(2, 2, "lyhytlainat");
            kortisto.lisaaNide(2, 2, "lyhytlainat");
            kortisto.lisaaNumero(1, 2012, 52);
            kortisto.lisaaNumero(1, 2013, 1);
            kortisto.lisaaNumero(2, 2010, 12);
            kortisto.lisaaNumero(2, 2010, 11);
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testHaeTeosISBN() {
        Teos teos = hakukone.haeTeosISBN(kortisto, "0001");
        assertEquals(1, teos.getID());
    }
    
    @Test
    public void testHaeTeosOlemattomallaISBN() {
        Teos teos = hakukone.haeTeosISBN(kortisto, "1234");
        assertEquals(null, teos);
    }

    @Test
    public void testHaeTeoksiaNimella() {
        ArrayList<Teos> teokset = hakukone.haeTeoksiaNimella(kortisto, "Aapinen");
        assertEquals(2, teokset.size());
    }

    @Test
    public void testHaeTeoksiaOlemattomallaNimella() {
        ArrayList<Teos> teokset = hakukone.haeTeoksiaNimella(kortisto, "Kekkonen");
        assertTrue(teokset.isEmpty());
    }

    @Test
    public void testHaeTeoksiaTekijalla() {
        boolean test = false;
        ArrayList<Teos> teokset = hakukone.haeTeoksiaTekijalla(kortisto, "Jeesus");
        for (Teos teos: teokset)
            if (teos.getNimi().equals("Raamattu"))
                test = true;
        assertTrue(test);
    }

    @Test
    public void testHaeTeoksiaOlemattomallaTekijalla() {
        ArrayList<Teos> teokset = hakukone.haeTeoksiaNimella(kortisto, "Kekkonen");
        assertTrue(teokset.isEmpty());
    }

    @Test
    public void testHaeLehtiTunnuksella() {
        Lehti lehti = hakukone.haeLehtiTunnuksella(kortisto, 1);
        assertTrue(lehti.getNimi().equals("Helsingin Sanomat"));
    }

    @Test
    public void testHaeLehtiOlemattomallaTunnuksella() {
        Lehti lehti = hakukone.haeLehtiTunnuksella(kortisto, 6);
        assertEquals(null, lehti);
    }

    @Test
    public void testHaeLehtiISSN() {
        Lehti lehti = hakukone.haeLehtiISSN(kortisto, "0004");
        assertTrue(lehti.getNimi().equals("Helsingin Sanomat"));
    }

    @Test
    public void testHaeLehtiOlemattomallaISSN() {
        Lehti lehti = hakukone.haeLehtiISSN(kortisto, "13456");
        assertEquals(null, lehti);
    }

    @Test
    public void testHaeLehtiaNimella() {
        ArrayList<Lehti> lehdet = hakukone.haeLehtiaNimella(kortisto, "sanomat");
        assertEquals(2, lehdet.size());
    }

    @Test
    public void testHaeLehtiaOlemattomallaNimella() {
        ArrayList<Lehti> lehdet = hakukone.haeLehtiaNimella(kortisto, "Aku ankka");
        assertTrue(lehdet.isEmpty());
    }
    
    @Test
    public void haeKokoelmanNiteet() {
        ArrayList<Nide> niteet = hakukone.haeKokoelmanNiteet(kortisto, "testikokoelma");
        assertEquals(2, niteet.size());
    }
    
    @Test
    public void haeOlemattomanKokoelmanNiteet() {
        ArrayList<Nide> niteet = hakukone.haeKokoelmanNiteet(kortisto, "kurssikirjat");
        assertTrue(niteet.isEmpty());
    }
}
