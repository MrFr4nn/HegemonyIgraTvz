package hr.tvz.java.projekt.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlUpravitelj {

    private static final String PUTANJA_DATOTEKE = "povijest.xml";

    private Document trenutniDokument;
    private Element korijenskiElement;

    public void pokreniNovuPovijest() {
        try {
            DocumentBuilderFactory tvornica = DocumentBuilderFactory.newInstance();
            DocumentBuilder graditelj = tvornica.newDocumentBuilder();
            trenutniDokument = graditelj.newDocument();
            korijenskiElement = trenutniDokument.createElement("PovijestIgre");
            trenutniDokument.appendChild(korijenskiElement);
        } catch (ParserConfigurationException greska) {
            System.out.println("Greska prilikom pokretanja nove povijesti: " + greska.getMessage());
        }
    }

    public void dodajPotezUPovijest(int brojRunde, String oznakaIgraca, String opisPoteza) {
        if (trenutniDokument == null) {
            pokreniNovuPovijest();
        }
        Element elementPoteza = trenutniDokument.createElement("Potez");

        Element elementRunde = trenutniDokument.createElement("Runda");
        elementRunde.setTextContent(String.valueOf(brojRunde));
        elementPoteza.appendChild(elementRunde);

        Element elementIgraca = trenutniDokument.createElement("Igrac");
        elementIgraca.setTextContent(oznakaIgraca);
        elementPoteza.appendChild(elementIgraca);

        Element elementOpisa = trenutniDokument.createElement("Opis");
        elementOpisa.setTextContent(opisPoteza);
        elementPoteza.appendChild(elementOpisa);

        korijenskiElement.appendChild(elementPoteza);
        spremiTrenutniDokument();
    }

    private void spremiTrenutniDokument() {
        try {
            TransformerFactory tvornicaTransformera = TransformerFactory.newInstance();
            Transformer transformer = tvornicaTransformera.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource izvor = new DOMSource(trenutniDokument);
            StreamResult odrediste = new StreamResult(new File(PUTANJA_DATOTEKE));
            transformer.transform(izvor, odrediste);
        } catch (TransformerException greska) {
            System.out.println("Greska prilikom spremanja XML povijesti: " + greska.getMessage());
        }
    }

    public boolean provjeriOsnovniFormat() {
        try {
            SAXParserFactory tvornicaSax = SAXParserFactory.newInstance();
            SAXParser parser = tvornicaSax.newSAXParser();
            ProvjeraSadrzajaHandler obradjivac = new ProvjeraSadrzajaHandler();
            parser.parse(new File(PUTANJA_DATOTEKE), obradjivac);
            return obradjivac.jeFormatIspravan();
        } catch (ParserConfigurationException | SAXException | IOException greska) {
            System.out.println("Greska prilikom SAX provjere formata: " + greska.getMessage());
            return false;
        }
    }

    public List<String> ucitajPovijestZaReplay() {
        List<String> lista = new ArrayList<>();
        try {
            DocumentBuilderFactory tvornica = DocumentBuilderFactory.newInstance();
            DocumentBuilder graditelj = tvornica.newDocumentBuilder();
            File datoteka = new File(PUTANJA_DATOTEKE);
            if (!datoteka.exists()) {
                System.out.println("Datoteka povijesti ne postoji, replay nije moguc.");
                return lista;
            }
            Document dokument = graditelj.parse(datoteka);
            NodeList listaPoteza = dokument.getElementsByTagName("Potez");

            int brojac = 0;
            while (brojac < listaPoteza.getLength()) {
                Node trenutniCvor = listaPoteza.item(brojac);
                if (trenutniCvor.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementPoteza = (Element) trenutniCvor;
                    String runda = dohvatiTekstPodElementa(elementPoteza, "Runda");
                    String igrac = dohvatiTekstPodElementa(elementPoteza, "Igrac");
                    String opis = dohvatiTekstPodElementa(elementPoteza, "Opis");
                    String redak = "Runda " + runda + " | " + igrac + " | " + opis;
                    lista.add(redak);
                }
                brojac = brojac + 1;
            }
        } catch (ParserConfigurationException | SAXException | IOException greska) {
            System.out.println("Greska prilikom ucitavanja povijesti za replay: " + greska.getMessage());
        }
        return lista;
    }

    private String dohvatiTekstPodElementa(Element roditelj, String nazivOznake) {
        NodeList podCvorovi = roditelj.getElementsByTagName(nazivOznake);
        if (podCvorovi.getLength() > 0) {
            return podCvorovi.item(0).getTextContent();
        } else {
            return "";
        }
    }

    private static class ProvjeraSadrzajaHandler extends DefaultHandler {
        private boolean pronadenKorijen = false;
        private boolean formatIspravan = true;

        @Override
        public void startElement(String uri, String lokalnoIme, String kvalificiranoIme,
                                 org.xml.sax.Attributes atributi) {
            if (kvalificiranoIme.equals("PovijestIgre")) {
                pronadenKorijen = true;
            }
            if (kvalificiranoIme.equals("Potez") && !pronadenKorijen) {
                formatIspravan = false;
            }
        }

        public boolean jeFormatIspravan() {
            return pronadenKorijen && formatIspravan;
        }
    }
}