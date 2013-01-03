package UI;

import Kortisto.Kortisto;
import java.io.IOException;

public class TextUI {
    
    private Kortisto kortisto;
    private TiedostonKasittelija tiedKas;
    
    public TextUI() {}
    
    public void start() {
        try {
        tiedKas = new TiedostonKasittelija("kortisto.dat");
        kortisto = tiedKas.lueTiedosto();
        } catch (IOException ioe) {
            System.out.println("Tiedoston kirjoituksessa tapahtui virhe.");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Luokkaa \"Kortisto\" ei löytynyt.");
        }
        System.out.println(" Tervetuloa kirjakortistoon. Valikoissa navigoidaan valitsemalla \n" +
                           " numero halutun operaation mukaan. \n\n");
        paaValikko();
    }

    public void paaValikko() {
        System.out.println("***********************************");
        System.out.println("* Päävalikko:                     *");
        System.out.println("*                                 *");
        System.out.println("* 1. Selaa aineistoja             *");
        System.out.println("* 2. Hae kirjoja                  *");
        System.out.println("* 3. Hae lehtiä                   *");
        System.out.println("* 4. Muokkaa kirjoja              *");
        System.out.println("* 5. Muokkaa lehtiä               *");
        System.out.println("* 6. Tallenna kortisto            *");
        System.out.println("* 7. Valitse kortisto             *");
        System.out.println("* 0. Lopeta                       *");
        System.out.println("***********************************");
    }
}