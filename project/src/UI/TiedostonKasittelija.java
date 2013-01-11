
/**
 * Luokka kortiston tallentamista ja lukemista varten.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

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

public class TiedostonKasittelija {
    
    /** tiedosto, johon kortisto tallennetaan. oletuksena "kirjasto.dat".*/
    private File tiedosto;
    /** kirjasto-olion lukemiseen käytetty syötevirran lukija. */
    private ObjectInputStream sisaan;
    /** olioiden kirjoittamiseen käytetty syötevirta. */
    private ObjectOutputStream ulos;
    
    public TiedostonKasittelija(String tiedosto) 
            throws IOException, ClassNotFoundException {
        this.tiedosto = new File(tiedosto);
        if (!this.tiedosto.exists())
            this.tiedosto.createNewFile();
        sisaan = null;
        ulos = null;
        try {
            ulos = new ObjectOutputStream(new FileOutputStream(this.tiedosto));
            ulos.flush();
            sisaan = new ObjectInputStream(new FileInputStream(this.tiedosto));
        } catch (FileNotFoundException ex) {
            this.tiedosto.createNewFile();
        }
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
            kortisto = (Kortisto) sisaan.readObject();
        } catch (FileNotFoundException ex) {
            tiedosto.createNewFile();
        } catch (EOFException eofex) {}
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
        ulos = new ObjectOutputStream(new FileOutputStream(this.tiedosto, false));
        ulos.flush();
        sisaan = new ObjectInputStream(new FileInputStream(this.tiedosto));
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
        ulos.writeObject(kortisto);
        ulos.flush();
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
        ulos = new ObjectOutputStream(new FileOutputStream(this.tiedosto));
        ulos.flush();
        sisaan = new ObjectInputStream(new FileInputStream(this.tiedosto));
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