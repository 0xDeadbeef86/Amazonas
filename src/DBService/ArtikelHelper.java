/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBService;

import View.ArtikelForm;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Artikel;

/**
 *
 * @author fixch
 */
public class ArtikelHelper {

    private static final MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();
    public static final HashMap<Integer, Artikel> artikelPuffer = new HashMap<>(); //ID, Artikel
    
    public static boolean insertArticle(String name, String beschreibung, int nettopreis, int mehrwertsteuer, String kategorie, boolean aktiv) throws SQLException {
        String sql;        
        sql = "INSERT INTO \"vwartikel\" (name, beschreibung, mehrwertsteuer, kategorie, nettopreis, aktiv)"
        + "VALUES (" + "'" + name + "'" + "," + "'" + beschreibung + "'" + "," + mehrwertsteuer + "," + "'" + kategorie + "'" + "," + nettopreis + "," + aktiv + ");";

        try {
            ArtikelHelper.dbVerbindung.executeUpdate(sql);
            return true;
        } catch (Exception ex) {

        }
        return false; //Fehler
    }    
    
    public static boolean deleteArticle(int id) {
        String sql;
        sql = "DELETE FROM \"Artikel\" "
                + "WHERE id = " + "'" + id + "'";
          try {
            ArtikelHelper.dbVerbindung.executeUpdate(sql);
            return true;
        } catch (Exception ex) {

        }

        return false; //Fehler
    }

    public static boolean updateArticle(int id, String name, String beschreibung, int nettopreis, int mehrwertsteuer, String kategorie, boolean aktiv) throws SQLException {
        String sql;
        sql = "UPDATE \"vwartikel\" SET name = "
                + "'" + name + "'"
                + ", beschreibung = " + "'" + beschreibung + "'"
                + ", nettopreis = " + "'" + nettopreis + "'"
                + ", mehrwertsteuer = " + "'" + mehrwertsteuer + "'"
                + ", kategorie = " + "'" + kategorie + "'"
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
        if (artikelPuffer.get(id) == null) //gesuchter Artikel ist nicht im Puffer, Artikel muss aus der Datenbank geladen werden
        {
            String sql;
            sql = "SELECT * FROM \"vwartikel\" WHERE id = '" + id + "';";
            ResultSet res = ArtikelHelper.dbVerbindung.executeQuery(sql);

            String name = "";
            String beschreibung = "";
            int nettopreis = -1;
            String kategorie = "";
            int mehrwertsteuer = -1;
            boolean aktiv = false;

            while (res.next()) {
                name = res.getString("name");
                beschreibung = res.getString("beschreibung");
                nettopreis = res.getInt("nettopreis");
                mehrwertsteuer = res.getInt("mehrwertsteuer");
                kategorie = res.getString("kategorie");
                aktiv = res.getBoolean("aktiv");
            }

            // set data for Artikel object
            Artikel artikel = new Artikel();

            artikel.setId(id);
            artikel.setName(name);
            artikel.setBeschreibung(beschreibung);
            artikel.setNettpreis(nettopreis);
            artikel.setMehrwertsteuer(mehrwertsteuer);
            artikel.setKategorie(kategorie);
            artikel.setAktiv(aktiv);
            artikelPuffer.put(id, artikel);
            return artikel;
        } 
        else //gesuchter Artikel ist im Puffer
        {
            return artikelPuffer.get(id);
        }
    }
    
    public static ArrayList<Artikel> getFilteredArticle(boolean inaktiveZusaetzlichAnzeigen, String kategorie, String suchtext) throws SQLException {
        String sql;  
        
        sql = "SELECT * FROM \"vwartikel\" WHERE"; 
        
        // filter aktiv/inaktiv articles
        if(inaktiveZusaetzlichAnzeigen)
            sql = sql.concat(" aktiv = true ");
        
        // filter kategorie        
        sql = sql.concat(" kategorie = " + "'" + kategorie + "' ");
        
        // search limitation via texfield
        String sqlSearchExtension =  "AND name like " + "'%" + suchtext + "%' ";
        if(!suchtext.isEmpty())
            sql = sql.concat(sqlSearchExtension);
        
        // finish the query
        sql = sql.concat(";");      
    
        ResultSet res = ArtikelHelper.dbVerbindung.executeQuery(sql);
        ArrayList<Artikel> artikelList = new ArrayList<>();

        while (res.next()) {
            artikelList.add(getArticle(res.getInt("id")));
        }
        return artikelList;
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

    public static ArrayList<Artikel> getAlleArtikel() throws SQLException {
        String sql;
        sql = "SELECT * FROM \"User\"";
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
    
        //Gibt eine HashMap mit Key: KatergorieID und Value: Katoegoriename zurück
    public static HashMap<Integer, String> getKategorienFromDB() throws SQLException {
        String sql;
        sql = "SELECT * FROM \"Kategorie\"";
        ResultSet res = ArtikelHelper.dbVerbindung.executeQuery(sql);
        HashMap<Integer, String> alleKategorien = new HashMap<>();
        int id = 0;
        String kategorie = "";
        while (res.next()) {
            id = Integer.parseInt(res.getString("id"));
            kategorie = res.getString("name");
            if (id != 0 && !"".equals(kategorie)) {
                alleKategorien.put(id, kategorie);
            }
        }
        return alleKategorien;
    }
}
