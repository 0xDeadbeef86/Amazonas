/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBService;


import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

/**
 *
 * @author fixch
 */
public class LoginHelper 
{
    //returns: 0 bei fehlgeschlagenem Loginversuch, ansonsten die ID
    public static int getLoginID(String name, String passwort) throws SQLException
    {
        String sql;
        sql = "SELECT id FROM \"User\" WHERE username='" + name + "' AND passwort = '" + passwort +  "'";
        MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();
        dbVerbindung.connect();
        //System.out.println(sql);
        ResultSet res = dbVerbindung.executeQuery(sql);
        System.out.println(res);
        
        int id = 0;
        while(res.next()) {            
            id = Integer.parseInt(res.getString("id"));            
        }
        

        return id;
    }
    

    public static void setUserData(int loginId) throws SQLException
    {        
        String name = "name";        
        String berechtigungLevel = "";
        
        //TODO: QUERY
        String sql_UserData = "SELECT * FROM \"User\" WHERE id='" + loginId + "'";
        //System.out.println(sql);
        MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();
        dbVerbindung.connect();
        ResultSet res_User = dbVerbindung.executeQuery(sql_UserData);
        //System.out.println(res_User);
       
        
        int berechtigungLevel_id = -1;
        while(res_User.next()) {            
            name = res_User.getString("username");     
            berechtigungLevel_id = Integer.parseInt(res_User.getString("fk_berechtigung"));            
            //System.out.println(name);
            //System.out.println(berechtigungLevel_id);            
            
            String sql_Berechtigung = "SELECT * FROM \"Berechtigung\" WHERE berechtigungsstufe='" + berechtigungLevel_id + "'";
            MyDatabaseConnection dbVerbindung2 = new MyDatabaseConnection();
            dbVerbindung2.connect();
            ResultSet res_Berechtigung = dbVerbindung2.executeQuery(sql_Berechtigung);
            while(res_Berechtigung.next()) {         
                berechtigungLevel = res_Berechtigung.getString("titel");                    
            }
        }     
        
        // set data for User object
        User.GetInstance().setName(name);
        User.GetInstance().setId(loginId);
        User.GetInstance().setTitle(berechtigungLevel);
        User.GetInstance().setTitle_id(berechtigungLevel_id);
    }
}
