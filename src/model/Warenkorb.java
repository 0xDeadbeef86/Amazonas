package model;

import java.util.HashMap;

/**
 *
 * @author fixch
 */
public class Warenkorb {
   
    
    HashMap<Integer, Integer> artikelID_Anzahl = new HashMap<>(); //Key: ArtikelID, Value: Anzahl
    
    private static final Warenkorb warenkorbObjekt = new Warenkorb();

    private Warenkorb() {
          
    }   
    
    public static Warenkorb GetInstance()
    {
        return warenkorbObjekt;
    }
    
    
    public void addArtikel(int id, int anzahl)
    {
        artikelID_Anzahl.put(id, anzahl);
        
    }
    
    public void removeArtikel(int id, int anzahl)
    {
        //ToDo: testen
        artikelID_Anzahl.replace(id, anzahl);
        if(artikelID_Anzahl.get(id) <= 0)
        {
            artikelID_Anzahl.remove(id);
        }
    }
    
    public void bestellen()
    {
        //ToDo: implementieren
    }
    
}
