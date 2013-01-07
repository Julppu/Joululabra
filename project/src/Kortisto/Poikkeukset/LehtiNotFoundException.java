
/**
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since 07012013
 */

package Kortisto.Poikkeukset;

public class LehtiNotFoundException extends Exception {
    
    public LehtiNotFoundException() { super(); }
    public LehtiNotFoundException(String poikkeus) { super(poikkeus); }
}