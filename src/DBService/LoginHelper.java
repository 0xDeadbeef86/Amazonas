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
public class LoginHelper {

    private static final MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();

    //returns: 0 bei fehlgeschlagenem Loginversuch, ansonsten die ID
    public static int getLoginID(String name, String passwort) throws SQLException {
        String sql;
        sql = "SELECT id FROM \"User\" WHERE username='" + name + "' AND passwort = '" + passwort + "'";
        ResultSet res = LoginHelper.dbVerbindung.executeQuery(sql);

        int id = 0;
        while (res.next()) {
            id = Integer.parseInt(res.getString("id"));
        }

        return id;
    }

    public static void setUserData(int loginId) throws SQLException {
        String name = "name";
        String berechtigungLevel = "";

        //TODO: QUERY
        String sql_UserData = "SELECT * FROM \"User\" WHERE id='" + loginId + "'";
        ResultSet res_User = LoginHelper.dbVerbindung.executeQuery(sql_UserData);

        int berechtigungLevel_id = -1;
        while (res_User.next()) {
            name = res_User.getString("username");
            berechtigungLevel_id = Integer.parseInt(res_User.getString("fk_berechtigung"));

            String sql_Berechtigung = "SELECT * FROM \"Berechtigung\" WHERE berechtigungsstufe='" + berechtigungLevel_id + "'";
            ResultSet res_Berechtigung = LoginHelper.dbVerbindung.executeQuery(sql_Berechtigung);
            while (res_Berechtigung.next()) {
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
