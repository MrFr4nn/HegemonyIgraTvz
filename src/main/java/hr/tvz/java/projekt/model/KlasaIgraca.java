package hr.tvz.java.projekt.model;

import java.io.Serializable;
import java.util.logging.Logger;

public abstract class KlasaIgraca implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(KlasaIgraca.class.getName());

    protected String naziv;
    protected int bodoviPobjede;
    protected int brojOdigranihPoteza;
    protected boolean naPotezu;

    protected KlasaIgraca(String naziv) {
        this.naziv = naziv;
        this.bodoviPobjede = 0;
        this.brojOdigranihPoteza = 0;
        this.naPotezu = false;
    }

    public abstract void odigrajPotez();
    public abstract String ispisiStanje();
    public abstract int izracunajUkupniRezultat();

    public void povecajBodove(int kolicina) {
        if (kolicina > 0) {
            this.bodoviPobjede = this.bodoviPobjede + kolicina;
        } else {
            LOG.warning("Pokusaj dodavanja negativnih bodova ignoriran.");
        }
    }

    public void smanjiBodove(int kolicina) {
        this.bodoviPobjede = this.bodoviPobjede - kolicina;
    }

    public void zabrojOdigranihPoteza() {
        this.brojOdigranihPoteza = this.brojOdigranihPoteza + 1;
    }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }
    public int getBodoviPobjede() { return bodoviPobjede; }
    public void setBodoviPobjede(int bodoviPobjede) { this.bodoviPobjede = bodoviPobjede; }
    public int getBrojOdigranihPoteza() { return brojOdigranihPoteza; }
    public boolean isNaPotezu() { return naPotezu; }
    public void setNaPotezu(boolean naPotezu) { this.naPotezu = naPotezu; }
}