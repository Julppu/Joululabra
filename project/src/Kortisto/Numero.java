package Kortisto;

import java.io.Serializable;

/**
 * Luokka yksittäiselle numerolle, joka sisältyy Lehden instanssina olevaan listaan.
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

public class Numero implements Serializable {
    
    /** tunnus lehteen, johon numero kuuluu. */
    private int ID;
    /** lehden julkaisunumero. */
    private int numero;
    /** lehden julkaisuvuosi. */
    private int vuosi;
    
    /**
     * Alustaa lehden numeron parametreina saamillaan arvoilla.
     * 
     * @param ID emälehden tunnusluku
     * @param vuosi numeron julkaisuvuosi
     * @param numero numeron julkaisunumero
     */
    public Numero(int ID, int vuosi, int numero) {
        this.ID = ID;
        this.numero = numero;
        this.vuosi = vuosi;
    }
    
    /**
     * Setteri lehden numeron uudelle julkaisunumerolle.
     * 
     * @param uusiNumero uusi julkaisunumero
     */
    public void setNumero(int uusiNumero) {
        this.numero = uusiNumero;
    }
    
    /**
     * Getteri numeron julkaisunumerolle.
     * 
     * @return julkaisunumero
     */
    public int getNumero() {
        return numero;
    }
    
    /**
     * Setteri numeron uudelle julkaisuvuodelle.
     * 
     * @param uusiVuosi uusi julkaisuvuosi
     */
    public void setVuosi(int uusiVuosi) {
        this.vuosi = uusiVuosi;
    }
    
    /**
     * Getteri numeron julkaisuvuodelle.
     * 
     * @return julkaisuvuosi
     */
    public int getVuosi() {
        return vuosi;
    }
    
    @Override
    public String toString() {
        return vuosi + "/#" + numero;
    }
}
