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
            return kapitalist.getNaziv() + " tvornice: +" + String.format("%.0f", proizvedeno) + " kapitala";
        } else if (igrac instanceof SrednjaKlasa) {
            SrednjaKlasa srednjaKlasa = (SrednjaKlasa) igrac;
            double proizvedeno = srednjaKlasa.getBrojMalihPoduzeca() * 8.0;
            srednjaKlasa.ostvariPrihod(proizvedeno);
            return srednjaKlasa.getNaziv() + " poduzeca: +" + String.format("%.0f", proizvedeno) + " kapitala";
        } else if (igrac instanceof RadnickaKlasa) {
            RadnickaKlasa radnickaKlasa = (RadnickaKlasa) igrac;
            if (radnickaKlasa.isJeUStrajku()) {
                return radnickaKlasa.getNaziv() + " je u strajku - nema prihoda.";
            }
            int kolicinaHrane = radnickaKlasa.getZaposleniRadnici();
            radnickaKlasa.setKolicinaHrane(radnickaKlasa.getKolicinaHrane() + kolicinaHrane);
            return radnickaKlasa.getNaziv() + " zaradila: +" + kolicinaHrane + " hrane";
        } else {
            Vlada vlada = (Vlada) igrac;
            double naplaceniPorez = vlada.getStopaPoreza() * 100.0;
            vlada.setDrzavniProracun(vlada.getDrzavniProracun() + naplaceniPorez);
            return vlada.getNaziv() + " automatski porez: +" + String.format("%.0f", naplaceniPorez);
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
            return radnickaKlasa.getNaziv() + " prehranila radnike (potroseno: " + potreba + ")";
        } else {
            int manjak = potreba - radnickaKlasa.getKolicinaHrane();
            radnickaKlasa.potrosiHranu(radnickaKlasa.getKolicinaHrane());
            int standardPrije = radnickaKlasa.getStandardZivota();
            int novStandard = standardPrije - (manjak * 3);
            if (novStandard < 0) {
                novStandard = 0;
            }
            radnickaKlasa.setStandardZivota(novStandard);
            return radnickaKlasa.getNaziv() + " manjak hrane (" + manjak + ") - standard pao s "
                    + standardPrije + " na " + novStandard;
        }
    }

    private String obradiPotrosnjuSrednje(SrednjaKlasa srednjaKlasa) {
        double potreba = 5.0;
        if (srednjaKlasa.getUstedjeniKapital() >= potreba) {
            srednjaKlasa.setUstedjeniKapital(srednjaKlasa.getUstedjeniKapital() - potreba);
            return srednjaKlasa.getNaziv() + " pokrila troskove (potroseno: 5 kapitala)";
        } else {
            double manjak = potreba - srednjaKlasa.getUstedjeniKapital();
            srednjaKlasa.setUstedjeniKapital(0.0);
            int standardPrije = srednjaKlasa.getStandardZivota();
            int novStandard = standardPrije - (int) (manjak * 2);
            if (novStandard < 0) {
                novStandard = 0;
            }
            srednjaKlasa.setStandardZivota(novStandard);
            return srednjaKlasa.getNaziv() + " manjak kapitala - standard pao s "
                    + standardPrije + " na " + novStandard;
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

        if (trenutnoGlasanje != null && trenutnoGlasanje.isGlasanjeZavrseno()
                && trenutnoGlasanje.isZakonPrihvacen()) {
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
                int bodovi = r.getZaposleniRadnici() * 2;
                igrac.povecajBodove(bodovi);
            } else if (igrac instanceof SrednjaKlasa) {
                SrednjaKlasa s = (SrednjaKlasa) igrac;
                int bodovi = s.getBrojMalihPoduzeca() * 5 + (int) (s.getUstedjeniKapital() / 10);
                igrac.povecajBodove(bodovi);
            } else if (igrac instanceof KapitalistickaKlasa) {
                KapitalistickaKlasa k = (KapitalistickaKlasa) igrac;
                int bodovi = (int) (k.getUkupniKapital() / 5);
                igrac.povecajBodove(bodovi);
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