package Kortisto.Poikkeukset;

/**
 * Exception-luokan toteuttava poikkeus, joka annetaan kun teos löytyy kortistosta
 * esimerkiksi lehden lisäyksen yhteydessä.
 * 
 * @author jumlindq
 * @since 07012013
 */

public class TeosFoundException extends Exception {
    
    public TeosFoundException() { super(); }
    public TeosFoundException(String poikkeus) { super(poikkeus); }
}
