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
import model.Benutzer;
import model.User;

/**
 *
 * @author Christopher
 */
public class BenutzerHelper {
    private static final MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();
    public static final HashMap<Integer, Benutzer> benutzerPuffer = new HashMap<>(); //ID, Benutzer
    
    /**
     *
     * @return
     * @throws SQLException
     */
    public static ArrayList<Benutzer> getAlleUser() throws SQLException {
        
        String sql = "";
        
        if(User.GetInstance().getAccessLevel().equals("Adminstrator")) {
            sql = "SELECT * FROM \"User\""; 
        }
        
        else if(User.GetInstance().getAccessLevel().equals("Mitarbeiter")) {
            sql = "SELECT * FROM \"User\" WHERE fk_berechtigung < '4'";
        }
               
        ResultSet res = dbVerbindung.executeQuery(sql);

        ArrayList<Benutzer> userList = new ArrayList<>();

        //if(res != null)
            while (res.next()) {
                userList.add(getBenutzer(res.getInt("id")));
            }
        
      
      
        return userList;
    }
    
       public static Benutzer getBenutzer(int id) throws SQLException {
        if (benutzerPuffer.get(id) == null) //gesuchter Artikel ist nicht im Puffer, Artikel muss aus der Datenbank geladen werden
        {
            String sql;
            sql = "SELECT * FROM \"vwuser\" WHERE id = '" + id + "';";
            ResultSet res = dbVerbindung.executeQuery(sql);

            String name = "";
            String passwort = "";
            String berechtigung = "";      
            //int id = -1;

            while (res.next()) {
                name = res.getString("username");
                passwort = res.getString("passwort");
                berechtigung = res.getString("berechtigung");
                // id = res.getInt("id");
            }

            // set data for Artikel object
            Benutzer user = new Benutzer();

            //artikel.setId(id);
            user.setName(name);
            user.setPasswort(passwort);
            user.setBerechtigung(berechtigung);
            user.setId(id);
            benutzerPuffer.put(id, user);
            return user;
        } 
        else //gesuchter Artikel ist im Puffer
        {
            return benutzerPuffer.get(id);
        }
    }
       
// INSERT INTO "vwuser" (username, passwort, berechtigung)
// VALUES ('Grupfel', 'a', 'Kunde');
       
    public static boolean insertBenutzer(String username, String passwort, String berechtigung) throws SQLException {
        String sql;        
        sql = "INSERT INTO \"vwuser\" (username, passwort, berechtigung)"
        + " VALUES (" + "'" + username + "'" + "," + "'" + passwort + "'" + "," + "'" + berechtigung + "'" + ");";
        
        try {
            dbVerbindung.executeUpdate(sql);
            return true;
        } catch (Exception ex) {

        }
        return false; //Fehler
    }    
    
    //Gibt eine HashMap mit Key: BerechtigungID und Value: Berechtigungsname zurück
    public static HashMap<Integer, String> getBerechtigungFromDB() throws SQLException {
        String sql;
        
        sql = "SELECT * FROM \"Berechtigung\"";
        if(!User.GetInstance().getAccessLevel().equals("Adminstrator"))
        {
            System.out.println("dasuzgdhasjd");
            sql = sql.concat(" WHERE berechtigungsstufe < 3");
        }
        System.out.println(sql);
        ResultSet res = dbVerbindung.executeQuery(sql);
        HashMap<Integer, String> alleBerechtigungen = new HashMap<>();
        int id = 0;
        String berechtigung = "";
        while (res.next()) {
            id = Integer.parseInt(res.getString("id"));
            berechtigung = res.getString("titel");
            if (id != 0 && !"".equals(berechtigung)) {
                alleBerechtigungen.put(id, berechtigung);
            }
        }
        return alleBerechtigungen;
    }
    
    public static boolean updateBenutzer(int id, String username, String passwort, String berechtigung) throws SQLException {
        String sql;
        sql = "UPDATE \"vwuser\" SET username = "
                + "'" + username + "'"
                + ", passwort = " + "'" + passwort + "'"
                + ", berechtigung = " + "'" + berechtigung + "'"
                + " where id = " + "'" + id + "'";
        
        try {
            dbVerbindung.executeUpdate(sql);
            return true;
        } catch (Exception ex) {

        }

        return false; //Fehler
    }
    
    public static boolean deleteBenutzer(int id) throws SQLException {
        String sql;
        
        //TODO: UserAdresse von diesem User auf Dummy-User umbiegen:
        UserAdresseHelper.updateUserAdresseToDummy(id);
        
        sql = "DELETE FROM \"User\" "
                + "WHERE id = " + "'" + id + "'";
          try {
            int executed = dbVerbindung.executeUpdate(sql);
            if(executed > 0)
                return true;
            //return true;
        } catch (Exception ex) {
               
        }
        return false;
        //return false; //Fehler
    }
    
}
