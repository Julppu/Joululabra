package Kortisto.KortistoOperaatiot;

import Kortisto.Teos;
import java.util.Comparator;

public class TeoksetNimiJarjestykseenComparator implements Comparator<Teos> {

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