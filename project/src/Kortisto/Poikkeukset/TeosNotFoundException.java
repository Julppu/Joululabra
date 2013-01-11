package Kortisto.Poikkeukset;

/**
 * Exception-luokan toteuttava poikkeus, joka annetaan kun teosta ei löydy 
 * kortistosta, esimerkiksi haun yhteydessä.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since 07012013
 */

public class TeosNotFoundException extends Exception {
    
    public TeosNotFoundException() { super(); }
    public TeosNotFoundException(String poikkeus) { super(poikkeus); }
}
