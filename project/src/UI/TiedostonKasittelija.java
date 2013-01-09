
/**
 * Luokka kortiston tallentamista ja lukemista varten.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

package UI;

import Kortisto.Kortisto;
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
    /** kirjasto-olion lukemiseen käytetty syötevirran lukija */
    private ObjectInputStream sisaan;
    /** olioiden kirjoittamiseen käytetty syötevirta */
    private ObjectOutputStream ulos;
    
    public TiedostonKasittelija(String tiedosto) 
            throws IOException, ClassNotFoundException {
        try {
            this.tiedosto = new File(tiedosto);
            if (!this.tiedosto.exists())
                this.tiedosto.createNewFile();
            sisaan = new ObjectInputStream(new FileInputStream(this.tiedosto));
            ulos = new ObjectOutputStream(new FileOutputStream(this.tiedosto));
        } catch (FileNotFoundException ex) {
            this.tiedosto.createNewFile();
        }
    }
    
    /**
     * Lukee oliomuotoisen kortiston luokan instanssina olevasta tiedostosta ja
     * palauttaa sen.
     * 
     * @return uusi kortiston instanssi
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public Kortisto lueTiedosto()
            throws ClassNotFoundException, IOException {
        Kortisto kortisto = null;
        try {
            if (this.tiedosto.length() == null)
                return new Kortisto();
            kortisto = (Kortisto) sisaan.readObject();
        } catch (FileNotFoundException ex) {
            tiedosto.createNewFile();
        }
        return kortisto;
    }
    
    /**
     * Lukee uuden kortiston parametrina saatavasta tiedostosta, asettaa molemmat
     * olion instansseiksi ja palauttaa sen.
     * 
     * @param tiedosto uusi luettava tiedosto
     * @return uusi kortistoinstanssi
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public Kortisto lueUusiTiedosto(String tiedosto)
            throws IOException, ClassNotFoundException {
        this.tiedosto = new File(tiedosto);
        if (!this.tiedosto.exists())
            this.tiedosto.createNewFile();
        sisaan = new ObjectInputStream(new FileInputStream(this.tiedosto));
        ulos = new ObjectOutputStream(new FileOutputStream(this.tiedosto, false));
        return lueTiedosto();
    }
    
    /**
     * Kirjoittaa kortiston tiedostoon oliomuodossa.
     * 
     * @param kortisto tallennettava kortisto
     * @throws IOException 
     */
    public void kirjoitaTiedosto(Kortisto kortisto) 
            throws IOException {
        if (!this.tiedosto.exists())
            tiedosto.createNewFile();
        ulos.writeObject(kortisto);
    }
    
    /**
     * Kirjoittaa kortiston oliomuodossa uuteen tiedostoon ja asettaa sen
     * tämän instanssin tiedostoksi.
     * 
     * @param kortisto tallennettava kortisto
     * @param tiedosto kohdetiedosto
     * @throws IOException 
     */
    public void kirjoitaUusiTiedosto(Kortisto kortisto, String tiedosto) 
            throws IOException {
        this.tiedosto = new File(tiedosto);
        ulos = new ObjectOutputStream(new FileOutputStream(this.tiedosto));
        kirjoitaTiedosto(kortisto);
    }
}
