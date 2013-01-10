package UI;

import Kortisto.Kortisto;
import Kortisto.Poikkeukset.LehtiFoundException;
import Kortisto.Poikkeukset.LehtiNotFoundException;
import Kortisto.Poikkeukset.TeosFoundException;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since 10012013
 */
public class TiedostonKasittelijaTest {

    private Kortisto kortisto;
    private TiedostonKasittelija tiedKas;

    public TiedostonKasittelijaTest() {
    }

    @Before
    public void setUp() {
        kortisto = new Kortisto();
        try {
            kortisto.lisaaTeos("0001", "Aapinen", "Aapiskukko", 2010, "WSOY");
            kortisto.lisaaTeos("0002", "Aapinen", "Aapiskukko", 2010, "WSOY");
            kortisto.lisaaTeos("0003", "Raamattu", "Jeesus", 0, "Jessen kirjuritoimisto");
            kortisto.lisaaLehti("0004", "Helsingin Sanomat", "Sanoma");
            kortisto.lisaaLehti("0005", "Iltasanomat", "Sanoma");
        } catch (TeosFoundException tex) {
        } catch (LehtiFoundException lex) {
        }
        kortisto.lisaaNide(1, 14, "testikokoelma");
        kortisto.lisaaNide(1, 14, "testikokoelma");
        kortisto.lisaaNide(2, 2, "lyhytlainat");
        kortisto.lisaaNide(2, 2, "lyhytlainat");
        kortisto.lisaaNumero(1, 2012, 52);
        kortisto.lisaaNumero(1, 2013, 1);
        kortisto.lisaaNumero(2, 2010, 12);
        kortisto.lisaaNumero(2, 2010, 11);
        try {
            tiedKas = new TiedostonKasittelija("testi.dat");
        } catch (Exception ioex) { 
            if (ioex.getClass().equals(IOException.class))
                ioex.printStackTrace();
        } //
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testLueTiedosto() throws Exception {
        boolean test = false;
        tiedKas.kirjoitaTiedosto(kortisto);
        kortisto = null;
        Kortisto uusiKortisto = tiedKas.lueTiedosto();
        if (uusiKortisto.getKokoelmat().contains("testikokoelma"))
            test = true;
        assertTrue(test);
    }

    @Test
    public void testLueUusiTiedosto() throws Exception {
    }

    @Test
    public void testKirjoitaTiedosto() throws Exception {
    }

    @Test
    public void testKirjoitaUusiTiedosto() throws Exception {
    }
}
