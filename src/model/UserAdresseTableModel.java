/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import DBService.UserAdresseHelper;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Christopher
 */
public class UserAdresseTableModel extends AbstractTableModel {

    private ArrayList<UserAdresse> adressList = new ArrayList<>();
    
    public UserAdresseTableModel() throws SQLException {
        this.adressList = UserAdresseHelper.getAlleUserAdresse(User.GetInstance().getId());  
    }
    
    @Override
    public int getRowCount() {
        return adressList.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }
    
    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "Id";
        } else if (column == 1) {
            return "UserId";
        } else if (column == 2) {
            return "AdresseId";
        } else if (column == 3) {
            return "User";
        } else if (column == 4) {
            return "Vorname";
        } else if (column == 5) {
            return "Nachname";        
        } else if (column == 6) {
            return "Adresse";
        } else {
            return "";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return adressList.get(rowIndex).getId();
        } else if (columnIndex == 1) {
            return (adressList.get(rowIndex).getUserid()); 
        } else if (columnIndex == 2) {
            return (adressList.get(rowIndex).getAdresseid()); 
        } else if (columnIndex == 3) {
            return (adressList.get(rowIndex).getUser()); 
        }else if (columnIndex == 4) {
            return (adressList.get(rowIndex).getVorname()); 
        } else if (columnIndex == 5) {
            return (adressList.get(rowIndex).getNachname()); 
        } else if (columnIndex == 6) {
            return (adressList.get(rowIndex).getAnschrift()); 
        } else {
            return "fehlerhafter Übergabeparameter";
        }
    }    
}