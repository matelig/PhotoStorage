/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 *
 * @author Jakub
 */
public class MainProgramFrame {

    private JFrame frame;

    public void displayMenu() {
        JMenuBar menuBar = new MainMenu().createMainMenu();
        frame.setJMenuBar(menuBar);
    }

    public void CreateAndShowGUI() {
        frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPanel(new SignIn(this));
        frame.pack();
        frame.setVisible(true);
    }

    public void setPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        if(!(panel instanceof SignIn)&& !(panel instanceof SignUp)){
            frame.add(new ToolBar().createToolBar(this), BorderLayout.PAGE_START);
        }
        frame.getContentPane().add(panel);
        frame.pack();
        frame.revalidate();
    }

    public void hideMenu() {
        frame.setJMenuBar(null);
    }
}