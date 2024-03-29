package Kortisto;

import Kortisto.Poikkeukset.NideNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Luokka teoksille, joka sisältää teoksen tiedot ja siihen kuuluvat niteet.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

public class Teos implements Serializable {
    
    /** lista teokseen kuuluvista niteistä. */
    private ArrayList<Nide> niteet;
    /** lista teoksen hakusanoista. */
    private ArrayList<String> hakusanat;
    /** teoksen uniikki tunnisteluku. */
    private int ID;
    /** teoksen ISBN-tunnistenumero. */
    private String ISBN;
    /** teoksen nimi. */
    private String nimi;
    /** teoksen tekijä, jos toimitettu asetetaan "(toim).". */
    private String tekija;
    /** teoksen painovuosi. */
    private int vuosi;
    /** teoksen kustantaja. */
    private String kustantaja;
    
    /**
     * Konstruktori alustaa muuttujat parametreinä saamistaan arvoista sekä alustaa
     * niteiden ja hakusanojen listat.
     * 
     * @param ID teoksen tunnusluku
     * @param ISBN teoksen ISBN-numero
     * @param nimi teoksen nimi
     * @param tekija teokse tekija(t)
     * @param vuosi julkaisuvuosi
     * @param kustantaja kustantaja
     */
    public Teos(int ID, String ISBN, String nimi, String tekija, int vuosi, String kustantaja) {
        this.ID = ID;
        this.ISBN = ISBN;
        this.nimi = nimi;
        this.tekija = tekija;
        this.vuosi = vuosi;
        this.kustantaja = kustantaja;
        this.niteet = new ArrayList<Nide>();
        this.hakusanat = new ArrayList<String>();
    }
    
    /**
     * Lisää teoksen listaan uuden niteen.
     * 
     * @param viivakoodi  Lisättävän niteen viivakoodi
     * @param lainaAika   Lisättävän niteen laina-aika
     * @param kokoelma    Lisättävän niteen kokoelma
     */
    public void lisaaNide(String viivakoodi, int lainaAika, String kokoelma) {
        niteet.add(new Nide(this.ID, viivakoodi, lainaAika, kokoelma));
    }
    
    /**
     * Poistaa teoksen listasta niteen viivakoodin perusteella.
     * 
     * @param  viivakoodi niteen viivakoodi
     * @throws Exception  poikkeus, jos nidettä ei löydy listasta
     */
    public void poistaNide(String viivakoodi) throws NideNotFoundException {
        for (Nide nide: niteet) {
            if (nide.getViivakoodi().equals(viivakoodi)) {
                niteet.remove(nide);
                return;
            }
        }
        throw new NideNotFoundException();
    }
    
    /**
     * Lisää teoksen listaan uuden hakusanan.
     * 
     * @param hakusana lisättävä hakusana
     */
    public void lisaaHakusana(String hakusana) {
        if (!hakusanat.contains(hakusana))
            hakusanat.add(hakusana);
        Collections.sort(hakusanat);
    }
    
    /**
     * Poistaa teoksen listasta hakusanan.
     * 
     * @param hakusana poistettava hakusana
     */
    public void poistaHakusana(String hakusana) {
        hakusanat.remove(hakusana);
        Collections.sort(hakusanat);
    }
    
    /**
     * Getteri listalle teoksen hakusanoista.
     * 
     * @return lista teoksen hakusanoista
     */
    public ArrayList<String> getHakusanat() {
        return hakusanat;
    }
    
    /**
     * Getteri listalle teoksen niteistä.
     * 
     * @return lista teoksen kaikista niteistä
     */
    public ArrayList<Nide> getNiteet() {
        return niteet;
    }

    /**
     * Getteri teoksen uniikille tunnukselle.
     * 
     * @return teoksen tunnus 
     */
    public int getID() {
        return ID;
    }
    
    /**
     * Getteri teoksen ISBN-luvulle
     * 
     * @return teoksen ISBN-luku 
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * Setteri teoksen uniikille tunnukselle.
     * 
     * @param ID uusi tunnusluku
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Getteri teoksen nimelle.
     * 
     * @return teoksen nimi
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Setteri teoksen nimen muuttamiseksi.
     * 
     * @param nimi teoksen uusi nimi
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    /**
     * Getteri teoksen tekijän nimelle.
     * 
     * @return tekijän nimi 
     */
    public String getTekija() {
        return tekija;
    }

    /**
     * Setteri teoksen tekijän muuttamiseksi.
     * 
     * @param tekija uusi tekijän nimi
     */
    public void setTekija(String tekija) {
        this.tekija = tekija;
    }

    /**
     * Getteri teoksen julkaisuvuodelle
     * 
     * @return julkaisuvuosi 
     */
    public int getVuosi() {
        return vuosi;
    }

    /**
     * Setteri teoksen julkaisuvuoden muuttamiseksi.
     * 
     * @param vuosi uusi vuosiluku
     */
    public void setVuosi(int vuosi) {
        this.vuosi = vuosi;
    }

    /**
     * Getteri teoksen kustantajalle.
     * 
     * @return teoksen kustantaja
     */
    public String getKustantaja() {
        return kustantaja;
    }

    /**
     * Setteri teoksen uuden kustantajan asettamiseksi.
     * 
     * @param kustantaja uusi kustantaja
     */
    public void setKustantaja(String kustantaja) {
        this.kustantaja = kustantaja;
    }
    
    /**
     * Palauttaa teoksen kuvauksen String-muodossa
     * @return teoksen kuvaus
     */
    @Override
    public String toString() {
        return "ISBN " + ISBN + ": " + nimi + ", " + tekija + ", " + vuosi +
                ". Tunnus " + ID + ", niteitä " + niteet.size();
    }
}