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

    public Connection database;
    private Statement statement;

    //stellt eine Verbindung zur Datenbank her
    public void connect() {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            this.database = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbprojekt", "projekt", "geheim"); // localhost:5432/dbprojekt", "projekt", "geheim"         "jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres"
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
            this.connect();
            result = statement.executeQuery(query);
            return result;
        } catch (Exception ex) {
            System.out.println("Die Abfrage " + query
                    + "hat den Fehler " + ex.getMessage() + " verursacht");
        }
        this.disconnect();
        return null;
    }

    //führt eine angegebene SQL-Anweisung aus, die eine Zahl zurückgibt (UPDATE, DELETE)
    public int executeUpdate(String query) {
        try {
            this.connect();
            int res = statement.executeUpdate(query);
            return res;
        } catch (Exception ex) {
            System.out.println("Update " + query
                    + "hat den Fehler " + ex.getMessage() + "  verursacht");
        }
        this.disconnect();
        return 0;
    }

    //Schließt die Verbindung zur Datenbank
    private void disconnect() {
        try {
            statement.close();
            database.close();
        } catch (Exception ex) {
            System.out.println("Schließen der Datenbankverbindung möglich: "
                    + ex.getMessage());
        }

    }
}
