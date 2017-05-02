/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

import com.mycompany.photostorage.model.NewPhoto;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author m_lig
 */
public class AddPhotoEdition extends JPanel{ //TODO FIX SCROLL PANE CONTAINING PHOTOS (IN CONTAINER PANEL)
    private List<NewPhoto> newPhoto = new ArrayList<>();
    private JPanel container = new JPanel();
    private JScrollPane scrollPane;
    public AddPhotoEdition(List<NewPhoto> newPhoto) {
        this.newPhoto.addAll(newPhoto);
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));       
        initComponents();        
            
        
        scrollPane = new JScrollPane(this);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         scrollPane.setPreferredSize(new Dimension(460,320));  
        this.add(container,BorderLayout.CENTER);
        setVisible(true);
        scrollPane.revalidate();
    }

    private void initComponents() {
        for (NewPhoto photo : newPhoto) {
            PhotoToEditPanel ptep = new PhotoToEditPanel(photo.getPath());
            container.add(ptep);
        }        
    }
}
