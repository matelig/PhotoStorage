/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage.model;

/**
 * Class storing information about current user
 * @author m_lig
 */
public class CurrentUser {
    private int userID;
    private String userName;
    
    /**
     * Constructor setting all fields
     * @param userID current user's id
     * @param userName current user's username
     */
    public CurrentUser(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }
  
    /**
     * Basic constructor
     */
    public CurrentUser() {
        
    }
    
    public int getUserID() {
        return this.userID;
    }
    
    public String getUserName() {
        return this.userName;
    }
}
