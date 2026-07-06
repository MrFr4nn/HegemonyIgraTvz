package hr.tvz.java.projekt.logika;

import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import hr.tvz.java.projekt.model.Vlada;

import java.util.List;

public class ObradaProizvodnje {

    public String obradiFazuProizvodnje(List<KlasaIgraca> listaIgraca) {
        StringBuilder izvjestaj = new StringBuilder();
        for (KlasaIgraca igrac : listaIgraca) {
            izvjestaj.append(obradiProizvodnjuZaIgraca(igrac)).append("\n");
        }
        return izvjestaj.toString();
    }

    private String obradiProizvodnjuZaIgraca(KlasaIgraca igrac) {
        if (igrac instanceof KapitalistickaKlasa kapitalist) {
            double proizvedeno = kapitalist.getBrojTvornica() * 15.0;
            kapitalist.setUkupniKapital(kapitalist.getUkupniKapital() + proizvedeno);
            return kapitalist.getNaziv() + " tvornice: +" + String.format("%.0f", proizvedeno) + " kapitala";
        } else if (igrac instanceof SrednjaKlasa srednjaKlasa) {
            double proizvedeno = srednjaKlasa.getBrojMalihPoduzeca() * 8.0;
            srednjaKlasa.ostvariPrihod(proizvedeno);
            return srednjaKlasa.getNaziv() + " poduzeca: +" + String.format("%.0f", proizvedeno) + " kapitala";
        } else if (igrac instanceof RadnickaKlasa radnickaKlasa) {
            if (radnickaKlasa.isJeUStrajku()) {
                return radnickaKlasa.getNaziv() + " je u strajku - nema prihoda.";
            }
            int kolicinaHrane = radnickaKlasa.getZaposleniRadnici();
            radnickaKlasa.setKolicinaHrane(radnickaKlasa.getKolicinaHrane() + kolicinaHrane);
            return radnickaKlasa.getNaziv() + " zaradila: +" + kolicinaHrane + " hrane";
        } else if (igrac instanceof Vlada vlada) {
            double naplaceniPorez = vlada.getStopaPoreza() * 100.0;
            vlada.setDrzavniProracun(vlada.getDrzavniProracun() + naplaceniPorez);
            return vlada.getNaziv() + " automatski porez: +" + String.format("%.0f", naplaceniPorez);
        }
        return "";
    }

    public String obradiFazuPotrosnje(List<KlasaIgraca> listaIgraca) {
        StringBuilder izvjestaj = new StringBuilder();
        for (KlasaIgraca igrac : listaIgraca) {
            izvjestaj.append(obradiPotrosnjuZaIgraca(igrac)).append("\n");
        }
        return izvjestaj.toString();
    }

    private String obradiPotrosnjuZaIgraca(KlasaIgraca igrac) {
        if (igrac instanceof RadnickaKlasa radnicka) return obradiPotrosnjuRadnicke(radnicka);
        if (igrac instanceof SrednjaKlasa srednja) return obradiPotrosnjuSrednje(srednja);
        return igrac.getNaziv() + " nema troskova potrosnje.";
    }

    private String obradiPotrosnjuRadnicke(RadnickaKlasa radnickaKlasa) {
        int potreba = radnickaKlasa.getBrojRadnika() / 2;
        if (radnickaKlasa.getKolicinaHrane() >= potreba) {
            radnickaKlasa.potrosiHranu(potreba);
            return radnickaKlasa.getNaziv() + " prehranila radnike (potroseno: " + potreba + ")";
        }
        int manjak = potreba - radnickaKlasa.getKolicinaHrane();
        radnickaKlasa.potrosiHranu(radnickaKlasa.getKolicinaHrane());
        int standardPrije = radnickaKlasa.getStandardZivota();
        int novStandard = Math.max(0, standardPrije - (manjak * 3));
        radnickaKlasa.setStandardZivota(novStandard);
        return radnickaKlasa.getNaziv() + " manjak hrane (" + manjak + ") - standard pao s " + standardPrije + " na " + novStandard;
    }

    private String obradiPotrosnjuSrednje(SrednjaKlasa srednjaKlasa) {
        double potreba = 5.0;
        if (srednjaKlasa.getUstedjeniKapital() >= potreba) {
            srednjaKlasa.setUstedjeniKapital(srednjaKlasa.getUstedjeniKapital() - potreba);
            return srednjaKlasa.getNaziv() + " pokrila troskove (potroseno: 5 kapitala)";
        }
        double manjak = potreba - srednjaKlasa.getUstedjeniKapital();
        srednjaKlasa.setUstedjeniKapital(0.0);
        int standardPrije = srednjaKlasa.getStandardZivota();
        int novStandard = Math.max(0, standardPrije - (int) (manjak * 2));
        srednjaKlasa.setStandardZivota(novStandard);
        return srednjaKlasa.getNaziv() + " manjak kapitala - standard pao s " + standardPrije + " na " + novStandard;
    }

    public void primijeniMmfProvjeru(Vlada vlada) {
        vlada.provjeriUvjeteBankrota();
    }

    public void primijeniFinalnoBodovanje(List<KlasaIgraca> listaIgraca) {
        for (KlasaIgraca igrac : listaIgraca) {
            if (igrac instanceof KapitalistickaKlasa kapitalist) {
                kapitalist.povecajBodove(kapitalist.getBrojTvornica() * 10);
            }
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
        for (KlasaIgraca igrac : listaIgraca) {
            if (igrac instanceof RadnickaKlasa r) {
                igrac.povecajBodove(r.getZaposleniRadnici() * 2);
            } else if (igrac instanceof SrednjaKlasa s) {
                igrac.povecajBodove(s.getBrojMalihPoduzeca() * 5 + (int) (s.getUstedjeniKapital() / 10));
            } else if (igrac instanceof KapitalistickaKlasa k) {
                igrac.povecajBodove((int) (k.getUkupniKapital() / 5));
            }
        }
    }

    private void primijeniPrihvaceniZakon(String nazivZakona, List<KlasaIgraca> listaIgraca, Vlada vlada, KatalogZakona katalogZakona) {
        for (int i = 0; i < katalogZakona.dohvatiBrojZakona(); i++) {
            if (katalogZakona.dohvatiNaziv(i).equals(nazivZakona)) {
                katalogZakona.primijeniEfekt(i, listaIgraca, vlada);
                vlada.donesiNoviZakon(nazivZakona);
                break;
            }
        }
    }
}