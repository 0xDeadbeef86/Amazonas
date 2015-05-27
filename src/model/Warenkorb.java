package model;

import DBService.ArtikelHelper;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author fixch
 */
public class Warenkorb {

    private final HashMap<Integer, Integer> warenkorbInhalt = new HashMap<>(); //Key: ArtikelID, Value: Anzahl

    private static final Warenkorb warenkorbObjekt = new Warenkorb();

    private Warenkorb() {

    }

    public static Warenkorb GetInstance() {
        return warenkorbObjekt;
    }

    public void addArtikel(int id, int anzahl) {
        int aktuelleAnzahl = 0;
        try {
            aktuelleAnzahl = warenkorbInhalt.get(id);
        } catch (Exception ex) {

        }
        warenkorbInhalt.put(id, anzahl + aktuelleAnzahl);
    }

    public void removeArtikel(int id) {
        //ToDo: testen
        warenkorbInhalt.remove(id);
        if (warenkorbInhalt.get(id) <= 0) {
            warenkorbInhalt.remove(id);
        }
    }

    public void bestellen() {
        //ToDo: implementieren
    }

    public double getGesamtbruttopreisAllerArtikel() throws SQLException {
        Object[] alleIDs = this.warenkorbInhalt.keySet().toArray();
        double gesamtpreisAllerArtikel = 0.0;
        for (int i = 0; i < this.warenkorbInhalt.size(); i++) {
            Artikel artikel = ArtikelHelper.getArticle((int) alleIDs[i]);
            gesamtpreisAllerArtikel += artikel.getBruttopreis() * this.warenkorbInhalt.get(alleIDs[i]);
        }
        return rundeKorrektInEuro(gesamtpreisAllerArtikel);
    }
    
    //behebt Probleme mit Fließkommazahlen
    private double rundeKorrektInEuro(double eingabe) {
        eingabe = eingabe * 100;
        eingabe = Math.round(eingabe);
        return eingabe / 100;
    }

    public HashMap<Integer, Integer> getWarenkorbinhalt() {
        return this.warenkorbInhalt;
    }

}
