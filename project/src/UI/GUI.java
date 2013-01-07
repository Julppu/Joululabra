
/**
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

package UI;

import Kortisto.Kortisto;
import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI implements Runnable {
    
    private Kortisto kortisto;
    private TiedostonKasittelija tiedKas;
    private JFrame mainFrame;
    private JPanel panel;
    private JButton button;
    
    public GUI() {}
    
    @Override
    public void run() {
        try {
            this.tiedKas = new TiedostonKasittelija("kortisto.dat");
            this.kortisto = tiedKas.lueTiedosto();
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Tiedoston käsittelyssä tapahtui virhe.");
        } catch (ClassNotFoundException cnfe) {
            JOptionPane.showMessageDialog(null, "Luokkaa \"Kortisto\" ei löytynyt");
        }
        luoKomponentit(mainFrame.getContentPane());
    }
    
    private void luoKomponentit(Container container) {
        mainFrame = new JFrame("Kortisto");
        mainFrame.setPreferredSize(new Dimension(640, 320));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}