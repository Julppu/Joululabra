/**
 * Kortisto-ohjelmiston tekstipohjaisen käyttöliittymän luokka.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since 06012013
 */
package UI;

import Kortisto.*;
import Kortisto.Poikkeukset.*;
import UI.TextUIValikot;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextUI {
    
    /** kirjojen ja lehtien tiedot sisältävä kortistoinstanssi */
    private Kortisto kortisto;
    /** instanssi tiedostojen kirjoittamista ja lukemista varten */
    private TiedostonKasittelija tiedKas;
    /** käyttäjän antaman syötteen lukija */
    private Scanner scanner;
    /** valikot sisältävä apuluokka */
    private TextUIValikot valikot;

    public TextUI() {
        scanner = new Scanner(System.in);
        kortisto = new Kortisto();
        try {
            tiedKas = new TiedostonKasittelija("kortisto.dat");
            kortisto = tiedKas.lueTiedosto();
        } catch (IOException ioe) {
            if (ioe.getClass() != EOFException.class)
                System.out.println("Tiedoston lukemisessa tapahtui virhe.");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Luokkaa \"Kortisto\" ei löytynyt.");
        }
    }

    /**
     * Käynnistää käyttöliittymän, tulostaa tervehdyksen ja siirtyy valikkoon.
     */
    public void start() {
        System.out.println("Tervetuloa kirjakortistoon. Valikoissa navigoidaan valitsemalla \n"
                + "numero halutun operaation mukaan.\n");
        valikot.paaValikko();
        aloitusValinta();
        System.out.println("\nKiitos kortiston käytöstä!");
    }

    /**
     * Metodi kirjojen hakemiseen nimen perusteella. Mahdollisuus tulostaa myös
     * kaikki sen niteet.
     */
    public void haeKirjojaNimella() {
        System.out.print("\nAnna kirjan nimi: ");
        String nimi = scanner.nextLine();
        while (nimi.isEmpty()) {
            System.out.print("Tyhjä syöte, anna uudestaan: ");
            nimi = scanner.nextLine();
        }
        ArrayList<Teos> teokset = kortisto.getHakukone().haeTeoksiaNimella(kortisto, nimi);
        if (teokset.isEmpty()) {
            System.out.println("\nMitään ei löytynyt, palataan.");
            return;
        }
        System.out.println("\n Löydetyt teokset: ");
        for (Teos teos : teokset)
            System.out.println("  " + teos);
        System.out.print("\n Jos haluat tarkastella jonkin teoksen niteitä, anna sen tunnus\n,"
                + "muussa tapauksessa palataan alkuun: ");
        String id = scanner.nextLine();
        if (id == null)
            return;
        else
            haeNiteita(Integer.parseInt(id));
    }

    /**
     * Metodi kirjojen hakemiseen tekijällä. Mahdollisuus myös tulostaa kaikki sen niteet.
     */
    public void haeKirjojaTekijalla() {
        System.out.print("\nAnna kirjan nimi: ");
        String tekija = scanner.nextLine();
        while (tekija.isEmpty()) {
            System.out.print("Tyhjä syöte, anna uudestaan: ");
            tekija = scanner.nextLine();
        }
        ArrayList<Teos> teokset = kortisto.getHakukone().haeTeoksiaTekijalla(kortisto, tekija);
        if (teokset.isEmpty()) {
            System.out.println("\nMitään ei löytynyt, palataan.");
            return;
        }
        System.out.println("\n Löydetyt teokset: ");
        for (Teos teos : teokset)
            System.out.println("  " + teos);
        System.out.print("\n Jos haluat tarkastella jonkin kirjan niteita, anna sen tunnus,\n"
                + "muussa tapauksessa palataan alkuun: ");
        String id = scanner.nextLine();
        if (id.isEmpty())
            return;
        else
            haeNiteita(Integer.parseInt(id));
    }

    /**
     * Metodi kirjojen hakemiseen ISBN-numerolla. Mahdollisuus myös tulostaa kaikki
     * sen niteet.
     */
    public void haeKirjaISBN() {
        Teos teos = haeKirja();
        if (teos == null)
            return;
        System.out.println(teos);
        for (Nide nide : teos.getNiteet())
            System.out.println("  " + nide);
        System.out.print("\n Jos haluat tarkastella jonkin lehden numeroita, anna sen tunnus,\n"
                + "tyhjällä palataan alkuun: ");
        String id = scanner.nextLine();
        if (id.isEmpty())
            return;
        else
            haeNiteita(Integer.parseInt(id));
    }

    /**
     * Metodi niteiden hakemiseen teoksen perusteella. Hakee hakukoneen avulla
     * listan teoksen niteistä ja tulostaa ne.
     * @param tunnus haettavan teoksen tunnus
     */
    public void haeNiteita(int tunnus) {
        while (tunnus != 0) {
            Teos teos = kortisto.getHakukone().haeTeosTunnuksella(kortisto, tunnus);
            if (teos == null) {
                System.out.println("\nMitään ei löytynyt, palataan.");
                return;
            }
            if (teos.getNiteet().isEmpty())
                System.out.println("Ei niteitä, palataan.");
            else {
                for (Nide nide : teos.getNiteet()) {
                    System.out.println(" " + teos);
                    System.out.println("  " + nide);
                }
            }
            System.out.println("\nAnna seuraava teoksen tunnus tai tyhjä lopettaaksesi:");
            tunnus = Integer.parseInt(scanner.nextLine());
        }
    }

    /**
     * Hakee niteen viivakoodin perusteella. Tulostaa teoksen tiedot, johon se kuuluu,
     * sekä niteen tiedot.
     */
    public void haeNideViivakoodilla() {
        System.out.print("\nAnna viivakoodi: ");
        String viivakoodi = scanner.nextLine();
        Nide nide = kortisto.getHakukone().haeNideViivakoodilla(kortisto, viivakoodi);
        if (nide == null)
            System.out.println("Haluttua nidettä ei löytynyt.");
        else {
            System.out.println("Nide löytyi.");
            Teos teos = kortisto.getHakukone().haeTeosTunnuksella(kortisto, nide.getID());
            System.out.println("Teos:");
            System.out.println(" " + teos);
            System.out.println("Nide:");
            System.out.println("  " + nide);
        }
    }

    /**
     * Hakee lehtiä nimen perusteella. Mahdollisuuten tulostaa lehtien numrot.
     * @return lehti muokattava lehti 
     */
    public void haeLehtiaNimella() {
        System.out.print("\nAnna lehden nimi: ");
        String nimi = scanner.nextLine();
        while (nimi.isEmpty()) {
            System.out.print("Tyhjä syöte, anna uudestaan: ");
            nimi = scanner.nextLine();
        }
        ArrayList<Lehti> lehdet = kortisto.getHakukone().haeLehtiaNimella(kortisto, nimi);
        System.out.println("\n Löydetyt lehdet: ");
        
        for (Lehti lehti : lehdet)
            System.out.println("  " + lehti);
        System.out.print("\n Jos haluat tarkastella jonkin lehden numeroita, anna sen tunnus\n,"
                + "muussa tapauksessa palataan alkuun: ");
        String id = scanner.nextLine();
        if (id.isEmpty())
            return;
        else
            haeNumeroita(Integer.parseInt(id));
    }

    /**
     * Hakee tiettyä numeroa lehden numeroiden listasta ja tulostaa sen.
     * @param tunnus haettavan lehden tunnus
     */
    public void haeNumeroita(int tunnus) {
        System.out.println("\nAnna numeron koko vuosi ja julkaisunumero välilyönnillä erotettuna.");
        String tiedot = scanner.nextLine();
        while (!tiedot.isEmpty()) {
            Numero haettuNumero;
            String[] tietotaulu = tiedot.split(tiedot);
            for (Numero numero : kortisto.getLehdenNumerot(tunnus)) {
                if (Integer.parseInt(tietotaulu[0]) == numero.getVuosi() && Integer.parseInt(tietotaulu[1]) == numero.getNumero()) {
                    haettuNumero = numero;
                    break;
                }
            }
            System.out.println("Haluttu numero löytyi.");
            System.out.println("\nSyötä uuden numeron tiedot tai tyhjä, jos haluta lopettaa.");
            tiedot = scanner.nextLine();
        }
    }

    /**
     * Lisää kortistoon kirjan luettuaan käyttäjältä syötteet. Palaa lisäämättä
     * takaisin jos saadaan poikkeus TeosFoundException.
     */
    public void lisaaKirja() {
        System.out.println("\nAnna tarvittavat kentät");
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Nimi: ");
        String nimi = scanner.nextLine();
        System.out.print("Tekijä(t), jos toimitettu jätä tyhjäksi:");
        String tekija = scanner.nextLine();
        if (tekija.isEmpty())
            tekija = "(toim.)";
        System.out.print("Julkaisuvuosi: ");
        int vuosi = Integer.parseInt(scanner.nextLine());
        System.out.print("Kustantaja: ");
        String kustantaja = scanner.nextLine();
        try {
            kortisto.lisaaTeos(isbn, nimi, tekija, vuosi, kustantaja);
        } catch (TeosFoundException tfe) {
            System.out.println("Teos löytyi jo kortistosta, palataan edelliseen.");
            return;
        }
        System.out.println("Teos lisätty.");
    }

    /**
     * Lisää kortistoon uuden niteen joko kirjan tunnuksen tai ISBN-numeron kautta. 
     */
    public void lisaaNide() {
        Teos teos = haeKirja();
        System.out.print("\nAnna niteen kokoelma: ");
        String kokoelma = scanner.nextLine();
        System.out.println("Laina-aika: ");
        int lainaAika = Integer.parseInt(scanner.nextLine());
        kortisto.lisaaNide(teos.getID(), lainaAika, kokoelma);
        System.out.println("\nNide lisätty.");
    }

    public void poistaKirja() {
        Teos teos = haeKirja();
        kortisto.poistaTeos(teos.getID());
        System.out.println("\nKirja poistettu.");
    }

    public void poistaNide() {
        Teos teos = haeKirja();
        if (teos == null)
            return;
        System.out.println(teos);
        for (Nide nide: teos.getNiteet())
            System.out.println(" " + nide);
        System.out.println("\nMikä nide poistetaan?");
        System.out.println("Anna niteen viivakoodi: ");
        String viivakoodi = scanner.nextLine();
        try {
            kortisto.poistaNide(teos.getID(), viivakoodi);
        } catch (TeosNotFoundException ex) {
            System.out.println("Virhe: Kirjaa ei löytynyt.");
        } catch (NideNotFoundException nex) {
            System.out.println("Virhe: Nidettä ei löytynyt.");
        }
        System.out.println("\nNide poistettu.");
    }
    
    public void muokkaaKirjaa() {
        Teos teos = haeKirja();
        valikot.yhdenKirjanMuokkausValikko();
        System.out.print("Anna valintasi: ");
        int valinta = Integer.parseInt(scanner.nextLine());
        while (valinta != 0) {
            switch (valinta) {
                case 1:
                    
                case 2:
                    
                case 3:
                    
                case 4:
                    
                case 5:
                    
                case 0:
                    return;
                default:
                    System.out.println("Väärä valinta, kokeile uudestaan.");
                    break;
            }
            valikot.yhdenKirjanMuokkausValikko();
            System.out.print("Anna seuraava valintasi: ");
            valinta = Integer.parseInt(scanner.nextLine());
        }
        System.out.println("\nPalataan valikkoon.");
    }
    
    public void vaihdaNiteenViivakoodi() {
        System.out.print("\nAnna muokattavan niteen viivakoodi: ");
        String viivakoodi = scanner.nextLine();
        System.out.print("Anna uusi viivakoodi: ");
        String uusiViivakoodi = scanner.nextLine();
        kortisto.getHakukone().haeNideViivakoodilla(kortisto, viivakoodi).setViivakoodi(uusiViivakoodi);
        System.out.println("\nViivakoodi vaihdettu.");
    }

    public void lisaaLehti() {
        System.out.println("");
    }

    public void lisaaNumero() {
        System.out.println("");
    }

    public void muokkaaLehtea() {
        System.out.println("");
    }
    
    /**
     * Hakee kirjan sen ISBN-numeron perusteella ja palauttaa sen tai null,
     * jos ei löydy.
     * @return haettu teos.
     */
    public Teos haeKirja() {
        System.out.print("\nAnna haettavan kirjan ISBN: ");
        String isbn = scanner.nextLine();
        Teos teos = kortisto.getHakukone().haeTeosISBN(kortisto, isbn);
        if (teos == null) {
            System.out.println("Teosta ei löytynyt, palataan.");
            return teos;
        }
        return teos;
    }
    
    /**
     * Kirjoittaa kortisto-instanssin senhetkiseen tiedostoon.
     */
    public void kirjoitaKortisto() {
        try {
            tiedKas.kirjoitaTiedosto(kortisto);
            System.out.println("\nKortisto tallennettu tiedostoon!");
        } catch (IOException ioe) {
            if (ioe.getClass() != EOFException.class)
                System.out.println("\nTiedoston kirjoittaminen epäonnistui!");
        }
    }
    
    /**
     * Kysyy käyttäjältä uuden tiedoston, muuttaa sen ja kirjoittaa kortiston
     * ja kirjoittaa kortiston siihen.
     */
    public void vaihdaTiedosto() {
        System.out.print("\nAnna luettavan tiedoston nimi:");
        String uusiTiedosto = scanner.nextLine();
        try {
            kortisto = tiedKas.lueUusiTiedosto(uusiTiedosto);
        } catch (IOException ioe) {
            if (ioe.getClass() != EOFException.class)
                System.out.println("Tiedoston kirjoittaminen epäonnistui.");
        } catch (ClassNotFoundException cex) {
            System.out.println("Luokkaa \"Kortisto\" ei löytynyt.");
        }
    }
    
    /**
     * Toiminnon valinta päävalikossa oleville toiminnoille ohjaamalla alavalikkoon.
     * Poikkeuksina valinta 2, joka suorittaa suoraan metodin haeLehtiaNimella()
     * sekä valinta 5, joka kirjoittaa kortiston sen hetkiseen tiedostoon.
     */
    public void aloitusValinta() {
        System.out.print("\nValinta: ");
        int valinta = Integer.parseInt(scanner.nextLine());
        while (valinta != 0) {
            switch (valinta) {
                case 1:
                    kirjat();
                    break;
                case 2:
                    haeLehtiaNimella();
                    break;
                case 3:
                    muokkaaKirjoja();
                    break;
                case 4:
                    muokkaaLehtia();
                    break;
                case 5:
                    kirjoitaKortisto();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Yritä uudestaan.");
                    break;
            }
            valikot.paaValikko();
            System.out.print("\nAnna seuraava valinta: ");
            valinta = Integer.parseInt(scanner.nextLine());
        }
    }

    /**
     * Toiminnan valinta kirjojen hakuvalikolle, joka ohjaa haluttuun hakumetodiin.
     */
    public void kirjat() {
        valikot.kirjaValikko();
        int valinta = Integer.parseInt(scanner.nextLine());
        while (valinta != 0) {
            switch (valinta) {
                case 1:
                    haeKirjojaNimella();
                    break;
                case 2:
                    haeKirjojaTekijalla();
                    break;
                case 3:
                    haeKirjaISBN();
                    break;
                case 4:
                    haeNideViivakoodilla();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Yritä uudestaan.");
                    break;
            }
            valikot.kirjaValikko();
            System.out.print("\nAnna seuraava valinta: ");
            valinta = Integer.parseInt(scanner.nextLine());
        }
    }

    /**
     * Toiminnan valinta kirjojen muokkausvalikolle, joka ohjaa haluttuun muokkausmetodiin.
     */
    public void muokkaaKirjoja() {
        valikot.kirjaMuokkausValikko();
        int valinta = Integer.parseInt(scanner.nextLine());
        while (valinta != 0) {
            switch (valinta) {
                case 1:
                    lisaaKirja();
                    break;
                case 2:
                    lisaaNide();
                    break;
                case 3:
                    muokkaaKirjaa();
                    break;
                case 4:
                    vaihdaNiteenViivakoodi();
                    break;
                case 5:
                    kirjoitaKortisto();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Huono syöte, yritä uudelleen.");
                    break;
            }
            valikot.kirjaMuokkausValikko();
            System.out.print("\nAnna seuraava valinta: ");
            valinta = Integer.parseInt(scanner.nextLine());
        }
    }

    /**
     * Toiminnan valinta lehtien muokkausvalikolle, joka ohjaa haluttuun muokkausmetodiin.
     */
    public void muokkaaLehtia() {
        valikot.lehtiMuokkausValikko();
        int valinta = Integer.parseInt(scanner.nextLine());
        while (valinta != 0) {
            switch (valinta) {
                case 1:
                    lisaaLehti();
                    break;
                case 2:
                    lisaaNumero();
                    break;
                case 3:
                    muokkaaLehtea();
                    break;
                case 5:
                    kirjoitaKortisto();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Huono syöte, yritä uudelleen.");
                    break;
            }
            valikot.lehtiMuokkausValikko();
            System.out.print("\nAnna seuraava valinta: ");
            valinta = Integer.parseInt(scanner.nextLine());
        }
    }
}