package UI;

/**
 * Luokka joka sisältää tekstipohjaisessa käyttöliittymässä tarvittavat valikot 
 * ja tulostaa ne kutsuttaessa.
 * @author jumlindq
 * @since 10012013
 */

public class TextUIValikot {
    
    /**
     * Tulostaa ohjelman päävalikon.
     * 
     * @see UI.TextUI#aloitusValinta()
     */
    public void paaValikko() {
        System.out.println("************************************************************");
        System.out.println("* Päävalikko:                                              *");
        System.out.println("*                                                          *");
        System.out.println("* 1. Hae kirjoja                                           *");
        System.out.println("* 2. Hae lehtiä                                            *");
        System.out.println("* 3. Muokkaa kirjoja                                       *");
        System.out.println("* 4. Muokkaa lehtiä                                        *");
        System.out.println("* 5. Tallenna kortisto                                     *");
        System.out.println("* 6. Valitse kortisto                                      *");
        System.out.println("* 0. Lopeta                                                *");
        System.out.println("*                                                          *");
        System.out.println("************************************************************");
    }

    /**
     * Tulostaa kirjojen hakuvalikon.
     * 
     * @see UI.TextUI#kirjat()
     */
    public void kirjaValikko() {
        System.out.println("* Kirjojen haku:");
        System.out.println("* 1. Hae nimellä");
        System.out.println("* 2. Hae tekijällä");
        System.out.println("* 3. Hae ISBN-numerolla");
        System.out.println("* 4. Hae nide viivakoodilla");
        System.out.println("* 0. Lopeta");
    }

    /**
     * Tulostaa kirjojen muokkausvalikon.
     * 
     * @see UI.TextUI#muokkaaKirjoja()
     */
    public void kirjaMuokkausValikko() {
        System.out.println("* Kirjojen muokkaus:");
        System.out.println("* 1. Lisää kirja");
        System.out.println("* 2. Lisää nide");
        System.out.println("* 3. Muokkaa kirjaa");
        System.out.println("* 4. Muokkaa nidettä");
        System.out.println("* 5. Poista kirja");
        System.out.println("* 6. Poista nide");
        System.out.println("* 0. Paluu");
    }
    
    /**
     * Tulostaa valikon yksittäisen kirjan muokkaamiselle.
     * 
     * @see UI.TextUI#muokkaaKirjaa()
     */
    public void yhdenKirjanMuokkausValikko() {
        System.out.println("\nMikä kenttä muutetaan?");
        System.out.println("1. Nimi");
        System.out.println("2. Tekijä");
        System.out.println("3. Kustantaja");
        System.out.println("4. Painovuosi");
        System.out.println("5. Hakusanoja\n");
        System.out.println("0. Paluu");
    }
    
    /**
     * Tulostaa lehtien muokkausvalikon.
     * 
     * @see UI.TextUI#muokkaaLehtia()
     */
    public void lehtiMuokkausValikko() {
        System.out.println("* Lehtien muokkaus:");
        System.out.println("* 1. ");
        System.out.println("* 2. ");
        System.out.println("* 3. ");
        System.out.println("* 4. ");
        System.out.println("* 5. ");
        System.out.println("* 0. Paluu");
    }
}