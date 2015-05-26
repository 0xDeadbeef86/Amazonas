/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import DBService.ArtikelHelper;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Christian
 */
public class WarenkorbTableModel extends AbstractTableModel {

    private HashMap<Integer, Integer> warenkorbInhalt = new HashMap<>(); //ArtikelID, Anzahl
    private Object[] alleIDs;

    public WarenkorbTableModel(HashMap<Integer, Integer> warenkorbInhalt) {
        this.warenkorbInhalt = warenkorbInhalt;
        this.alleIDs = warenkorbInhalt.keySet().toArray();
    }

    @Override
    public int getRowCount() {
        return this.warenkorbInhalt.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Artikel artikel = null;
        try {
            System.out.println("TEST:" + this.alleIDs[rowIndex] + " " + this.alleIDs.length + " " + rowIndex);
            artikel = ArtikelHelper.getArticle((int) this.alleIDs[rowIndex]);
        } catch (SQLException ex) {
            Logger.getLogger(WarenkorbTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (artikel != null) {
            if (columnIndex == 0) {
                //Artikelname 
                return artikel.getName();

            } else if (columnIndex == 1) {
                //Anzahl
                return this.warenkorbInhalt.get(artikel.getId()).toString();
            } else if (columnIndex == 2) {
                //Einzelpreis
                return String.valueOf(artikel.getBruttopreis()) + " €"; //zwei Nachkommastellen
            } else {
                //Gesamtpreis
                return (artikel.getBruttopreis() * this.warenkorbInhalt.get(artikel.getId()));

            }
        } else {
            return "Artikel ist leer";
        }
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "Artikelname";
        } else if (column == 1) {
            return "Anzahl";
        } else if (column == 2) {
            return "Einzelpreis";
        } else {
            return "Gesamtpreis";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int colIndex) {
        if (colIndex == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setValueAt(Object eingabe, int rowIndex, int colIndex) {
        int neueAnzahl = -1;
        try {
            neueAnzahl = Integer.parseInt((String) eingabe);
        } catch (Exception ex) {

        }
        if (neueAnzahl > -1) //positive Ganzzahl eingegeben
        {
            if(neueAnzahl > 0)
            {
                this.warenkorbInhalt.put((Integer) this.alleIDs[rowIndex], neueAnzahl);
            }
            else // neueAnzahl == 0 -> Artikel soll aus Warenkorb entfernt werden
            {
                this.warenkorbInhalt.remove(this.alleIDs[rowIndex]);
            }
            fireTableDataChanged();
        }

    }

}
