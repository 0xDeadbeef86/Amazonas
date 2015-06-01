/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import DBService.ArtikelHelper;
import DBService.BenutzerHelper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Christopher
 */
public class BenutzerTableModel extends AbstractTableModel {    
    
    private ArrayList<Benutzer> userList = new ArrayList<>();
    
    public BenutzerTableModel() throws SQLException {
        this.userList = BenutzerHelper.getAlleUser();        
    }

    
     @Override
    public int getRowCount() {
        return userList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "Name";
        } else if (column == 1) {
            return "Passwort";
        } else if (column == 2) {
            return "Berechtigung";
        } else if (column == 3) {
            return "Id";
        } else {
            return "";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return userList.get(rowIndex).getName();
        } else if (columnIndex == 1) {
            return (userList.get(rowIndex).getPasswort()); 
        } else if (columnIndex == 2) {
            return (userList.get(rowIndex).getBerechtigung()); 
        } else if (columnIndex == 3) {
            return (userList.get(rowIndex).getId()); 
        } else {
            return "fehlerhafter Übergabeparameter";
        }
    }    
    
    
    @Override
    public boolean isCellEditable(int row, int col) {
        if(col < 3)
            return true;
        else
            return false;
    }
    
    public void setValueAt(Object value, int row, int col) {
        if (col == 0) {
            userList.get(row).setName((String)value);
        } else if (col == 1) {
            userList.get(row).setPasswort((String)value); 
        } else if (col == 2) {
            userList.get(row).setBerechtigung((String)value); 
        }
        
        try {
            //TODO: Update Benutzer
            int index = userList.get(3).getId();// ((Benutzer)value).getId();
            BenutzerHelper.updateBenutzer(index, userList.get(row).getName(), userList.get(row).getPasswort(), userList.get(row).getBerechtigung());
            //BenutzerHelper.insertBenutzer(userList.get(row).getName(), userList.get(row).getPasswort(), userList.get(row).getBerechtigung());
        } catch (SQLException ex) {
            Logger.getLogger(BenutzerTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
