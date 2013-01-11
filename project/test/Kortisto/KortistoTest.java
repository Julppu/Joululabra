
package Kortisto;

import Kortisto.KortistoOperaatiot.Hakukone;
import Kortisto.Poikkeukset.*;
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
            kortisto.lisaaLehti("0003", "Helsingin Sanomat", "Sanoma");
            kortisto.lisaaNide(1, 0, "testikokoelma");
            kortisto.lisaaNide(2, 0, "testikokoelma");
            kortisto.lisaaNumero(1, 2002, 1);
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
            kortisto.lisaaTeos("0004", "Miksi pitää tehdä JUnitia?", "Keijo Käpistelijä", 
                2012, "omakustanne");
        } catch (Exception ex) {
            System.out.println(ex);
            fail("Poikkeus napattu.");
        }
        for (Teos teos: kortisto.getTeokset())
            if (teos.getISBN().equals("0004"))
                test = true;
        assertTrue(test);
    }
    
    @Test (expected=TeosFoundException.class)
    public void testLisaaOlemassaolevaTeos() throws TeosFoundException {
        kortisto.lisaaTeos("0001", "Aapinen", "Aapiskukko", 2000, "WSOY");
    }

    @Test
    public void testPoistaTeos() throws TeosNotFoundException {
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
        for (Teos teos: kortisto.getTeokset()) {
            if (teos.getID() == 1) {
                for (Nide nide: teos.getNiteet()) {
                    if (nide.getViivakoodi().equals("100002"))
                        test = true;
                }
                break;
            }
        }
        assertTrue(test);
    }

    @Test
    public void testPoistaNide() throws TeosNotFoundException {
        ArrayList<Nide> niteet = null;
        try {
            kortisto.poistaNide(1, "100000");
            niteet = kortisto.getTeosTunnuksella(1).getNiteet();
        } catch (Exception ex) {
            System.out.println(ex);
            fail("Poikkeus napattu.");
        }
        assertTrue(niteet.isEmpty());
    }
    
    @Test (expected=NideNotFoundException.class)
    public void testPoistaOlematonNide() throws Exception {
        kortisto.poistaNide(1, "0100");
    }
    
    @Test
    public void testLisaaLehti() {
        int pituus = kortisto.getLehdet().size();
        try {
            kortisto.lisaaLehti("0005", "Aku ankka", "Sanoma");
        } catch (Exception ex) {
            System.out.println(ex);
            fail("Poikkeus napattu.");
        }
        assertEquals(kortisto.getLehdet().size(), pituus + 1);
    }
    
    @Test  (expected=LehtiFoundException.class)
    public void testLisaaOlemassaolevaLehti() throws LehtiFoundException {
        kortisto.lisaaLehti("0003", "Helsingin sanomat", "Sanoma");
    }

    @Test
    public void testPoistaLehti() {
        try {
            kortisto.poistaLehti(1);
        } catch (LehtiNotFoundException lnfe) {
            fail("Lehteä ei löytynyt.");
        }
        assertTrue(kortisto.getLehdet().isEmpty());
    }
    
    @Test (expected=LehtiNotFoundException.class)
    public void testPoistaOlematonLehti() throws LehtiNotFoundException {
        kortisto.poistaLehti(20);
    }
    
    @Test
    public void testLisaaNumero() {
        try {
            kortisto.lisaaNumero(1, 2000, 21);
        } catch (LehtiNotFoundException lnfe) {
            fail("Lehteä ei löytynyt.");
        }
        test = false;
        for (Lehti lehti: kortisto.getLehdet()) {
            if (lehti.getID() == 1) {
                for (Numero numero: lehti.getNumerot()) {
                    if (numero.getNumero() == 21 && numero.getVuosi() == 2000) {
                        test = true;
                        break;
                    }
                }
            }
        }
        assertTrue(test);
    }

    @Test
    public void testLisaaOlemassaOlevaNumero() throws LehtiNotFoundException {
        kortisto.lisaaNumero(1, 2000, 21);
        kortisto.lisaaNumero(1, 2000, 22);
        kortisto.lisaaNumero(1, 2000, 21);
        assertEquals(3, kortisto.getLehdenNumerot(1).size());
        
    }

    @Test
    public void testPoistaNumero() throws LehtiNotFoundException {
        try {
            kortisto.poistaNumero(1, 2002, 1);
        } catch (Exception ex) {
            System.out.println(ex);
            fail("Poikkeus napattu.");
        }
        assertEquals(0, kortisto.getLehdenNumerot(1).size());
    }
    
    @Test (expected=NumeroNotFoundException.class)
    public void testPoistaOlematonNumero() throws NumeroNotFoundException {
        try {
            kortisto.poistaNumero(1, 2002, 10);
        } catch(LehtiNotFoundException ex) {
            System.out.println(ex);
            fail("Poikkeus napattu.");
        }
    }
    
    @Test
    public void testGetTeosTunnuksella() throws TeosNotFoundException {
        assertEquals("Aapinen", kortisto.getTeosTunnuksella(1).getNimi());
    }
    
    @Test (expected=TeosNotFoundException.class)
    public void testGetTeosOlemattomallaTunnuksella() throws TeosNotFoundException {
        kortisto.getTeosTunnuksella(666);
    }
    
    @Test
    public void testMuutaTeoksenNimea() throws TeosNotFoundException {
        kortisto.getTeosTunnuksella(1).setNimi("Kekkonen");
        assertEquals("Kekkonen", kortisto.getTeosTunnuksella(1).getNimi());
    }
    
    @Test
    public void testMuutaTeoksenTekijaa() throws TeosNotFoundException {
        kortisto.getTeosTunnuksella(1).setTekija("Kekkonen");
        assertEquals("Kekkonen", kortisto.getTeosTunnuksella(1).getTekija());
    }
    
    @Test
    public void testMuutaTeoksenKustantaja() throws TeosNotFoundException {
        kortisto.getTeosTunnuksella(1).setKustantaja("Omakustanne");
        assertEquals("Omakustanne", kortisto.getTeosTunnuksella(1).getKustantaja());
    }
    
    @Test
    public void testMuutaTeoksenVuosi() throws TeosNotFoundException {
        kortisto.getTeosTunnuksella(1).setVuosi(2012);
        assertEquals(2012, kortisto.getTeosTunnuksella(1).getVuosi());
    }
    
        @Test
    public void testMuutaLehdenNimea() throws LehtiNotFoundException {
        kortisto.getLehtiTunnuksella(1).setNimi("Journal of Computing");
        assertEquals("Journal of Computing", kortisto.getLehtiTunnuksella(1).getNimi());
    }
    
    @Test
    public void testMuutaLehdenKustantaja() throws LehtiNotFoundException {
        kortisto.getLehtiTunnuksella(1).setKustantaja("Omakustanne");
        assertEquals("Omakustanne", kortisto.getLehtiTunnuksella(1).getKustantaja());
    }
    
    @Test
    public void testGetTeokset() {
       ArrayList<Teos> teokset = kortisto.getTeokset();
       int testi = teokset.size();
       assertEquals(2, testi);
    }

    @Test
    public void testGetLehdet() {
        int testi = 0;
        for (Lehti lehti: kortisto.getLehdet())
            if (lehti.getNimi().equals("Helsingin Sanomat"))
                testi++;
        assertEquals(1, testi);
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
        ArrayList<String> testi = new ArrayList<String>();
        testi.add("testikokoelma");
        assertEquals(testi, kortisto.getKokoelmat());
    }
}