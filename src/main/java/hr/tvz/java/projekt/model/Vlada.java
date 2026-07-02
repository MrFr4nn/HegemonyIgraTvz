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
        this.drzavniProracun = 0.0;
        this.stopaPoreza = 0.2;
        this.minimalnaPlaca = 5.0;
        this.donesenZakoni = new ArrayList<>();
        this.proracunUManjku = false;
        this.prijedlogZakona = "";
        this.legitimnost = 0;
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
        tekst = tekst + "Drzavni proracun: " + String.format("%.2f", drzavniProracun) + "\n";
        tekst = tekst + "Stopa poreza: " + stopaPoreza + "\n";
        tekst = tekst + "Minimalna placa: " + minimalnaPlaca + "\n";
        tekst = tekst + "Legitimnost: " + legitimnost + "\n";
        tekst = tekst + "Broj donesenih zakona: " + donesenZakoni.size() + "\n";
        tekst = tekst + "Pod MMF kaznom: " + podMMFKaznom;
        return tekst;
    }

    @Override
    public int izracunajUkupniRezultat() {
        int privremena = (int) drzavniProracun + legitimnost;
        if (!proracunUManjku) {
            privremena = privremena + 20;
        }
        privremena = privremena + (donesenZakoni.size() * 5);
        return privremena;
    }

    public boolean izracunajPolitickiRezultat() {
        return drzavniProracun >= 0;
    }

    public void provjeriStanjeProracuna() {
        proracunUManjku = drzavniProracun < 0;
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
        smanjiLegitimnost(20);
    }

    public void ukloniMMFKaznu() {
        podMMFKaznom = false;
    }

    public void povecajLegitimnost(int iznos) {
        legitimnost = legitimnost + iznos;
        if (legitimnost > 100) {
            legitimnost = 100;
        }
    }

    public void smanjiLegitimnost(int iznos) {
        legitimnost = legitimnost - iznos;
        if (legitimnost < 0) {
            legitimnost = 0;
        }
    }

    public int preracunajLegitimnostUBodove() {
        int bodovi = legitimnost / 10;
        povecajBodove(bodovi);
        return bodovi;
    }

    public void naplatiPorez(double oporeziviPrihod) {
        double iznos = oporeziviPrihod * stopaPoreza;
        drzavniProracun = drzavniProracun + iznos;
    }

    public void isplatiSubvenciju(double iznos) {
        drzavniProracun = drzavniProracun - iznos;
        povecajLegitimnost(5);
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
    }

    public void promijeniMinimalnuPlacu(double novaPlaca) {
        minimalnaPlaca = Math.max(1.0, novaPlaca);
    }

    public void donesiNoviZakon(String opisZakona) {
        donesenZakoni.add(opisZakona);
        povecajLegitimnost(8);
    }

    public List<String> getDonesenZakoni() { return donesenZakoni; }
    public double getDrzavniProracun() { return drzavniProracun; }
    public void setDrzavniProracun(double drzavniProracun) { this.drzavniProracun = drzavniProracun; }
    public double getStopaPoreza() { return stopaPoreza; }
    public double getMinimalnaPlaca() { return minimalnaPlaca; }
    public boolean isProracunUManjku() { return proracunUManjku; }
    public String getPrijedlogZakona() { return prijedlogZakona; }
    public void setPrijedlogZakona(String prijedlogZakona) { this.prijedlogZakona = prijedlogZakona; }
    public int getLegitimnost() { return legitimnost; }
    public boolean isPodMMFKaznom() { return podMMFKaznom; }
}