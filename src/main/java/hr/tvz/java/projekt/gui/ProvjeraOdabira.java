package hr.tvz.java.projekt.gui;

import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import hr.tvz.java.projekt.model.Vlada;

import java.util.ArrayList;
import java.util.List;

public class ProvjeraOdabira {

    public boolean provjeriJesuLiUlogeRazlicite(List<String> odabraneUloge) {
        int brojac = 0;
        while (brojac < odabraneUloge.size()) {
            int drugiBrojac = brojac + 1;
            while (drugiBrojac < odabraneUloge.size()) {
                if (odabraneUloge.get(brojac).equals(odabraneUloge.get(drugiBrojac))) {
                    return false;
                }
                drugiBrojac = drugiBrojac + 1;
            }
            brojac = brojac + 1;
        }
        return true;
    }

    public boolean sadrziVladu(List<String> odabraneUloge) {
        return odabraneUloge.contains("Vlada");
    }

    public List<KlasaIgraca> napraviIgraceOdUloga(List<String> odabraneUloge) {
        List<KlasaIgraca> listaIgraca = new ArrayList<>();
        int brojac = 0;
        while (brojac < odabraneUloge.size()) {
            String uloga = odabraneUloge.get(brojac);
            String nazivIgraca = uloga + " - Igrac " + (brojac + 1);

            if (uloga.equals("Radnicka klasa")) {
                listaIgraca.add(new RadnickaKlasa(nazivIgraca));
            } else if (uloga.equals("Srednja klasa")) {
                listaIgraca.add(new SrednjaKlasa(nazivIgraca));
            } else if (uloga.equals("Kapitalisticka klasa")) {
                listaIgraca.add(new KapitalistickaKlasa(nazivIgraca));
            } else {
                listaIgraca.add(new Vlada(nazivIgraca));
            }
            brojac = brojac + 1;
        }
        return listaIgraca;
    }
}