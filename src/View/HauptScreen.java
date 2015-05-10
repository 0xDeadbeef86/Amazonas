/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import DBService.ArtikelHelper;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Artikel;
import model.ArtikelTableModel;
import model.User;

/**
 *
 * @author fixch
 */
public class HauptScreen extends javax.swing.JFrame {

    private int currentlySelectedArtikle = -1;

    void setCurrentlySelectedArticle(int id) {
        currentlySelectedArtikle = id;
        System.out.println(currentlySelectedArtikle);
    }
    
    public HauptScreen() throws SQLException {
        initComponents();
        
        this.TBL_Artikel.setModel(new ArtikelTableModel());

        BT_NeuerArtikel.setVisible(false);
        BT_NeuerArtikel.setEnabled(false);
        System.out.println("AccessLevel: " + User.GetInstance().getAccessLevel());
        String accessLevel = User.GetInstance().getAccessLevel();
        if (accessLevel.equals("Mitarbeiter") || accessLevel.equals("Adminstrator")) {
            BT_NeuerArtikel.setVisible(true);
        }
    }

   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TBL_Artikel = new javax.swing.JTable();
        BT_NeuerArtikel = new javax.swing.JButton();
        BT_ArtikelBearbeiten = new javax.swing.JButton();
        BT_ZumWarenkorb = new javax.swing.JButton();
        jDesktopPane1 = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        jScrollPane1.setViewportView(TBL_Artikel);

        BT_NeuerArtikel.setText("neuer Artikel");

        BT_ArtikelBearbeiten.setText("Bearbeiten");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BT_ZumWarenkorb)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BT_ArtikelBearbeiten)
                                .addGap(22, 22, 22)
                                .addComponent(BT_NeuerArtikel))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(BT_ZumWarenkorb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BT_NeuerArtikel)
                    .addComponent(BT_ArtikelBearbeiten))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BT_ZumWarenkorbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_ZumWarenkorbActionPerformed
       
    }//GEN-LAST:event_BT_ZumWarenkorbActionPerformed

    private void BT_NeuerArtikelActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            new ArtikelForm().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(HauptScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    private void TBL_ArtikelFocusGained(java.awt.event.FocusEvent evt) {
        // TODO add your handling code here:
        //int index = this.TBL_Artikel.getSelectedRow();
        //System.out.println(index);
    }

    private void ArtikelAnzeigen(int clickcount) {
        int index = this.TBL_Artikel.getSelectedRow();
        if (index != -1) {
            BT_NeuerArtikel.setEnabled(true);
            int id = (int) this.TBL_Artikel.getModel().getValueAt(index, 0);
            setCurrentlySelectedArticle(id);
        }

        if (clickcount >= 2) {
            try {
                int id = (int) this.TBL_Artikel.getModel().getValueAt(index, 0);
                Artikel artikel = ArtikelHelper.getArticle(id);

                new ArtikelDetails(artikel).setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(HauptScreen.class.getName()).log(Level.SEVERE, null, ex);
            }

            //System.out.println("Doppelklick auf: " + index);
        }
    }

    private void TBL_ArtikelMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        ArtikelAnzeigen(evt.getClickCount());
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        System.out.println("TODO: Formular für \"Artikel\" bearbeiten");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HauptScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HauptScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HauptScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HauptScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new HauptScreen().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(HauptScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BT_ArtikelBearbeiten;
    private javax.swing.JButton BT_NeuerArtikel;
    private javax.swing.JButton BT_ZumWarenkorb;
    private javax.swing.JTable TBL_Artikel;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
