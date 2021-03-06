/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

import com.mycompany.photostorage.entity.Category;
import com.mycompany.photostorage.entity.Photo;
import com.mycompany.photostorage.entity.Tag;
import com.mycompany.photostorage.util.HibernateUtil;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * JPanel providing interface allowing to edit photo information
 *
 * @author alachman
 */
public class PhotoToEditPanel extends javax.swing.JPanel {

    /**
     * BufferedImage used to creation of miniature
     */
    private BufferedImage image;
    /**
     * List of categories
     */
    private List<Category> categories = new ArrayList<>();
    /**
     * ID of photo in database
     */
    private int photoID;
    /**
     * List of tags
     */
    private List<String> allTags = new ArrayList<>();
    /**
     * Parent panel to PhotoToEditPanel, to access TagsPanels
     */
    private JPanel parentPanel;

    /**
     * Constructor for object assigned to photo given by file path
     * @param filePath path to photo
     * @param categories list of user's categories
     * @param allTags list of user's tags
     * @param parentPanel JPanel containing object
     */
    public PhotoToEditPanel(String filePath, List<Category> categories, Set<String> allTags, JPanel parentPanel) {
        this.categories = categories;
        this.allTags.addAll(allTags);
        this.parentPanel = parentPanel;
        initComponents();
        txtFieldDescription.setText("Default");
        setComboBoxCategories();
        setImageMiniature(filePath);
        setVisible(true);
    }

    /**
     * Constructor for object assigned to specific photo
     * @param photoID photo id in DB
     * @param categories list of user's categories
     * @param allTags list of user's tags
     * @param parentPanel JPanel containing object
     */
    public PhotoToEditPanel(int photoID, List<Category> categories, List<String> allTags, JPanel parentPanel) {
        this.categories.addAll(categories);
        this.photoID = photoID;
        this.allTags = allTags;
        this.parentPanel = parentPanel;
        initComponents();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Photo p where p.idp = " + photoID);
        Photo dbPhoto = (Photo) query.list().get(0);
        txtFieldDescription.setText(dbPhoto.getDescription());
        Set<Tag> tags = dbPhoto.getTags();
        for (Tag tag : tags) {
            tagPanel1.addTagComponent(tag.getValue(), this);
        }
        setComboBoxCategories();
        if (dbPhoto.getCategory() != null) {
            String categoryName = dbPhoto.getCategory().getName();

            for (int i = 0; i < categoryComboBox.getItemCount(); i++) {
                if (this.categoryComboBox.getItemAt(i).equals(categoryName)) {
                    categoryComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
        setImageMiniature(dbPhoto.getMiniature());
        session.getTransaction().commit();
        session.close();
        setVisible(true);
    }

    /**
     * sets photo's miniature from byte array
     * @param file byte informatioin about photo
     */
    private void setImageMiniature(byte[] file) {
        try {
            InputStream in = new ByteArrayInputStream(file);
            image = ImageIO.read(in);
            ImageIcon icon = new ImageIcon();
            icon.setImage(image);
            Image image = icon.getImage();
            image = image.getScaledInstance(114, 104, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            labelPhoto.setIcon(icon);
            this.image = null;
        } catch (IOException ex) {
            System.out.println("sdgsdsss");
        }
    }

    /**
     * sets photo's miniature from file
     * @param filePath path to photo
     */
    private void setImageMiniature(String filePath) {
        try {
            image = ImageIO.read(new File(filePath));
            ImageIcon icon = new ImageIcon();
            icon.setImage(image);
            Image image = icon.getImage();
            image = image.getScaledInstance(114, 104, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            labelPhoto.setIcon(icon);
            this.image = null;
        } catch (IOException ex) {
        }
    }

    /**
     * 
     * @return description of Photo
     */
    public String getDescription() {
        return txtFieldDescription.getText();
    }

    /**
     * 
     * @return String representation of photo category
     */
    public String getCategory() {
        return getCategoryComboBox().getSelectedItem().toString();
    }

    /**
     * 
     * @return List of string representation of photo tags
     */
    public List<String> getTags() {
        return getTagPanel().takeTags();
    }

    /**
     * 
     * @return photo ID
     */
    public int getPhotoID() {
        return this.photoID;
    }

    /**
     * 
     * @return Scalled munature of the photo
     */
    public Icon getMiniature() {
        return labelPhoto.getIcon();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelPhoto = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtFieldDescription = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        categoryComboBox = new javax.swing.JComboBox<>();
        tagPanel1 = new com.mycompany.photostorage.TagPanel();

        jLabel1.setText("Description");

        txtFieldDescription.setText("Old description");

        jLabel2.setText("Categories");

        jLabel3.setText("Tags");

        categoryComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(labelPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFieldDescription)
                    .addComponent(categoryComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tagPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(tagPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(labelPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void categoryComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categoryComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> categoryComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel labelPhoto;
    private com.mycompany.photostorage.TagPanel tagPanel1;
    private javax.swing.JTextField txtFieldDescription;
    // End of variables declaration//GEN-END:variables

    /**
     * Add all categories to comboBox
     */
    private void setComboBoxCategories() {
        getCategoryComboBox().addItem("None");
        for (Category c : categories) {
            getCategoryComboBox().addItem(c.getName());
        }
    }

    /**
     * @return the tagPanel1
     */
    public com.mycompany.photostorage.TagPanel getTagPanel() {
        return tagPanel1;
    }

    /**
     * @param tagPanel1 the tagPanel1 to set
     */
    public void setTagPanel(com.mycompany.photostorage.TagPanel tagPanel1) {
        this.tagPanel1 = tagPanel1;
    }

    /**
     * update photo and user information according to changes made in panel
     */
    public void updatePanel() {
        Set<String> tags = new HashSet<>();
        tags.addAll(allTags);
        if (parentPanel instanceof PhotoPanelEdit) {
            PhotoPanelEdit viewPanel = (PhotoPanelEdit) parentPanel;
            Component[] comps = viewPanel.getContainer().getComponents();
            for (int i = 0; i < comps.length; i++) {
                tags.addAll(((PhotoToEditPanel) comps[i]).getTags());
            }
            this.tagPanel1.setPossibility(tags);
        } else if(parentPanel instanceof AddPhotoEdition) {
            AddPhotoEdition viewPanel = (AddPhotoEdition) parentPanel;
            Component[] comps = viewPanel.getContainer().getComponents();
            for (int i = 0; i < comps.length; i++) {
                tags.addAll(((PhotoToEditPanel) comps[i]).getTags());
            }
            this.tagPanel1.setPossibility(tags);
        }
    }

    /**
     * @return the categoryComboBox
     */
    public javax.swing.JComboBox<String> getCategoryComboBox() {
        return categoryComboBox;
    }
}
