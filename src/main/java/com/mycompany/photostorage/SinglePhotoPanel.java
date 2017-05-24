/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

/**
 *
 * @author Wojtek
 */
public class SinglePhotoPanel extends javax.swing.JPanel {
    
    private int photoID;
    private BufferedImage image;
    private Border blackBorder = BorderFactory.createLineBorder(Color.BLACK,2);
    private Boolean isChecked = false;
    private Border emptyBorder = BorderFactory.createEmptyBorder(2,2,2,2);
    /**
     * Creates new form SinglePhotoPanel
     */
    public SinglePhotoPanel() {
        initComponents();
        setBorder(emptyBorder);
        setImageMiniature();        
        photoNameLabel.setText("Wódz wspaniały");
    }
    public SinglePhotoPanel(byte[] photo,String description, int id) {
        initComponents();
        this.photoID = id;
        setBorder(emptyBorder);
        setImageMiniature(photo);
        photoNameLabel.setText(description);
    }

    private void setImageMiniature() {
        try {
            image = ImageIO.read(new File("src/Photo.png"));
            ImageIcon icon = new ImageIcon();
            icon.setImage(image);
            Image image = icon.getImage();
            image = image.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            photoImage.setIcon(icon);
        } catch (IOException ex) {
           
        }
    }
    
    private void setImageMiniature(byte[] photo) {
        try {
            InputStream in = new ByteArrayInputStream(photo);
            image = ImageIO.read(in);
            ImageIcon icon = new ImageIcon();
            icon.setImage(image);
            Image image = icon.getImage();
            image = image.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            photoImage.setIcon(icon);
        } catch (IOException ex) {
           
        }
    }
    
    public boolean isChecked() {
        return this.isChecked;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        photoImage = new javax.swing.JLabel();
        photoNameLabel = new javax.swing.JLabel();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        photoNameLabel.setText("Name");
        photoNameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                photoNameLabelMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(photoNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(photoImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(photoImage, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(photoNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void photoNameLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_photoNameLabelMouseEntered
       photoNameLabel.setToolTipText(photoNameLabel.getText());
    }//GEN-LAST:event_photoNameLabelMouseEntered

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
        if (!isChecked) {
            setBorder(blackBorder);
        } else {
            setBorder(emptyBorder);
        }
        isChecked=!isChecked;
    }//GEN-LAST:event_formMouseReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel photoImage;
    private javax.swing.JLabel photoNameLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the photoID
     */
    public int getPhotoID() {
        return photoID;
    }
}
