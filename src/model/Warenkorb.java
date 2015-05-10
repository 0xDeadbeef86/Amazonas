package model;

import java.util.HashMap;

/**
 *
 * @author fixch
 */
public class Warenkorb {
   
    private HashMap<Integer, Integer> warenkorbInhalt = new HashMap<>(); //Key: ArtikelID, Value: Anzahl
    
    private static final Warenkorb warenkorbObjekt = new Warenkorb();

    private Warenkorb() {
          
    }   
    
    public static Warenkorb GetInstance()
    {
        return warenkorbObjekt;
    }
    
    
    public void addArtikel(int id, int anzahl)
    {
        warenkorbInhalt.put(id, anzahl);
        
    }
    
    public void removeArtikel(int id)
    {
        //ToDo: testen
        warenkorbInhalt.remove(id);
        if(warenkorbInhalt.get(id) <= 0)
        {
            warenkorbInhalt.remove(id);
        }
    }
    
    public void bestellen()
    {
        //ToDo: implementieren
    }
    
    public HashMap<Integer, Integer> getWarenkorbinhalt()
    {
        return this.warenkorbInhalt;
    }
    
}
