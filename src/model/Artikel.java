/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author lenzch
 */
public class Artikel {

    private int id;
    private String name;
    private String beschreibung;
    private int nettopreis;
    private int mehrwertsteuer;
    private String kategorie;
    private boolean aktiv;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return this.beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public int getNettopreis() {
        return this.nettopreis;
    }

    public void setNettpreis(int nettopreis) {
        this.nettopreis = nettopreis;
    }

    public int getMehrwertsteuer() {
        return this.mehrwertsteuer;
    }

    public void setMehrwertsteuer(int mehrwertsteuer) {
        this.mehrwertsteuer = mehrwertsteuer;
    }

    public String getKategorie() {
        return this.kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public boolean isAktiv() {
        return this.aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public double getBruttopreis() {
        System.out.println(this.getMehrwertsteuer() + " " + this.getNettopreis());
        return rundeKorrektInEuro((double) ((int) (((this.getMehrwertsteuer() + 100) * this.getNettopreis()) / 100) / 100d));
    }

    //behebt Probleme mit Fließkommazahlen
    private double rundeKorrektInEuro(double eingabe) {
        eingabe = eingabe * 100;
        eingabe = Math.round(eingabe);
        return eingabe / 100;
    }
}
