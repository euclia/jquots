/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.euclia.jquots.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author pantelispanka
 */
public class QuotsUser extends Quots {
    
    private String id;
    private String email;
    private String username;
    private float credits;
    private ArrayList<Spenton> spenton;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getCredits() {
        return credits;
    }

    public void setCredits(float credits) {
        this.credits = credits;
    }

    public ArrayList<Spenton> getSpenton() {
        return spenton;
    }

    public void setSpenton(ArrayList<Spenton> spenton) {
        this.spenton = spenton;
    }
    
    
    
    
    
}
