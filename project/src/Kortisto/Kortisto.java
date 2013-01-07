
/**
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

package Kortisto;

import Kortisto.KortistoOperaatiot.*;
import java.util.ArrayList;
import java.util.Collections;

public class Kortisto {
    
    private static int kirjaID;
    private static int lehtiID;
    private static int viivakoodi;
    private ArrayList<Teos> teokset;
    private ArrayList<Lehti> lehdet;
    private ArrayList<String> kokoelmat;
    private Hakukone hakukone;
    
    public Kortisto() {
        teokset = new ArrayList();
        lehdet = new ArrayList();
        kokoelmat = new ArrayList();
        hakukone = new Hakukone();
        kirjaID = 1;
        lehtiID = 1;
        viivakoodi = 100000;
    }
    
    /**
     * Metodi lisää kortistoon uuden teoksen ja lajittelee listan lisäyksen
     * jälkeen.
     * 
     * @param ISBN        teoksen ISBN-numero
     * @param nimi        teoksen nimi
     * @param tekija      teoksen tekijä(t)
     * @param vuosi       teoksen julkaisuvuosi
     * @param kustantaja  teoksen kustantaja
     * 
     * @throws Exception  heittää poikkeuksen jos teos löytyy jo kortistosta
     *                    ja jättää sen lisäämättä.
     */
    public void lisaaTeos(String ISBN, String nimi, String tekija, int vuosi, String kustantaja) 
      throws Exception {
        if (hakukone.haeTeosISBN(this, ISBN) == null) {
            teokset.add(new Teos(kirjaID++, ISBN, nimi, tekija, vuosi, kustantaja));
            Collections.sort(teokset, new TeoksetNimiJarjestykseenComparator());
        } else
            throw new Exception("Teos on jo kortistossa, ei lisätty.");
    }
    
    /**
     * Poistaa teoksen listasta haettuaan sen tunnuksen perusteella.
     * 
     * @param ID teoksen tunnusluku
     */
    public void poistaTeos(int ID) {
        teokset.remove(hakukone.haeTeosTunnuksella(this, ID));
        Collections.sort(teokset, new TeoksetNimiJarjestykseenComparator());
    }
    
    /**
     * Lisää kortiston teokseen niteen. Hakee teoksen tunnuksella, jonka
     * jälkeen lisää sen käyttäen niteen omaa metodia.
     * 
     * @param ID         teoksen tunnusluku
     * @param lainaAika  niteen laina-aika
     * @param kokoelma   niteen kokoelma
     * 
     * @see   Kortisto.Teos#lisaaNide(java.lang.String, int, java.lang.String) 
     */
    public void lisaaNide(int ID, int lainaAika, String kokoelma) {
        for (Teos teos: teokset) {
            if (teos.getID() == ID) {
                teos.lisaaNide(Integer.toString(viivakoodi++), lainaAika, kokoelma);
                break;
            }
        }
        if (!kokoelmat.contains(kokoelma))
            kokoelmat.add(kokoelma);
        Collections.sort(kokoelmat);
    }
    
    /**
     * Poistaa niteen kortistosta hakemalla sen teoksen tunnuksen perusteella.
     * Käyttää niteen omaa metodia.
     * 
     * @param  ID         teoksen tunnus
     * @param  viivakoodi niteen viivakoodi
     * @throws Exception  poikkeus niteen puuttuessa tiedoista
     * 
     * @see    Kortisto.Teos#poistaNide(java.lang.String)
     */
    public void poistaNide(int ID, String viivakoodi) throws Exception {
        for (Teos teos: teokset) {
            if (teos.getID() == ID) {
                teos.poistaNide(viivakoodi);
                return;
            }
        }
        throw new Exception("Nidettä ei löytynyt, ei poistettu."); 
    }
    
    /**
     * Hakee kortistosta teoksen ja palauttaa listan sen niteistä.
     * 
     * @param ID    teoksen tunnus
     * @return      teoksen niteet tai null jos teosta ei löydy.
     */
    public ArrayList<Nide> getTeoksenNiteet(int ID) {
        for (Teos teos: teokset)
            if (teos.getID() == ID)
                return teos.getNiteet();
        return null;
    }
    
    /**
     * Lisää kortistoon uuden lehden jonka jälkeen lajittelee listan lehdistä.
     * 
     * @param  ISSN        lehden ISSN-numero
     * @param  nimi        lehden nimi
     * @param  kustantaja  lehden kustantaja
     * @throws Exception   poikkeus, jos lehti on jo kortistossa
     * 
     * @see   Kortisto.Lehti
     */
    public void lisaaLehti(String ISSN, String nimi, String kustantaja)
      throws Exception {
        if (hakukone.haeLehtiISSN(this, ISSN) == null) {
            lehdet.add(new Lehti(lehtiID++, ISSN, nimi, kustantaja));
            Collections.sort(lehdet, new LehdetNimiJarjestykseenComparator());
        }
        else
            throw new Exception("Lehti on jo kortistossa, ei lisätty.");
    }
    
    /**
     * Poistaa lehden kortistosta sen tunnuksen perusteella
     * 
     * @param ID lehden tunnus
     */
    public void poistaLehti(int ID) {
        lehdet.remove(hakukone.haeLehtiTunnuksella(this, ID));
        Collections.sort(lehdet, new LehdetNimiJarjestykseenComparator());
    }
    
    /**
     * Lisää numeron kortiston lehteen hakemalla lehden sen tunnuksella ja
     * käyttämällä lehden omaa metodia.
     * 
     * @param ID     lehden tunnus
     * @param vuosi  numeron julkaisuvuosi
     * @param numero lehden numero
     * 
     * @see   Kortisto.Lehti#lisaaNumero(int, int)
     */
    public void lisaaNumero(int ID, int vuosi, int numero) {
        for (Lehti lehti: lehdet) {
            if (lehti.getID() == ID) {
                lehti.lisaaNumero(vuosi, numero);
                break;
            }
        }
    }
    
    /**
     * Poistaa kortiston lehden yhden numeron lehden omalla metodilla 
     * jos numero löytyy.
     * 
     * @param  ID          lehden tunnus
     * @param  vuosi       lehden julkaisuvuosi
     * @param  numero      julkaisun numero
     * @throws Exception   poikkeus, jos lehteä ei löydy
     * 
     * @see    Kortisto.Lehti#poistaNumero(int, int)
     */
    public void poistaNumero(int ID, int vuosi, int numero) throws Exception {
        for (Lehti lehti: lehdet) {
            if (lehti.getID() == ID) {
                lehti.poistaNumero(vuosi, numero);
                return;
            }
        }
        throw new Exception("Lehteä ei löytynyt, ei poistettu.");
    }
    
    /**
     * Getteri lehden kaikille numeroille.
     * 
     * @param ID lehden tunnus
     * @return   listan lehden kaikista numeroista
     */
    public ArrayList<Numero> getLehdenNumerot(int ID) {
        for (Lehti lehti: lehdet)
            if (lehti.getID() == ID)
                return lehti.getNumerot();
        return null;
    }
    
    /**
     * Getteri kortiston kaikille teoksille.
     * 
     * @return lista kortiston kaikista teoksista
     */
    public ArrayList<Teos> getTeokset() {
        return teokset;
    }
    
    /**
     * Getteri kortiston kaikille lehdille.
     * 
     * @return lista kortiston kaikista lehdistä.
     */
    public ArrayList<Lehti> getLehdet() {
        return lehdet;
    }
    
    /**
     * Metodi kokoelman manuaalista lisäystä varten.
     * 
     * @param kokoelma lisättävä kokoelma
     */
    public void lisaaKokoelma(String kokoelma) {
        kokoelmat.add(kokoelma);
        Collections.sort(kokoelmat);
    }
    
    /**
     * Metodi kokoelman manuaaliselle poistamiselle.
     * 
     * @param kokoelma poistettava kokoelma
     */
    public void poistaKokoelma(String kokoelma) {
        kokoelmat.remove(kokoelma);
        Collections.sort(kokoelmat);
    }
    
    /**
     * Getteri kortiston kaikille kokoelmille.
     * 
     * @return lista kortiston kokoelmista
     */
    public ArrayList<String> getKokoelmat() {
        return kokoelmat;
    }

    /**
     * Getteri hakukone-instanssille.
     * 
     * @return hakukone-instanssi
     */
    public Hakukone getHakukone() {
        return hakukone;
    }

    /**
     * Setteri uudelle hakukone-instanssille.
     * 
     * @param hakukone uusi Hakukone
     */
    public void setHakukone(Hakukone hakukone) {
        this.hakukone = hakukone;
    }
}
