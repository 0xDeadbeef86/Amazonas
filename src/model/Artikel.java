package model;

/**
 *
 * @author lenzch
 */
public class Artikel {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public int getNettopreis() {
        return nettopreis;
    }

    public void setNettpreis(int nettopreis) {
        this.nettopreis = nettopreis;
    }

    public int getMehrwertsteuer() {
        return mehrwertsteuer;
    }

    public void setMehrwertsteuer(int mehrwertsteuer) {
        this.mehrwertsteuer = mehrwertsteuer;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }
    private int id;
    private String name;
    private String beschreibung;
    private int nettopreis;
    private int mehrwertsteuer;
    private boolean aktiv;
}
