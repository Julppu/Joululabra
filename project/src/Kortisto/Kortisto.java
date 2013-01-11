
/**
 * Kortistoluokka, joka pitää sisällään tiedot teoksista ja lehdistä sekä
 * niiden kautta niteistä ja numeroista.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

package Kortisto;

import Kortisto.KortistoOperaatiot.*;
import Kortisto.Poikkeukset.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Kortisto implements Serializable {
    
    /** laskuri kirjan tunnistenumeroille, jota teoksen lisäys kasvattaa.  */
    private static int kirjaID;
    /** laskuri lehden tunnistenumeroille, jota lehden lisäys kasvattaa. */
    private static int lehtiID;
    /** laskuri niteiden viivakoodeille. muunnetaan niteen lisäyksessä String-muotoon. */
    private static int viivakoodi;
    /** aineiston hakemiseen käytettävän hakukoneen instanssi. */
    private Hakukone hakukone;
    /** lista kortiston teoksista. */
    private ArrayList<Teos> teokset;
    /** lista kortiston lehdistä. */
    private ArrayList<Lehti> lehdet;
    /** lista kortiston kokoelmista. */
    private ArrayList<String> kokoelmat;
    
    public Kortisto() {
        teokset = new ArrayList<Teos>();
        lehdet = new ArrayList<Lehti>();
        kokoelmat = new ArrayList<String>();
        hakukone = new Hakukone();
        kirjaID = 1;
        lehtiID = 1;
        viivakoodi = 100000;
    }
    
    /**
     * Metodi lisää kortistoon uuden teoksen ja lajittelee listan lisäyksen
     * jälkeen.
     * 
     * @param ISBN teoksen ISBN-numero
     * @param nimi teoksen nimi
     * @param tekija teoksen tekijä(t)
     * @param vuosi teoksen julkaisuvuosi
     * @param kustantaja teoksen kustantaja
     * 
     * @throws TeosFoundException  jos teos on jo kortistossa
     */
    public void lisaaTeos(String ISBN, String nimi, String tekija, int vuosi, String kustantaja) 
      throws TeosFoundException {
        if (hakukone.haeTeosISBN(this, ISBN) == null) {
            teokset.add(new Teos(kirjaID++, ISBN, nimi, tekija, vuosi, kustantaja));
            Collections.sort(teokset, new TeoksetNimiJarjestykseenComparator());
        } else
            throw new TeosFoundException();
    }
    
    /**
     * Poistaa teoksen listasta haettuaan sen tunnuksen perusteella.
     * 
     * @param ID teoksen tunnusluku
     */
    public void poistaTeos(int ID) throws TeosNotFoundException {
        teokset.remove(getTeosTunnuksella(ID));
        Collections.sort(teokset, new TeoksetNimiJarjestykseenComparator());
    }
    
    /**
     * Lisää kortiston teokseen niteen. Hakee teoksen tunnuksella, jonka
     * jälkeen lisää sen käyttäen niteen omaa metodia.
     * 
     * @param ID teoksen tunnusluku
     * @param lainaAika niteen laina-aika
     * @param kokoelma niteen kokoelma
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
     * @param  ID teoksen tunnus
     * @param  viivakoodi niteen viivakoodi
     * @throws TeosNotFoundException jos teosta ei löydy kortistosta
     * @throws NideNotFoundException jos nidettä ei löydy kortistosta
     * 
     * @see    Kortisto.Teos#poistaNide(java.lang.String)
     */
    public void poistaNide(int ID, String viivakoodi) 
            throws TeosNotFoundException, NideNotFoundException {
        for (Teos teos: teokset) {
            if (teos.getID() == ID) {
                teos.poistaNide(viivakoodi);
                return;
            }
        }
        throw new TeosNotFoundException(); 
    }
    
    /**
     * Hakee kortistosta teoksen ja palauttaa listan sen niteistä.
     * 
     * @param ID teoksen tunnus
     * @return teoksen niteet tai null jos teosta ei löydy.
     * @throws TeosNotFoundException jos teosta ei löydy kortistosta
     */
    public ArrayList<Nide> getTeoksenNiteet(int ID) throws TeosNotFoundException {
        for (Teos teos: teokset)
            if (teos.getID() == ID)
                return teos.getNiteet();
        throw new TeosNotFoundException();
    }
    
    /**
     * Lisää kortistoon uuden lehden jonka jälkeen lajittelee listan lehdistä.
     * Tarkistaa ensimmäisenä löytyykö lehteä jo kortistosta.
     * 
     * @param  ISSN lehden ISSN-numero
     * @param  nimi lehden nimi
     * @param  kustantaja lehden kustantaja
     * @throws LehtiFoundException poikkeus, jos lehti on jo kortistossa
     * 
     * @see   Kortisto.KortistoOperaatiot.Hakukone#haeLehtiISSN(Kortisto.Kortisto, java.lang.String)
     */
    public void lisaaLehti(String ISSN, String nimi, String kustantaja)
            throws LehtiFoundException {
        if (hakukone.haeLehtiISSN(this, ISSN) == null) {
            lehdet.add(new Lehti(lehtiID++, ISSN, nimi, kustantaja));
            Collections.sort(lehdet, new LehdetNimiJarjestykseenComparator());
        }
        else
            throw new LehtiFoundException();
    }
    
    /**
     * Poistaa lehden kortistosta sen tunnuksen perusteella
     * 
     * @param ID lehden tunnus
     */
    public void poistaLehti(int ID) throws LehtiNotFoundException {
        lehdet.remove(getLehtiTunnuksella(ID));
        Collections.sort(lehdet, new LehdetNimiJarjestykseenComparator());
    }
    
    /**
     * Lisää numeron kortiston lehteen hakemalla lehden sen tunnuksella ja
     * käyttämällä lehden omaa metodia.
     * 
     * @param ID lehden tunnus
     * @param vuosi numeron julkaisuvuosi
     * @param numero lehden numero
     * @throws LehtiNotFoundException jos lehteä ei löydy kortistosta
     * 
     * @see   Kortisto.Lehti#lisaaNumero(int, int)
     */
    public void lisaaNumero(int ID, int vuosi, int numero) throws LehtiNotFoundException {
        for (Lehti lehti: lehdet) {
            if (lehti.getID() == ID) {
                lehti.lisaaNumero(vuosi, numero);
                return;
            }
        }
        throw new LehtiNotFoundException();
    }
    
    /**
     * Poistaa kortiston lehden yhden numeron lehden omalla metodilla 
     * jos numero löytyy.
     * 
     * @param  ID lehden tunnus
     * @param  vuosi lehden julkaisuvuosi
     * @param  numero julkaisun numero
     * @throws LehtiNotFoundException jos lehteä ei löydy
     * @throws NumeroNotFoundException jos numeroa ei löydy
     * 
     * @see    Kortisto.Lehti#poistaNumero(int, int)
     */
    public void poistaNumero(int ID, int vuosi, int numero) 
            throws LehtiNotFoundException, NumeroNotFoundException {
        Lehti lehti = hakukone.haeLehtiTunnuksella(this, ID);
        if (lehti == null)
            throw new LehtiNotFoundException();
        else
            lehti.poistaNumero(vuosi, numero);
    }
    
    /**
     * Getteri lehden kaikille numeroille.
     * 
     * @param ID lehden tunnus
     * @return listan lehden kaikista numeroista
     * @throws LehtiNotFoundException jos teosta ei löydy kortistosta
     */
    public ArrayList<Numero> getLehdenNumerot(int ID) throws LehtiNotFoundException {
        for (Lehti lehti: lehdet)
            if (lehti.getID() == ID)
                return lehti.getNumerot();
        throw new LehtiNotFoundException();
    }
    
    /**
     * Hakee teoksen kortistosta tunnuksella ja palauttaa sen muokkaamista varten.
     * Muokkaamiseen ei siis käytetä hakukonetta.
     * 
     * @param ID haettavan teoksen tunnus
     * @return haettu teos
     * @throws TeosNotFoundException jos teosta ei löydy kortistosta
     */
    public Teos getTeosTunnuksella(int ID) throws TeosNotFoundException {
        for (Teos teos: teokset)
            if (teos.getID() == ID)
                return teos;
        throw new TeosNotFoundException();
    }
    
    /**
     * Hakee lehden kortistosta tunnuksella ja palauttaa sen muokkaamista varten.
     * Muokkaamiseen ei siis käytetä hakukonetta.
     * 
     * @param ID haettavan lehden tunnus
     * @return haettu lehti
     * @throws LehtiNotFoundException jos lehteä ei löydy kortistosta
     */
    public Lehti getLehtiTunnuksella(int ID) throws LehtiNotFoundException {
        for (Lehti lehti: lehdet)
            if (lehti.getID() == ID)
                return lehti;
        throw new LehtiNotFoundException();
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