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

    /**
     * Program frame
     */
    private JFrame frame;
    /**
     * Currently logged user
     */
    private CurrentUser currentUser;


    /**
     * Creates frame and assign default values
     */
    public void CreateAndShowGUI() {
        setFrame(new JFrame("PhotoStorage"));
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPanel(new SignIn(this));
        getFrame().pack();
        getFrame().setResizable(false);
        getFrame().setVisible(true);
        getFrame().setLocationRelativeTo(null);
    }

    /**
     * Sets displayed JPanel
     * @param panel JPanel to be displayed
     */
    public void setPanel(JPanel panel) {
        getFrame().getContentPane().removeAll();
        if(!(panel instanceof SignIn)&& !(panel instanceof SignUp)){
            getFrame().add(new ToolBar().createToolBar(this), BorderLayout.PAGE_START);
        }
        getFrame().getContentPane().add(panel);
        getFrame().pack();
        getFrame().revalidate();
    }
  
    /**
     * @param cu current user
     */
    public void setCurrentUser(CurrentUser cu) {
        this.currentUser = cu;
    }
  
    /**
     * @return current user
     */
    public CurrentUser getCurrentUser() {
        return this.currentUser;
    }
    
    /**
     * Set frame position to center of screen
     */
    public void setRelative() {
        getFrame().setLocationRelativeTo(null);
    }

    /**
     * @return the frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * @param frame the frame to set
     */
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
