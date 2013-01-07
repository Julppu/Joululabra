
/**
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since 07012013
 */

package Kortisto.Poikkeukset;

public class NumeroNotFoundException extends Exception {
    
    public NumeroNotFoundException() { super(); }
    public NumeroNotFoundException(String poikkeus) { super(poikkeus); }
}