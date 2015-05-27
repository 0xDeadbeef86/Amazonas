/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Artikel;
import model.ArtikelTableModel;

/**
 *
 * @author fixch
 */
public class ArtikelHelper {

    private static final MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();

    public static boolean insertArticle(String name, String beschreibung, int nettopreis, int mehrwertsteuerID, int kategorieID, boolean aktiv) throws SQLException {
        String sql;
        sql = "INSERT INTO \"Artikel\" (name, beschreibung, fk_mehrwertsteuer, fk_kategorie, nettopreis, aktiv)"
                + "VALUES (" + "'" + name + "'" + "," + "'" + beschreibung + "'" + "," + mehrwertsteuerID + "," + kategorieID + "," + nettopreis + "," + aktiv + ");";

        try {
            ArtikelHelper.dbVerbindung.executeUpdate(sql);
            return true;
        } catch (Exception ex) {

        }
        return false; //Fehler
    }

    public static boolean updateArticle(String name, String beschreibung, int nettopreis, int mehrwertsteuerID, int kategorieID, boolean aktiv, int id) throws SQLException {
        String sql;
        sql = "UPDATE \"Artikel\" SET name = "
                + "'" + name + "'"
                + ", beschreibung = " + "'" + beschreibung + "'"
                + ", nettopreis = " + "'" + nettopreis + "'"
                + ", fk_mehrwertsteuer = " + "'" + mehrwertsteuerID + "'"
                + ", fk_kategorie = " + "'" + kategorieID + "'"
                + ", aktiv = " + "'" + aktiv + "'"
                + "where id = " + "'" + id + "'";
        try {
            ArtikelHelper.dbVerbindung.executeUpdate(sql);
            return true;
        } catch (Exception ex) {

        }

        return false; //Fehler
    }

    public static Artikel getArticle(int id) throws SQLException {
        String sql;
        sql = "SELECT * FROM \"Artikel\" WHERE id = '" + id + "';";
        ResultSet res = ArtikelHelper.dbVerbindung.executeQuery(sql);

        String name = "";
        String beschreibung = "";
        int nettopreis = -1;
        int mehrwertsteuerId = -1;
        int kategorieId = -1;
        String kategorie = "";
        int mehrwertsteuersatz = -1;
        boolean aktiv = false;

        while (res.next()) {
            name = res.getString("name");
            beschreibung = res.getString("beschreibung");
            nettopreis = res.getInt("nettopreis");
            mehrwertsteuerId = res.getInt("fk_mehrwertsteuer");
            kategorieId = res.getInt("fk_kategorie");
            aktiv = res.getBoolean("aktiv");
        }
        mehrwertsteuersatz = getMwst(mehrwertsteuerId);
        kategorie = getKat(kategorieId);

        // set data for Artikel object
        Artikel artikel = new Artikel();

        artikel.setId(id);
        artikel.setName(name);
        artikel.setBeschreibung(beschreibung);
        artikel.setNettpreis(nettopreis);
        artikel.setMehrwertsteuer(mehrwertsteuersatz);
        artikel.setKategorie(kategorie);
        artikel.setAktiv(aktiv);

        return artikel;
    }

    public static ArrayList<Artikel> getAllActiveArticle() throws SQLException {
        String sql;
        sql = "SELECT * FROM \"Artikel\" WHERE aktiv = true";
        ResultSet res = ArtikelHelper.dbVerbindung.executeQuery(sql);

        ArrayList<Artikel> artikelList = new ArrayList<>();

        while (res.next()) {
            artikelList.add(getArticle(res.getInt("id")));
        }
        return artikelList;
    }
    
    public static ArrayList<Artikel> getAlleArtikel() throws SQLException
    {
        String sql;
        sql = "SELECT * FROM \"Artikel\"";
        ResultSet res = ArtikelHelper.dbVerbindung.executeQuery(sql);

        ArrayList<Artikel> artikelList = new ArrayList<>();

        while (res.next()) {
            artikelList.add(getArticle(res.getInt("id")));
        }
        return artikelList;
    }

    private static int getMwst(int id) throws SQLException {
        String sql;
        sql = "SELECT * FROM \"Mehrwertsteuer\" WHERE id =" + id + ";";
        ResultSet res = ArtikelHelper.dbVerbindung.executeQuery(sql);

        int mehrwertsteuersatz = -1;
        boolean aktiv = false;

        while (res.next()) {
            mehrwertsteuersatz = res.getInt("mehrwertsteuersatz");
        }

        return mehrwertsteuersatz;
    }

    private static String getKat(int id) throws SQLException {
        String sql;
        sql = "SELECT * FROM \"Kategorie\" WHERE id =" + id + ";";
        ResultSet res = ArtikelHelper.dbVerbindung.executeQuery(sql);

        String kategorie = "-1";
        boolean aktiv = false;

        while (res.next()) {
            kategorie = res.getString("name");
        }

        return kategorie;
    }
}
