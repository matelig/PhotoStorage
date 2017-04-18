/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author Jakub
 */
public class ToolBar implements ActionListener{
    JToolBar toolBar;
    JButton button;
    
    public JToolBar createToolBar(){
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        button  = new JButton("Add photos");
        button.setActionCommand("Add photos");
        button.addActionListener(this);
        toolBar.add(button);
        
        button  = new JButton("Edit photos");
        button.setActionCommand("Edit photos");
        button.addActionListener(this);
        toolBar.add(button);
        
        button  = new JButton("Delete photos");
        button.setActionCommand("Delete photos");
        button.addActionListener(this);
        toolBar.add(button);
        
        button  = new JButton("Move photos");
        button.setActionCommand("Move photos");
        button.addActionListener(this);
        toolBar.add(button);
        
        button  = new JButton("Add category");
        button.setActionCommand("Add category");
        button.addActionListener(this);
        toolBar.add(button);
        
        button  = new JButton("Search");
        button.setActionCommand("Search");
        button.addActionListener(this);
        toolBar.add(button);
        
        button  = new JButton("Generate report");
        button.setActionCommand("Generate report");
        button.addActionListener(this);
        toolBar.add(button);
        
        button  = new JButton("Log out");
        button.setActionCommand("Log out");
        button.addActionListener(this);
        toolBar.add(button);
        
        return toolBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
