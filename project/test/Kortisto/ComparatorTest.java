package Kortisto;

import Kortisto.KortistoOperaatiot.*;
import Kortisto.Poikkeukset.LehtiNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ComparatorTest {
    
    private Hakukone hakukone;
    private Kortisto kortisto;
    
    public ComparatorTest() {
    }
    
    @Before
    public void setUp() {
        hakukone = new Hakukone();
        kortisto = new Kortisto();
        try {
            kortisto.lisaaTeos("0001", "Aapinen", "Aapiskukko", 2010, "WSOY");
            kortisto.lisaaTeos("0002", "Aapinen", "Aapiskukko", 2010, "WSOY");
            kortisto.lisaaTeos("0003", "Raamattu", "Jeesus", 0, "Jessen kirjuritoimisto");
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
    public void testLehdetNimiJarjestykseen() {
        ArrayList<Lehti> lehdet = kortisto.getLehdet();
        Comparator<Lehti> comparator = new LehdetNimiJarjestykseenComparator();
        Collections.sort(lehdet, comparator);
        assertTrue(lehdet.get(0).getNimi().compareTo(lehdet.get(1).getNimi()) < 1);
    }
    
    @Test
    public void testNumerotJarjestykseen() {
        Comparator<Numero> comparator = new NumerotJarjestykseenComparator();
        ArrayList<Numero> numerot = null;;
        try {
            numerot = kortisto.getLehdenNumerot(2);
        } catch (LehtiNotFoundException lnfe) {
            System.out.println("Lehteä ei löytynyt.");
            fail();
        }
        Collections.sort(numerot, comparator);
        assertTrue(numerot.get(0).getNumero() < numerot.get(1).getNumero() 
                && numerot.get(0).getVuosi() == numerot.get(1).getVuosi());
    }
        
    @Test
    public void testTeoksetNimiJarjestykseen() {
        ArrayList<Teos> teokset = kortisto.getTeokset();
        Comparator<Teos> comparator = new TeoksetNimiJarjestykseenComparator();
        Collections.sort(teokset, comparator);
        assertEquals(teokset.get(0).getNimi() + teokset.get(0).getTekija(),
                     teokset.get(1).getNimi() + teokset.get(1).getTekija());
    }
            
    @Test
    public void testTeoksetTekijaJarjestykseen() {
        ArrayList<Teos> teokset = kortisto.getTeokset();
        Comparator<Teos> comparator = new TeoksetTekijaJarjestykseenComparator();
        Collections.sort(teokset, comparator);
        assertEquals("Jeesus", teokset.get(2).getTekija());
    }
    
    @Test
    public void testTeoksetVuosiJarjestykseen() {
        ArrayList<Teos> teokset = kortisto.getTeokset();
        Comparator<Teos> comparator = new TeoksetVuosiJarjestykseenComparator();
        Collections.sort(teokset, comparator);
        assertEquals(0, teokset.get(0).getVuosi());
    }
}