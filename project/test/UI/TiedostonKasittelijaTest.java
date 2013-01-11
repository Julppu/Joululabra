package UI;

import Kortisto.Kortisto;
import Kortisto.Poikkeukset.LehtiFoundException;
import Kortisto.Poikkeukset.LehtiNotFoundException;
import Kortisto.Poikkeukset.TeosFoundException;
import java.io.File;
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
            kortisto.lisaaNide(1, 14, "testikokoelma");
            kortisto.lisaaNide(1, 14, "testikokoelma");
            kortisto.lisaaNide(2, 2, "lyhytlainat");
            kortisto.lisaaNide(2, 2, "lyhytlainat");
            kortisto.lisaaNumero(1, 2012, 52);
            kortisto.lisaaNumero(1, 2013, 1);
            kortisto.lisaaNumero(2, 2010, 12);
            kortisto.lisaaNumero(2, 2010, 11);
        } catch (TeosFoundException tex) {
        } catch (LehtiFoundException lex) {
        } catch (LehtiNotFoundException lnex) {}
        try {
            tiedKas = new TiedostonKasittelija("testi.dat");
        } catch (IOException ioex) { 
            ioex.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Tarkistaa samalla sekä tiedostoon kirjoittamisen että siitä lukemisen.
     * 
     * @throws Exception tiedostonkirjoituksen poikkeukset
     */
    @Test
    public void testLueTiedosto() throws Exception {
        tiedKas.kirjoitaTiedosto(kortisto);
        kortisto = null;
        Kortisto uusiKortisto = tiedKas.lueTiedosto();
        assertTrue(uusiKortisto.getKokoelmat().contains("testikokoelma"));
    }

    @Test
    public void testLueUusiTiedosto() throws Exception {
        kortisto = tiedKas.lueUusiTiedosto("tyhjatesti.dat");
        assertTrue(kortisto.getLehdet().isEmpty() && kortisto.getTeokset().isEmpty());
    }

    @Test
    public void testKirjoitaUusiTiedosto() throws Exception {
        tiedKas.kirjoitaUusiTiedosto(kortisto, "kortisto.dat");
        Kortisto uusiKortisto = tiedKas.lueTiedosto();
        assertTrue(uusiKortisto.getKokoelmat().contains("testikokoelma"));
    }
    
    @Test
    public void testLueTiedostoMontaKertaa() throws Exception {
        tiedKas.kirjoitaTiedosto(kortisto);
        kortisto = tiedKas.lueTiedosto();
        Kortisto uusiKortisto = tiedKas.lueTiedosto();
        Kortisto uudempiKortisto = tiedKas.lueTiedosto();
        assertEquals(uusiKortisto.getKokoelmat().contains("testikokoelma"),
                uudempiKortisto.getKokoelmat().contains("lyhytlainat"));
    }
    
    @Test
    public void testKirjoitaUusiTiedostoMontaKertaa() throws Exception {
        for (int i = 0; i < 10; i++)
            tiedKas.kirjoitaUusiTiedosto(kortisto, "testi" + i + ".dat");
    }
        
    @Test
    public void testLueUusiTiedostoMontaKertaa() throws Exception {
        for (int i = 0; i < 10; i++)
            tiedKas.kirjoitaUusiTiedosto(kortisto, "testi" + i + ".dat");
        for (int i = 0; i < 10; i++)
            kortisto = tiedKas.lueUusiTiedosto("testi" + i + ".dat");
    }
}
