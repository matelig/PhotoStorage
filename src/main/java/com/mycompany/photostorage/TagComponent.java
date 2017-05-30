/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;



/**
 *
 * @author Jakub
 */
public class TagComponent extends JPanel {
    private JPanel parentPanel;
    private String tagString;
    private JPanel mainPanel;

    public TagComponent(String text, JPanel parent, JPanel mPanel) {
        parentPanel= parent;
        tagString = text;
        mainPanel = mPanel;
        JLabel textlable = new JLabel(text);
        JLabel close = new JLabel("X");
        close.setOpaque(true);
        close.setBackground(new Color(123, 123, 123));
        textlable.setOpaque(true);
        textlable.setBackground(Color.white);
        textlable.setForeground(Color.black);
        setLayout(new BorderLayout());
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                parentPanel.remove(TagComponent.this);
                if(mainPanel instanceof PhotoViewPanel){
                    //tu wywołujesz wyszukiwanie
                }
                parentPanel.repaint();
                parentPanel.revalidate();
            }
        });
        add(close, BorderLayout.EAST);
        add(textlable, BorderLayout.WEST);
    }
    
    public String getTag() {
        return this.tagString;
    }

}
