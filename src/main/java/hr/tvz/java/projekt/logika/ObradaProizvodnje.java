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
            izvjestaj = izvjestaj + obradiProizvodnjuZaIgraca(listaIgraca.get(brojac)) + "\n";
            brojac = brojac + 1;
        }
        return izvjestaj;
    }

    private String obradiProizvodnjuZaIgraca(KlasaIgraca igrac) {
        if (igrac instanceof KapitalistickaKlasa) {
            KapitalistickaKlasa kapitalist = (KapitalistickaKlasa) igrac;
            double proizvedeno = kapitalist.getBrojTvornica() * 15.0;
            kapitalist.setUkupniKapital(kapitalist.getUkupniKapital() + proizvedeno);
            return kapitalist.getNaziv() + " tvornice proizvele: +" + String.format("%.0f", proizvedeno) + " kapitala";
        } else if (igrac instanceof SrednjaKlasa) {
            SrednjaKlasa srednjaKlasa = (SrednjaKlasa) igrac;
            double proizvedeno = srednjaKlasa.getBrojMalihPoduzeca() * 8.0;
            srednjaKlasa.ostvariPrihod(proizvedeno);
            return srednjaKlasa.getNaziv() + " poduzeca zaradila: +" + String.format("%.0f", proizvedeno) + " kapitala";
        } else if (igrac instanceof RadnickaKlasa) {
            RadnickaKlasa radnickaKlasa = (RadnickaKlasa) igrac;
            if (radnickaKlasa.isJeUStrajku()) {
                return radnickaKlasa.getNaziv() + " je u strajku - nema prihoda od rada.";
            }
            int kolicinaHrane = radnickaKlasa.getZaposleniRadnici();
            radnickaKlasa.setKolicinaHrane(radnickaKlasa.getKolicinaHrane() + kolicinaHrane);
            return radnickaKlasa.getNaziv() + " zaradila hranu: +" + kolicinaHrane + " jedinica";
        } else {
            Vlada vlada = (Vlada) igrac;
            double naplaceniPorez = vlada.getStopaPoreza() * 100.0;
            vlada.setDrzavniProracun(vlada.getDrzavniProracun() + naplaceniPorez);
            return vlada.getNaziv() + " naplatila automatski porez: +" + String.format("%.0f", naplaceniPorez);
        }
    }

    public String obradiFazuPotrosnje(List<KlasaIgraca> listaIgraca) {
        String izvjestaj = "";
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            izvjestaj = izvjestaj + obradiPotrosnjuZaIgraca(listaIgraca.get(brojac)) + "\n";
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
            return igrac.getNaziv() + " nema troskova potrosnje.";
        }
    }

    private String obradiPotrosnjuRadnicke(RadnickaKlasa radnickaKlasa) {
        int potreba = radnickaKlasa.getBrojRadnika() / 2;
        if (radnickaKlasa.getKolicinaHrane() >= potreba) {
            radnickaKlasa.potrosiHranu(potreba);
            return radnickaKlasa.getNaziv() + " prehranila sve radnike (potroseno: " + potreba + ")";
        } else {
            int manjak = potreba - radnickaKlasa.getKolicinaHrane();
            radnickaKlasa.potrosiHranu(radnickaKlasa.getKolicinaHrane());
            radnickaKlasa.smanjiBodove(1);
            return radnickaKlasa.getNaziv() + " manjak hrane (" + manjak + " jedinica) — kazna: -1 bod";
        }
    }

    private String obradiPotrosnjuSrednje(SrednjaKlasa srednjaKlasa) {
        double potreba = 5.0;
        if (srednjaKlasa.getUstedjeniKapital() >= potreba) {
            srednjaKlasa.setUstedjeniKapital(srednjaKlasa.getUstedjeniKapital() - potreba);
            return srednjaKlasa.getNaziv() + " pokrila osnovne troskove (potroseno: 5 kapitala)";
        } else {
            srednjaKlasa.smanjiBodove(1);
            return srednjaKlasa.getNaziv() + " nema dovoljno kapitala za troskove — kazna: -1 bod";
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
                int bonusBodovi = kapitalist.getBrojTvornica() * 10;
                kapitalist.povecajBodove(bonusBodovi);
            }
            brojac = brojac + 1;
        }
    }

    public void obradiKrajRunde(List<KlasaIgraca> listaIgraca, Vlada vlada, Glasanje trenutnoGlasanje,
                                KatalogZakona katalogZakona, int brojRunde, int maksimalniBrojRundi) {
        dodijelijBodoveSvimIgracima(listaIgraca);
        vlada.preracunajLegitimnostUBodove();
        primijeniMmfProvjeru(vlada);

        if (trenutnoGlasanje != null && trenutnoGlasanje.isGlasanjeZavrseno() && trenutnoGlasanje.isZakonPrihvacen()) {
            primijeniPrihvaceniZakon(trenutnoGlasanje.getNazivZakona(), listaIgraca, vlada, katalogZakona);
        }

        if (brojRunde == maksimalniBrojRundi) {
            primijeniFinalnoBodovanje(listaIgraca);
        }
    }

    private void dodijelijBodoveSvimIgracima(List<KlasaIgraca> listaIgraca) {
        int brojac = 0;
        while (brojac < listaIgraca.size()) {
            KlasaIgraca igrac = listaIgraca.get(brojac);
            if (igrac instanceof RadnickaKlasa) {
                RadnickaKlasa r = (RadnickaKlasa) igrac;
                igrac.povecajBodove(r.getZaposleniRadnici() * 2);
            } else if (igrac instanceof SrednjaKlasa) {
                SrednjaKlasa s = (SrednjaKlasa) igrac;
                igrac.povecajBodove(s.getBrojMalihPoduzeca() * 5 + (int) (s.getUstedjeniKapital() / 10));
            } else if (igrac instanceof KapitalistickaKlasa) {
                KapitalistickaKlasa k = (KapitalistickaKlasa) igrac;
                igrac.povecajBodove((int) (k.getUkupniKapital() / 5));
            }
            brojac = brojac + 1;
        }
    }

    private void primijeniPrihvaceniZakon(String nazivZakona, List<KlasaIgraca> listaIgraca,
                                          Vlada vlada, KatalogZakona katalogZakona) {
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