package UI;

/**
 * Kortisto-ohjelmiston tekstipohjaisen käyttöliittymän luokka.
 * 
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since 06012013
 */


import Kortisto.*;
import Kortisto.Poikkeukset.*;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

public class TextUI {
    
    /** kirjojen ja lehtien tiedot sisältävä kortistoinstanssi. */
    private Kortisto kortisto;
    /** instanssi tiedostojen kirjoittamista ja lukemista varten. */
    private TiedostonKasittelija tiedKas;
    /** valikot sisältävä apuluokka. */
    private TextUIValikot valikot;
    /** lukee syötteen käyttäjältä ja palauttaa sen. */
    private SyotteenLukija lukija;

    public TextUI() {
        lukija = new SyotteenLukija();
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
                + " numero halutun operaation mukaan.\n");
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
        String nimi = lukija.lueString();
        while (nimi.isEmpty()) {
            System.out.print("Tyhjä syöte, anna uudestaan: ");
            nimi = lukija.lueString();
        }
        ArrayList<Teos> teokset = kortisto.getHakukone().haeTeoksiaNimella(kortisto, nimi);
        if (teokset.isEmpty()) {
            System.out.println("\nMitään ei löytynyt, palataan.");
            return;
        }
        System.out.println("\n Löydetyt teokset: ");
        for (Teos teos : teokset)
            System.out.println("  " + teos);
        System.out.print("\n Jos haluat tarkastella jonkin teoksen niteitä, anna sen tunnus,\n"
                + "muussa tapauksessa palataan alkuun: ");
        String id = lukija.lueString();
        if (id.isEmpty())
            return;
        else {
            try {
                haeNiteita(Integer.parseInt(id));
            } catch (NumberFormatException nfe) {
                System.out.println("Et antanut numeroa, palataan takaisin.");
            }
        }
    }

    /**
     * Metodi kirjojen hakemiseen tekijällä. Mahdollisuus myös tulostaa kaikki sen niteet.
     */
    public void haeKirjojaTekijalla() {
        System.out.print("\nAnna kirjan nimi: ");
        String tekija = lukija.lueString();
        while (tekija.isEmpty()) {
            System.out.print("Tyhjä syöte, anna uudestaan: ");
            tekija = lukija.lueString();
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
        String id = lukija.lueString();
        if (id.isEmpty())
            return;
        try {
            haeNiteita(Integer.parseInt(id));
        } catch (NumberFormatException nfe) {
            System.out.println("Et antanut numeroa, palataan.");
        }
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
        String kokoelma = lukija.lueString();
        while (!kokoelma.isEmpty()) {
            ArrayList<Nide> niteet = kortisto.getHakukone().haeKokoelmanNiteet(kortisto, kokoelma);
            for (Nide nide: niteet) {
                try {
                    System.out.println("\n " + kortisto.getTeosTunnuksella(nide.getID()));
                    System.out.println("  " + nide);
                } catch (TeosNotFoundException tnfe) {
                    System.out.println("\nTeosta ei löydytynyt, kokeile uudestaan.");
                }
            }
            System.out.print("\nAnna seuraava kokoelma (tyhjällä palaa): ");
            kokoelma = lukija.lueString();
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
                System.out.println("\nEi niteitä, palataan.");
            else {
                System.out.println(" " + teos);
                for (Nide nide : teos.getNiteet())
                    System.out.println("  " + nide);
            }
            System.out.println("\nAnna seuraava teoksen tunnus tai tyhjä lopettaaksesi:");
            String naruTunnus = lukija.lueString();
            if (naruTunnus.isEmpty())
                return;
            try {
                tunnus = Integer.parseInt(naruTunnus);
            } catch (NumberFormatException nfe) {
                System.out.println("Et antanut numeroa, palataan.");
            }
        }
    }

    /**
     * Hakee niteen viivakoodin perusteella. Tulostaa teoksen tiedot, johon se kuuluu,
     * sekä niteen tiedot.
     */
    public void haeNideViivakoodilla() {
        System.out.print("\nAnna viivakoodi: ");
        String viivakoodi = lukija.lueString();
        Nide nide = kortisto.getHakukone().haeNideViivakoodilla(kortisto, viivakoodi);
        if (nide == null)
            System.out.println("\nHaluttua nidettä ei löytynyt.");
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
        String nimi = lukija.lueString();
        while (nimi.isEmpty()) {
            System.out.print("\nTyhjä syöte, anna uudestaan: ");
            nimi = lukija.lueString();
        }
        ArrayList<Lehti> lehdet = kortisto.getHakukone().haeLehtiaNimella(kortisto, nimi);
        if (lehdet.isEmpty()) {
            System.out.println("\nLehtiä ei löytynyt, palataan.");
            return;
        }
        
        System.out.println("\n Löydetyt lehdet: ");
        for (Lehti lehti : lehdet)
            System.out.println("  " + lehti);
        System.out.print("\n Jos haluat tarkastella jonkin lehden numeroita, anna sen tunnus,\n"
                + " muussa tapauksessa palataan alkuun: ");
        String id = lukija.lueString();
        if (id.isEmpty())
            return;
        try {
            haeNumeroita(Integer.parseInt(id));
        } catch (NumberFormatException nfe) {
            System.out.println("Et antanut numeroa, palataan.");
        }
    }

    /**
     * Hakee tiettyä numeroa lehden numeroiden listasta ja tulostaa sen.
     * 
     * @param tunnus haettavan lehden tunnus
     */
    public void haeNumeroita(int tunnus) {
        System.out.println("\nAnna ensin vuosi ja sitten julkaisunumero:");
        int vuosi = lukija.lueInt();
        int julkaisunNumero = lukija.lueInt();
        Numero haettuNumero;
        try {
            for (Numero numero : kortisto.getLehdenNumerot(tunnus)) {
                if (vuosi == numero.getVuosi() && julkaisunNumero == numero.getNumero()) {
                    haettuNumero = numero;
                    break;
                }
            }
            System.out.println("\nHaluttu numero löytyi.");
        } catch (LehtiNotFoundException lnfe) {
            System.out.println("\nLehteä ei löytynyt, yritä uudelleen.");
        }
    }

    /**
     * Lisää kortistoon kirjan luettuaan käyttäjältä syötteet. Palaa lisäämättä
     * takaisin jos saadaan poikkeus TeosFoundException.
     */
    public void lisaaKirja() {
        System.out.println("\nAnna tarvittavat kentät");
        System.out.print("ISBN: ");
        String isbn = lukija.lueString();
        System.out.print("Nimi: ");
        String nimi = lukija.lueString();
        System.out.print("Tekijä(t), jos toimitettu jätä tyhjäksi:");
        String tekija = lukija.lueString();
        if (tekija.isEmpty())
            tekija = "(toim.)";
        System.out.print("Julkaisuvuosi: ");
        int vuosi = lukija.lueInt();
        System.out.print("Kustantaja: ");
        String kustantaja = lukija.lueString();
        try {
            kortisto.lisaaTeos(isbn, nimi, tekija, vuosi, kustantaja);
        } catch (TeosFoundException tfe) {
            System.out.println("\nTeos löytyi jo kortistosta, palataan edelliseen.");
            return;
        }
        System.out.println("\nTeos lisätty.");
    }

    /**
     * Lisää kortistoon uuden niteen hakemalla teoksen ISBN-numerolla.
     * 
     * @see #haeKirja()
     */
    public void lisaaNide() {
        Teos teos = haeKirja();
        System.out.print("\nAnna niteen kokoelma: ");
        String kokoelma = lukija.lueString();
        System.out.print("Laina-aika: ");
        int lainaAika = lukija.lueInt();
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
            System.out.println("\nKirjaa ei löytynyt, palataan.");
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
        System.out.print("Anna niteen viivakoodi: ");
        String viivakoodi = lukija.lueString();
        try {
            kortisto.poistaNide(teos.getID(), viivakoodi);
        } catch (TeosNotFoundException ex) {
            System.out.println("\nVirhe: Kirjaa ei löytynyt.");
        } catch (NideNotFoundException nex) {
            System.out.println("\nVirhe: Nidettä ei löytynyt.");
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
        int valinta = lukija.lueInt();
        while (valinta != 0) {
            try {
                switch (valinta) {
                    case 1:
                        System.out.print("\nAnna uusi nimi: ");
                        muutos = lukija.lueString();
                        kortisto.getTeosTunnuksella(teos.getID()).setNimi(muutos);
                        break;
                    case 2:
                        System.out.print("\nAnna uusi tekijä: ");
                        muutos = lukija.lueString();
                        kortisto.getTeosTunnuksella(teos.getID()).setTekija(muutos);
                        break;
                    case 3:
                        System.out.print("\nAnna uusi kustantaja: ");
                        muutos = lukija.lueString();
                        kortisto.getTeosTunnuksella(teos.getID()).setKustantaja(muutos);
                        break;
                    case 4:
                        System.out.print("\nAnna uusi julkaisuvuosi: ");
                        int vuosi = lukija.lueInt();
                        kortisto.getTeosTunnuksella(teos.getID()).setVuosi(vuosi);
                        break;
                    case 5:
                        System.out.print("\nAnna uusi hakusana: ");
                        muutos = lukija.lueString();
                        kortisto.getTeosTunnuksella(teos.getID()).lisaaHakusana(muutos);
                        break;
                    case 6:
                        System.out.print("\nAnna poistettava hakusana: ");
                        muutos = lukija.lueString();
                        kortisto.getTeosTunnuksella(teos.getID()).poistaHakusana(muutos);
                    case 0:
                        return;
                    default:
                        System.out.println("\nVäärä valinta, kokeile uudestaan.");
                        break;
                }
            } catch (TeosNotFoundException tnfe) {
                System.out.println("\nHaettua teosta ei löytynyt, kokeile uudestaan.");
            }
            valikot.yhdenKirjanMuokkausValikko();
            System.out.print("\nAnna seuraava valintasi: ");
            valinta = lukija.lueInt();
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
        String issn = lukija.lueString();
        System.out.print("Nimi: ");
        String nimi = lukija.lueString();
        System.out.print("Kustantaja: ");
        String kustantaja = lukija.lueString();
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
        if (lehti == null) {
            System.out.println("Lehteä ei löytynyt, palataan.");
            return;
        }
        System.out.println("\nAnna ensin lisättävän numeron julkaisuvuosi ja sen jälkeen -numero: ");
        int vuosi = lukija.lueInt();
        int numero = lukija.lueInt();
        try {
            kortisto.lisaaNumero(lehti.getID(), vuosi, numero);
        } catch (LehtiNotFoundException lnfe) {
            System.out.println("\nLehteä ei löytynyt, ei poisteta.");
            return;
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
        System.out.println("\nAnna ensin poistettavan numeron julkaisuvuosi ja sitten -numero:");
        int vuosi = lukija.lueInt();
        int numero = lukija.lueInt();
        try {
            kortisto.poistaNumero(lehti.getID(), vuosi, numero);
        } catch (LehtiNotFoundException lfne) {
            System.out.println("\nLehteä ei löytynyt, ei poistettu.");
            return;
        } catch (NumeroNotFoundException nnfe) {
            System.out.println("\nNumeroa ei löytynyt, ei poistettu.");
            return;
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
        int valinta = lukija.lueInt();
        while (valinta != 0) {
            try {
                switch (valinta) {
                    case 1:
                        System.out.print("\nAnna uusi ISSN: ");
                        muutos = lukija.lueString();
                        kortisto.getLehtiTunnuksella(lehti.getID()).setISSN(muutos);
                        break;
                    case 2:
                        System.out.print("\nAnna uusi nimi: ");
                        muutos = lukija.lueString();
                        kortisto.getLehtiTunnuksella(lehti.getID()).setNimi(muutos);
                        break;
                    case 3:
                        System.out.print("\nAnna uusi kustantaja: ");
                        muutos = lukija.lueString();
                        kortisto.getLehtiTunnuksella(lehti.getID()).setKustantaja(muutos);
                        break;
                    case 4:
                        System.out.print("\nAnna lisättävä hakusana: ");
                        muutos = lukija.lueString();
                        kortisto.getLehtiTunnuksella(lehti.getID()).lisaaHakusana(muutos);
                        break;
                    case 5:
                        System.out.print("\nAnna poistettava hakusana: ");
                        muutos = lukija.lueString();
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
            valinta = lukija.lueInt();
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
        String isbn = lukija.lueString();
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
        String issn = lukija.lueString();
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
                System.out.print("\nAnna kirjoitettavan tiedoston nimi: ");
                uusiTiedosto = lukija.lueString();
                tiedKas.kirjoitaUusiTiedosto(kortisto, uusiTiedosto);
            } else {
                System.out.print("\nAnna luettavan tiedoston nimi: ");
                uusiTiedosto = lukija.lueString();
                kortisto = tiedKas.lueUusiTiedosto(uusiTiedosto);
            }
        } catch (IOException ioe) {
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
        System.out.print("\nAnna valintasi: ");
        int valinta = lukija.lueInt();
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
                    System.out.println("\nHuono syöte, yritä uudestaan.");
                    break;
            }
            valikot.paaValikko();
            System.out.print("\nAnna seuraava valinta: ");
            valinta = lukija.lueInt();
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
        int valinta = lukija.lueInt();
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
                    System.out.println("Huono syöte, yritä uudestaan.");
                    break;
            }
            valikot.kirjaValikko();
            System.out.print("\nAnna seuraava valinta: ");
            valinta = lukija.lueInt();
        }
    }

    /**
     * Toiminnan valinta kirjojen muokkausvalikolle, joka ohjaa haluttuun muokkausmetodiin.
     * 
     * @see TextUIValikot#kirjaMuokkausValikko() 
     */
    public void muokkaaKirjoja() {
        valikot.kirjaMuokkausValikko();
        System.out.print("\nAnna valintasi: ");
        int valinta = lukija.lueInt();
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
                    System.out.println("\nHuono syöte, yritä uudestaan.");
                    break;
            }
            valikot.kirjaMuokkausValikko();
            System.out.print("\nAnna seuraava valinta: ");
            valinta = lukija.lueInt();
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
        int valinta = lukija.lueInt();
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
            valinta = lukija.lueInt();
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
        int valinta = lukija.lueInt();
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
                    lukija.lueString();
                case 0:
                    return;
                default:
                    System.out.println("\nHuono syöte, anna uudelleen.");
            }
            valikot.tiedostoValikko();
            System.out.print("\nAnna seuraava valinta: ");
            valinta = lukija.lueInt();
        }
    }
}