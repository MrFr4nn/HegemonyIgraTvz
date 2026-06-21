package hr.tvz.java.projekt.model;

import java.util.ArrayList;
import java.util.List;

public class Vlada extends KlasaIgraca {

    private static final long serialVersionUID = 1L;

    private double drzavniProracun;
    private double stopaPoreza;
    private double minimalnaPlaca;
    private List<String> donesenZakoni;
    private boolean proracunUManjku;
    private String prijedlogZakona;
    private int legitimnost;
    private boolean podMMFKaznom;

    public Vlada(String naziv) {
        super(naziv);
        this.drzavniProracun = 100.0;
        this.stopaPoreza = 0.2;
        this.minimalnaPlaca = 5.0;
        this.donesenZakoni = new ArrayList<>();
        this.proracunUManjku = false;
        this.prijedlogZakona = "";
        this.legitimnost = 50;
        this.podMMFKaznom = false;
    }

    @Override
    public void odigrajPotez() {
        zabrojOdigranihPoteza();
        provjeriStanjeProracuna();
        provjeriUvjeteBankrota();
    }

    @Override
    public String ispisiStanje() {
        String tekst = "";
        tekst = tekst + "Vlada: " + naziv + "\n";
        tekst = tekst + "Drzavni proracun: " + drzavniProracun + "\n";
        tekst = tekst + "Stopa poreza: " + stopaPoreza + "\n";
        tekst = tekst + "Minimalna placa: " + minimalnaPlaca + "\n";
        tekst = tekst + "Legitimnost: " + legitimnost + "\n";
        tekst = tekst + "Broj donesenih zakona: " + donesenZakoni.size() + "\n";
        tekst = tekst + "Proracun u manjku: " + proracunUManjku + "\n";
        tekst = tekst + "Pod MMF kaznom: " + podMMFKaznom;
        return tekst;
    }

    @Override
    public int izracunajUkupniRezultat() {
        int privremena = (int) drzavniProracun + legitimnost;
        if (!proracunUManjku) {
            privremena = privremena + 20;
        }
        privremena = privremena + (donesenZakoni.size() * 3);
        return privremena;
    }

    public boolean izracunajPolitickiRezultat() {
        return drzavniProracun >= 0;
    }

    public void provjeriStanjeProracuna() {
        if (drzavniProracun < 0) {
            proracunUManjku = true;
        } else {
            proracunUManjku = false;
        }
    }

    public void provjeriUvjeteBankrota() {
        if (drzavniProracun < 0 && !podMMFKaznom) {
            aktivirajMMFKaznu();
        }
    }

    public void aktivirajMMFKaznu() {
        podMMFKaznom = true;
        stopaPoreza = 0.5;
        minimalnaPlaca = 1.0;
        smanjiLegitimnost(15);
        System.out.println("MMF intervencija! Stopa poreza prisilno postavljena na 0.5, minimalna placa na 1.0.");
    }

    public void ukloniMMFKaznu() {
        podMMFKaznom = false;
    }

    public void povecajLegitimnost(int iznos) {
        int privremena = legitimnost + iznos;
        if (privremena > 100) {
            privremena = 100;
        }
        legitimnost = privremena;
    }

    public void smanjiLegitimnost(int iznos) {
        int privremena = legitimnost - iznos;
        if (privremena < 0) {
            privremena = 0;
        }
        legitimnost = privremena;
    }

    public int preracunajLegitimnostUBodove() {
        int bodovi = legitimnost / 10;
        povecajBodove(bodovi);
        return bodovi;
    }

    public void naplatiPorez(double oporeziviPrihod) {
        double iznos = oporeziviPrihod * stopaPoreza;
        drzavniProracun = drzavniProracun + iznos;
        System.out.println("Naplacen je porez u iznosu: " + iznos);
    }

    public void isplatiSubvenciju(double iznos) {
        double privremena = drzavniProracun - iznos;
        if (privremena < -50) {
            System.out.println("Subvencija odbijena, proracun bi previse pao.");
        } else {
            drzavniProracun = privremena;
            povecajLegitimnost(3);
        }
        provjeriStanjeProracuna();
    }

    public void promijeniStopuPoreza(double novaStopa) {
        if (novaStopa < 0.0) {
            stopaPoreza = 0.0;
        } else if (novaStopa > 0.9) {
            stopaPoreza = 0.9;
        } else {
            stopaPoreza = novaStopa;
        }
        donesenZakoni.add("Promjena poreza na " + stopaPoreza);
    }

    public void promijeniMinimalnuPlacu(double novaPlaca) {
        if (novaPlaca < 1.0) {
            minimalnaPlaca = 1.0;
        } else {
            minimalnaPlaca = novaPlaca;
        }
        donesenZakoni.add("Promjena minimalne place na " + minimalnaPlaca);
    }

    public void donesiNoviZakon(String opisZakona) {
        donesenZakoni.add(opisZakona);
    }

    public List<String> getDonesenZakoni() {
        return donesenZakoni;
    }

    public double getDrzavniProracun() {
        return drzavniProracun;
    }

    public void setDrzavniProracun(double drzavniProracun) {
        this.drzavniProracun = drzavniProracun;
    }

    public double getStopaPoreza() {
        return stopaPoreza;
    }

    public double getMinimalnaPlaca() {
        return minimalnaPlaca;
    }

    public boolean isProracunUManjku() {
        return proracunUManjku;
    }

    public String getPrijedlogZakona() {
        return prijedlogZakona;
    }

    public void setPrijedlogZakona(String prijedlogZakona) {
        this.prijedlogZakona = prijedlogZakona;
    }

    public int getLegitimnost() {
        return legitimnost;
    }

    public boolean isPodMMFKaznom() {
        return podMMFKaznom;
    }
}