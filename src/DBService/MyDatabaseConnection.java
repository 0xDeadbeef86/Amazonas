/*

*/
package DBService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author lenzch
 */
public class MyDatabaseConnection {

    Connection database;
    Statement statement;

    //stellt eine Verbindung zur Datenbank her
    public void connect() {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            database = DriverManager.getConnection("jdbc:postgresql://localhost:5432/public", "postgres", "postgres"); // localhost:5432/dbprojekt", "projekt", "geheim"
            statement = database.createStatement();
        } catch (Exception ex) {
            System.out.println("Keine Datenbankverbindung möglich: "
                    + ex.getMessage());
        }
    }

    //führt eine angegebene SQL-Anweisung aus (SELECT)
    public ResultSet executeQuery(String query) {
        ResultSet result;
        try {
            result = statement.executeQuery(query);
            /*while (result.next()) {
                System.out.println(result.getString("mehrwertsteuersatz"));
            }*/
            return result;
        } 
        catch (Exception ex) {
            System.out.println("Die Abfrage " + query
                    + "hat den Fehler " + ex.getMessage() + " produziert");
        }
        
        return null;
    }
    
    //führt eine angegebene SQL-Anweisung aus, die eine Zahl zurückgibt (UPDATE, DELETE)
    public int executeUpdate(String query) {
        try {
            return statement.executeUpdate(query);           
        } 
        catch (Exception ex) {
            System.out.println("Update " + query
                    + "hat den Fehler " + ex.getMessage() + " produziert");
        }
        return 0;
    }

    //Schließt die Verbindung zur Datenbank
    public void disconnect() {
        try {
            statement.close();
            database.close();
        } catch (Exception ex) {
            System.out.println("Schließen der Datenbankverbindung möglich: "
                    + ex.getMessage());
        }

    }
}
