
/**
 * Luokka yksittäiselle niteelle, joka kuuluu yhden teoksen listaan.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

package Kortisto;

import java.io.Serializable;

public class Nide implements Serializable {
    
    /** teoksen tunnus, johon nide kuuluu. */
    private int ID;
    /** niteen uniikki viivakoodi. */
    private String viivakoodi;
    /** niteen laina-aika. */
    private int lainaAika;
    /** kokoelma, johon nide kuuluu. */
    private String kokoelma;
    
    public Nide(int ID, String viivakoodi, int lainaAika, String kokoelma) {
        this.ID = ID;
        this.viivakoodi = viivakoodi;
        this.lainaAika = lainaAika;
        this.kokoelma = kokoelma;
    }
    
    /**
     * Setteri niteen viivakoodille.
     * 
     * @param viivakoodi uusi viivakoodi
     */
    public void setViivakoodi(String viivakoodi) {
        this.viivakoodi = viivakoodi;
    }
    
    /**
     * Getteri niteen viivakoodille.
     * 
     * @return viivakoodi
     */
    public String getViivakoodi() {
        return viivakoodi;
    }
    
    /**
     * Getteri niteen uniikille tunnukselle.
     * 
     * @return tunnusluku
     */
    public int getID() {
        return this.ID;
    }
    
    /**
     * Getteri niteen laina-ajalle.
     * 
     * @return laina-aika
     */
    public int getLainaAika() {
        return lainaAika;
    }
    
    /**
     * Setteri niteen uudelle laina-ajalle
     * 
     * @param lainaAika uusi laina-aika
     */
    public void setLainaAika(int lainaAika) {
        this.lainaAika = lainaAika;
    }
    
    /**
     * Getteri kokoelmalle, johon nide kuuluu.
     * 
     * @return niteen kokoelma
     */
    public String getKokoelma() {
        return kokoelma;
    }
    
    /**
     * Asettaa niteelle uuden kokoelman.
     * 
     * @param uusiKokoelma niteen uusi kokoelma
     */
    public void setKokoelma(String uusiKokoelma) {
        this.kokoelma = uusiKokoelma;
    }
    
    @Override
    public String toString() {
        return viivakoodi + ", kokoelma " + kokoelma + ", laina-aika " + " vrk. ";
    }
}
