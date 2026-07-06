package hr.tvz.java.projekt.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PoolKarata {

    private static final String SVG_MINUS = "M19 13 H5 V11 H19 Z";
    private static final String SVG_OBRAZOVANJE = "M4 18 L12 14 L20 18 L12 22 Z M12 14 V4";
    private static final String NAZIV_OBRAZOVANJE = "Obrazovanje";

    public static class PodaciKarte {
        private final String naziv;
        private final String opis;
        private final String svgIkona;
        private final String nazivAkcije;

        public PodaciKarte(String naziv, String opis, String svgIkona, String nazivAkcije) {
            this.naziv = naziv;
            this.opis = opis;
            this.svgIkona = svgIkona;
            this.nazivAkcije = nazivAkcije;
        }

        public String getNaziv() { return naziv; }
        public String getOpis() { return opis; }
        public String getSvgIkona() { return svgIkona; }
        public String getNazivAkcije() { return nazivAkcije; }
    }

    private List<PodaciKarte> poolRadnicke;
    private List<PodaciKarte> poolSrednje;
    private List<PodaciKarte> poolKapitalisticke;
    private List<PodaciKarte> poolVlade;

    private List<PodaciKarte> aktivneRadnicke;
    private List<PodaciKarte> aktivneSrednje;
    private List<PodaciKarte> aktivneKapitalisticke;
    private List<PodaciKarte> aktivneVlade;

    public PoolKarata() {
        napuniPooloveKarata();
        izaberiBrzRandKarte();
    }

    private void napuniPooloveKarata() {
        poolRadnicke = new ArrayList<>();
        poolRadnicke.add(new PodaciKarte("Zaposljavanje", "Posalji radnika u firmu",
                "M4 8 H20 V18 H4 Z M9 8 V5 H15 V8", "Zaposljavanje"));
        poolRadnicke.add(new PodaciKarte(NAZIV_OBRAZOVANJE, "Podigni kvalifikaciju",
                SVG_OBRAZOVANJE, NAZIV_OBRAZOVANJE));
        poolRadnicke.add(new PodaciKarte("Sindikalni prosvjed", "Pokreni strajk",
                "M12 2 L13 10 L21 10 L14 14 L17 22 L12 17 L7 22 L10 14 L3 10 L11 10 Z", "Strajk"));
        poolRadnicke.add(new PodaciKarte("Otpusti radnika", "Smanji broj zaposlenih",
                SVG_MINUS, "OtpustiRadnika"));
        poolRadnicke.add(new PodaciKarte("Kolektivni ugovor", "Pregovaraj o boljim uvjetima",
                "M9 5 H15 V9 H9 Z M5 9 H19 V19 H5 Z", "KolektivniUgovor"));
        poolRadnicke.add(new PodaciKarte("Zdravstvena zastita", "Osiguraj zdravlje radnika",
                "M12 2 C16 2 19 5 19 9 C19 13 12 22 12 22 C12 22 5 13 5 9 C5 5 8 2 12 2 Z", "ZdravstvenaZastita"));
        poolRadnicke.add(new PodaciKarte("Prekovremeni rad", "Povecaj prihode",
                "M12 6 V12 L16 14 M12 2 A10 10 0 1 0 12 22 A10 10 0 1 0 12 2", "PrekovremeniRad"));
        poolRadnicke.add(new PodaciKarte("Regionalni razvoj", "Otvori nova radna mjesta",
                "M3 9 L12 2 L21 9 V20 H3 Z", "RegionalniRazvoj"));

        poolSrednje = new ArrayList<>();
        poolSrednje.add(new PodaciKarte("Otvori poduzece", "Pokreni novi posao (trosak 15)",
                "M4 10 H20 V20 H4 Z M9 10 V6 H15 V10", "OtvoriPoduzece"));
        poolSrednje.add(new PodaciKarte(NAZIV_OBRAZOVANJE, "Podigni kvalifikaciju",
                SVG_OBRAZOVANJE, "ObrazovanjeSrednja"));
        poolSrednje.add(new PodaciKarte("Ostvari prihod", "Prodaj robu na trzistu",
                "M4 20 L12 4 L20 20 Z", "OstvariPrihod"));
        poolSrednje.add(new PodaciKarte("Zatvori poduzece", "Smanji broj poduzeca",
                SVG_MINUS, "ZatvoriPoduzece"));
        poolSrednje.add(new PodaciKarte("Digitalizacija", "Uvedi tehnologiju",
                "M4 6 H20 V18 H4 Z M8 10 H16 M8 14 H12", "Digitalizacija"));
        poolSrednje.add(new PodaciKarte("Izvoz robe", "Prodaj na strano trziste",
                "M3 12 L12 3 L21 12 M12 3 V21", "IzvozRobe"));
        poolSrednje.add(new PodaciKarte("Partnerstvo", "Udruzi se s drugima",
                "M12 2 L15 9 H22 L16 13 L19 21 L12 16 L5 21 L8 13 L2 9 H9 Z", "Partnerstvo"));
        poolSrednje.add(new PodaciKarte("Stednja", "Stedi kapital",
                "M12 2 A10 10 0 1 0 12 22 A10 10 0 1 0 12 2 M12 8 V12 L15 14", "Stednja"));

        poolKapitalisticke = new ArrayList<>();
        poolKapitalisticke.add(new PodaciKarte("Investicija", "Izgradi tvornicu (trosak 50)",
                "M3 18 H21 V20 H3 Z M5 18 V10 H7 V18 Z M9 18 V6 H11 V18 Z M13 18 V11 H15 V18 Z", "IzgradiTvornicu"));
        poolKapitalisticke.add(new PodaciKarte("Lobiranje", "Plati za politicki utjecaj",
                "M4 20 H20 V21 H4 Z M12 4 L20 10 H4 Z", "Lobiranje"));
        poolKapitalisticke.add(new PodaciKarte("Prodaj tvornicu", "Otkupna vrijednost",
                SVG_MINUS, "ProdajTvornicu"));
        poolKapitalisticke.add(new PodaciKarte("Plati porez", "Podmiri poreznu obvezu",
                "M12 6 V18 M9 9 H15 M9 15 H15", "PlatiPorez"));
        poolKapitalisticke.add(new PodaciKarte("Fuzija kompanija", "Pripoji manju kompaniju",
                "M4 8 H10 V14 H4 Z M14 8 H20 V14 H14 Z M10 11 H14", "FuzijaKompanija"));
        poolKapitalisticke.add(new PodaciKarte("Burzovna spekulacija", "Rizicna investicija",
                "M3 17 L7 13 L11 15 L17 7 L21 11", "BurzovnaSpeculacija"));
        poolKapitalisticke.add(new PodaciKarte("Outsourcing", "Smanji troskove rada",
                "M4 12 H20 M12 4 L20 12 L12 20", "Outsourcing"));
        poolKapitalisticke.add(new PodaciKarte("Monopolizacija", "Preuzmi trziste",
                "M12 2 L22 8.5 V15.5 L12 22 L2 15.5 V8.5 Z", "Monopolizacija"));

        poolVlade = new ArrayList<>();
        poolVlade.add(new PodaciKarte("Javne investicije", "Izgradi javnu ustanovu",
                "M4 20 H20 V21 H4 Z M6 20 V11 H8 V20 Z M11 20 V11 H13 V20 Z M3 11 L12 4 L21 11 Z", "JavneInvesticije"));
        poolVlade.add(new PodaciKarte("Socijalni paket", "Isplati pomoc",
                "M12 2 C16 2 19 5 19 9 C19 13 12 22 12 22 C12 22 5 13 5 9 C5 5 8 2 12 2 Z", "SocijalniPaket"));
        poolVlade.add(new PodaciKarte("Naplati porez", "Prihod od poreza",
                "M12 6 V18 M9 9 H15 M9 15 H15", "NaplatiPorez"));
        poolVlade.add(new PodaciKarte("Dekret", "Pojacaj legitimnost",
                "M12 3 L20 8 V16 L12 21 L4 16 V8 Z", "Dekret"));
        poolVlade.add(new PodaciKarte("Infrastruktura", "Gradi ceste i mostove",
                "M3 12 H21 M3 6 H21 M3 18 H21", "Infrastruktura"));
        poolVlade.add(new PodaciKarte("Obrazovna reforma", "Ulozi u skolstvo",
                "M4 18 L12 14 L20 18 L12 22 Z M12 14 V4 M8 8 H16", "ObrazovnaReforma"));
        poolVlade.add(new PodaciKarte("Anticiklicna politika", "Stabilizacija ekonomije",
                "M3 17 L7 13 L11 15 L17 7 L21 11", "AnticiklicnaPolitika"));
        poolVlade.add(new PodaciKarte("Vanjska politika", "Privuci strane investicije",
                "M12 2 A10 10 0 1 0 12 22 A10 10 0 1 0 12 2 M2 12 H22", "VanjskaPolitika"));
    }

    public void izaberiBrzRandKarte() {
        aktivneRadnicke = izaberiRandom4(poolRadnicke);
        aktivneSrednje = izaberiRandom4(poolSrednje);
        aktivneKapitalisticke = izaberiRandom4(poolKapitalisticke);
        aktivneVlade = izaberiRandom4(poolVlade);
    }

    @SuppressWarnings("java:S2245")
    private List<PodaciKarte> izaberiRandom4(List<PodaciKarte> pool) {
        List<PodaciKarte> kopija = new ArrayList<>(pool);
        Collections.shuffle(kopija);
        List<PodaciKarte> odabrane = new ArrayList<>();
        int brojac = 0;
        while (brojac < 4 && brojac < kopija.size()) {
            odabrane.add(kopija.get(brojac));
            brojac = brojac + 1;
        }
        return odabrane;
    }

    public List<PodaciKarte> getAktivneRadnicke() { return aktivneRadnicke; }
    public List<PodaciKarte> getAktivneSrednje() { return aktivneSrednje; }
    public List<PodaciKarte> getAktivneKapitalisticke() { return aktivneKapitalisticke; }
    public List<PodaciKarte> getAktivneVlade() { return aktivneVlade; }
}