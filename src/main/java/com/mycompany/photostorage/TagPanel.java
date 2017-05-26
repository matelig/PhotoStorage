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
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Jakub
 */
public class TagPanel extends JPanel{
    JTextField tagTextField;
    JPanel tagPanel;
    public TagPanel(){
        //this.setPreferredSize(new Dimension(400, 20));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        tagPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tagTextField = new JTextField("Type here", 15);
        tagTextField.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent evt) {
            }

            @Override
            public void keyReleased(KeyEvent evt) {
                if(evt.getKeyCode() == KeyEvent.VK_SPACE && evt.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    addTagComponent();
                } 
            }

            @Override
            public void keyPressed(KeyEvent arg) {
                
            }
        });
        this.add(tagTextField);
        this.add(tagPanel);
        //tagTextField.setAlignmentX(RIGHT_ALIGNMENT);
        //tagPanel.setAlignmentX(RIGHT_ALIGNMENT);
    }
    
    public void addTagComponent(){
        String tagText = tagTextField.getText();
        TagComponent tagComponent = new TagComponent(tagText, tagPanel);
        tagPanel.add(tagComponent);
        tagTextField.setText("");
        tagPanel.repaint();
        tagPanel.revalidate();
    }
}
