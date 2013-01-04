package Kortisto;

import Kortisto.KortistoOperaatiot.Hakukone;
import java.util.ArrayList;

public class Kortisto {
    
    private static int kirjaID = 1;
    private static int lehtiID = 1;
    private static int viivakoodi = 100000;
    private ArrayList<Teos> teokset;
    private ArrayList<Lehti> lehdet;
    private ArrayList<String> kokoelmat;
    private Hakukone hakukone;
    
    public Kortisto() {
        teokset = new ArrayList();
        lehdet = new ArrayList();
        kokoelmat = new ArrayList();
        hakukone = new Hakukone();
    }
    
    public void lisaaTeos(String ISBN, String nimi, String tekija, int vuosi, String kustantaja) 
      throws Exception {
        if (hakukone.haeTeosISBN(this, ISBN) == null)
            teokset.add(new Teos(kirjaID++, ISBN, nimi, tekija, vuosi, kustantaja));
        else
            throw new Exception("Teos on jo kortistossa, ei lisätty.");
    }
    
    public void poistaTeos(int ID) {
        teokset.remove(hakukone.haeTeosTunnuksella(this, ID));
    }
    
    public void lisaaNide(int ID, int lainaAika, String kokoelma) {
        for (Teos teos: teokset) {
            if (teos.getID() == ID) {
                teos.lisaaNide(Integer.toString(viivakoodi++), lainaAika, kokoelma);
                break;
            }
        }
        if (!kokoelmat.contains(kokoelma))
            kokoelmat.add(kokoelma);
    }
    
    public void poistaNide(int ID, String viivakoodi) {
        for (Teos teos: teokset) {
            if (teos.getID() == ID) {
                teos.poistaNide(viivakoodi);
                break;
            }
        }
    }
    
    public void lisaaLehti(String ISSN, String nimi, String kustantaja)
      throws Exception {
        if (hakukone.haeLehtiISSN(this, ISSN) == null)
            lehdet.add(new Lehti(kirjaID++, ISSN, nimi, kustantaja));
        else
            throw new Exception("Lehti on jo kortistossa, ei lisätty.");
    }
    
    public void poistaLehti(int ID) {
        lehdet.remove(hakukone.haeLehtiTunnuksella(this, ID));
    }
    
    public void lisaaNumero(int ID, int vuosi, int numero) {
        for (Lehti lehti: lehdet) {
            if (lehti.getID() == ID) {
                lehti.lisaaNumero(numero, vuosi);
                break;
            }
        }
    }   
    
    public void poistaNumero(int ID, int vuosi, int numero) {
        for (Lehti lehti: lehdet)
            if (lehti.getID() == ID)
                lehti.poistaNumero(vuosi, numero);
    }
    
    public ArrayList<Teos> getTeokset() {
        return teokset;
    }
    
    public ArrayList<Lehti> getLehdet() {
        return lehdet;
    }
    
    public void lisaaKokoelma(String kokoelma) {
        kokoelmat.add(kokoelma);
    }
    
    public void poistaKokoelma(String kokoelma) {
        kokoelmat.remove(kokoelma);
    }
    
    public ArrayList<String> getKokoelmat() {
        return kokoelmat;
    }

    public Hakukone getHakukone() {
        return hakukone;
    }

    public void setHakukone(Hakukone hakukone) {
        this.hakukone = hakukone;
}
