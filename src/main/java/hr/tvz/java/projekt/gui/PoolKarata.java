package hr.tvz.java.projekt.gui;

import java.util.ArrayList;
import java.util.List;

public class PoolKarata {

    private static final String NAZIV_OBRAZOVANJE = "Obrazovanje";

    public static class PodaciKarte {
        private final String naziv;
        private final String opis;
        private final String emojiIkona;
        private final String nazivAkcije;

        public PodaciKarte(String naziv, String opis, String emojiIkona, String nazivAkcije) {
            this.naziv = naziv;
            this.opis = opis;
            this.emojiIkona = emojiIkona;
            this.nazivAkcije = nazivAkcije;
        }

        public String getNaziv() { return naziv; }
        public String getOpis() { return opis; }
        public String getEmojiIkona() { return emojiIkona; }
        public String getNazivAkcije() { return nazivAkcije; }
    }

    private List<PodaciKarte> poolRadnicke;
    private List<PodaciKarte> poolSrednje;
    private List<PodaciKarte> poolKapitalisticke;
    private List<PodaciKarte> poolVlade;

    public PoolKarata() {
        napuniPooloveKarata();
    }

    private void napuniPooloveKarata() {
        poolRadnicke = new ArrayList<>();
        poolRadnicke.add(new PodaciKarte("Zaposljavanje", "Posalji radnika u firmu", "👷", "Zaposljavanje"));
        poolRadnicke.add(new PodaciKarte(NAZIV_OBRAZOVANJE, "Podigni kvalifikaciju", "🎓", NAZIV_OBRAZOVANJE));
        poolRadnicke.add(new PodaciKarte("Sindikalni prosvjed", "Pokreni strajk", "✊", "Strajk"));
        poolRadnicke.add(new PodaciKarte("Otpusti radnika", "Smanji broj zaposlenih", "📉", "OtpustiRadnika"));
        poolRadnicke.add(new PodaciKarte("Kolektivni ugovor", "Pregovaraj o boljim uvjetima", "🤝", "KolektivniUgovor"));
        poolRadnicke.add(new PodaciKarte("Regionalni razvoj", "Otvori nova radna mjesta", "🏗️", "RegionalniRazvoj"));

        poolSrednje = new ArrayList<>();
        poolSrednje.add(new PodaciKarte("Otvori poduzece", "Pokreni novi posao (trosak 15)", "🏪", "OtvoriPoduzece"));
        poolSrednje.add(new PodaciKarte(NAZIV_OBRAZOVANJE, "Podigni kvalifikaciju", "🎓", "ObrazovanjeSrednja"));
        poolSrednje.add(new PodaciKarte("Marketing", "Ulozi u promidzbu obrta", "📣", "Marketing"));
        poolSrednje.add(new PodaciKarte("Zatvori poduzece", "Smanji broj poduzeca", "📉", "ZatvoriPoduzece"));
        poolSrednje.add(new PodaciKarte("Izvoz robe", "Prodaj na strano trziste", "🚢", "IzvozRobe"));
        poolSrednje.add(new PodaciKarte("Stednja", "Stedi kapital", "🏦", "Stednja"));

        poolKapitalisticke = new ArrayList<>();
        poolKapitalisticke.add(new PodaciKarte("Trazi investitora", "Pribavi pocetni kapital", "🤵", "TraziInvestitora"));
        poolKapitalisticke.add(new PodaciKarte("Investicija", "Izgradi tvornicu (trosak 50)", "🏭", "IzgradiTvornicu"));
        poolKapitalisticke.add(new PodaciKarte("Lobiranje", "Plati za politicki utjecaj", "🏛️", "Lobiranje"));
        poolKapitalisticke.add(new PodaciKarte("Prodaj tvornicu", "Otkupna vrijednost", "📉", "ProdajTvornicu"));
        poolKapitalisticke.add(new PodaciKarte("Diverzifikacija", "Ulozi kapital u nova trzista", "🧩", "Diverzifikacija"));
        poolKapitalisticke.add(new PodaciKarte("Fuzija kompanija", "Pripoji manju kompaniju", "🏢", "FuzijaKompanija"));
        poolKapitalisticke.add(new PodaciKarte("Burzovna spekulacija", "Rizicna investicija", "📈", "BurzovnaSpeculacija"));

        poolVlade = new ArrayList<>();
        poolVlade.add(new PodaciKarte("Javne investicije", "Izgradi javnu ustanovu", "🏛️", "JavneInvesticije"));
        poolVlade.add(new PodaciKarte("Socijalni paket", "Isplati pomoc", "❤️", "SocijalniPaket"));
        poolVlade.add(new PodaciKarte("Naplati porez", "Prihod od poreza", "🧾", "NaplatiPorez"));
        poolVlade.add(new PodaciKarte("Dekret", "Pojacaj legitimnost", "📜", "Dekret"));
        poolVlade.add(new PodaciKarte("Infrastruktura", "Gradi ceste i mostove", "🛣️", "Infrastruktura"));
        poolVlade.add(new PodaciKarte("Vanjska politika", "Privuci strane investicije", "🌐", "VanjskaPolitika"));
    }

    public List<PodaciKarte> getPoolRadnicke() { return poolRadnicke; }
    public List<PodaciKarte> getPoolSrednje() { return poolSrednje; }
    public List<PodaciKarte> getPoolKapitalisticke() { return poolKapitalisticke; }
    public List<PodaciKarte> getPoolVlade() { return poolVlade; }
}