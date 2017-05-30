/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Jakub
 */
public class TagPanel extends JPanel{
    AutoCompleteTextField tagTextField;
    JPanel tagPanel;
    public TagPanel(){
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
    
    public List<String> takeTags() {
        List<String> tagList = new ArrayList<>();
        for (int i = 0; i< tagPanel.getComponentCount();i++) {
            TagComponent tagComponent = (TagComponent) tagPanel.getComponent(i);
            tagList.add(tagComponent.getTag());
        }
        return tagList;
    }
    
    public void addPosibility(List<String> tagName) {
        tagTextField.addAllPossibilities(tagName);
    }
}
