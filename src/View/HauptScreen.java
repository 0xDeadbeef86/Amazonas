/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import DBService.ArtikelHelper;
//import com.sun.xml.internal.ws.api.model.ExceptionType;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Artikel;
import model.ArtikelTableModel;
import model.User;

/**
 *
 * @author lenzch
 */
public class HauptScreen extends javax.swing.JFrame {

    private int currentlySelectedArtikle = -1;
    void setCurrentlySelectedArticle(int id) {
        currentlySelectedArtikle = id;
        System.out.println(currentlySelectedArtikle);
    }
    /**
     * Creates new form HauptScreen
     */
    public HauptScreen() throws SQLException {
        initComponents();
        //TBL_Artikel.
        this.TBL_Artikel.setModel(new ArtikelTableModel());            
        
        btnEdit.setVisible(false);
        btnEdit.setEnabled(false);
        System.out.println("AccessLevel: " + User.GetInstance().getAccessLevel());
        String accessLevel = User.GetInstance().getAccessLevel();
        if(accessLevel.equals("Mitarbeiter") || accessLevel.equals("Adminstrator")) 
            btnEdit.setVisible(true);
        
        //int index = this.TBL_Artikel.getSelectedRow();
        //System.out.println(index);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BT_NeuerArtikel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TBL_Artikel = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        BT_ZumWarenkorb = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BT_NeuerArtikel.setText("neuer Artikel");
        BT_NeuerArtikel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_NeuerArtikelActionPerformed(evt);
            }
        });

        TBL_Artikel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TBL_Artikel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TBL_ArtikelFocusGained(evt);
            }
        });
        TBL_Artikel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TBL_ArtikelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TBL_Artikel);

        btnEdit.setText("Edit");
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        BT_ZumWarenkorb.setText("zum Warenkorb");
        BT_ZumWarenkorb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_ZumWarenkorbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BT_ZumWarenkorb)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEdit)
                        .addGap(18, 18, 18)
                        .addComponent(BT_NeuerArtikel)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BT_ZumWarenkorb)
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BT_NeuerArtikel)
                    .addComponent(btnEdit))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BT_NeuerArtikelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_NeuerArtikelActionPerformed
        try {
            new ArtikelForm().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(HauptScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BT_NeuerArtikelActionPerformed

    private void TBL_ArtikelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TBL_ArtikelFocusGained
        // TODO add your handling code here:
        //int index = this.TBL_Artikel.getSelectedRow();
        //System.out.println(index);
    }//GEN-LAST:event_TBL_ArtikelFocusGained

    private void TBL_ArtikelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TBL_ArtikelMouseClicked
        // TODO add your handling code here:
        int index = this.TBL_Artikel.getSelectedRow();       
        if(index != -1) {
            btnEdit.setEnabled(true);
             int id = (int) this.TBL_Artikel.getModel().getValueAt(index, 0);
             setCurrentlySelectedArticle(id);
        }
        
        if(evt.getClickCount() >= 2) {
            try {                
                int id = (int) this.TBL_Artikel.getModel().getValueAt(index, 0);
                Artikel artikel = ArtikelHelper.getArticle(id);

                new ArtikelDetails(artikel).setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(HauptScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //System.out.println("Doppelklick auf: " + index);
        }
    }//GEN-LAST:event_TBL_ArtikelMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        //System.out.println("TODO: Formular für \"Artikel\" bearbeiten");
        int selectedRowId = this.TBL_Artikel.getSelectedRow();        
        
        int artikelId = -1;
        if(selectedRowId != -1) {
            btnEdit.setEnabled(true);
             artikelId = (int) this.TBL_Artikel.getModel().getValueAt(selectedRowId, 0);
             
            Artikel artikel;
            try {
                artikel = ArtikelHelper.getArticle(artikelId);
                new ArtikelForm(artikel).setVisible(true);// EditArtikelForm(artikel).setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(HauptScreen.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }  
    }//GEN-LAST:event_btnEditActionPerformed

    private void BT_ZumWarenkorbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_ZumWarenkorbActionPerformed
        try {
            new WarenkorbScreen().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(HauptScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BT_ZumWarenkorbActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BT_NeuerArtikel;
    private javax.swing.JButton BT_ZumWarenkorb;
    private javax.swing.JTable TBL_Artikel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
