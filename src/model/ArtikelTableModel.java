/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import DBService.ArtikelHelper;
import DBService.MyDatabaseConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author lenzch
 */
public class ArtikelTableModel extends AbstractTableModel {

    private static final MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();

    private ArrayList<Artikel> artikelList = new ArrayList<>();

    public ArtikelTableModel() throws SQLException {
        artikelList = ArtikelHelper.getAllActiveArticle();
    }

    @Override
    public int getRowCount() {
        return artikelList.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {

        if (column == 0) {
            return "Artikelname";
        } else if (column == 1) {
            return "Bruttopreis";
        } else {
            return "";
        }

    }

    public int getArtikelIDByRow(int rowIndex) {
        return this.artikelList.get(rowIndex).getId();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return artikelList.get(rowIndex).getName();
        } else if (columnIndex == 1) {
            return (String.valueOf(this.artikelList.get(rowIndex).getBruttopreis()) + " €");
        } else if (columnIndex == 2) { // wird nicht ausgegeben
            return this.getArtikelIDByRow(rowIndex);
        } else {
            return "fehlerhafter Übergabeparameter";
        }

    }

}
