package hr.tvz.java.projekt.logika;

import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.Vlada;

import java.util.ArrayList;
import java.util.List;

public class KatalogZakona {

    private final List<String> nazivi = new ArrayList<>();
    private final List<String> opisi = new ArrayList<>();
    private final List<Boolean> iskoristeni = new ArrayList<>();

    public KatalogZakona() {
        napuniKatalog();
    }

    private void napuniKatalog() {
        dodajZakon("Povecanje poreza", "Stopa poreza raste za 0.10");
        dodajZakon("Smanjenje poreza", "Stopa poreza pada za 0.10");
        dodajZakon("Povecanje minimalne place", "Minimalna placa raste za 2.00");
        dodajZakon("Smanjenje minimalne place", "Minimalna placa pada za 1.00");
        dodajZakon("Socijalni transferi", "Vlada gubi 20 iz proracuna, radnici dobivaju pomoc");
        dodajZakon("Porezne olaksice za poduzetnike", "Stopa poreza pada za 0.05, poticaj poduzetnistvu");
        dodajZakon("Privatizacija javnih usluga", "Vlada gubi 10 legitimnosti, dobiva 30 u proracun");
        dodajZakon("Nacionalizacija kljucnih industrija", "Kapitalist gubi 30 kapitala, Vlada dobiva 30 u proracun");
        dodajZakon("Investicijski poticaji", "Vlada gubi 25 iz proracuna, Kapitalist dobiva 25 kapitala");
        dodajZakon("Reforma radnog zakonodavstva", "Radnicka klasa dobiva 10 standarda zivota, Vlada gubi 5 legitimnosti");
    }

    private void dodajZakon(String naziv, String opis) {
        nazivi.add(naziv);
        opisi.add(opis);
        iskoristeni.add(false);
    }

    public int dohvatiBrojZakona() { return nazivi.size(); }
    public String dohvatiNaziv(int indeks) { return nazivi.get(indeks); }
    public String dohvatiOpis(int indeks) { return opisi.get(indeks); }
    public boolean jeIskoristen(int indeks) { return iskoristeni.get(indeks); }
    public void oznaciIskoristenim(int indeks) { iskoristeni.set(indeks, true); }

    public void primijeniEfekt(int indeks, List<KlasaIgraca> listaIgraca, Vlada vlada) {
        String nazivZakona = dohvatiNaziv(indeks);

        switch (nazivZakona) {
            case "Povecanje poreza" -> vlada.promijeniStopuPoreza(vlada.getStopaPoreza() + 0.10);
            case "Smanjenje poreza" -> vlada.promijeniStopuPoreza(vlada.getStopaPoreza() - 0.10);
            case "Povecanje minimalne place" -> vlada.promijeniMinimalnuPlacu(vlada.getMinimalnaPlaca() + 2.0);
            case "Smanjenje minimalne place" -> vlada.promijeniMinimalnuPlacu(vlada.getMinimalnaPlaca() - 1.0);
            case "Socijalni transferi" -> primijeniSocijalneTransfere(listaIgraca, vlada);
            case "Porezne olaksice za poduzetnike" -> vlada.promijeniStopuPoreza(vlada.getStopaPoreza() - 0.05);
            case "Privatizacija javnih usluga" -> {
                vlada.setDrzavniProracun(vlada.getDrzavniProracun() + 30.0);
                vlada.smanjiLegitimnost(10);
            }
            case "Nacionalizacija kljucnih industrija" -> primijeniNacionalizaciju(listaIgraca, vlada);
            case "Investicijski poticaji" -> primijeniInvesticijskePoticaje(listaIgraca, vlada);
            case "Reforma radnog zakonodavstva" -> primijeniReformuRada(listaIgraca, vlada);
            default -> System.out.println("Nepoznat zakon: " + nazivZakona);
        }
    }

    private void primijeniSocijalneTransfere(List<KlasaIgraca> listaIgraca, Vlada vlada) {
        vlada.setDrzavniProracun(vlada.getDrzavniProracun() - 20.0);
        for (KlasaIgraca igrac : listaIgraca) {
            if (igrac instanceof RadnickaKlasa radnicka) {
                radnicka.kupiHranu(5, 0);
            }
        }
    }

    private void primijeniNacionalizaciju(List<KlasaIgraca> listaIgraca, Vlada vlada) {
        vlada.setDrzavniProracun(vlada.getDrzavniProracun() + 30.0);
        for (KlasaIgraca igrac : listaIgraca) {
            if (igrac instanceof KapitalistickaKlasa kapitalist) {
                kapitalist.setUkupniKapital(kapitalist.getUkupniKapital() - 30.0);
            }
        }
    }

    private void primijeniInvesticijskePoticaje(List<KlasaIgraca> listaIgraca, Vlada vlada) {
        vlada.setDrzavniProracun(vlada.getDrzavniProracun() - 25.0);
        for (KlasaIgraca igrac : listaIgraca) {
            if (igrac instanceof KapitalistickaKlasa kapitalist) {
                kapitalist.setUkupniKapital(kapitalist.getUkupniKapital() + 25.0);
            }
        }
    }

    private void primijeniReformuRada(List<KlasaIgraca> listaIgraca, Vlada vlada) {
        vlada.smanjiLegitimnost(5);
        for (KlasaIgraca igrac : listaIgraca) {
            if (igrac instanceof RadnickaKlasa radnicka) {
                radnicka.setStandardZivota(radnicka.getStandardZivota() + 10);
            }
        }
    }
}