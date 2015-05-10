/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package DBService;

import java.sql.ResultSet;
import java.sql.SQLException;
import model.Artikel;

/**
 *
 * @author fixch
 */


public class ArtikelHelper {

    public static boolean insertArticle(String name, String beschreibung, int nettopreis, int mehrwertsteuerID, int kategorieID, boolean aktiv) throws SQLException {
        String sql;
        sql = "INSERT INTO \"Artikel\" (name, beschreibung, fk_mehrwertsteuer, fk_kategorie, nettopreis, aktiv)"
                + "VALUES (" + "'" + name + "'" + "," + "'" + beschreibung + "'" + "," + mehrwertsteuerID + "," + kategorieID + "," + nettopreis + "," + aktiv + ");";
        MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();
        try {
            dbVerbindung.connect();
            dbVerbindung.executeUpdate(sql);
            return true;
        } catch (Exception ex) {
        }
        return false; //Fehler
    }

    public static Artikel getArticle(int id) throws SQLException {
        String sql;
        sql = "SELECT * FROM \"Artikel\" WHERE id =" + id + ";";
        MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();
        dbVerbindung.connect();
        ResultSet res = dbVerbindung.executeQuery(sql);
        String name = "";
        String beschreibung = "";
        int nettopreis = -1;
        int mehrwertsteuer = -1;
        boolean aktiv = false;
        while (res.next()) {
            name = res.getString("name");
            beschreibung = res.getString("beschreibung");
            nettopreis = res.getInt("nettopreis");
            mehrwertsteuer = res.getInt("fk_mehrwertsteuer");
            aktiv = res.getBoolean("aktiv");
        }
// set data for Artikel object
        Artikel artikel = new Artikel();
        artikel.setId(id);
        artikel.setName(name);
        artikel.setBeschreibung(beschreibung);
        artikel.setNettpreis(nettopreis);
        artikel.setMehrwertsteuer(mehrwertsteuer);
        artikel.setAktiv(aktiv);
        return artikel;
    }
}

