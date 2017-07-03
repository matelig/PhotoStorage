/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;


import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * JPanel managing tags
 * @author Jakub
 */
public class TagPanel extends JPanel {
    /**
     * Auto completing field that gives suggestions based on existing tags
     */
    AutoCompleteTextField tagTextField;
    /**
     * Tag Panel
     */
    JPanel tagPanel;
    
    public TagPanel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        tagPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tagPanel.setPreferredSize(new Dimension(303, 40));
        tagTextField = new AutoCompleteTextField(this);
        this.add(tagTextField);
        this.add(tagPanel);;
    }
    
    /**
     * Creates and adds TagComponent to tagPanel field
     * @param tagText created tag text
     * @param mainPanel container having this instance on TagPanel
     */
    public void addTagComponent(String tagText, Container mainPanel){        
        TagComponent tagComponent = new TagComponent(tagText, tagPanel, mainPanel);
        tagPanel.add(tagComponent);        
        tagTextField.setText("");
        tagPanel.repaint();
        tagPanel.revalidate();
        if(mainPanel instanceof PhotoViewPanel){
            PhotoViewPanel viewPanel = (PhotoViewPanel) mainPanel;
            viewPanel.updateMainView();
        }else if(mainPanel instanceof PhotoToEditPanel) {
                ((PhotoToEditPanel)mainPanel).updatePanel();
        }else if(mainPanel instanceof JPanel) {
            Component[] pteps = mainPanel.getComponents();
            for(int i = 0; i < pteps.length; i++) {
                ((PhotoToEditPanel)pteps[i]).updatePanel();
            }
        }
    }
    
    /**
     * returns list of currently picked tags
     * @return list of current tags
     */
    public List<String> takeTags() {
        List<String> tagList = new ArrayList<>();
        for (int i = 0; i< tagPanel.getComponentCount();i++) {
            TagComponent tagComponent = (TagComponent) tagPanel.getComponent(i);
            tagList.add(tagComponent.getTag());
        }
        return tagList;
    }
    
    /**
     * adds new possibilities of autocompletion
     * @param tagName list of new words
     */
    public void addPosibility(List<String> tagName) {
        tagTextField.addAllPossibilities(tagName);
    }  
    
    /**
     * sets list of autocompletion to new set of words
     * @param tagName list of new words
     */
    public void setPossibility(Set<String> tagName) {
        tagTextField.setAllPossibilities(tagName);
    }
}
