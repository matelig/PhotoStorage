/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

import com.mycompany.photostorage.model.CurrentUser;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * JFrame containing all used swing components
 * @author Jakub
 */
public class MainProgramFrame {

    private JFrame frame;
    private CurrentUser currentUser;


    /**
     * Creates frame and assign default values
     */
    public void CreateAndShowGUI() {
        frame = new JFrame("PhotoStorage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPanel(new SignIn(this));
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Sets displayed JPanel
     * @param panel JPanel to be displayed
     */
    public void setPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        if(!(panel instanceof SignIn)&& !(panel instanceof SignUp)){
            frame.add(new ToolBar().createToolBar(this), BorderLayout.PAGE_START);
        }
        frame.getContentPane().add(panel);
        frame.pack();
        frame.revalidate();
    }
  
    public void setCurrentUser(CurrentUser cu) {
        this.currentUser = cu;
    }
  
    public CurrentUser getCurrentUser() {
        return this.currentUser;
    }
    
    public void setRelative() {
        frame.setLocationRelativeTo(null);
    }
}
