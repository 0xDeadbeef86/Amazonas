/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Christopher
 */
public class UserAdresse {

    int id;
    int userid;
    int adresseid;
    String user;
    String vorname;
    String nachname;
    String anschrift;
    int hausnummer = -1;
    int plz = -1;

    public int getAdresseid() {
        return adresseid;
    }

    public void setAdresseid(int adresseid) {
        this.adresseid = adresseid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getAnschrift() {
        return anschrift;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public void setAnschrift(String anschrift) {
        this.anschrift = anschrift;
    }    
    
    public int getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(int hausnummer) {
        this.hausnummer = hausnummer;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }
}
