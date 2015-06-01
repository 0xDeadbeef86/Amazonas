/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Christopher
 */
public class Benutzer {
    private int id;
    private String name;
    private String passwort;
    private String berechtigung;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPasswort() {
        return passwort;
    }
    
    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }    
    
    public String getBerechtigung() {
        return berechtigung;
    }
    
    public void setBerechtigung(String berechtigung) {
        this.berechtigung = berechtigung;
    }
}
