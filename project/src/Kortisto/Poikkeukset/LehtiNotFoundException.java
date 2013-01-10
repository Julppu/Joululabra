
/**
 * Exception-luokan toteuttava poikkeusluokka, joka annetaan, jos lehteä ei
 * löydy kortistosta esimerkiksi numeron lisäyksen tai etsimisen yhteydessä.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since 07012013
 */

package Kortisto.Poikkeukset;

public class LehtiNotFoundException extends Exception {
    
    public LehtiNotFoundException() { super(); }
    public LehtiNotFoundException(String poikkeus) { super(poikkeus); }
}
