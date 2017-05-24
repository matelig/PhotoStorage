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
    MainProgramFrame frame;
    
    public JToolBar createToolBar(MainProgramFrame parentFrame){
        frame = parentFrame;
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        button  = new JButton("Add photos");
        button.setActionCommand("Add photos");
        button.addActionListener(this);
        toolBar.add(button);        
          
        button  = new JButton("Add category");
        button.setActionCommand("Add category");
        button.addActionListener(this);
        toolBar.add(button);
        
        button  = new JButton("Add device");
        button.setActionCommand("Add device");
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
        switch(e.getActionCommand()){
            //nie ma buttonów/opcji dla tych panelów
            
            //archivePhotos
            //changeCategory
            //closeDevice
            //deleteDevice
            
            case "Add photos":
                frame.setPanel(new AddPhotoPanel(frame,frame.getCurrentUser()));
                break;
            case "Add device":
                frame.setPanel(new AddDevicePanel());
                break;            
            case "Add category":
                frame.setPanel(new AddCategoryPanel(frame.getCurrentUser()));
                break;
            case "Search":
                frame.setPanel(new PhotoViewPanel(frame,frame.getCurrentUser()));
                break;
            case "Generate report":
                frame.setPanel(new GenerateReportPanel());
                break;
            case "Log out":
                frame.hideMenu();
                frame.setPanel(new SignIn(frame));
                break;
            default:
                break;
                
        }
    }
}
