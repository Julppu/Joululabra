package Kortisto.Poikkeukset;
/**
 * 
 * Exception luokan toteuttava poikkeus, joka annetaan, kun lehdestä etsittävää
 * numeroa ei löydy.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since 07012013
 */

public class NumeroNotFoundException extends Exception {
    
    public NumeroNotFoundException() { super(); }
    public NumeroNotFoundException(String poikkeus) { super(poikkeus); }
}
