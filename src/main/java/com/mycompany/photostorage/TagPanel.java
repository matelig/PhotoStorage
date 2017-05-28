/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

/**
 *
 * @author Jakub
 */
public class TagPanel extends JPanel{
    AutoCompleteTextField tagTextField;
    JPanel tagPanel;
    public TagPanel(){
        //this.setPreferredSize(new Dimension(400, 20));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        tagPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tagPanel.setPreferredSize(new Dimension(303, 40));
        tagTextField = new AutoCompleteTextField(this);
        tagTextField.addPossibility("AlaMaKota");
        this.add(tagTextField);
        this.add(tagPanel);
    }
    
    public void addTagComponent(String tagText){
        //String tagText = tagTextField.getText();
        TagComponent tagComponent = new TagComponent(tagText, tagPanel);
        tagPanel.add(tagComponent);
        tagTextField.setText("");
        tagPanel.repaint();
        tagPanel.revalidate();
    }
}
