package Kortisto;

import Kortisto.KortistoOperaatiot.NumerotJarjestykseenComparator;
import Kortisto.Poikkeukset.NumeroNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Luokka lehdille, joka sisältää kaikki kyseisen instanssin numerot.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

public class Lehti implements Serializable {
    
    /** lehden uniikki tunniste. */
    private int ID;
    /** lehden ISSN-tunnistenumero. */
    private String ISSN;
    /** lehden nimi. */
    private String nimi;
    /** lehden julkaisija. */
    private String kustantaja;
    /** lista lehden hakusanoista. */
    private ArrayList<String> hakusanat;
    /** lista lehden numeroista. */
    private ArrayList<Numero> numerot;
    
    /**
     * Alustaa lehden tarvittavat kentät parametreina saamistaan arvoista sekä
     * alustaa listat niteille ja hakusanoille.
     * @param ID lehden tunnusluku
     * @param ISSN lehden ISSN-numero
     * @param nimi lehden nimi
     * @param kustantaja kustantaja
     */
    public Lehti(int ID, String ISSN, String nimi, String kustantaja) {
        this.ID = ID;
        this.ISSN = ISSN;
        this.nimi = nimi;
        this.kustantaja = kustantaja;
        this.numerot = new ArrayList<Numero>();
        this.hakusanat = new ArrayList<String>();
    } 
    
    /**
     * Lisää uuden numeron lehden listaan sekä lajittelee sen.
     * 
     * @param vuosi julkaisuvuosi
     * @param numero numero
     */
    public void lisaaNumero(int vuosi, int numero) {
        for (Numero lehdenNumero: numerot)
            if (lehdenNumero.getVuosi() == vuosi && lehdenNumero.getNumero() == numero)
                return;
        numerot.add(new Numero(ID, vuosi, numero));
        Collections.sort(numerot, new NumerotJarjestykseenComparator());
    }
    
    /**
     * Poistaa numeron lehden listasta, antaa poikkeuksen jos numeroa ei löydy.
     * 
     * @param vuosi julkaisuvuosi
     * @param numero numero
     * @throws NumeroNotFoundException jos numeroa ei löydy
     */
    public void poistaNumero(int vuosi, int numero) 
            throws NumeroNotFoundException {
        Numero lehdenNumero = null;
        for (Numero haettavaNumero: numerot)
            if (haettavaNumero.getNumero() == numero && haettavaNumero.getVuosi() == vuosi)
                lehdenNumero = haettavaNumero;
        if (lehdenNumero == null)
            throw new NumeroNotFoundException();
        else {
            numerot.remove(lehdenNumero);
            Collections.sort(numerot, new NumerotJarjestykseenComparator());
        }
    }
    
    /**
     * Getteri listalle lehden numeroista.
     * 
     * @return lista numeroista
     */
    public ArrayList<Numero> getNumerot() {
        return numerot;
    }
    
    /**
     * Lisää hakusanan lehden listaan ja lajittelee sen.
     * 
     * @param hakusana lisättävä hakusana
     */
    public void lisaaHakusana(String hakusana) {
        if (!hakusanat.contains(hakusana))
            hakusanat.add(hakusana);
        Collections.sort(hakusanat);
    }
        
    /**
     * Poistaa hakusanan lehden listasta ja lajittelee sen.
     * 
     * @param hakusana poistettava hakusana
     */
    public void poistaHakusana(String hakusana) {
        hakusanat.remove(hakusana);
        Collections.sort(hakusanat);
    }
    
    /**
     * Getteri listalle lehden hakusanoista.
     * 
     * @return lista hakusanoista
     */
    public ArrayList<String> getHakusanat() {
        return hakusanat;
    }
    
    /**
     * Getteri lehden uniikille tunnusluvulle.
     * 
     * @return tunnusluku
     */
    public int getID() {
        return ID;
    }

    /**
     * Setteri uudelle tunnusluvulle.
     * 
     * @param ID uusi tunnusluku
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Getteri lehden ISSN-numerolle.
     * 
     * @return ISSN-luku
     */
    public String getISSN() {
        return ISSN;
    }

    /**
     * Setteri uudelle ISSN-numerolle.
     * 
     * @param ISBN uusi ISSN
     */
    public void setISSN(String ISBN) {
        this.ISSN = ISBN;
    }

    /**
     * Getteri lehden nimelle.
     * 
     * @return nimi
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Setteri lehden uudelle nimelle.
     * 
     * @param nimi uusi nimi
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    /**
     * Getteri lehden kustantajalle.
     * 
     * @return kustantaja
     */
    public String getKustantaja() {
        return kustantaja;
    }

    /**
     * Setteri lehden uudelle kustantajalle.
     * 
     * @param kustantaja uusi kustantaja
     */
    public void setKustantaja(String kustantaja) {
        this.kustantaja = kustantaja;
    }
    
    @Override
    public String toString() {
        return "ISSN " + ISSN + ": " + nimi + ", " + kustantaja +
                ". Tunnus " + ID + ", Numeroita luettelossa " + numerot.size();
    }
}