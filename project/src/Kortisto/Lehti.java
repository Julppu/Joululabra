package Kortisto;

import java.util.ArrayList;

public class Lehti {
    
    private int ID;
    private String ISSN;
    private String nimi;
    private String kustantaja;
    private ArrayList<String> hakusanat;
    private ArrayList<Numero> numerot;
    
    public Lehti(int ID, String ISSN, String nimi, String kustantaja) {
        this.ID = ID;
        this.ISSN = ISSN;
        this.nimi = nimi;
        this.kustantaja = kustantaja;
        this.numerot = new ArrayList();
    } 
    
    public void lisaaNumero(int numero, int vuosi) {
        for (Numero lehdenNumero: numerot) {
            if (lehdenNumero.getVuosi() == vuosi && lehdenNumero.getNumero() == numero)
                return;
        }
        numerot.add(new Numero(ID, numero, vuosi));
    }
    
    public void poistaNumero(int lehdenNumero, int vuosi) {
        for (Numero numero: numerot) {
            if (numero.getNumero() == lehdenNumero && numero.getVuosi() == vuosi)
                numerot.remove(numero);
        }
    }
    
    public ArrayList<Numero> getNumerot() {
        return numerot;
    }
    
    public void lisaaHakusana(String hakusana) {
        if (!hakusanat.contains(hakusana))
            hakusanat.add(hakusana);
    }
        
    public void poistaHakusana(String hakusana) {
        hakusanat.remove(hakusana);
    }
    
    public ArrayList<String> getHakusanat() {
        return hakusanat;
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
}