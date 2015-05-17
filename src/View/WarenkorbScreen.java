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
import javax.swing.JFrame;
import model.Artikel;
import model.Warenkorb;
import model.WarenkorbTableModel;

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
        //test
        this.TBL_Warenkorb.setModel(new WarenkorbTableModel(alleArtikelImWarenkorb));
        
        //test
        
        

        Object[] alleIDs = alleArtikelImWarenkorb.keySet().toArray();
        for (Object artID : alleIDs) {
            Artikel art = ArtikelHelper.getArticle((int) artID); 
            int bruttoPreisInCent = (int)((art.getMehrwertsteuer() * art.getNettopreis()) / 100);
            //this.PANEL_AlleArtikel.add(new PanelArtikel(art.getName(), bruttoPreisInCent, alleArtikelImWarenkorb.get((int) artID)));//ändern in JTable mit Spinner
        }
        //this.LB_SUM_Gesamtpreis.setText(1+"");
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
        LB_Gesamtpreis = new javax.swing.JLabel();
        PANEL_Gesamt = new javax.swing.JPanel();
        LB_SUM_Gesamtpreis = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TBL_Warenkorb = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        LB_Warenkorb.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        LB_Warenkorb.setText("Warenkorb");

        PANEL_AlleArtikel.setLayout(new javax.swing.BoxLayout(PANEL_AlleArtikel, javax.swing.BoxLayout.LINE_AXIS));
        jScrollPane2.setViewportView(PANEL_AlleArtikel);

        LB_Artikelname.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        LB_Artikelname.setText("Artikelname");

        LB_Anzahl.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        LB_Anzahl.setText("Anzahl");

        LB_Einzelpreis.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        LB_Einzelpreis.setText("Einzelpreis");

        LB_Gesamtpreis.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        LB_Gesamtpreis.setText("Gesamtpreis");

        javax.swing.GroupLayout PANEL_GesamtLayout = new javax.swing.GroupLayout(PANEL_Gesamt);
        PANEL_Gesamt.setLayout(PANEL_GesamtLayout);
        PANEL_GesamtLayout.setHorizontalGroup(
            PANEL_GesamtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PANEL_GesamtLayout.createSequentialGroup()
                .addContainerGap(515, Short.MAX_VALUE)
                .addComponent(LB_SUM_Gesamtpreis, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PANEL_GesamtLayout.setVerticalGroup(
            PANEL_GesamtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PANEL_GesamtLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(LB_SUM_Gesamtpreis, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        TBL_Warenkorb.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(TBL_Warenkorb);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 845, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PANEL_Gesamt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LB_Warenkorb))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(LB_Artikelname)
                        .addGap(68, 68, 68)
                        .addComponent(LB_Anzahl)
                        .addGap(60, 60, 60)
                        .addComponent(LB_Einzelpreis)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LB_Gesamtpreis)
                        .addGap(53, 53, 53))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LB_Warenkorb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LB_Anzahl, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LB_Gesamtpreis)
                        .addComponent(LB_Artikelname)
                        .addComponent(LB_Einzelpreis)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(274, 274, 274)
                        .addComponent(PANEL_Gesamt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(217, 217, 217))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LB_Anzahl;
    private javax.swing.JLabel LB_Artikelname;
    private javax.swing.JLabel LB_Einzelpreis;
    private javax.swing.JLabel LB_Gesamtpreis;
    private javax.swing.JLabel LB_SUM_Gesamtpreis;
    private javax.swing.JLabel LB_Warenkorb;
    private javax.swing.JPanel PANEL_AlleArtikel;
    private javax.swing.JPanel PANEL_Gesamt;
    private javax.swing.JTable TBL_Warenkorb;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
