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
 * @author lenzch
 */
public class ArtikelTableModel extends AbstractTableModel {

    private ArrayList<Artikel> artikelList = new ArrayList<Artikel>();  

    public ArtikelTableModel() throws SQLException {
        artikelList = getAllActiveArticle();        
    }
    
    @Override
    public int getRowCount() {        
        return artikelList.size();
    }

    @Override
    public int getColumnCount() {        
        //TODO: 
        
        return 2;
    }
    

    @Override
    public String getColumnName(int column) {
        if(column == 0)
            return "id";
        if(column == 1)
            return "Name";
        else 
            return "Beschreibung";    
    }
    
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
          if(columnIndex == 0)
            return artikelList.get(rowIndex).getId();
        if(columnIndex == 1)
            return artikelList.get(rowIndex).getName();
        else if(columnIndex == 2)
            return artikelList.get(rowIndex).getBeschreibung();
        else
            return null;
    }
    
    public static ArrayList<Artikel> getAllActiveArticle() throws SQLException {
        String sql;
        sql = "SELECT * FROM \"Artikel\" WHERE aktiv = true;"; 
        MyDatabaseConnection dbVerbindung = new MyDatabaseConnection();
        dbVerbindung.connect();
        ResultSet res = dbVerbindung.executeQuery(sql);
            
        
        ArrayList<Artikel> artikelList = new ArrayList<Artikel>();  
        
         while(res.next()) {          
            Artikel artikel = new Artikel();        

            int id = res.getInt("id");
            String name = res.getString("name");    
            String beschreibung = res.getString("beschreibung");
            boolean aktiv = true;// res.getBoolean("aktiv");
            // set data for Artikel object
            artikel.setId(id);
            artikel.setName(name);
            artikel.setBeschreibung(beschreibung);
            artikel.setAktiv(aktiv);            
            
            artikelList.add(artikel);
         }
        
        return artikelList;
    }
    
}
