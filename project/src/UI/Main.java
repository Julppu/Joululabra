
/**
 * Kirjakortiston ohjelmiston main-metodin luokka, joka ajetaan ohjelmiston käynnistyessä. Ohjelman argumentilla "-gui" käynnistetään graafinen käyttöliittymä, muussa tapauksessa tekstipohjainen käyttöliittymä.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

package UI;

public class Main {

    public static void main(String[] args) {              
        GUI gui;
        TextUI tui;
        if (args.length == 0) {
            tui = new TextUI();
            tui.start();
        } else if (args[0].equalsIgnoreCase("-gui")) {
            gui = new GUI();
            gui.run();
        } else {
            System.out.println("Huono argumentti, kokeile \"-GUI\" tai \"-gui\".");
        }
    }
}
