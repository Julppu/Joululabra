package Kortisto.Poikkeukset;

/**
 * Exception-luokan toteuttava poikkeusluokka, joka annetaan, jos lehti löy-
 * tyy kortistosta esimerkiksi numeron lisäyksen yhteydessä.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since 10012013
 */

public class LehtiFoundException extends Exception {

    public LehtiFoundException() { super(); }
    public LehtiFoundException(String viesti) { super(viesti); }

}