package Kortisto;

import java.util.ArrayList;

public class Teos {
    
    private ArrayList<Nide> niteet;
    private ArrayList<String> hakusanat;
    private int ID;
    private String ISBN;
    private String nimi;
    private String tekija;
    private int vuosi;
    private String kustantaja;
    
    private Teos(int ID, String ISBN, String nimi, String tekija, int vuosi, String kustantaja) {
        this.ID = ID;
        this.ISBN = ISBN;
        this.nimi = nimi;
        this.tekija = tekija;
        this.vuosi = vuosi;
        this.kustantaja = kustantaja;
        this.niteet = new ArrayList<Nide>();
        this.hakusanat = new ArrayList<String>();
    }
    
    public void lisaaNide(String viivakoodi, int lainaAika, String kokoelma) {
        for (Nide nide: niteet) {
            if (!nide.getViivakoodi().equals(viivakoodi))
                niteet.add(new Nide(viivakoodi, this.ID, lainaAika, kokoelma));
        }
    }
    
    public void lisaaHakusana(String hakusana) {
        if (!hakusanat.contains(hakusana))
            hakusanat.add(hakusana);
    }

    public ArrayList<Nide> getNiteet() {
        return niteet;
    }

    public int getID() {
        return ID;
    }
    
    public String getISBN() {
        return ISBN;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getTekija() {
        return tekija;
    }

    public void setTekija(String tekija) {
        this.tekija = tekija;
    }

    public int getVuosi() {
        return vuosi;
    }

    public void setVuosi(int vuosi) {
        this.vuosi = vuosi;
    }

    public String getKustantaja() {
        return kustantaja;
    }

    public void setKustantaja(String kustantaja) {
        this.kustantaja = kustantaja;
    }

    public ArrayList<String> getHakusanat() {
        return hakusanat;
    }
}