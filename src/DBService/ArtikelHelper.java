/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBService;


import java.sql.ResultSet;
import java.sql.SQLException;
import model.Artikel;
import model.User;

/**
 *
 * @author fixch
 */
public class ArtikelHelper 
{
    // test
    public static void insertArticle(String name, String beschreibung, int nettopreis, boolean aktiv) throws SQLException {
        String sql;
        String test = "test";
        sql = "INSERT INTO \"Artikel\" (name, beschreibung, nettopreis, aktiv) \n" +
        "VALUES (" + "'"+name+"'" +"," + "'"+beschreibung+"'" + "," + nettopreis + "," + aktiv + ");";
        MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();
        dbVerbindung.connect();
        dbVerbindung.executeUpdate(sql);
    }
    
    public static Artikel getArticle(int id) throws SQLException {
        String sql;
        sql = "SELECT * FROM \"Artikel\" WHERE id =" + id +";"; 
        MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();
        dbVerbindung.connect();
        ResultSet res = dbVerbindung.executeQuery(sql);
                
        String name = "";
        String beschreibung = "";
        boolean aktiv = false;
        
         while(res.next()) {            
            name = res.getString("name");    
            beschreibung = res.getString("beschreibung");
            aktiv = res.getBoolean("aktiv");
         }
        
        // set data for Artikel object
        Artikel artikel = new Artikel();
        
        artikel.setId(id);
        artikel.setName(name);
        artikel.setBeschreibung(beschreibung);
        artikel.setAktiv(aktiv);
        
        return artikel;
    }
}
