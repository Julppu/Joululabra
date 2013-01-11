package UI;

/**
 * Kirjakortiston ohjelmiston main-metodin luokka, joka ajetaan ohjelmiston 
 * käynnistyessä. Käynnistää tekstikäyttöliittymän.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

public class Main {

    public static void main(String[] args) {              
        TextUI tui;
        tui = new TextUI();
        tui.start();
    }
}
