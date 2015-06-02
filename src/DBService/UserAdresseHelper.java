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
        System.out.println(sql);
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

            while (res.next()) {
                userId = res.getInt("userId");
                user = res.getString("user");
                vorname = res.getString("vorname");
                nachname = res.getString("nachname");
                adresse = res.getString("adresse");
            }

            // set data for UserAdresse object
            UserAdresse userAdresse = new UserAdresse();

            userAdresse.setId(id);
            userAdresse.setUserid(userId);
            userAdresse.setUser(user);
            userAdresse.setVorname(vorname);
            userAdresse.setNachname(nachname);
            userAdresse.setAnschrift(adresse);
            UserAdressePuffer.put(id, userAdresse);
            return userAdresse;
        } 
        else //gesuchte UserAdresse ist im Puffer
        {
            return UserAdressePuffer.get(id);
        }
    }
    
    public static boolean insertUserAdresse(int userId, String user, String vorname, String nachname, String adresse) throws SQLException {
        try {    
            
            String sql_adresse;
            sql_adresse = "INSERT INTO \"Adresse\" (vorname, nachname, anschrift)" 
                + " VALUES (" + "'" + vorname + "'" + "," + "'" + nachname + "'" + "," + "'" + adresse + "'" + ");";   
            
            String sql_userAdresse;
            sql_userAdresse = "INSERT into \"UserAdresse\" (fk_user, fk_adresse) VALUES ((select id from \"User\" where username = '" + user
                    + "'), (select id from \"Adresse\" where vorname = '" + vorname + "' and nachname = '" + nachname + "'));";
            
            dbVerbindung.executeUpdate(sql_adresse);
            dbVerbindung.executeUpdate(sql_userAdresse);

            System.out.println(sql_userAdresse);

            return true;
        } catch (Exception ex) {
            System.out.println("EXCEPTION!!!");
        }
        return false; //Fehler
    }    

    public static boolean deleteUserAdresse(int id) { 
        String sql;
        sql = "DELETE FROM \"UserAdresse\" "
                + "WHERE id = " + "'" + id + "'";
          try {
            dbVerbindung.executeUpdate(sql);
            return true;
        } catch (Exception ex) {

        }

        return false; //Fehler
    }    
}
