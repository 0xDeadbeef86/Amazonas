/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import DBService.MyDatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author fixch
 */
public class StatistikenTableModel extends AbstractTableModel {

    private static final MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();

    private static final ArrayList<String> kunden = new ArrayList<>();
    private static final ArrayList<Integer> gesamtbruttoumsaetze = new ArrayList<>(); //in Cent
    private static final ArrayList<Integer> gesamtnettoumsaetze = new ArrayList<>(); // in Cent
    private static final ArrayList<Integer> bestellungen = new ArrayList<>();
    private static double gesamtbruttoumsaetzeAVG = 0, gesamtnettoumsaetzeAVG = 0, bestellungenAVG = 0; // in Cent

    public StatistikenTableModel() throws SQLException {
        StatistikenTableModel.kunden.clear();
        StatistikenTableModel.gesamtbruttoumsaetze.clear();
        StatistikenTableModel.gesamtnettoumsaetze.clear();
        StatistikenTableModel.bestellungen.clear();
        StatistikenTableModel.gesamtbruttoumsaetzeAVG = 0;
        StatistikenTableModel.gesamtnettoumsaetzeAVG = 0;
        StatistikenTableModel.bestellungenAVG = 0;
        String sql = "SELECT * FROM v_KundeUmsatzBestellungen";
        ResultSet res = StatistikenTableModel.dbVerbindung.executeQuery(sql);
        while (res.next()) {
            StatistikenTableModel.kunden.add(res.getString("Kunde"));
            StatistikenTableModel.gesamtbruttoumsaetze.add(res.getInt("Gesamtbruttoumsatz"));
            StatistikenTableModel.gesamtbruttoumsaetzeAVG += res.getInt("Gesamtbruttoumsatz");

            StatistikenTableModel.gesamtnettoumsaetze.add(res.getInt("Gesamtnettoumsatz"));
            StatistikenTableModel.gesamtnettoumsaetzeAVG += res.getInt("Gesamtnettoumsatz");

            StatistikenTableModel.bestellungen.add(res.getInt("Bestellungen"));
            StatistikenTableModel.bestellungenAVG += res.getInt("Bestellungen");
        }
        gesamtbruttoumsaetzeAVG = gesamtbruttoumsaetzeAVG / StatistikenTableModel.kunden.size();
        gesamtnettoumsaetzeAVG = gesamtnettoumsaetzeAVG / StatistikenTableModel.kunden.size();
        bestellungenAVG = bestellungenAVG / StatistikenTableModel.kunden.size();
    }

    @Override
    public int getRowCount() {
        return StatistikenTableModel.kunden.size() + 1; //letzte Zeile: Durchschnitt
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < StatistikenTableModel.kunden.size()) {
            if (columnIndex == 0) {
                return StatistikenTableModel.kunden.get(rowIndex);
            } else if (columnIndex == 1) {
                return rundeKorrektInEuro(StatistikenTableModel.gesamtbruttoumsaetze.get(rowIndex) / 100d) + " €";
            } else if (columnIndex == 2) {
                return rundeKorrektInEuro(StatistikenTableModel.gesamtnettoumsaetze.get(rowIndex) / 100d) + " €";
            } else {
                return StatistikenTableModel.bestellungen.get(rowIndex);
            }
        } else //letzte Zeile
        {
            if (columnIndex == 0) {
                return "<html>&Oslash; Durchschnitt</html>";
            } else if (columnIndex == 1) {
                return rundeKorrektInEuro(StatistikenTableModel.gesamtbruttoumsaetzeAVG / 100d) + " €";
            } else if (columnIndex == 2) {
                return rundeKorrektInEuro(StatistikenTableModel.gesamtnettoumsaetzeAVG / 100d) + " €";
            } else {
                return rundeKorrektInEuro(StatistikenTableModel.bestellungenAVG);
            }

        }

    }

    @Override
    public String getColumnName(int column) {

        if (column == 0) {
            return "Kunde";
        } else if (column == 1) {
            return "Gesamtumsatz brutto";
        } else if (column == 2) {
            return "Gesamtumsatz netto";
        } else {
            return "Anzahl Bestellungen";
        }
    }

    //behebt Probleme mit Fließkommazahlen
    private double rundeKorrektInEuro(double eingabe) {
        eingabe = eingabe * 100;
        eingabe = Math.round(eingabe);
        return eingabe / 100;
    }

}
