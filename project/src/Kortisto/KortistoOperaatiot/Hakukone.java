
/**
 * Luokka, jolla haetaan niteitä ja lehtiä metodien parametrinä saamasta kortistosta ja palautetaan haun mukainen tulos.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

package Kortisto.KortistoOperaatiot;

import Kortisto.Kortisto;
import Kortisto.Lehti;
import Kortisto.Nide;
import Kortisto.Numero;
import Kortisto.Teos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Hakukone {
    
    /**
     * Hakee kortistosta yhden teoksen sen tunnuksen perusteella ja palauttaa
     * joko sen tai null jos teosta ei löydy.
     * 
     * @param kortisto kortiston instanssi
     * @param ID       teoksen tunnus
     * @return         haettu teos tai null
     */
    public Teos haeTeosTunnuksella(Kortisto kortisto, int ID) {
        for (Teos teos: kortisto.getTeokset())
            if (teos.getID() == ID)
                return teos;
        return null;
    }
    
    /**
     * Hakee kortistosta yhden teoksen sen ISSN-numeron perusteella ja palauttaa
     * joko sen tai null jos teosta ei löydy.
     * 
     * @param  kortisto kortiston instanssi
     * @param  ISBN     ISBN-numero
     * @return          haettu teos tai null
     */
    public Teos haeTeosISBN(Kortisto kortisto, String ISBN) {
        for (Teos teos: kortisto.getTeokset())
            if (teos.getISBN().equals(ISBN))
                return teos;
        return null;
    }
    
    /**
     * Hakee kortistosta kaikki teokset joiden nimessä on haettava teksti ja
     * palauttaa ne listana. Jos teoksia ei löydy palauttaa tyhjän listan.
     * 
     * @param kortisto kortiston instanssi
     * @param nimi     haettava nimi
     * @return         lista teoksista tai tyhjä lista.
     */
    public ArrayList<Teos> haeTeoksiaNimella(Kortisto kortisto, String nimi) {
        ArrayList<Teos> teokset = new ArrayList();
        for (Teos teos: kortisto.getTeokset())
            if (teos.getNimi().toLowerCase().contains(nimi.toLowerCase()))
                teokset.add(teos);
        return teokset;
    }
    
    /**
     * Hakee kortistosta kaikki toekset joiden tekijän nimessä on haettava teksti
     * ja palauttaa ne listana. Jos teoksia ei löydy palauttaa tyhjän listan.
     * 
     * @param kortisto kortiston instanssi
     * @param tekija   haettava tekija
     * @return         lista teoksista
     */
    public ArrayList<Teos> haeTeoksiaTekijalla(Kortisto kortisto, String tekija) {
        ArrayList<Teos> teokset = new ArrayList();
        for (Teos teos: kortisto.getTeokset())
            if (teos.getTekija().toLowerCase().contains(tekija.toLowerCase()))
                teokset.add(teos);
        return teokset;
    }
    
    /**
     * Hakee kortistosta niteen sen viivakoodilla ja palauttaa sen tai null jos
     * nidettä ei löydy.
     * 
     * @param kortisto   kortiston instanssi
     * @param viivakoodi haettavan niteen viivakoodi
     * @return           haettu nide
     */
    public Nide haeNideViivakoodilla(Kortisto kortisto, String viivakoodi) {
        for (Teos teos: kortisto.getTeokset())
            for (Nide haettavaNide: teos.getNiteet())
                if (haettavaNide.getViivakoodi().equals(viivakoodi))
                    return haettavaNide;
        return null;
    }
    
    /**
     * Hakee kortistosta lehden sen uniikin tunnuksen perusteella ja palauttaa
     * joko sen tai null, jos teosta ei löydy.
     * 
     * @param kortisto kortiston instanssi
     * @param ID       haettavan lehden tunnus
     * @return         haettu lehti tai null
     */
    public Lehti haeLehtiTunnuksella(Kortisto kortisto, int ID) {
        for (Lehti lehti: kortisto.getLehdet())
            if (lehti.getID() == ID)
                return lehti;
        return null;
    }
    
    /**
     * Hakee kortistosta lehden sen ISSN-numerolla ja palauttaa joko sen tai
     * null jos lehteä ei löydy.
     * 
     * @param kortisto kortiston instanssi
     * @param ISSN     haetun lehden ISSN
     * @return         lehti tai null
     */
    public Lehti haeLehtiISSN(Kortisto kortisto, String ISSN) {
        for (Lehti lehti: kortisto.getLehdet())
            if (lehti.getISSN().equals(ISSN))
                return lehti;
        return null;
    }
    
    /**
     * Hakee kortistosta lehtiä joiden nimi sisältää parametrin "nimi" ja
     * palauttaa ne listana. Jos lehtiä ei löydy palauttaa tyhjän listan.
     * 
     * @param kortisto kortiston instanssi
     * @param nimi     haettava nimi
     * @return         lista lehdistä
     */
    public ArrayList<Lehti> haeLehtiaNimella(Kortisto kortisto, String nimi) {
        ArrayList<Lehti> lehdet = new ArrayList();
        for (Lehti lehti: kortisto.getLehdet())
            if (lehti.getNimi().toLowerCase().contains(nimi.toLowerCase()))
                lehdet.add(lehti);
        return lehdet;
    }
    
    public Numero haeNumero(Kortisto kortisto, int ID, int vuosi, int numero) {
        for (Numero lehdenNumero: haeLehtiTunnuksella(kortisto, ID).getNumerot()) {
            if (lehdenNumero.getNumero() == numero && lehdenNumero.getVuosi() == vuosi)
                return lehdenNumero;
        }
        return null;
    }
    
    /**
     * Hakee kortistosta kaikki niteet jotka kuuluvat tiettyyn kokoelmaan ja
     * palauttaa joko sen tai tyhjän listan jos niteitä ei löydy.
     * 
     * @param kortisto kortiston instanssi
     * @param kokoelma haettava kokoelma
     * @return         lista niteistä
     */
    public ArrayList<Nide> haeKokoelmanNiteet(Kortisto kortisto, String kokoelma) {
        ArrayList<Nide> niteet = new ArrayList();
        for (Teos teos: kortisto.getTeokset())
            for (Nide nide: teos.getNiteet())
                if (nide.getKokoelma().equalsIgnoreCase(kokoelma))
                    niteet.add(nide);
        return niteet;
    }
}
