package UI;

/**
 * Luokka joka sisältää tekstipohjaisessa käyttöliittymässä tarvittavat valikot 
 * ja tulostaa ne kutsuttaessa.
 * 
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
        System.out.println("\n************************************************************");
        System.out.println("* Päävalikko:                                              *");
        System.out.println("*                                                          *");
        System.out.println("* 1. Hae kirjoja                                           *");
        System.out.println("* 2. Hae lehtiä                                            *");
        System.out.println("* 3. Muokkaa kirjoja                                       *");
        System.out.println("* 4. Muokkaa lehtiä                                        *");
        System.out.println("* 5. Tiedoston käsittely                                   *");
        System.out.println("* 0. Lopeta                                                *");
        System.out.println("*                                                          *");
        System.out.println("************************************************************");
    }

    /**
     * Tulostaa kirjojen hakuvalikon.
     * 
     * @see UI.TextUI#kirjaValinta()
     */
    public void kirjaValikko() {
        System.out.println("\n* Kirjojen haku:");
        System.out.println("* 1. Hae nimellä");
        System.out.println("* 2. Hae tekijällä");
        System.out.println("* 3. Hae ISBN-numerolla");
        System.out.println("* 4. Hae nide viivakoodilla");
        System.out.println("* 5. Listaa kokoelman niteet");
        System.out.println("* 0. Paluu");
    }

    /**
     * Tulostaa kirjojen muokkausvalikon.
     * 
     * @see UI.TextUI#muokkaaKirjoja()
     */
    public void kirjaMuokkausValikko() {
        System.out.println("\n* Kirjojen muokkaus:");
        System.out.println("* 1. Lisää kirja");
        System.out.println("* 2. Lisää nide");
        System.out.println("* 3. Muokkaa kirjaa");
        System.out.println("* 4. Poista kirja");
        System.out.println("* 5. Poista nide");
        System.out.println("* 6. Kirjoita kortisto");
        System.out.println("* 0. Paluu");
    }
    
    /**
     * Tulostaa valikon yksittäisen kirjan muokkaamiselle.
     * 
     * @see UI.TextUI#muokkaaKirjaa()
     */
    public void yhdenKirjanMuokkausValikko() {
        System.out.println("\n* Mikä kenttä muutetaan?");
        System.out.println("* 1. Nimi");
        System.out.println("* 2. Tekijä");
        System.out.println("* 3. Kustantaja");
        System.out.println("* 4. Painovuosi");
        System.out.println("* 5. Hakusanoja\n");
        System.out.println("* 0. Paluu");
    }
    
    /**
     * Tulostaa lehtien muokkausvalikon.
     * 
     * @see UI.TextUI#muokkaaLehtiaValinta() 
     */
    public void lehtiMuokkausValikko() {
        System.out.println("\n* Lehtien muokkaus:");
        System.out.println("* 1. Lisää lehti");
        System.out.println("* 2. Lisää numero");
        System.out.println("* 3. Muokkaa lehteä");
        System.out.println("* 4. Poista lehti");
        System.out.println("* 5. Poista numero");
        System.out.println("* 6. Kirjoita kortisto");
        System.out.println("* 0. Paluu");
    }
    
    /**
     * Tulostaa tiedostovalikon,
     * 
     * @see UI.TextUI#tiedostoValikko() 
     */
    public void tiedostoValikko() {
        System.out.println("\n* Kortiston tiedosto-operaatiot:");
        System.out.println("* 1. Kirjoita kortisto tiedostoon");
        System.out.println("* 2. Kirjoita toiseen tiedostoon");
        System.out.println("* 3. Lue toinen tiedosto");
        System.out.println("* 4. Näytä käytössä oleva tiedosto");
        System.out.println("* 0. Paluu");
    }
}