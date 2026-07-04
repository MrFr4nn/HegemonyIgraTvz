package hr.tvz.java.projekt.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlUpravitelj {

    private static final String PUTANJA_DATOTEKE = "povijest.xml";
    private static final String PUTANJA_SHEME = "povijest.xsd";
    private Document trenutniDokument;

    public void pokreniNovuPovijest() {
        try {
            DocumentBuilderFactory tvornicaDokumenata = DocumentBuilderFactory.newInstance();
            DocumentBuilder graditeljiDokumenta = tvornicaDokumenata.newDocumentBuilder();
            trenutniDokument = graditeljiDokumenta.newDocument();
            Element korijen = trenutniDokument.createElement("PovijestIgre");
            trenutniDokument.appendChild(korijen);
            spremiDokument();
        } catch (Exception greska) {
            System.out.println("Greska pri pokretanju povijesti: " + greska.getMessage());
        }
    }

    public void dodajPotezUPovijest(int runda, String igrac, String opis) {
        try {
            if (trenutniDokument == null) {
                pokreniNovuPovijest();
            }
            Element korijen = trenutniDokument.getDocumentElement();
            Element potez = trenutniDokument.createElement("Potez");
            Element elementRunda = trenutniDokument.createElement("Runda");
            elementRunda.setTextContent(String.valueOf(runda));
            Element elementIgrac = trenutniDokument.createElement("Igrac");
            elementIgrac.setTextContent(igrac);
            Element elementOpis = trenutniDokument.createElement("Opis");
            elementOpis.setTextContent(opis);

            potez.appendChild(elementRunda);
            potez.appendChild(elementIgrac);
            potez.appendChild(elementOpis);
            korijen.appendChild(potez);
            spremiDokument();
        } catch (Exception greska) {
            System.out.println("Greska pri dodavanju poteza: " + greska.getMessage());
        }
    }

    private void spremiDokument() {
        try {
            TransformerFactory tvornicaTransformatora = TransformerFactory.newInstance();
            Transformer transformator = tvornicaTransformatora.newTransformer();
            transformator.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            DOMSource izvor = new DOMSource(trenutniDokument);
            StreamResult rezultat = new StreamResult(new File(PUTANJA_DATOTEKE));
            transformator.transform(izvor, rezultat);
        } catch (Exception greska) {
            System.out.println("Greska pri spremanju XML-a: " + greska.getMessage());
        }
    }

    public List<String> ucitajPovijestZaReplay() {
        List<String> listaPoteza = new ArrayList<>();
        try {
            DocumentBuilderFactory tvornica = DocumentBuilderFactory.newInstance();
            DocumentBuilder graditelj = tvornica.newDocumentBuilder();
            File datoteka = new File(PUTANJA_DATOTEKE);
            if (!datoteka.exists()) {
                return listaPoteza;
            }
            Document dokument = graditelj.parse(datoteka);
            NodeList listaCvorova = dokument.getElementsByTagName("Potez");
            int brojac = 0;
            while (brojac < listaCvorova.getLength()) {
                Element potez = (Element) listaCvorova.item(brojac);
                String runda = potez.getElementsByTagName("Runda").item(0).getTextContent();
                String igrac = potez.getElementsByTagName("Igrac").item(0).getTextContent();
                String opis = potez.getElementsByTagName("Opis").item(0).getTextContent();
                listaPoteza.add("Runda " + runda + " | " + igrac + " | " + opis);
                brojac = brojac + 1;
            }
        } catch (Exception greska) {
            System.out.println("Greska pri ucitavanju povijesti: " + greska.getMessage());
        }
        return listaPoteza;
    }

    public boolean validirajProtivSheme() {
        try {
            InputStream tokSheme = getClass().getClassLoader().getResourceAsStream(PUTANJA_SHEME);
            if (tokSheme == null) {
                System.out.println("XSD shema nije pronadena: " + PUTANJA_SHEME);
                return false;
            }
            SchemaFactory tvornicaSheme = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema shema = tvornicaSheme.newSchema(new StreamSource(tokSheme));
            Validator validator = shema.newValidator();
            validator.validate(new StreamSource(new File(PUTANJA_DATOTEKE)));
            System.out.println("XML validacija uspjesna - povijest.xml je ispravna prema XSD shemi.");
            return true;
        } catch (SAXException greska) {
            System.out.println("XML validacija neuspjesna: " + greska.getMessage());
            return false;
        } catch (IOException greska) {
            System.out.println("Greska pri citanju datoteke za validaciju: " + greska.getMessage());
            return false;
        } catch (Exception greska) {
            System.out.println("Neocekivana greska pri validaciji: " + greska.getMessage());
            return false;
        }
    }

    public List<String> ekstrairajSaxom() {
        List<String> ekstrahiraniPodaci = new ArrayList<>();
        try {
            File datoteka = new File(PUTANJA_DATOTEKE);
            if (!datoteka.exists()) {
                return ekstrahiraniPodaci;
            }
            SAXParserFactory tvornicaSax = SAXParserFactory.newInstance();
            SAXParser saxParser = tvornicaSax.newSAXParser();
            SaxEkstraktorHandler handler = new SaxEkstraktorHandler();
            saxParser.parse(datoteka, handler);
            ekstrahiraniPodaci = handler.dohvatiEkstrahiranePodatke();
            System.out.println("SAX ekstrakcija uspjesna - pronadeno " + ekstrahiraniPodaci.size() + " poteza.");
        } catch (Exception greska) {
            System.out.println("Greska pri SAX ekstrakciji: " + greska.getMessage());
        }
        return ekstrahiraniPodaci;
    }

    private static class SaxEkstraktorHandler extends DefaultHandler {

        private List<String> ekstrahiraniPodaci = new ArrayList<>();
        private StringBuilder trenutniTekst = new StringBuilder();
        private String trenutnaRunda = "";
        private String trenutniIgrac = "";
        private String trenutniOpis = "";
        private String trenutniElement = "";

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            trenutniElement = qName;
            trenutniTekst.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            trenutniTekst.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            String tekst = trenutniTekst.toString().trim();
            if (qName.equals("Runda")) {
                trenutnaRunda = tekst;
            } else if (qName.equals("Igrac")) {
                trenutniIgrac = tekst;
            } else if (qName.equals("Opis")) {
                trenutniOpis = tekst;
            } else if (qName.equals("Potez")) {
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
}