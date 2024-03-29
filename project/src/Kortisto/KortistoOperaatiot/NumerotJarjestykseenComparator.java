package Kortisto.KortistoOperaatiot;

import Kortisto.Numero;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator-luokan toteuttava luokka lehden numeroiden järjestämiseen,
 * ensin vuoden ja sitten julkaisunumeron mukaan.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

public class NumerotJarjestykseenComparator implements Comparator<Numero>, Serializable {

    @Override
    public int compare(Numero n1, Numero n2) {
        if (n1.getVuosi() < n2.getVuosi())
            return -1;
        else if (n2.getVuosi() > n2.getVuosi())
            return 1;
        else {
            if (n1.getNumero() < n2.getNumero())
                return -1;
            else if (n1.getNumero() > n2.getNumero())
                return 1;
            else
                return 0;
        }
    }
}
