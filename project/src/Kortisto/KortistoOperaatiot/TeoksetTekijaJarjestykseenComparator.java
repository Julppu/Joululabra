package Kortisto.KortistoOperaatiot;

import Kortisto.Teos;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator-luokan toteuttava luokka teosten järjestämiseen niiden tekijän mukaan.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

public class TeoksetTekijaJarjestykseenComparator implements Comparator<Teos>, Serializable {

    @Override
    public int compare(Teos t1, Teos t2) {
        if (t1.getTekija().compareTo(t2.getTekija()) < 0)
            return -1;
        else if (t1.getTekija().compareTo(t2.getTekija()) > 0)
            return 1;
        else
            return 0;
    }
}
