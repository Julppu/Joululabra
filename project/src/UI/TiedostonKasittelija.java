package UI;

import Kortisto.Kortisto;
import java.io.File;
import java.io.FileInputStream;
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
            
        }
    }
    
    public Kortisto lueTiedosto() {
        try {
            if (!tiedosto.exists()){
                tiedosto.createNewFile();
                return new Kortisto();
            }
            this.kortisto = (Kortisto) sisaan.readObject();
        } catch (Exception ex) {
            
        } finally {
            
        }
        return kortisto;
    }
    
    public Kortisto lueUusiTiedosto(String tiedosto) {
        this.tiedosto = new File(tiedosto);
        try {
            sisaan = new ObjectInputStream(new FileInputStream(this.tiedosto));
        } catch (Exception ex) {
            
        }
        return lueTiedosto();
    }
    
    public void kirjoitaTiedosto(Kortisto kortisto) {
        setKortisto(kortisto);
        try {
            if (!this.tiedosto.exists())
                tiedosto.createNewFile();
            ulos.writeObject(kortisto);
        } catch (IOException io) {
            
        } finally {
            
        }
    }
    
    public void kirjoitaUusiTiedosto(Kortisto kortisto, String tiedosto) {
        setKortisto(kortisto);
        this.tiedosto = new File(tiedosto);
        try {
            ulos = new ObjectOutputStream(new FileOutputStream(this.tiedosto));
        } catch (Exception ex) {
            
        }
        kirjoitaTiedosto(this.kortisto);
    }
    
    public void setKortisto(Kortisto kortisto) {
        this.kortisto = kortisto;
    }
}
