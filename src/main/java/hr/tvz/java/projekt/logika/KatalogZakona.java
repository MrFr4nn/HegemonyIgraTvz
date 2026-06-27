package hr.tvz.java.projekt.logika;

import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import hr.tvz.java.projekt.model.Vlada;

import java.util.ArrayList;
import java.util.List;

public class KatalogZakona {

    private List<String> nazivi;
    private List<String> opisi;
    private List<Boolean> iskoristeni;

    public KatalogZakona() {
        nazivi = new ArrayList<>();
        opisi = new ArrayList<>();
        iskoristeni = new ArrayList<>();
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

    public int dohvatiBrojZakona() {
        return nazivi.size();
    }

    public String dohvatiNaziv(int indeks) {
        return nazivi.get(indeks);
    }

    public String dohvatiOpis(int indeks) {
        return opisi.get(indeks);
    }

    public boolean jeIskoristen(int indeks) {
        return iskoristeni.get(indeks);
    }

    public void oznaciIskoristenim(int indeks) {
        iskoristeni.set(indeks, true);
    }

    public void primijeniEfekt(int indeks, List<KlasaIgraca> listaIgraca, Vlada vlada) {
        if (indeks == 0) {
            vlada.promijeniStopuPoreza(vlada.getStopaPoreza() + 0.10);
        } else if (indeks == 1) {
            vlada.promijeniStopuPoreza(vlada.getStopaPoreza() - 0.10);
        } else if (indeks == 2) {
            vlada.promijeniMinimalnuPlacu(vlada.getMinimalnaPlaca() + 2.0);
        } else if (indeks == 3) {
            vlada.promijeniMinimalnuPlacu(vlada.getMinimalnaPlaca() - 1.0);
        } else if (indeks == 4) {
            primijeniSocijalneTransfere(listaIgraca, vlada);
        } else if (indeks == 5) {
            vlada.promijeniStopuPoreza(vlada.getStopaPoreza() - 0.05);
        } else if (indeks == 6) {
            vlada.setDrzavniProracun(vlada.getDrzavniProracun() + 30.0);
            vlada.smanjiLegitimnost(10);
        } else if (indeks == 7) {
            primijeniNacionalizaciju(listaIgraca, vlada);
        } else if (indeks == 8) {
            primijeniInvesticijskePoticaje(listaIgraca, vlada);
        } else {
            primijeniReformuRada(listaIgraca, vlada);
        }
    }

    private void primijeniSocijalneTransfere(List<KlasaIgraca> listaIgraca, Vlada vlada) {
        vlada.setDrzavniProracun(vlada.getDrzavniProracun() - 20.0);
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            KlasaIgraca igrac = listaIgraca.get(brojac);
            if (igrac instanceof RadnickaKlasa) {
                ((RadnickaKlasa) igrac).kupiHranu(5, 0);
            }
            brojac = brojac + 1;
        }
    }

    private void primijeniNacionalizaciju(List<KlasaIgraca> listaIgraca, Vlada vlada) {
        vlada.setDrzavniProracun(vlada.getDrzavniProracun() + 30.0);
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            KlasaIgraca igrac = listaIgraca.get(brojac);
            if (igrac instanceof KapitalistickaKlasa) {
                ((KapitalistickaKlasa) igrac).platiPorez(30.0);
            }
            brojac = brojac + 1;
        }
    }

    private void primijeniInvesticijskePoticaje(List<KlasaIgraca> listaIgraca, Vlada vlada) {
        vlada.setDrzavniProracun(vlada.getDrzavniProracun() - 25.0);
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            KlasaIgraca igrac = listaIgraca.get(brojac);
            if (igrac instanceof KapitalistickaKlasa) {
                ((KapitalistickaKlasa) igrac).setUkupniKapital(((KapitalistickaKlasa) igrac).getUkupniKapital() + 25.0);
            }
            brojac = brojac + 1;
        }
    }

    private void primijeniReformuRada(List<KlasaIgraca> listaIgraca, Vlada vlada) {
        vlada.smanjiLegitimnost(5);
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            KlasaIgraca igrac = listaIgraca.get(brojac);
            if (igrac instanceof RadnickaKlasa) {
                ((RadnickaKlasa) igrac).setStandardZivota(((RadnickaKlasa) igrac).getStandardZivota() + 10);
            }
            brojac = brojac + 1;
        }
    }
}