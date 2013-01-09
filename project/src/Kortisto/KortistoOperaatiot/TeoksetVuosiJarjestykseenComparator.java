
/**
 * Comparator-luokan toteuttava luokka teosten järjestämiseen niiden julkaisuvuoden mukaan. 
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

package Kortisto.KortistoOperaatiot;

import Kortisto.Teos;
import java.util.Comparator;

public class TeoksetVuosiJarjestykseenComparator implements Comparator<Teos> {

    @Override
    public int compare(Teos t1, Teos t2) {
        if (t1.getVuosi() < t2.getVuosi())
            return -1;
        else if (t1.getVuosi() > t2.getVuosi())
            return 1;
        else
            return 0;
    }
}
