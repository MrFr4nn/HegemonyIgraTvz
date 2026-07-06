package hr.tvz.java.projekt.util;

import hr.tvz.java.projekt.model.KapitalistickaKlasa;
import hr.tvz.java.projekt.model.KlasaIgraca;
import hr.tvz.java.projekt.model.RadnickaKlasa;
import hr.tvz.java.projekt.model.SrednjaKlasa;
import hr.tvz.java.projekt.model.Vlada;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Serijalizator {

    private static final Logger LOG = Logger.getLogger(Serijalizator.class.getName());
    private static final String PUTANJA_DATOTEKE = "stanje.bin";
    private static final String SEPARATOR = "--------------------------";

    public boolean spremiStanje(List<KlasaIgraca> listaIgraca) {
        try (FileOutputStream tokDatoteke = new FileOutputStream(PUTANJA_DATOTEKE);
             ObjectOutputStream tokObjekta = new ObjectOutputStream(tokDatoteke)) {
            tokObjekta.writeObject(listaIgraca);
            LOG.info("Stanje igre je uspjesno spremljeno u datoteku.");
            return true;
        } catch (IOException greska) {
            LOG.severe("Doslo je do greske prilikom spremanja stanja: " + greska.getMessage());
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public List<KlasaIgraca> ucitajStanje() {
        try (FileInputStream tokDatoteke = new FileInputStream(PUTANJA_DATOTEKE);
             ObjectInputStream tokObjekta = new ObjectInputStream(tokDatoteke)) {
            Object procitaniObjekt = tokObjekta.readObject();
            LOG.info("Stanje igre je uspjesno ucitano iz datoteke.");
            return (List<KlasaIgraca>) procitaniObjekt;
        } catch (IOException greska) {
            LOG.severe("Doslo je do greske prilikom ucitavanja stanja: " + greska.getMessage());
        } catch (ClassNotFoundException greska) {
            LOG.severe("Klasa nije pronadena prilikom ucitavanja: " + greska.getMessage());
        }
        return new ArrayList<>();
    }

    public String napraviTehnickuUsporedbu() {
        StringBuilder tekst = new StringBuilder();
        tekst.append(analizirajKlasuPomocuReflectiona(RadnickaKlasa.class));
        tekst.append("\n").append(SEPARATOR).append("\n");
        tekst.append(analizirajKlasuPomocuReflectiona(SrednjaKlasa.class));
        tekst.append("\n").append(SEPARATOR).append("\n");
        tekst.append(analizirajKlasuPomocuReflectiona(KapitalistickaKlasa.class));
        tekst.append("\n").append(SEPARATOR).append("\n");
        tekst.append(analizirajKlasuPomocuReflectiona(Vlada.class));
        return tekst.toString();
    }

    private String analizirajKlasuPomocuReflectiona(Class<?> klasa) {
        StringBuilder tekst = new StringBuilder();
        tekst.append("Analiza klase: ").append(klasa.getSimpleName()).append("\n");

        Field[] poljaKlase = klasa.getDeclaredFields();
        tekst.append("Broj atributa: ").append(poljaKlase.length).append("\n");
        int brojac = 0;
        while (brojac < poljaKlase.length) {
            Field trenutnoPolje = poljaKlase[brojac];
            tekst.append("  - Atribut: ").append(trenutnoPolje.getName())
                    .append(" (").append(trenutnoPolje.getType().getSimpleName()).append(")\n");
            brojac = brojac + 1;
        }

        Method[] metodeKlase = klasa.getDeclaredMethods();
        tekst.append("Broj metoda: ").append(metodeKlase.length).append("\n");
        int drugiBrojac = 0;
        while (drugiBrojac < metodeKlase.length) {
            Method trenutnaMetoda = metodeKlase[drugiBrojac];
            tekst.append("  - Metoda: ").append(trenutnaMetoda.getName()).append("()\n");
            drugiBrojac = drugiBrojac + 1;
        }

        Class<?> nadklasa = klasa.getSuperclass();
        if (nadklasa != null) {
            tekst.append("Nasljeduje od: ").append(nadklasa.getSimpleName()).append("\n");
        }

        return tekst.toString();
    }
}