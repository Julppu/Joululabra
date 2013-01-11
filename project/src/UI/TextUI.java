/**
 * Kortisto-ohjelmiston tekstipohjaisen käyttöliittymän luokka.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since 06012013
 */
package UI;

import Kortisto.*;
import Kortisto.Poikkeukset.*;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextUI {
    
    /** kirjojen ja lehtien tiedot sisältävä kortistoinstanssi. */
    private Kortisto kortisto;
    /** instanssi tiedostojen kirjoittamista ja lukemista varten. */
    private TiedostonKasittelija tiedKas;
    /** käyttäjän antaman syötteen lukija. */
    private Scanner scanner;
    /** valikot sisältävä apuluokka. */
    private TextUIValikot valikot;

    public TextUI() {
        scanner = new Scanner(System.in);
        kortisto = new Kortisto();
        valikot = new TextUIValikot();
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
        if (id.isEmpty())
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
        haeNiteita(Integer.parseInt(id));
    }

    /**
     * Hakee yhden teoksen ISBN-numeron avulla ja tulostaa sen sekä sen niteet.
     * 
     * @see #haeKirja()
     */
    public void haeKirjaISBN() {
        Teos teos = haeKirja();
        if (teos == null)
            return;
        System.out.println(" " + teos);
        for (Nide nide : teos.getNiteet())
            System.out.println("  " + nide);
    }
    
    /**
     * Hakee hakukoneluokan avulla käyttäjän syöttämän kokoelman kaikki niteet ja
     * listaa ne sekä teokset, joihin ne kuuluvat kunnes käyttäjä antaa tyhjän
     * syötteen.
     */
    private void listaaKokoelmanNiteet() {
        System.out.print("\nAnna listattava kokoelma (tyhjällä palaa): ");
        String kokoelma = scanner.nextLine();
        while (!kokoelma.isEmpty()) {
            ArrayList<Nide> niteet = kortisto.getHakukone().haeKokoelmanNiteet(kortisto, kokoelma);
            for (Nide nide: niteet) {
                try {
                    System.out.println(" " + kortisto.getTeosTunnuksella(nide.getID()));
                    System.out.println("  " + nide);
                } catch (TeosNotFoundException tnfe) {
                    System.out.println("\nTeosta ei löydytynyt, kokeile uudestaan.");
                }
            }
            System.out.print("\nAnna seuraava kokoelma (tyhjällä palaa): ");
            kokoelma = scanner.nextLine();
        }
    }

    /**
     * Metodi niteiden hakemiseen teoksen perusteella. Hakee hakukoneen avulla
     * listan teoksen niteistä ja tulostaa ne.
     * 
     * @param tunnus haettavan teoksen tunnus
     */
    public void haeNiteita(int tunnus) {
        while (tunnus != 0) {
            Teos teos;
            try {
                teos = kortisto.getTeosTunnuksella(tunnus);
            } catch (TeosNotFoundException tnfe) {
                System.out.println("\nMitään ei löytynyt, palataan.");
                return;
            }
            if (teos.getNiteet().isEmpty())
                System.out.println("Ei niteitä, palataan.");
            else {
                System.out.println(" " + teos);
                for (Nide nide : teos.getNiteet())
                    System.out.println("  " + nide);
            }
            System.out.println("\nAnna seuraava teoksen tunnus tai tyhjä lopettaaksesi:");
            String naruTunnus = scanner.nextLine();
            if (naruTunnus.isEmpty())
                return;
            tunnus = Integer.parseInt(naruTunnus);
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
            try {
                System.out.println("\nNide löytyi.");
                Teos teos = kortisto.getTeosTunnuksella(nide.getID());
                System.out.println("Teos:");
                System.out.println(" " + teos);
                System.out.println("Nide:");
                System.out.println("  " + nide);
            } catch (TeosNotFoundException tnfe) {
                System.out.println("\nTeosta ei löytynyt, palataan.");
            }
        }
    }

    /**
     * Hakee lehtiä nimen perusteella. Mahdollisuuten tulostaa lehtien numerot.
     * 
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
     * 
     * @param tunnus haettavan lehden tunnus
     */
    public void haeNumeroita(int tunnus) {
        System.out.println("\nAnna numeron koko vuosi ja julkaisunumero välilyönnillä erotettuna.");
        String tiedot = scanner.nextLine();
        while (!tiedot.isEmpty()) {
            Numero haettuNumero;
            String[] tietotaulu = tiedot.split(tiedot);
            try {
                for (Numero numero : kortisto.getLehdenNumerot(tunnus)) {
                    if (Integer.parseInt(tietotaulu[0]) == numero.getVuosi() && 
                            Integer.parseInt(tietotaulu[1]) == numero.getNumero()) {
                        haettuNumero = numero;
                        break;
                    }
                }
                System.out.println("\nHaluttu numero löytyi.");
            } catch (LehtiNotFoundException lnfe) {
                System.out.println("\nLehteä ei löytynyt, yritä uudelleen.");
            }
            System.out.println("\nSyötä uuden numeron tiedot tai tyhjä, jos haluta lopettaa.");
            tiedot = scanner.nextLine();
            if (tiedot.isEmpty())
                return;
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
     * Lisää kortistoon uuden niteen hakemalla teoksen ISBN-numerolla.
     * 
     * @see #haeKirja()
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

    /**
     * Hakee kortistosta teoksen ISBN-numerolla ja poistaa sen.
     * 
     * @see #haeKirja( )
     */
    public void poistaKirja() {
        Teos teos = haeKirja();
        if (teos == null)
            return;
        try {
            kortisto.poistaTeos(teos.getID());
        } catch (TeosNotFoundException tnfe) {
            System.out.println("Kirjaa ei löytynyt, palataan.");
            return;
        }
        System.out.println("\nKirja poistettu.");
    }

    /**
     * Poistaa niteen kortistosta hakemalla ensin teoksen ISBN-numerolla, tulostaa ne ja antaa
     * käyttäjän valita poistettava nide.
     * 
     * @see #haeKirja( )
     * @see Kortisto.Kortisto#poistaNide(int, String)
     */
    public void poistaNide() {
        Teos teos = haeKirja();
        if (teos == null) {
            System.out.println("\nKirjaa ei löytynyt, palataan.");
            return;
        }
        System.out.println(" " + teos);
        for (Nide nide: teos.getNiteet())
            System.out.println("  " + nide);
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
    
    /**
     * Muokkaa teoksen muutettavia kenttiä switch-case rakenteella kunnes käyttäjä antaa
     * toistorakenteen katkaisevan syötteen.
     * 
     * @see TextUIValikot#yhdenKirjanMuokkausValikko()
     * @see Kortisto.Kortisto#getTeosTunnuksella(int)
     */
    public void muokkaaKirjaa() {
        Teos teos = haeKirja();
        valikot.yhdenKirjanMuokkausValikko();
        String muutos;
        System.out.print("\nAnna valintasi: ");
        int valinta = Integer.parseInt(scanner.nextLine());
        while (valinta != 0) {
            try {
                switch (valinta) {
                    case 1:
                        System.out.print("\nAnna uusi nimi: ");
                        muutos = scanner.nextLine();
                        kortisto.getTeosTunnuksella(teos.getID()).setNimi(muutos);
                        break;
                    case 2:
                        System.out.print("\nAnna uusi tekijä: ");
                        muutos = scanner.nextLine();
                        kortisto.getTeosTunnuksella(teos.getID()).setTekija(muutos);
                        break;
                    case 3:
                        System.out.print("\nAnna uusi kustantaja: ");
                        muutos = scanner.nextLine();
                        kortisto.getTeosTunnuksella(teos.getID()).setKustantaja(muutos);
                        break;
                    case 4:
                        System.out.print("\nAnna uusi julkaisuvuosi: ");
                        muutos = scanner.nextLine();
                        kortisto.getTeosTunnuksella(teos.getID()).setVuosi(Integer.parseInt(muutos));
                        break;
                    case 5:
                        System.out.print("\nAnna uusi hakusana: ");
                        muutos = scanner.nextLine();
                        kortisto.getTeosTunnuksella(teos.getID()).lisaaHakusana(muutos);
                        break;
                    case 6:
                        System.out.print("\nAnna poistettava hakusana: ");
                        muutos = scanner.nextLine();
                        kortisto.getTeosTunnuksella(teos.getID()).poistaHakusana(muutos);
                    case 0:
                        return;
                    default:
                        System.out.println("\nVäärä valinta, kokeile uudestaan.");
                        break;
                }
            } catch (TeosNotFoundException tnfe) {
                System.out.println("Haettua teosta ei löytynyt, kokeile uudestaan.");
            }
            valikot.yhdenKirjanMuokkausValikko();
            System.out.print("\nAnna seuraava valintasi: ");
            valinta = Integer.parseInt(scanner.nextLine());
        }
        System.out.println("\nPalataan valikkoon.");
    }
    
    /**
     * Luo uuden lehden käyttäjän antamista syötteistä ja lisää sen kortistoon.
     * 
     * @see Kortisto.Kortisto#lisaaLehti(java.lang.String, java.lang.String, java.lang.String) 
     */
    public void lisaaLehti() {
        System.out.print("\nLehden ISSN: ");
        String issn = scanner.nextLine();
        System.out.print("Nimi: ");
        String nimi = scanner.nextLine();
        System.out.print("Kustantaja: ");
        String kustantaja = scanner.nextLine();
        try {
            kortisto.lisaaLehti(issn, nimi, kustantaja);
        } catch (LehtiFoundException lfe) {
            System.out.println("\nLehti löytyi kortistosta, ei lisätty.");
        }
        System.out.println("\nLehti lisätty.");
    }
    
    /**
     * Poistaa kortistosta lehden käyttäjän syötteen preusteella.
     * 
     * @see #haeLehti()
     * @see Kortisto.Kortisto#poistaLehti(int) 
     */
    public void poistaLehti() {
        Lehti lehti = haeLehti();
        if (lehti == null) {
            System.out.println("\nLehteä ei löytynyt, ei poisteta.");
            return;
        }
        try {
            kortisto.poistaLehti(lehti.getID());
        } catch (LehtiNotFoundException lnfe) {
            System.out.println("\nLehteä ei löytynyt, ei poisteta.");
        } 
        System.out.println("\nLehti poistettu.");
    }
    
    /**
     * Lisää uuden numeron ISBN-numerolla haettavaan lehteen.
     * 
     * @see #haeLehti()
     * @see Kortisto.Kortisto#lisaaNumero(int, int, int)
     */
    public void lisaaNumero() {
        Lehti lehti = haeLehti();
        if (lehti == null)
            return;
        System.out.print("\nAnna lisättävän numeron julkaisuvuosi ja -numero välilyönnillä\n"
                +" erotettuna: ");
        String tiedot = scanner.nextLine();
        String[] tietotaulu = tiedot.split(tiedot);
        try {
            kortisto.lisaaNumero(lehti.getID(), Integer.parseInt(tietotaulu[0]),
                Integer.parseInt(tietotaulu[1]));
        } catch (LehtiNotFoundException lnfe) {
            System.out.println("\nLehteä ei löytynyt, ei poisteta.");
        }
        System.out.println("\nNumero lisätty.");
    }

    /**
     * Poistaa kortistosta numeron käyttäjän syötteestä luetusta lehdestä.
     * 
     * @see #haeLehti()
     * @see Kortisto.Kortisto#poisatNumero(int, int, int)
     */
    public void poistaNumero() {
        Lehti lehti = haeLehti();
        if (lehti == null)
            return;
        System.out.print("\nAnna poistettavan numeron julkaisuvuosi ja -numero välilyönnillä\n"
                + " erotettuna:");
        String tiedot = scanner.nextLine();
        String[] tietotaulu = tiedot.split(tiedot);
        try {
            kortisto.poistaNumero(lehti.getID(), Integer.parseInt(tietotaulu[0]),
                    Integer.parseInt(tietotaulu[1]));
        } catch (LehtiNotFoundException lfne) {
            System.out.println("Lehteä ei löytynyt, ei poistettu.");
        } catch (NumeroNotFoundException nnfe) {
            System.out.println("Numeroa ei löytynyt, ei poistettu.");
        }
        System.out.println("\nNumero poistettu.");
    }

    /**
     * Muokkaa kortistossa olevaa lehteä kunnes käyttäjä antaa toistorakenteen 
     * päättävän syötteen.
     * 
     * @see TextUIValikot#lehtiMuokkausValikko() 
     * @see Kortisto.Kortisto#getLehtiTunnuksella(int) 
     */
    public void muokkaaLehtea() {
        Lehti lehti = haeLehti();
        if (lehti == null)
            return;
        valikot.lehtiMuokkausValikko();
        String muutos;
        System.out.println(lehti);
        System.out.print("\nValitse muokkauskohde: ");
        int valinta = Integer.parseInt(scanner.nextLine());
        while (valinta != 0) {
            try {
                switch (valinta) {
                    case 1:
                        System.out.print("\nAnna uusi ISSN: ");
                        muutos = scanner.nextLine();
                        kortisto.getLehtiTunnuksella(lehti.getID()).setISSN(muutos);
                        break;
                    case 2:
                        System.out.print("\nAnna uusi nimi: ");
                        muutos = scanner.nextLine();
                        kortisto.getLehtiTunnuksella(lehti.getID()).setNimi(muutos);
                        break;
                    case 3:
                        System.out.print("\nAnna uusi kustantaja: ");
                        muutos = scanner.nextLine();
                        kortisto.getLehtiTunnuksella(lehti.getID()).setKustantaja(muutos);
                        break;
                    case 4:
                        System.out.print("\nAnna lisättävä hakusana: ");
                        muutos = scanner.nextLine();
                        kortisto.getLehtiTunnuksella(lehti.getID()).lisaaHakusana(muutos);
                        break;
                    case 5:
                        System.out.print("\nAnna poistettava hakusana: ");
                        muutos = scanner.nextLine();
                        kortisto.getLehtiTunnuksella(lehti.getID()).poistaHakusana(muutos);
                        break;
                    case 6:
                        kirjoitaKortisto();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.print("\nHuono syöte, anna uudestaan.");
                }
            } catch (LehtiNotFoundException lnfe) {
                System.out.println("\nHaettua lehteä ei löytynyt, yritä uudestaan.");
            }
            valikot.lehtiMuokkausValikko();
            System.out.print("\nAnna seuraava valinta: ");
            valinta = Integer.parseInt(scanner.nextLine());
        }
    }
    
    /**
     * Yleiskäyttöinen metodi, joka hakee kutsuvalle metodille kirjan sen ISBN-
     * numeron perusteella ja palauttaa sen tai null, jos ei löydy.
     * 
     * @return haettu teos.
     */
    public Teos haeKirja() {
        System.out.print("\nAnna haettavan kirjan ISBN: ");
        String isbn = scanner.nextLine();
        Teos teos = kortisto.getHakukone().haeTeosISBN(kortisto, isbn);
        if (teos == null) {
            System.out.println("\nTeosta ei löytynyt, palataan.");
            return teos;
        }
        return teos;
    }
    
    /**
     * Yleiskäyttöinen metodi, joka hakee kutsuvalle metodille lehden sen ISSN-numeron perusteella
     * ja palautta sen tai null, jos ei löydy.
     * 
     * @return haettu lehti.
     */
    public Lehti haeLehti() {
        System.out.print("\nAnna haettavan lehden ISSN: ");
        String issn = scanner.nextLine();
        Lehti lehti = kortisto.getHakukone().haeLehtiISSN(kortisto, issn);
        if (lehti == null) {
            System.out.println("\nLehteä ei löytynyt, palataan.");
            return lehti;
        }
        return lehti;
    }
    
    /**
     * Kirjoittaa kortisto-instanssin senhetkiseen tiedostoon.
     *
     * @see UI.TiedostonKasittelija#kirjoitaTiedosto()
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
     * Kysyy käyttäjältä uuden tiedoston ja parametrista riippuen joko kirjoittaa
     * kortiston uuteen tiedostoon tai lukee uudesta tiedostosta, asettaa sen instans-
     * siksi ja palauttaa uudesta tiedostosta luetun kortiston.
     * 
     * @param vaihtoehto true jos kirjoitetaan uusi tiedosto, false jos vain luetaan
     */
    public void vaihdaTiedosto(boolean vaihtoehto) {
        String uusiTiedosto;
        try {
            if (vaihtoehto) {
                System.out.print("\nAnna kirjoitettavan tiedoston nimi:");
                uusiTiedosto = scanner.nextLine();
                tiedKas.kirjoitaUusiTiedosto(kortisto, uusiTiedosto);
            } else {
                System.out.print("\nAnna luettavan tiedoston nimi:");
                uusiTiedosto = scanner.nextLine();
                kortisto = tiedKas.lueUusiTiedosto(uusiTiedosto);
            }
        } catch (IOException ioe) {
            if (ioe.getClass() != EOFException.class)
                System.out.println("\nTiedoston kirjoittaminen epäonnistui.");
        } catch (ClassNotFoundException cex) {
                System.out.println("\nLuokkaa \"Kortisto\" ei löytynyt.");
        }
    }
    
    /**
     * Toiminnon valinta päävalikossa oleville toiminnoille ohjaamalla alavalikkoon.
     * Poikkeuksina tähän valinta 2, joka suorittaa suoraan metodin haeLehtiaNimella()
     * sekä valinta 5, joka kirjoittaa kortiston sen hetkiseen tiedostoon.
     * 
     * @see TextUIValikot#paaValikko()
     */
    public void aloitusValinta() {
        System.out.print("\nValinta: ");
        int valinta = Integer.parseInt(scanner.nextLine());
        while (valinta != 0) {
            switch (valinta) {
                case 1:
                    kirjaValinta();
                    break;
                case 2:
                    haeLehtiaNimella();
                    break;
                case 3:
                    muokkaaKirjoja();
                    break;
                case 4:
                    muokkaaLehtiaValinta();
                    break;
                case 5:
                    tiedostoValikko();
                    break;
                default:
                    System.out.println("\nYritä uudestaan.");
                    break;
            }
            valikot.paaValikko();
            System.out.print("\nAnna seuraava valinta: ");
            valinta = Integer.parseInt(scanner.nextLine());
        }
    }

    /**
     * Toiminnan valinta kirjojen hakuvalikolle, joka ohjaa haluttuun hakumetodiin.
     * 
     * @see TextUIValikot#kirjaValikko()
     */
    public void kirjaValinta() {
        valikot.kirjaValikko();
        System.out.print("\nAnna valintasi: ");
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
                case 5:
                    listaaKokoelmanNiteet();
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
     * 
     * @see TextUIValikot#kirjaMuokkausValikko() 
     */
    public void muokkaaKirjoja() {
        valikot.kirjaMuokkausValikko();
        System.out.print("\nAnna valinta: ");
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
                    poistaKirja();
                    break;
                case 5:
                    poistaNide();
                    break;
                case 6:
                    kirjoitaKortisto();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\nHuono syöte, yritä uudelleen.");
                    break;
            }
            valikot.kirjaMuokkausValikko();
            System.out.print("\nAnna seuraava valinta: ");
            valinta = Integer.parseInt(scanner.nextLine());
        }
    }

    /**
     * Toiminnan valinta lehtien muokkausvalikolle, joka ohjaa haluttuun muokkausmetodiin.
     * 
     * @see TextUIValikot#lehtiMuokkausValikko() 
     */
    public void muokkaaLehtiaValinta() {
        valikot.lehtiMuokkausValikko();
        System.out.print("\nAnna valintasi: ");
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
                case 4:
                    poistaLehti();
                    break;
                case 5:
                    poistaNumero();
                    break;
                case 6:
                    kirjoitaKortisto();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\nHuono syöte, yritä uudelleen.");
                    break;
            }
            valikot.lehtiMuokkausValikko();
            System.out.print("\nAnna seuraava valinta: ");
            valinta = Integer.parseInt(scanner.nextLine());
        }
    }
    
    /**
     * Valikko tiedostonkäsittelyn toimintoja varten.
     * 
     * @see TextUIValikot#tiedostoValikko() 
     */
    public void tiedostoValikko() {
        valikot.tiedostoValikko();
        System.out.print("\nAnna valinta: ");
        int valinta = Integer.parseInt(scanner.nextLine());
        while (valinta != 0) {
            switch (valinta) {
                case 1:
                    kirjoitaKortisto();
                    break;
                case 2:
                    vaihdaTiedosto(true);
                    break;
                case 3:
                    vaihdaTiedosto(false);
                    break;
                case 4:
                    System.out.println("\nTällä hetkellä käytössä on tiedosto \"" + tiedKas.getFilename() + "\".");
                    System.out.println("Paina rivinvaihtoa jatkaaksesi.");
                    scanner.nextLine();
                case 0:
                    return;
                default:
                    System.out.println("\nVäärä syöte, anna uudelleen.");
            }
            valikot.tiedostoValikko();
            System.out.print("\nAnna seuraava valinta: ");
            valinta = Integer.parseInt(scanner.nextLine());
        }
    }
}