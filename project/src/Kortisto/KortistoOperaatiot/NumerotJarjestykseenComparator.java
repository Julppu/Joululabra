
/**
 * Comparator-luokan toteuttava luokka lehden numeroiden järjestämiseen, ensin vuoden ja sitten julkaisunumeron mukaan.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

package Kortisto.KortistoOperaatiot;

import Kortisto.Numero;
import java.util.Comparator;

public class NumerotJarjestykseenComparator implements Comparator<Numero> {

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
