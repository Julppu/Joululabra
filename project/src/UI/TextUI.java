
/**
 * @author Juha Lindqvist <juha.lindqvist@cs.helsinki.fi>
 * @since  06012013
 */

package UI;

import Kortisto.Kortisto;
import Kortisto.Lehti;
import Kortisto.Nide;
import Kortisto.Teos;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextUI {
    
    private Kortisto kortisto;
    private TiedostonKasittelija tiedKas;
    private Scanner scanner;
    
    public TextUI() {
        scanner = new Scanner(System.in);
    }
    
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
    
    public void aloitusValinta() {
        int valinta = scanner.nextInt();
        while (valinta != 0) {
            switch (valinta) {
                case 1:
                    kirjat();
                    break;
                case 2:
                    lehdet();
                    break;
                case 3:
                    muokkaaKirjoja();
                    break;
                case 4:
                    muokkaaLehtia();
                    break;
                case 5:
                    try {
                        tiedKas.kirjoitaKortisto();
                        System.out.println("Kortisto kirjoitettu tiedostoon!");
                    } catch() {
                        System.out.println("Tiedoston kirjoittaminen epäonnistui!");
                    }
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Yritä uudestaan.");
                    break;
            }
            paaValikko();
            System.out.print("Anna seuraava valinta: ");
            valinta = scanner.nextInt();
        }
    }
    
    public void kirjat() {
        kirjaValikko();
        int valinta = scanner.nextInt();
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
            kirjaValikko();
            valinta = scanner.nextInt();
        }
    }

    public void muokkaaLehtia() {
        lehtiValikko();
        int valinta = scanner.nextInt();
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
                    muokkaaNumeroa();
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
             lehtiValikko();
             valinta = scanner.nextInt();
         }
    }
    
    public void haeKirjojaNimella() {
        System.out.print("Anna kirjan nimi: ");
        String nimi = scanner.nextLine();
        while (nimi.isEmpty()) {
            System.out.print("Tyhjä syöte, anna uudestaan: ");
            nimi = scanner.nextLine();
        }
        ArrayList<Teos> teokset = kortisto.getHakukone().haeTeoksiaNimella(kortisto, nimi);
        System.out.println("\n Löydetyt teokset: ");
        for (Teos teos: teokset)
            System.out.println("  " + teos);
        System.out.print("\n\n Jos haluat tarkastella jonkin teoksen niteitä, anna sen tunnus\n," +
                "muussa tapauksessa palataan alkuun: ");
        haeNiteita(scanner.nextLine());
    }
    
    public void haeKirjojaTekijalla() {
        System.out.print("Anna kirjan nimi: ");
        String tekija = scanner.nextLine();
        while (tekija.isEmpty()) {
            System.out.print("Tyhjä syöte, anna uudestaan: ");
            tekija = scanner.nextLine();
        }
        ArrayList<Teos> teokset = kortisto.getHakukone().haeTeoksiaTekijalla(kortisto, tekija);
        System.out.println("\n Löydetyt teokset: ");
        for (Teos teos: teokset)
            System.out.println("  " + teos);
        System.out.print("\n\n Jos haluat tarkastella jonkin teoksen niteitä, anna sen tunnus\n," +
                "muussa tapauksessa palataan alkuun: ");
        haeNiteita(scanner.nextLine());
    }
    
    public void haeKirjaISBN() {
        System.out.print("Anna kirjan ISBN: ");
        String isbn = scanner.nextLine();
        while (isbn.isEmpty()) {
            System.out.print("Tyhjä syöte, anna uudestaan: ");
            isbn = scanner.nextLine();
        }
        Teos teos = kortisto.getHakukone().haeTeosISBN(kortisto, isbn);
        System.out.println(teos);
        for (Nide nide: teos.getNiteet())
            System.out.println("  " + nide);
    }
    
    public void haeNiteita(int tunnus) {
        while (!tunnus.isEmpty()) {
            Teos teos = kortisto.getHakukone().haeTeosTunnuksella(kortisto, Integer.parseInt(tunnus));
            if (teos.getNiteet().isEmpty()) {
                System.out.println("Ei niteitä, palataan.");
            } else {
                for (Nide nide: teos.getNiteet()) {
                    System.out.println(" " + teos);
                    System.out.println("  " + nide);
                }
            }
            System.out.println("\n\nAnna seuraava teoksen tunnus tai tyhjä lopettaaksesi:");
            tunnus = scanner.nextLine();
        }
    }

    public void haeNideViivakoodilla() {
        System.out.print("Anna viivakoodi: ");
        String viivakoodi = scanner.nextLine();
        Nide nide = kortisto.getHakukone().haeNideViivakoodilla(kortisto, viivakoodi);
        if (nide == null)
            System.out.println("Haluttua nidettä ei löytynyt.");
        else {
            System.out.println("Nide löytyi."
            Teos teos = kortisto.getHakukone().haeTeosTunnuksella(kortisto, nide.getID());
            System.out.println("Teos:");
            System.out.println(" " + teos);
            System.out.println("Nide:");
            System.out.println("  " + nide);
        }
    }
    
    public void haeLehtiaNimella() {
        System.out.print("Anna lehden nimi: ");
        String nimi = scanner.nextLine();
        while (nimi.isEmpty()) {
            System.out.print("Tyhjä syöte, anna uudestaan: ");
            nimi = scanner.nextLine();
        }
        ArrayList<Lehti> lehdet = kortisto.getHakukone().haeLehtiaNimella(kortisto, nimi);
        System.out.println("\n Löydetyt lehdet: ");
        for (Lehti lehti: lehdet)
            System.out.println("  " + lehti);
        System.out.print("\n\n Jos haluat tarkastella jonkin lehden numeroita, anna sen tunnus\n," +
                "muussa tapauksessa palataan alkuun: ");
        haeNumeroita(scanner.nextLine());
    }
    
    public void haeNumeroita(int tunnus) {
        System.out.println("Anna numeron koko vuosi ja julkaisunumero välilyönnillä erotettuna.");
        String tiedot = scanner.nextLine();
        while (!tiedot.isEmpty()) {
            Numero haettuNumero;
            String[] tietotaulu = tiedot.split();
            for (Numero numero: kortisto.getLehdenNumerot(tunnus)) {
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
    
    public void muokkaaTeosta() {
        
    }
    
    public void muokkaaLehtea() {
        
    }
    
    public void lueKortisto() {
        
    }

    public void vaihdaTiedosto() {
        
    }

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

    public void kirjaValikko() {
        System.out.println("* Kirjojen haku:");
        System.out.println("* 1. Hae nimellä");
        System.out.println("* 2. Hae tekijällä");
        System.out.println("* 3. Hae ISBN-numerolla");
        System.out.println("* 4. Hae nide viivakoodilla");
        System.out.println("* 0. Lopeta");
    }

    public void kirjaMuokkaus() {
        System.out.println("* Kirjojen muokkaus:");
        System.out.println("* 1. Lisää kirja");
        System.out.println("* 2. Lisää nide");
        System.out.println("* 3. Muokkaa kirjaa");
        System.out.println("* 4. Muokkaa nidettä");
    }
}
