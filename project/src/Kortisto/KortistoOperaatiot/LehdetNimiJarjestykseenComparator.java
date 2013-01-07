
/**
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

package Kortisto.KortistoOperaatiot;

import Kortisto.Lehti;
import java.util.Comparator;

public class LehdetNimiJarjestykseenComparator implements Comparator<Lehti> {

    @Override
    public int compare(Lehti l1, Lehti l2) {
        if (l1.getNimi().compareTo(l2.getNimi()) < 0)
            return -1;
        else if (l1.getNimi().compareTo(l2.getNimi()) > 0)
            return 1;
        else
            return 0;
    }
}