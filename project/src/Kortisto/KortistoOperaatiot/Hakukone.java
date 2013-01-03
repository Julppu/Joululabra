package Kortisto.KortistoOperaatiot;

import Kortisto.Kortisto;
import Kortisto.Lehti;
import Kortisto.Teos;
import java.util.ArrayList;
import java.util.Collections;

public class Hakukone {
    
    public Teos haeTeosTunnuksella(Kortisto kortisto, int ID) {
        for (Teos teos: kortisto.getTeokset())
            if (teos.getID() == ID)
                return teos;
        return null;
    }
    
    public Teos haeTeosISBN(Kortisto kortisto, String ISBN) {
        for (Teos teos: kortisto.getTeokset())
            if (teos.getISBN().equals(ISBN))
                return teos;
        return null;
    }
    
    public ArrayList<Teos> haeTeoksiaNimella(Kortisto kortisto, String nimi) {
        ArrayList<Teos> teokset = new ArrayList();
        for (Teos teos: kortisto.getTeokset())
            if (teos.getNimi().contains(nimi.trim()))
                teokset.add(teos);
        return teokset;
    }
    
    public ArrayList<Teos> haeTeoksiaTekijalla(Kortisto kortisto, String tekija) {
        ArrayList<Teos> teokset = new ArrayList();
        for (Teos teos: kortisto.getTeokset())
            if (teos.getTekija().contains(tekija.trim()))
                teokset.add(teos);
        return teokset;
    }
    
    public Lehti haeLehtiTunnuksella(Kortisto kortisto, int ID) {
        for (Lehti lehti: kortisto.getLehdet())
            if (lehti.getID() == ID)
                return lehti;
        return null;
    }
    
    public Lehti haeLehtiISSN(Kortisto kortisto, String ISSN) {
        for (Lehti lehti: kortisto.getLehdet())
            if (lehti.getISSN().equals(ISSN))
                return lehti;
        return null;
    }
    
    public ArrayList<Lehti> haeLehtiaNimella(Kortisto kortisto, String nimi) {
        ArrayList<Lehti> lehdet = new ArrayList();
        for (Lehti lehti: kortisto.getLehdet())
            if (lehti.getNimi().contains(nimi.trim()))
                lehdet.add(lehti);
        return null;
    }
}