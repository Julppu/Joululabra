
/**
 * Comparator-luokan toteuttava luokka teosten järjestämiseen niiden nimen mukaan.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

package Kortisto.KortistoOperaatiot;

import Kortisto.Teos;
import java.io.Serializable;
import java.util.Comparator;

public class TeoksetNimiJarjestykseenComparator implements Comparator<Teos>, Serializable {

    @Override
    public int compare(Teos t1, Teos t2) {
        if (t1.getNimi().compareTo(t2.getNimi()) < 0)
            return -1;
        else if (t1.getNimi().compareTo(t2.getNimi()) > 0)
            return 1;
        else
            return 0;
    }
}
