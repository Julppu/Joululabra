
package Kortisto;

import Kortisto.KortistoOperaatiot.Hakukone;
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
            kortisto.lisaaTeos("0002", "Raamattu", "Jeesus", 0, "Jessen kirjuritoimisto");
            kortisto.lisaaLehti("0003", "Helsingin Sanomat", "Sanoma");
            kortisto.lisaaLehti("0004", "Iltasanomat", "Sanoma");
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
    public void testHaeTeosTunnuksella() {

    }

    @Test
    public void testHaeTeosOlemattomallaTunuksella() {

    }

    @Test
    public void testHaeTeosISBN() {

    }

    @Test
    public void testHaeTeosOlemattomallaISBN() {
        
    }

    @Test
    public void testHaeTeoksiaNimella() {

    }

    @Test
    public void testHaeTeoksiaOlemattomallaNimella() {
        
    }

    @Test
    public void testHaeTeoksiaTekijalla() {

    }

    @Test
    public void testHaeTeoksiaOlemattomallaTekijalla() {
        
    }

    @Test
    public void testHaeLehtiTunnuksella() {
        
    }

    @Test
    public void testHaeLehtiOlemattomallaTunnuksella() {
        
    }

    @Test
    public void testHaeLehtiISSN() {
        
    }

    @Test
    public void testHaeLehtiOlemattomallaISSN() {

    }

    @Test
    public void testHaeLehtiaNimella() {

    }

    @Test
    public void testHaeLehtiaOlemattomallaNimella() {

    }
}
