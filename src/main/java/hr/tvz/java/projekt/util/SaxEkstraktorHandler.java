package hr.tvz.java.projekt.util;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;

public class SaxEkstraktorHandler extends DefaultHandler {

    private static final String TAG_POTEZ = "Potez";
    private static final String TAG_RUNDA = "Runda";
    private static final String TAG_IGRAC = "Igrac";
    private static final String TAG_OPIS = "Opis";

    private List<String> ekstrahiraniPodaci = new ArrayList<>();
    private StringBuilder trenutniTekst = new StringBuilder();
    private String trenutnaRunda = "";
    private String trenutniIgrac = "";
    private String trenutniOpis = "";

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        trenutniTekst.setLength(0);
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        trenutniTekst.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        String tekst = trenutniTekst.toString().trim();
        if (qName.equals(TAG_RUNDA)) {
            trenutnaRunda = tekst;
        } else if (qName.equals(TAG_IGRAC)) {
            trenutniIgrac = tekst;
        } else if (qName.equals(TAG_OPIS)) {
            trenutniOpis = tekst;
        } else if (qName.equals(TAG_POTEZ)) {
            if (!trenutnaRunda.isEmpty() && !trenutniIgrac.isEmpty()) {
                ekstrahiraniPodaci.add("R" + trenutnaRunda + " | " + trenutniIgrac + " | " + trenutniOpis);
            }
            trenutnaRunda = "";
            trenutniIgrac = "";
            trenutniOpis = "";
        }
    }

    public List<String> dohvatiEkstrahiranePodatke() {
        return ekstrahiraniPodaci;
    }
}