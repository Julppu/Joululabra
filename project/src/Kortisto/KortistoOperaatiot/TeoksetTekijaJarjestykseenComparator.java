package Kortisto.KortistoOperaatiot;

import Kortisto.Teos;
import java.util.Comparator;

public class TeoksetTekijaJarjestykseenComparator implements Comparator<Teos> {

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