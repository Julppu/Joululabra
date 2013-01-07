
/**
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since 07012013
 */

package Kortisto.Poikkeukset;

public class NideNotFoundException extends Exception {
    
    public NideNotFoundException() { super(); }
    public NideNotFoundException(String poikkeus) { super(poikkeus); }
}