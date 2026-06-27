package hr.tvz.java.projekt.logika;

import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import hr.tvz.java.projekt.model.Vlada;

import java.util.List;

public class ObradaProizvodnje {

    public String obradiFazuProizvodnje(List<KlasaIgraca> listaIgraca) {
        String izvjestaj = "";
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            KlasaIgraca igrac = listaIgraca.get(brojac);
            izvjestaj = izvjestaj + obradiProizvodnjuZaIgraca(igrac) + "\n";
            brojac = brojac + 1;
        }
        return izvjestaj;
    }

    private String obradiProizvodnjuZaIgraca(KlasaIgraca igrac) {
        if (igrac instanceof KapitalistickaKlasa) {
            KapitalistickaKlasa kapitalist = (KapitalistickaKlasa) igrac;
            double proizvedeno = kapitalist.getBrojTvornica() * 10.0;
            kapitalist.setUkupniKapital(kapitalist.getUkupniKapital() + proizvedeno);
            return kapitalist.getNaziv() + " je proizveo: " + String.format("%.2f", proizvedeno);
        } else if (igrac instanceof SrednjaKlasa) {
            SrednjaKlasa srednjaKlasa = (SrednjaKlasa) igrac;
            double proizvedeno = srednjaKlasa.getBrojMalihPoduzeca() * 6.0;
            srednjaKlasa.ostvariPrihod(proizvedeno);
            return srednjaKlasa.getNaziv() + " je proizveo: " + String.format("%.2f", proizvedeno);
        } else if (igrac instanceof RadnickaKlasa) {
            RadnickaKlasa radnickaKlasa = (RadnickaKlasa) igrac;
            int kolicinaHrane = radnickaKlasa.getZaposleniRadnici();
            radnickaKlasa.setKolicinaHrane(radnickaKlasa.getKolicinaHrane() + kolicinaHrane);
            return radnickaKlasa.getNaziv() + " je zaradio hranu kroz rad: " + kolicinaHrane;
        } else {
            return igrac.getNaziv() + " nema proizvodnju u ovoj fazi.";
        }
    }

    public String obradiFazuPotrosnje(List<KlasaIgraca> listaIgraca) {
        String izvjestaj = "";
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            KlasaIgraca igrac = listaIgraca.get(brojac);
            izvjestaj = izvjestaj + obradiPotrosnjuZaIgraca(igrac) + "\n";
            brojac = brojac + 1;
        }
        return izvjestaj;
    }

    private String obradiPotrosnjuZaIgraca(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa) {
            return obradiPotrosnjuRadnicke((RadnickaKlasa) igrac);
        } else if (igrac instanceof SrednjaKlasa) {
            return obradiPotrosnjuSrednje((SrednjaKlasa) igrac);
        } else {
            return igrac.getNaziv() + " ne treba trositi hranu.";
        }
    }

    private String obradiPotrosnjuRadnicke(RadnickaKlasa radnickaKlasa) {
        int potreba = radnickaKlasa.getBrojRadnika() / 2;
        if (radnickaKlasa.getKolicinaHrane() >= potreba) {
            radnickaKlasa.potrosiHranu(potreba);
            return radnickaKlasa.getNaziv() + " je prehranio sve radnike, potroseno: " + potreba;
        } else {
            radnickaKlasa.smanjiBodove(2);
            radnickaKlasa.potrosiHranu(radnickaKlasa.getKolicinaHrane());
            return radnickaKlasa.getNaziv() + " nema dovoljno hrane! Kazna: -2 boda.";
        }
    }

    private String obradiPotrosnjuSrednje(SrednjaKlasa srednjaKlasa) {
        double potrebaZaKapitalom = 5.0;
        if (srednjaKlasa.getUstedjeniKapital() >= potrebaZaKapitalom) {
            srednjaKlasa.setUstedjeniKapital(srednjaKlasa.getUstedjeniKapital() - potrebaZaKapitalom);
            return srednjaKlasa.getNaziv() + " je pokrila osnovne potrebe.";
        } else {
            srednjaKlasa.smanjiBodove(1);
            return srednjaKlasa.getNaziv() + " nema dovoljno kapitala za potrebe! Kazna: -1 bod.";
        }
    }

    public void primijeniMmfProvjeru(Vlada vlada) {
        vlada.provjeriUvjeteBankrota();
    }

    public void primijeniFinalnoBodovanje(List<KlasaIgraca> listaIgraca) {
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            KlasaIgraca igrac = listaIgraca.get(brojac);
            if (igrac instanceof KapitalistickaKlasa) {
                KapitalistickaKlasa kapitalist = (KapitalistickaKlasa) igrac;
                int bonusBodovi = (int) (kapitalist.getUkupniKapital() / 20);
                kapitalist.povecajBodove(bonusBodovi);
            }
            brojac = brojac + 1;
        }
    }

    public void obradiKrajRunde(List<KlasaIgraca> listaIgraca, Vlada vlada, Glasanje trenutnoGlasanje,
                                KatalogZakona katalogZakona, int brojRunde, int maksimalniBrojRundi) {
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            KlasaIgraca trenutniIgrac = listaIgraca.get(brojac);
            trenutniIgrac.odigrajPotez();
            if (trenutniIgrac instanceof SrednjaKlasa) {
                trenutniIgrac.povecajBodove(((SrednjaKlasa) trenutniIgrac).getStandardZivota() / 20);
            }
            brojac = brojac + 1;
        }

        vlada.preracunajLegitimnostUBodove();
        primijeniMmfProvjeru(vlada);

        if (trenutnoGlasanje != null && trenutnoGlasanje.isGlasanjeZavrseno() && trenutnoGlasanje.isZakonPrihvacen()) {
            primijeniPrihvaceniZakon(trenutnoGlasanje.getNazivZakona(), listaIgraca, vlada, katalogZakona);
        }

        if (brojRunde == maksimalniBrojRundi) {
            primijeniFinalnoBodovanje(listaIgraca);
        }
    }

    private void primijeniPrihvaceniZakon(String nazivZakona, List<KlasaIgraca> listaIgraca, Vlada vlada, KatalogZakona katalogZakona) {
        int brojac = 0;
        while (brojac < katalogZakona.dohvatiBrojZakona()) {
            if (katalogZakona.dohvatiNaziv(brojac).equals(nazivZakona)) {
                katalogZakona.primijeniEfekt(brojac, listaIgraca, vlada);
                vlada.donesiNoviZakon(nazivZakona);
                break;
            }
            brojac = brojac + 1;
        }
    }
}