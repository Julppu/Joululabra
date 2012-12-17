package Kortisto;

public class Nide {
    
    private int ID;
    private String viivakoodi;
    private int lainaAika;
    private String kokoelma;
    
    public Nide(String viivakoodi, int ID, int lainaAika, String kokoelma) {
        this.viivakoodi = viivakoodi;
        this.ID = ID;
        this.lainaAika = lainaAika;
        this.kokoelma = kokoelma;
    }
    
    public void setViivakoodi(String viivakoodi) {
        this.viivakoodi = viivakoodi;
    }
    
    public String getViivakoodi() {
        return viivakoodi;
    }
    
    public int getID() {
        return this.ID;
    }
    
    public int getLainaAika() {
        return lainaAika;
    }
    
    public void setLainaAika(int lainaAika) {
        this.lainaAika = lainaAika;
    }
    
    public String getKokoelma() {
        return kokoelma;
    }
    
    public void setKokoelma(String uusiKokoelma) {
        this.kokoelma = uusiKokoelma;
    }
}