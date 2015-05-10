/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import DBService.ArtikelHelper;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Artikel;
import model.Warenkorb;

/**
 *
 * @author fixch
 */
public class WarenkorbScreen extends javax.swing.JFrame {

    /**
     * Creates new form WarenkorbScreen
     * @throws java.sql.SQLException
     */
    public WarenkorbScreen() throws SQLException {
        initComponents();
        HashMap<Integer, Integer> alleArtikelImWarenkorb = Warenkorb.GetInstance().getWarenkorbinhalt();
        Object[] alleIDs = alleArtikelImWarenkorb.keySet().toArray();
        System.out.println(alleIDs.length);
        for (Object alleID : alleIDs) {
            Artikel art = ArtikelHelper.getArticle((int) alleID); 
            System.out.println(art);
            int bruttoPreis = (int)(art.getMehrwertsteuer() / 100) * art.getNettopreis();
            PANEL_AlleArtikel.add(new PanelArtikel(art.getName(), bruttoPreis, alleArtikelImWarenkorb.get(alleID)));
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

        LB_Warenkorb = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        PANEL_AlleArtikel = new javax.swing.JPanel();
        LB_Artikelname = new javax.swing.JLabel();
        LB_Anzahl = new javax.swing.JLabel();
        LB_Einzelpreis = new javax.swing.JLabel();
        LB_Gesamt = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        LB_Warenkorb.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        LB_Warenkorb.setText("Warenkorb");

        PANEL_AlleArtikel.setLayout(new javax.swing.BoxLayout(PANEL_AlleArtikel, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(PANEL_AlleArtikel);

        LB_Artikelname.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        LB_Artikelname.setText("Artikelname");

        LB_Anzahl.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        LB_Anzahl.setText("Anzahl");

        LB_Einzelpreis.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        LB_Einzelpreis.setText("Einzelpreis");

        LB_Gesamt.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        LB_Gesamt.setText("Gesamtpreis");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LB_Warenkorb))
                        .addContainerGap(35, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(LB_Artikelname)
                        .addGap(68, 68, 68)
                        .addComponent(LB_Anzahl)
                        .addGap(60, 60, 60)
                        .addComponent(LB_Einzelpreis)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LB_Gesamt)
                        .addGap(53, 53, 53))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LB_Warenkorb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LB_Anzahl, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LB_Gesamt)
                        .addComponent(LB_Artikelname)
                        .addComponent(LB_Einzelpreis)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(WarenkorbScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WarenkorbScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WarenkorbScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WarenkorbScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new WarenkorbScreen().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(WarenkorbScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LB_Anzahl;
    private javax.swing.JLabel LB_Artikelname;
    private javax.swing.JLabel LB_Einzelpreis;
    private javax.swing.JLabel LB_Gesamt;
    private javax.swing.JLabel LB_Warenkorb;
    private javax.swing.JPanel PANEL_AlleArtikel;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
