/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBService;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import model.Artikel;

/**
 *
 * @author fixch
 */
public class BestellungHelper {

    private static final MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();

    public static boolean insertBestellung(int userAdresseID, HashMap<Integer, Integer> artikelAnzahl) {
        int rechnungID = 0;
        String sqlInsertRechnung, sqlGetRechnungID;
        String[] sqlInsertRechnungArtikel = null;
        sqlInsertRechnung = "INSERT INTO \"Rechnung\" (fk_user_adresse) VALUES (" + userAdresseID + ")";
        sqlGetRechnungID = "SELECT id, MAX(datum) FROM \"Rechnung\" GROUP BY id ORDER BY datum DESC LIMIT 1";
        Object[] alleArtikelIDs = artikelAnzahl.keySet().toArray();

        try {
            BestellungHelper.dbVerbindung.executeUpdate(sqlInsertRechnung);

            ResultSet res = BestellungHelper.dbVerbindung.executeQuery(sqlGetRechnungID);
            while (res.next()) {
                rechnungID = res.getInt("id");
            }

            for (int i = 0; i < artikelAnzahl.size(); i++) {
                 BestellungHelper.dbVerbindung.executeUpdate("INSERT INTO \"RechnungArtikel\" (fk_rechnung, fk_artikel, anzahl)"
                        + " VALUES (" + rechnungID + ", " + alleArtikelIDs[i] + ", " + artikelAnzahl.get(alleArtikelIDs[i]) + ")");
            }

            return true;
        } catch (Exception ex) {

        }
        return false; //Fehler

    }
}
