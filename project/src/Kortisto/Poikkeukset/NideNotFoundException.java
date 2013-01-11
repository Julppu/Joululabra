package Kortisto.Poikkeukset;

/**
 * Exception-luokan toteuttava poikkeusluokka, joka annetaan, jos nidettä ei
 * löydy kortistosta esimeriksi haun tai poiston yhteydessä.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since 07012013
 */

public class NideNotFoundException extends Exception {
    
    public NideNotFoundException() { super(); }
    public NideNotFoundException(String poikkeus) { super(poikkeus); }
}