/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import model.User;
import model.UserAdresse;

/**
 *
 * @author Christopher
 */
public class UserAdresseHelper {
    private static final MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();
    public static final HashMap<Integer, UserAdresse> UserAdressePuffer = new HashMap<>(); //ID, UserAdresse
        
    public static ArrayList<UserAdresse> getAlleUserAdresse(int userId) throws SQLException {
        String sql;
        sql = "SELECT * FROM \"vwuseradresse\" WHERE userId = " + "'" + userId + "';";
        ResultSet res = dbVerbindung.executeQuery(sql);
        
        ArrayList<UserAdresse> userList = new ArrayList<>();

        while (res.next()) {
            userList.add(getUserAdresse(res.getInt("id")));
        }
        return userList;
    }    
    
    public static UserAdresse getUserAdresse(int id) throws SQLException {
        if (UserAdressePuffer.get(id) == null) //gesuchter Artikel ist nicht im Puffer, Artikel muss aus der Datenbank geladen werden
        {
            String sql;
            sql = "SELECT * FROM \"vwuseradresse\" WHERE id = '" + id + "';";
            ResultSet res = dbVerbindung.executeQuery(sql);

            int userId = -1;
            String user = "";
            String vorname = "";
            String nachname = "";   
            String adresse = "";   
            int hausnummer = -1;
            int plz = -1;

            while (res.next()) {
                userId = res.getInt("userId");
                user = res.getString("user");
                vorname = res.getString("vorname");
                nachname = res.getString("nachname");
                adresse = res.getString("adresse");
                hausnummer = res.getInt("hausnummer");
                plz = res.getInt("plz");
                
            }

            // set data for UserAdresse object
            UserAdresse userAdresse = new UserAdresse();

            userAdresse.setId(id);
            userAdresse.setUserid(userId);
            userAdresse.setUser(user);
            userAdresse.setVorname(vorname);
            userAdresse.setNachname(nachname);
            userAdresse.setAnschrift(adresse);
            userAdresse.setHausnummer(hausnummer);
            userAdresse.setPlz(plz);
            UserAdressePuffer.put(id, userAdresse);
            return userAdresse;
        } 
        else //gesuchte UserAdresse ist im Puffer
        {
            return UserAdressePuffer.get(id);
        }
    }
    
    public static boolean insertUserAdresse(int userId, String user, String vorname, String nachname, String adresse, int hausnummer, int plz) throws SQLException {
        try {    
            
            String sql_adresse;
            sql_adresse = "INSERT INTO \"Adresse\" (vorname, nachname, anschrift, hausnummer, plz)" 
                + " VALUES (" + "'" + vorname + "'" + "," + "'" + nachname + "'" + "," + "'" + adresse + "'" + ", " + "'" + hausnummer + "'" + ", " + "'" + plz + "'" + ");";   
            
            String sql_userAdresse;
            sql_userAdresse = "INSERT into \"UserAdresse\" (fk_user, fk_adresse) VALUES ((select id from \"User\" where username = '" + user
                    + "'), (select id from \"Adresse\" where vorname = '" + vorname + "' and nachname = '" + nachname + "'));";
            
            dbVerbindung.executeUpdate(sql_adresse);
            dbVerbindung.executeUpdate(sql_userAdresse);
            
            return true;
        } catch (Exception ex) {
            
        }
        return false; //Fehler
    }    

    public static boolean deleteUserAdresse(int id) {         
            String deleteRechnungArtikel;
            deleteRechnungArtikel = 
                    "DELETE from \"RechnungArtikel\" \n" +
                    "WHERE fk_rechnung in (\n" +
                    "    SELECT id FROM \"Rechnung\" \n" +
                    "    WHERE fk_user_adresse IN (\n" +
                    "        SELECT id FROM \"UserAdresse\" WHERE \"UserAdresse\".id = " + id + ")\n" +
                    ")";
        
            String deleteRechnung;
            deleteRechnung = "DELETE FROM \"Rechnung\" "
            + "WHERE fk_user_adresse = " + "'" + id + "'";

            String deleteUserAdresse;
            deleteUserAdresse = "DELETE FROM \"UserAdresse\" "
                    + "WHERE id = " + "'" + id + "'";
            try {
                dbVerbindung.executeUpdate(deleteRechnungArtikel);                                
                dbVerbindung.executeUpdate(deleteRechnung);
                dbVerbindung.executeUpdate(deleteUserAdresse);
                return true;
            } catch (Exception ex) {

        }

        return false; //Fehler
    }    
    
    //Gibt eine HashMap mit Key: UserAdresseID und Value: Anschrift zurück
    public static HashMap<Integer, String> getAlleUserAdresse() throws SQLException {
        String sql;
        sql = "SELECT * FROM \"vwuseradresse\" WHERE \"vwuseradresse\".user = '" + User.GetInstance().getName() + "'";
        
        ResultSet res = ArtikelHelper.dbVerbindung.executeQuery(sql);
        HashMap<Integer, String> alleUserAdresse = new HashMap<>();
        int id = 0;
        String anschrift = "";
        while (res.next()) {
            id = Integer.parseInt(res.getString("id"));
            anschrift = res.getString("plz") + ", " + res.getString("adresse") + " " + res.getString("hausnummer");
            if (id != 0 && !"".equals(anschrift)) {
                alleUserAdresse.put(id, anschrift);
            }
        }
        return alleUserAdresse;
    }
    
        public static boolean updateUserAdresseToDummy(int id) throws SQLException {
        String sql;
        sql = "UPDATE \"UserAdresse\" SET fk_user = '1' "               
                + "where fk_user = " + "'" + id + "'";
        try {
            ArtikelHelper.dbVerbindung.executeUpdate(sql);
            return true;
        } catch (Exception ex) {

        }

        return false; //Fehler
    }
}
