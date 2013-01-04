
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
            kortisto.lisaaTeos("0004", "Miksi pitää tehdä JUnitia?", "Keijo Käpistelijä", 
                2012, "omakustanne");
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
        kortisto.poistaNide(1, "100003");
        assertTrue(hakukone.haeTeosTunnuksella(kortisto, 1).getNiteet().isEmpty());
    }
    
    @Test
    public void testPoistaOlematonNide() {
        int pituusAlku, pituusLoppu;
        ArrayList<Nide> niteet;
        for (Teos teos: kortisto.getTeokset())
            if (teos.getID() == 1)
                niteet = teos.getNiteet();
        pituusAlku = niteet.length();
        kortisto.poistaNide(1, "0100");
        pituusLoppu = niteet.length();
        assertFalse(pituusLoppu == pituusAlku);
    }
    
    @Test
    public void testLisaaLehti() {
        int pituus = kortisto.getLehdet().length();
        kortisto.lisaaLehti("0005", "Aku ankka", "Sanoma");
        assertEquals(kortisto.getLehdet().length(), pituus + 1);
    }
    
    @Test  (expected=Exception.class)
    public void testLisaaOlemassaolevaLehti() throws Exception {
        kortisto.lisaaLehti("0003", "Helsingin sanomat", "Sanoma");
    }

    @Test
    public void testPoistaLehti() {
        test = false;
        kortisto.poistaLehti(3);
        if (kortisto.getLehdet().length() == 0)
            test = true;
        assertTrue(test);
    }
    
    @Test
    public void testPoistaOlematonLehti() {
        test = false;
        kortisto.poistaLehti(20);
        if (kortisto.getLehdet().length() == 1)
            test = true;
        assertTrue(test);
    }
    
    @Test
    public void testLisaaNumero() {
        kortisto.lisaaNumero(1, 2000, 21);
        test = false;
        for (Lehti lehti: kortisto.getLehdet())
            if (lehti.getID() == 1) {
                for (Numero numero: lehti.getNumerot())
                    if (numero.getNumero() == 21 && numero.getVuosi() == 2000)
                        test = true;
        assertTrue(test);
    }

    @Test
    public void testLisaaOlemassaOlevaNumero() {
        int numeroita = 0;
        kortisto.lisaaNumero(1, 2000, 21);
        kortisto.lisaaNumero(1, 2000, 22);
        kortisto.lisaaNumero(1, 2000, 21);
        for (Lehti lehti: kortisto.getLehdet())
            if (lehti.getID() == 1)
                numeroita = lehti.getNumerot().length();
        assertEquals(numeroita, 2);
    }

    @Test
    public void testPoistaNumero() {
        for (Lehti lehti: kortisto.getLehdet())
            
    }
    
    @Test
    public void testPoistaOlematonNumero() {
        
    }
    
    @Test
    public void testGetTeokset() {
       ArrayList<Teos> teokset = kortisto.getTeokset();
       int testi = 0;
       for (Teos teos: teokset)
           if (teos.getISBN() == "0001" || teos.getISBN() == "0002");
               testi++;
       assertEquals(testi, 2); 
    }

    @Test
    public void testGetLehdet() {
        kortisto.lisaaLehti("0004", "Iltasanomat", "Sanoma");
        ArrayList<Lehti> lehdet = kortisto.getLehdet();
        int testi = 0;
        for (Lehti lehti: lehdet)
            if (lehti.getNimi().equals("Helsingin Sanomat") || lehti.getNimi().equals("Iltasanomat"))
                testi++;
        assertEquals(testi, 2);
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
