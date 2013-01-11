package UI;

import java.util.Scanner;

/**
 * Lukee käyttäjän syötteen ja palauttaa sen kutsujalle.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since 11012013
 */

public class SyotteenLukija {
    
    /** syötteenlukija. */
    private Scanner scanner;
    
    /**
     * Alustaa Scanner-luokan olion syötteen lukijaksi.
     */
    public SyotteenLukija() {
        scanner = new Scanner(System.in);
    }
    
    /**
     * Lukee String-muotoisen syötteen ja palauttaa sen.
     * 
     * @return annetty merkkijono
     */
    public String lueString() {
        return scanner.nextLine();
    }

    /**
     * Lukee käyttäjän antaman Integer-muotoisen syötteen ja palauttaa sen.
     * Pyytää uutta numeroa niin kauan kunnes käyttäjä antaa numeron.
     * 
     * @return annettu numero
     */
    public int lueInt() {
        int luku;
        while (true) {
            try {
                luku = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException nfe) {
                System.out.print("\nAnna numero, kiitos: ");
            }
        }
        return luku;
    }
}