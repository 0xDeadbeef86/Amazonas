/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

/**
 *
 * @author fixch
 */
public class LoginHelper {

    private static final MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();

    //returns: 0 bei fehlgeschlagenem Loginversuch, ansonsten die ID
    @SuppressWarnings("empty-statement")
    public static int getLoginID(String name, String passwort) throws SQLException {
        String sql = "SELECT id FROM \"User\" WHERE username = ? AND passwort = ? AND fk_berechtigung > 1";
        dbVerbindung.connect();
        PreparedStatement prepStmt = dbVerbindung.database.prepareStatement(sql);
        prepStmt.setString(1, name);
        prepStmt.setString(2, passwort);

        ResultSet res = prepStmt.executeQuery();

        int id = 0;
        while (res.next()) {
            id = Integer.parseInt(res.getString("id"));
        }

        return id;
    }

    public static void setUserData(int loginId) throws SQLException {
        String name = "";
        String titel = "";
        int berechtigungsstufe = -1;

        //TODO: QUERY
        String sql_UserData = "SELECT * FROM \"vwuser\" WHERE id='" + loginId + "'";
        ResultSet res_User = LoginHelper.dbVerbindung.executeQuery(sql_UserData);
        
        while (res_User.next()) {
            name = res_User.getString("username");
            titel = res_User.getString("berechtigung");
            berechtigungsstufe = res_User.getInt("berechtigungsstufe");
        }

        // set data for User object
        User.GetInstance().setName(name);
        User.GetInstance().setId(loginId);
        User.GetInstance().setTitle(titel);
        User.GetInstance().setTitle_id(berechtigungsstufe);
    }
}
