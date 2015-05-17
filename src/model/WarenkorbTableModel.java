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
public class WarenkorbTableModel extends AbstractTableModel
{
    private HashMap<Integer, Integer> warenkorbInhalt = new HashMap<>();
    private Object[] alleIDs;
    
    public WarenkorbTableModel(HashMap<Integer, Integer> warenkorbInhalt) 
    {
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
            artikel = ArtikelHelper.getArticle((int) this.alleIDs[rowIndex]);
        } catch (SQLException ex) {
            Logger.getLogger(WarenkorbTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(artikel != null)
        {
            if(columnIndex == 0)
            {
                //Artikelname 
                return artikel.getName();
                
            }
            else if(columnIndex == 1)
            {
                //Anzahl
                return this.warenkorbInhalt.get(artikel.getId()).toString();
            }
            else if(columnIndex == 2)
            {
                //Einzelpreis
                return String.valueOf((double)((int)((artikel.getMehrwertsteuer() * artikel.getNettopreis()) / 100 ) / 100d)) + " €"; //zwei Nachkommastellen
            }
            else
            {
                return "Gesamtpreis (ToDo)";
                //Gesamtpreis
            }
        }
        else
        {
            return "Artikel ist leer";
        }
    }
    
   @Override
    public String getColumnName(int column) {
        if(column == 0)
        {
            return "Artikelname";
        }
        else if(column == 1)
        {
            return "Anzahl";
        }
        else if(column == 2)
        {
            return "Einzelpreis";    
        }
        else
        {
            return "Gesamtpreis";
        }
    }
    //behebt Probleme mit Fließkommazahlen
    private double rundeKorrektInEuro(double eingabe)
    {
        eingabe = eingabe * 100;
        eingabe = Math.round(eingabe);
        return eingabe / 100;
    }
    
}
