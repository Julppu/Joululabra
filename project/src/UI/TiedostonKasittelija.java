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
    
    private Kortisto kortisto;
    private File tiedosto;
    private ObjectInputStream sisaan;
    private ObjectOutputStream ulos;
    
    public TiedostonKasittelija(Kortisto kortisto, String tiedosto) {
        this.kortisto = kortisto;
        this.tiedosto = new File(tiedosto);
        try {
            sisaan = new ObjectInputStream(new FileInputStream(this.tiedosto));
            ulos = new ObjectOutputStream(new FileOutputStream(this.tiedosto));
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }
    
    private Kortisto lueKortisto() {
        try {
            if (!tiedosto.exists())
                tiedosto.createNewFile();
            this.kortisto = (Kortisto) sisaan.readObject();
        } catch (IOException io) {
            throw io;
        } catch (ClassNotFoundException cnfe) {
            throw cnfe;
        } finally {
            sisaan.close();
        }
        return kortisto;
    }
    
    public Kortisto lueTiedosto() {
         return lueKortisto();
    }
    
    public Kortisto lueUusiTiedosto(String tiedosto) {
        this.tiedosto = new File(tiedosto);
        return lueKortisto();
    }
    
    public void kirjoitaTiedosto(Kortisto kortisto) {
        try {
            if (!this.tiedosto.exists())
                tiedosto.createNewFile();
            ulos.writeObject(kortisto);
        } catch (IOException io) {
            throw io;
        } finally {
            ulos.close();
        }
    }
}
