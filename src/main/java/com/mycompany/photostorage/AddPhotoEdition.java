/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

import com.mycompany.photostorage.model.NewPhoto;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author m_lig
 */
public class AddPhotoEdition extends JPanel{ //TODO FIX SCROLL PANE CONTAINING PHOTOS (IN CONTAINER PANEL)
    
    private JButton insertPhotoButton;
    private List<NewPhoto> newPhoto = new ArrayList<>();
    private JPanel container = new JPanel();
    private JScrollPane scrollPane;
    public AddPhotoEdition(List<NewPhoto> newPhoto) {
        this.newPhoto.addAll(newPhoto);
        
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));       
        initComponents();                 
        this.setLayout(new BorderLayout());
        scrollPane = new JScrollPane(container);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(480,360));
       
        this.add(scrollPane,BorderLayout.CENTER);
        this.add(insertPhotoButton,BorderLayout.SOUTH);      
        setVisible(true);
        scrollPane.revalidate();
    }

    private void initComponents() {
        for (NewPhoto photo : newPhoto) {
            PhotoToEditPanel ptep = new PhotoToEditPanel(photo.getPath());
            container.add(ptep);
        } 
        this.insertPhotoButton = new JButton("Save");
        this.insertPhotoButton.setSize(new Dimension(40,20));
        this.insertPhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                insertPhotoButtonActionPerformed();
            }            
        });
    }
    
    private void insertPhotoButtonActionPerformed() {
        
    }
}
