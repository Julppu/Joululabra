package UI;

import Kortisto.Kortisto;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Luokka kortiston tallentamista ja lukemista varten.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

public class TiedostonKasittelija {
    
    /** tiedosto, johon kortisto tallennetaan. oletuksena "kirjasto.dat".*/
    private File tiedosto;
    /** kirjasto-olion lukemiseen käytetty syötevirran lukija. */
    private ObjectInputStream sisaan;
    /** olioiden kirjoittamiseen käytetty syötevirta. */
    private ObjectOutputStream ulos;
    /** ObjectInputStreamin käyttämä tiedostovirran lukija. */
    private FileInputStream fin;
    /** ObjectOuputStreamin käyttämä tiedostovirran kirjoittaja. */
    private FileOutputStream fout;
    
    /**
     * Alustaa käsittelijän tiedoston ja luo uuden tarvittaessa.
     * @param tiedosto tiedoston polku ja nimi String-muotoisena
     * @throws IOException tiedoston kirjoituksen virheestä
     * @throws ClassNotFoundException Kortisto-luokan puuttuessa
     */
    public TiedostonKasittelija(String tiedosto) 
            throws IOException, ClassNotFoundException {
        this.tiedosto = new File(tiedosto);
        if (!this.tiedosto.exists())
            this.tiedosto.createNewFile();
    }
    
    /**
     * Lukee oliomuotoisen kortiston luokan instanssina olevasta tiedostosta ja
     * palauttaa sen.
     * 
     * @return uusi kortiston instanssi
     * @throws ClassNotFoundException luokan Kortisto puuttuessa
     * @throws IOException tiedoston kirjoittamisessa tapahtuvasta virheestä
     */
    public Kortisto lueTiedosto()
            throws ClassNotFoundException, IOException {
        Kortisto kortisto = new Kortisto();
        try {
            fin = new FileInputStream(this.tiedosto);
            sisaan = new ObjectInputStream(fin);
            kortisto = (Kortisto) sisaan.readObject();
        } catch (FileNotFoundException fnfex) {
            tiedosto.createNewFile();
        } catch (EOFException eofex) {
            return kortisto;
        }
        finally {
            sisaan.close();
        }
        return kortisto;
    }
    
    /**
     * Lukee uuden kortiston parametrina saatavasta tiedostosta, asettaa molemmat
     * olion instansseiksi ja palauttaa sen.
     * 
     * @param tiedosto uusi luettava tiedosto
     * @return uusi kortistoinstanssi
     * @throws IOException tiedoston kirjoittamisessa tapahtuvasta virheestä
     * @throws ClassNotFoundException luokan Kortisto puuttuessa
     */
    public Kortisto lueUusiTiedosto(String tiedosto)
            throws IOException, ClassNotFoundException {
        this.tiedosto = new File(tiedosto);
        if (!this.tiedosto.exists())
            this.tiedosto.createNewFile();
        return lueTiedosto();
    }
    
    /**
     * Kirjoittaa kortiston tiedostoon oliomuodossa luokan instanssina olevaan
     * tiedostoon.
     * 
     * @param kortisto tallennettava kortisto
     * @throws IOException tiedoston kirjoittamisessa tapahtuvasta virheestä
     */
    public void kirjoitaTiedosto(Kortisto kortisto) 
            throws IOException {
        fout = new FileOutputStream(this.tiedosto, false);
        ulos = new ObjectOutputStream(fout);
        ulos.flush();
        ulos.writeObject(kortisto);
        ulos.close();
    }
    
    /**
     * Kirjoittaa kortiston oliomuodossa uuteen tiedostoon ja asettaa sen
     * tämän instanssin tiedostoksi.
     * 
     * @param kortisto tallennettava kortisto
     * @param tiedosto kohdetiedosto
     * @throws IOException tiedoston kirjoittamisessa tapahtuvasta virheestä
     */
    public void kirjoitaUusiTiedosto(Kortisto kortisto, String tiedosto)
            throws IOException {
        this.tiedosto = new File(tiedosto);
        if (!this.tiedosto.exists())
            this.tiedosto.createNewFile();
        kirjoitaTiedosto(kortisto);
    }
    
    /**
     * Palauttaa senhetkisenä instanssina olevan tiedoston nimen.
     * 
     * @return tiedoston nimi
     */
    public String getFilename() {
        return this.tiedosto.getName();
    }
}