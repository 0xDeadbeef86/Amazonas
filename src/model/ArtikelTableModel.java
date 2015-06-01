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

    // private static final MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();

    private ArrayList<Artikel> artikelList = new ArrayList<>();

    public ArtikelTableModel(boolean inaktiveZusaetzlichAnzeigen) throws SQLException {
         artikelLaden(inaktiveZusaetzlichAnzeigen);
    }
    
    public ArtikelTableModel(boolean inaktiveZusaetzlichAnzeigen, String kategorie, String suchtext) throws SQLException {
        this.artikelList = ArtikelHelper.getFilteredArticle(inaktiveZusaetzlichAnzeigen, kategorie, suchtext);
    }
    
    private void artikelLaden(boolean inaktiveZusaetzlichAnzeigen) throws SQLException {      
        if(!inaktiveZusaetzlichAnzeigen) //nur aktive Artikel anzeigen
        {
            this.artikelList = ArtikelHelper.getAllActiveArticle();
        }
        else //auch inaktive Artikel anzeigen
        {
            this.artikelList = ArtikelHelper.getAlleArtikel();
        }
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
