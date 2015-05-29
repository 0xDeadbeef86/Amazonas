/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author lenzch
 */
public class User {

    private String name;
    private int id;
    private String titel;
    private int title_id;

    public void setTitle_id(int title_id) {
        this.title_id = title_id;
    }

    public int getTitle_id() {
        return title_id;
    }

    private static final User userObjekt = new User();

    private User() {

    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String accessLevel) {
        this.titel = accessLevel;
    }

    // Getter
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getAccessLevel() {
        return titel;
    }

    public static User GetInstance() {
        return userObjekt;
    }

}
