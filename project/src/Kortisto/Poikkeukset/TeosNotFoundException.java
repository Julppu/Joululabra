
/**
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since 07012013
 */

package Kortisto.Poikkeukset;

public class TeosNotFoundException extends Exception {
    
    public TeosNotFoundException() { super(); }
    public TeosNotFoundException(String poikkeus) { super(poikkeus); }
}