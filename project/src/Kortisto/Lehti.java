package Kortisto;

import java.util.ArrayList;

public class Lehti {
    
    private ArrayList<Numero> numerot;
    private int ID;
    private String ISSN;
    private String nimi;
    private String kustantaja;
    private ArrayList<String> hakusanat;
    
    public Lehti(int ID, String ISSN, String nimi, String kustantaja) {
        this.ID = ID;
        this.ISSN = ISSN;
        this.nimi = nimi;
        this.kustantaja = kustantaja;
    } 
    
    public void lisaaNumero(int numero, int vuosi) {
        for (Numero lehdenNumero: numerot) {
            if (lehdenNumero.getVuosi() != vuosi && lehdenNumero.getNumero() != numero)
                numerot.add(new Numero(this.ID, numero, vuosi));
        }
    }

    public ArrayList<Numero> getNumerot() {
        return numerot;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getISSN() {
        return ISSN;
    }

    public void setISSN(String ISBN) {
        this.ISSN = ISBN;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
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

    public void setHakusanat(ArrayList<String> hakusanat) {
        this.hakusanat = hakusanat;
    }
}