package hr.tvz.java.projekt.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
import java.util.logging.Logger;

public class XmlUpravitelj {

    private static final Logger LOG = Logger.getLogger(XmlUpravitelj.class.getName());
    private static final String PUTANJA_DATOTEKE = "povijest.xml";
    private static final String PUTANJA_SHEME = "povijest.xsd";
    private static final String TAG_POTEZ = "Potez";
    private static final String TAG_RUNDA = "Runda";
    private static final String TAG_IGRAC = "Igrac";
    private static final String TAG_OPIS = "Opis";
    private static final String FEATURE_DOCTYPE = "http://apache.org/xml/features/disallow-doctype-decl";
    private static final String FEATURE_EXT_GENERAL = "http://xml.org/sax/features/external-general-entities";
    private static final String FEATURE_EXT_PARAM = "http://xml.org/sax/features/external-parameter-entities";

    private Document trenutniDokument;

    public void pokreniNovuPovijest() {
        try {
            DocumentBuilderFactory tvornica = napraviSiguranDocumentBuilderFactory();
            DocumentBuilder graditelj = tvornica.newDocumentBuilder();
            trenutniDokument = graditelj.newDocument();
            Element korijen = trenutniDokument.createElement("PovijestIgre");
            trenutniDokument.appendChild(korijen);
            spremiDokument();
        } catch (Exception greska) {
            LOG.severe("Greska pri pokretanju povijesti: " + greska.getMessage());
        }
    }

    public void dodajPotezUPovijest(int runda, String igrac, String opis) {
        try {
            if (trenutniDokument == null) {
                pokreniNovuPovijest();
            }
            Element korijen = trenutniDokument.getDocumentElement();
            Element potez = trenutniDokument.createElement(TAG_POTEZ);
            Element elementRunda = trenutniDokument.createElement(TAG_RUNDA);
            elementRunda.setTextContent(String.valueOf(runda));
            Element elementIgrac = trenutniDokument.createElement(TAG_IGRAC);
            elementIgrac.setTextContent(igrac);
            Element elementOpis = trenutniDokument.createElement(TAG_OPIS);
            elementOpis.setTextContent(opis);
            potez.appendChild(elementRunda);
            potez.appendChild(elementIgrac);
            potez.appendChild(elementOpis);
            korijen.appendChild(potez);
            spremiDokument();
        } catch (Exception greska) {
            LOG.severe("Greska pri dodavanju poteza: " + greska.getMessage());
        }
    }

    private void spremiDokument() {
        try {
            TransformerFactory tvornicaT = TransformerFactory.newInstance();
            tvornicaT.setAttribute(javax.xml.XMLConstants.ACCESS_EXTERNAL_DTD, "");
            tvornicaT.setAttribute(javax.xml.XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
            Transformer transformator = tvornicaT.newTransformer();
            transformator.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformator.transform(new DOMSource(trenutniDokument), new StreamResult(new File(PUTANJA_DATOTEKE)));
        } catch (Exception greska) {
            LOG.severe("Greska pri spremanju XML-a: " + greska.getMessage());
        }
    }

    public List<String> ucitajPovijestZaReplay() {
        List<String> listaPoteza = new ArrayList<>();
        try {
            File datoteka = new File(PUTANJA_DATOTEKE);
            if (!datoteka.exists()) {
                return listaPoteza;
            }
            DocumentBuilder graditelj = napraviSiguranDocumentBuilderFactory().newDocumentBuilder();
            Document dokument = graditelj.parse(datoteka);
            NodeList listaCvorova = dokument.getElementsByTagName(TAG_POTEZ);
            int brojac = 0;
            while (brojac < listaCvorova.getLength()) {
                Element potez = (Element) listaCvorova.item(brojac);
                String runda = potez.getElementsByTagName(TAG_RUNDA).item(0).getTextContent();
                String igrac = potez.getElementsByTagName(TAG_IGRAC).item(0).getTextContent();
                String opis = potez.getElementsByTagName(TAG_OPIS).item(0).getTextContent();
                listaPoteza.add("Runda " + runda + " | " + igrac + " | " + opis);
                brojac = brojac + 1;
            }
        } catch (Exception greska) {
            LOG.severe("Greska pri ucitavanju povijesti: " + greska.getMessage());
        }
        return listaPoteza;
    }

    public boolean validirajProtivSheme() {
        try {
            InputStream tokSheme = getClass().getClassLoader().getResourceAsStream(PUTANJA_SHEME);
            if (tokSheme == null) {
                LOG.warning("XSD shema nije pronadena.");
                return false;
            }
            SchemaFactory tvornicaSheme = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
            tvornicaSheme.setProperty(javax.xml.XMLConstants.ACCESS_EXTERNAL_DTD, "");
            tvornicaSheme.setProperty(javax.xml.XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            Schema shema = tvornicaSheme.newSchema(new StreamSource(tokSheme));
            Validator validator = shema.newValidator();
            validator.setProperty(javax.xml.XMLConstants.ACCESS_EXTERNAL_DTD, "");
            validator.setProperty(javax.xml.XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            validator.validate(new StreamSource(new File(PUTANJA_DATOTEKE)));
            LOG.info("XML validacija uspjesna.");
            return true;
        } catch (SAXException | IOException greska) {
            LOG.severe("Greska pri validaciji: " + greska.getMessage());
            return false;
        } catch (Exception greska) {
            LOG.severe("Neocekivana greska: " + greska.getMessage());
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
            tvornicaSax.setFeature(FEATURE_DOCTYPE, true);
            tvornicaSax.setFeature(FEATURE_EXT_GENERAL, false);
            tvornicaSax.setFeature(FEATURE_EXT_PARAM, false);
            SAXParser saxParser = tvornicaSax.newSAXParser();
            SaxEkstraktorHandler handler = new SaxEkstraktorHandler();
            saxParser.parse(datoteka, handler);
            ekstrahiraniPodaci = handler.dohvatiEkstrahiranePodatke();
            LOG.info("SAX ekstrakcija uspjesna - " + ekstrahiraniPodaci.size() + " poteza.");
        } catch (Exception greska) {
            LOG.severe("Greska pri SAX ekstrakciji: " + greska.getMessage());
        }
        return ekstrahiraniPodaci;
    }

    private DocumentBuilderFactory napraviSiguranDocumentBuilderFactory() throws Exception {
        DocumentBuilderFactory tvornica = DocumentBuilderFactory.newInstance();
        tvornica.setFeature(FEATURE_DOCTYPE, true);
        tvornica.setFeature(FEATURE_EXT_GENERAL, false);
        tvornica.setFeature(FEATURE_EXT_PARAM, false);
        return tvornica;
    }
}