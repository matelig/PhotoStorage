/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

import com.mycompany.photostorage.entity.Category;
import com.mycompany.photostorage.entity.Photo;
import com.mycompany.photostorage.entity.Tag;
import com.mycompany.photostorage.entity.User;
import com.mycompany.photostorage.util.HibernateUtil;
import java.awt.BorderLayout;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * JPanel providing interface allowing to edit photo parameters
 * @author m_lig
 */
public class PhotoPanelEdit extends JPanel{

    private JScrollPane scrollPane;
    private JButton editButton;
    private JPanel container = new JPanel();
    private List<SinglePhotoPanel> selectedPhotos = new ArrayList<>();
    private List<Category> categoriesAL = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private JComboBox categoriesComboBox = new JComboBox();
    private JButton applyButton;
    private MainProgramFrame frame;

    /**
     * constructor
     * @param selectedPhotos list of photos to edit
     * @param categoriesAL list of categories
     * @param tags list of all tags used in creating photos
     * @param frame Main frame of the program
     */
    public PhotoPanelEdit(List<SinglePhotoPanel> selectedPhotos, List<Category> categoriesAL, List<String> tags, MainProgramFrame frame) {
        this.frame = frame;
        this.selectedPhotos.addAll(selectedPhotos);
        this.categoriesAL.addAll(categoriesAL);
        this.tags.addAll(tags);
        container.setLayout(new BoxLayout(getContainer(), BoxLayout.Y_AXIS));
        initComponent();
        JLabel label = new JLabel("Choose category for all photos:");
        label.setSize(new Dimension(100,30));
        label.setAlignmentX(LEFT_ALIGNMENT);
        categoriesComboBox.setAlignmentX(LEFT_ALIGNMENT);
        FillCategoriesComboBox();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(getContainer());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(480, 360));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setAlignmentX(LEFT_ALIGNMENT);
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.add(label);
        this.add(Box.createRigidArea(new Dimension(0,5)));
        this.add(categoriesComboBox);
        this.add(Box.createRigidArea(new Dimension(0,5)));
        this.add(applyButton);
        this.add(Box.createRigidArea(new Dimension(0,5)));
        this.add(scrollPane);
        this.add(Box.createRigidArea(new Dimension(0,5)));
        this.add(editButton);
        setVisible(true);
        scrollPane.revalidate();
    }

    /**
     * Fills combo box with all categories
     */
    public void FillCategoriesComboBox() {
        categoriesComboBox.addItem("None");
        for (Category cat : categoriesAL) {
            categoriesComboBox.addItem(cat.getName());
        }
    }
    
    /**
     * initialise JPanel components
     */
    private void initComponent() {
        for (SinglePhotoPanel panel : selectedPhotos) {
            PhotoToEditPanel ptep = new PhotoToEditPanel(panel.getPhotoID(), categoriesAL, tags, this);
            ptep.getTagPanel().addPosibility(tags);
            getContainer().add(ptep);
        }
        this.editButton = new JButton("Edit");
        this.editButton.setSize(new Dimension(40, 20));
        this.editButton.setAlignmentX(LEFT_ALIGNMENT);
        this.editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                onEditButtonClick();
            }
        });
        this.applyButton = new JButton("Apply");
        this.applyButton.setPreferredSize(new Dimension(100, 25));
        this.applyButton.setAlignmentX(LEFT_ALIGNMENT);
        this.applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                changeAllCategories();
            }
        });
    }
    
    /**
     * Changes categories in all PhotoToEditPanels
     */
    private void changeAllCategories() {
        Component[] comps = container.getComponents();
        for(int i = 0; i < comps.length; i++) {
            PhotoToEditPanel panel = (PhotoToEditPanel)comps[i];
            panel.getCategoryComboBox().setSelectedItem(categoriesComboBox.getSelectedItem());
        }
    }

    /**
     * Event fired, when "Edit" button is clicked. Modyfies Values of selected photos in Database.
     */
    private void onEditButtonClick() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        for (int i = 0; i < selectedPhotos.size(); i++) {
            PhotoToEditPanel ptep = (PhotoToEditPanel) getContainer().getComponent(i);
            Query queryPhoto = session.createQuery("from Photo where idp=" + ptep.getPhotoID());
            Photo dbPhoto = (Photo) queryPhoto.list().get(0);
            if (!ptep.getCategory().equalsIgnoreCase("none")) {
                for (Category c : categoriesAL) {
                    if (c.getName().equals(ptep.getCategory())) {
                        dbPhoto.setCategory(c);
                        break;
                    }
                }
            } else {
                dbPhoto.setCategory(null);
            }
            dbPhoto.setDescription(ptep.getDescription());
            Set<Tag> tagsFromDB = dbPhoto.getTags();
            List<Tag> oldTags = new ArrayList<>();
            for (Tag t : tagsFromDB) {
                oldTags.add(t);
            }
            List<String> newTags = ptep.getTags();
            for (int k = oldTags.size()-1;k>=0;k--) {
                for (int j = newTags.size()-1;j>=0;j--) {
                    if (oldTags.get(k).getValue().equals(newTags.get(j))) {
                        oldTags.remove(k);
                        newTags.remove(j);
                    }
                }
            }
            for (Tag tag : oldTags) {
                session.delete(tag);
            }
            session.update(dbPhoto);
            for (String s : newTags) {
                Tag tag = new Tag (dbPhoto,s);
                session.save(tag);
            }            
            
        }
        session.getTransaction().commit();
        session.close();
        JOptionPane.showMessageDialog(this,
                        "Photos have been edited.",
                        "Information",
                        JOptionPane.INFORMATION_MESSAGE);       
        frame.setPanel(new PhotoViewPanel(frame,frame.getCurrentUser()));
    }

    /**
     * @return the container
     */
    public JPanel getContainer() {
        return container;
    }

    /**
     * @param container the container to set
     */
    public void setContainer(JPanel container) {
        this.container = container;
    }
}
