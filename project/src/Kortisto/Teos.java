package Kortisto;

import java.util.ArrayList;

public class Teos {
    
    private ArrayList<Nidos> niteet;
    private int ID;
    private String nimi;
    private String tekija;
    private int vuosi;
    private String kustantaja;
    private ArrayList<Nidos> hakusanat;
    
    private Teos(int ID, String nimi, String tekija, int vuosi, String kustantaja) {
        this.ID = ID;
        this.nimi = nimi;
        this.tekija = tekija;
        this.vuosi = vuosi;
        this.kustantaja = kustantaja;
        this.niteet = new ArrayList<Nidos>();
    }

    private ArrayList<Nidos> getNiteet() {
        return niteet;
    }

    public int getID() {
        return ID;
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

    public ArrayList<Nidos> getHakusanat() {
        return hakusanat;
    }
}