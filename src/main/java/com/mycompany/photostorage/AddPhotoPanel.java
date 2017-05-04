/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

import com.mycompany.photostorage.model.CurrentUser;
import com.mycompany.photostorage.model.NewPhoto;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author m_ligus
 */
public class AddPhotoPanel extends javax.swing.JPanel {

    private File[] photos;
    private MainProgramFrame parentFrame;
    private CurrentUser currentUser;
    /**
     * Creates new form AddPhotoPanel
     */
    public AddPhotoPanel(MainProgramFrame frame,CurrentUser currentUser) {
        initComponents();
        parentFrame = frame;
        this.currentUser = currentUser;
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        selectPhotoButton = new javax.swing.JButton();

        selectPhotoButton.setText("Select Photos");
        selectPhotoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectPhotoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(selectPhotoButton)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(selectPhotoButton)
                .addContainerGap(65, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void selectPhotoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectPhotoButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            photos = fileChooser.getSelectedFiles();
        }
        List<File> imageFile = new ArrayList<>();
        MimetypesFileTypeMap mimeType;
        mimeType = new MimetypesFileTypeMap();
        mimeType.addMimeTypes("image png jpg jpeg");
        for (int i = photos.length - 1; i >= 0; i--) {
            String fileType = mimeType.getContentType(photos[i]).split("/")[0];
            String[] test = mimeType.getContentType(photos[i]).split("/");
            if (fileType.equalsIgnoreCase("image")) {
                imageFile.add(photos[i]);
            }
        }

        List<NewPhoto> newPhotos = new ArrayList<>(); //placeholder - i just need to remember what to do ^.^
        for (File file : imageFile) {
            try {
                BufferedImage bi;
                bi = ImageIO.read(file);      
                
//                String[] filePart = file.getPath().split(".");
                String resolution = bi.getWidth()+"x"+bi.getHeight();
                NewPhoto photo = new NewPhoto();
                photo.setPath(file.getPath());
                photo.setFormat(FilenameUtils.getExtension(file.getPath()));
                photo.setResolution(resolution);
                photo.setSize(Long.toString(file.length()));
                
                newPhotos.add(photo);
            } catch (IOException ex) {

            }
        }
        parentFrame.setPanel(new AddPhotoEdition(newPhotos,currentUser));
    }//GEN-LAST:event_selectPhotoButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton selectPhotoButton;
    // End of variables declaration//GEN-END:variables
}
