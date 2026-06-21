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
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Serijalizator {

    private static final String PUTANJA_DATOTEKE = "stanje.bin";

    public boolean spremiStanje(List<KlasaIgraca> listaIgraca) {
        try (FileOutputStream tokDatoteke = new FileOutputStream(PUTANJA_DATOTEKE);
             ObjectOutputStream tokObjekta = new ObjectOutputStream(tokDatoteke)) {
            tokObjekta.writeObject((Serializable) listaIgraca);
            System.out.println("Stanje igre je uspjesno spremljeno u datoteku.");
            return true;
        } catch (IOException greska) {
            System.out.println("Doslo je do greske prilikom spremanja stanja: " + greska.getMessage());
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public List<KlasaIgraca> ucitajStanje() {
        try (FileInputStream tokDatoteke = new FileInputStream(PUTANJA_DATOTEKE);
             ObjectInputStream tokObjekta = new ObjectInputStream(tokDatoteke)) {
            Object procitaniObjekt = tokObjekta.readObject();
            System.out.println("Stanje igre je uspjesno ucitano iz datoteke.");
            return (List<KlasaIgraca>) procitaniObjekt;
        } catch (IOException greska) {
            System.out.println("Doslo je do greske prilikom ucitavanja stanja: " + greska.getMessage());
        } catch (ClassNotFoundException greska) {
            System.out.println("Klasa nije pronadena prilikom ucitavanja: " + greska.getMessage());
        }
        return new ArrayList<>();
    }

    public String napraviTehnickuUsporedbu() {
        String tekst = "";
        tekst = tekst + analizirajKlasuPomocuReflectiona(RadnickaKlasa.class);
        tekst = tekst + "\n--------------------------\n";
        tekst = tekst + analizirajKlasuPomocuReflectiona(SrednjaKlasa.class);
        tekst = tekst + "\n--------------------------\n";
        tekst = tekst + analizirajKlasuPomocuReflectiona(KapitalistickaKlasa.class);
        tekst = tekst + "\n--------------------------\n";
        tekst = tekst + analizirajKlasuPomocuReflectiona(Vlada.class);
        return tekst;
    }

    private String analizirajKlasuPomocuReflectiona(Class<?> klasa) {
        String tekst = "";
        tekst = tekst + "Analiza klase: " + klasa.getSimpleName() + "\n";

        Field[] poljaKlase = klasa.getDeclaredFields();
        tekst = tekst + "Broj atributa: " + poljaKlase.length + "\n";
        int brojac = 0;
        while (brojac < poljaKlase.length) {
            Field trenutnoPolje = poljaKlase[brojac];
            tekst = tekst + "  - Atribut: " + trenutnoPolje.getName() + " (" + trenutnoPolje.getType().getSimpleName() + ")\n";
            brojac = brojac + 1;
        }

        Method[] metodeKlase = klasa.getDeclaredMethods();
        tekst = tekst + "Broj metoda: " + metodeKlase.length + "\n";
        int drugiBrojac = 0;
        while (drugiBrojac < metodeKlase.length) {
            Method trenutnaMetoda = metodeKlase[drugiBrojac];
            tekst = tekst + "  - Metoda: " + trenutnaMetoda.getName() + "()\n";
            drugiBrojac = drugiBrojac + 1;
        }

        Class<?> nadklasa = klasa.getSuperclass();
        if (nadklasa != null) {
            tekst = tekst + "Nasljeduje od: " + nadklasa.getSimpleName() + "\n";
        }

        return tekst;
    }
}