/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

import com.mycompany.photostorage.entity.Device;
import com.mycompany.photostorage.entity.Photo;
import com.mycompany.photostorage.util.HibernateUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Rafał Swoboda
 */
public class MovePhotosPanel extends javax.swing.JPanel {

    MainProgramFrame frame;
    List<Photo> photos = new ArrayList<>();

    /**
     * Creates new form MovePhotosPanel
     *
     * @param frame
     * @param photos
     */
    public MovePhotosPanel(MainProgramFrame frame, List<Photo> photos) {
        this.frame = frame;
        this.photos = photos;
        initComponents();
        fillComboBox();
    }

    public final void fillComboBox() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Device");
        List<Device> devices = new ArrayList<>();
        devices = query.list();
        for (Device device : devices) {
            devicesComboBox.addItem(device.getName());
        }
        session.getTransaction().commit();
        session.close();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        devicesComboBox = new javax.swing.JComboBox<>();
        cancelButton = new javax.swing.JButton();
        acceptButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        acceptButton.setText("Accept");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Choose device:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(cancelButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(acceptButton))
                        .addComponent(devicesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(devicesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(acceptButton))
                .addContainerGap(25, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptButtonActionPerformed
        File[] files = File.listRoots();
        String name;
        String destination = "";
        String deviceName = devicesComboBox.getSelectedItem().toString();
        List<Device> databaseDevices;
        Device currentDevice = null;
        boolean found = false;
        for (int i = 0; i < files.length; i++) {
            name = FileSystemView.getFileSystemView().getSystemDisplayName(files[i]);
            if (name.contains(deviceName.split(" ")[0])) {
                destination = files[i].getAbsolutePath();
                found = true;
                break;
            }
        }
        if (found) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Device");
            databaseDevices = query.list();
            for (Device device : databaseDevices) {
                if (device.getName().equals(deviceName)) {
                    currentDevice = device;
                    break;
                }
            }
            Set<Photo> photosOnDevice = currentDevice.getPhotos();
            for (Photo photo : photos) {
                File file = new File(photo.getPath());
                try {
                    Files.move(Paths.get(photo.getPath()), Paths.get(destination + file.getName()));
                    photo.setPath(destination + file.getName());
                    photo.setIsArchivised((byte) 1);
                    photosOnDevice.add(photo);
                    session.update(photo);
                } catch (IOException ex) {
                }
            }
            currentDevice.setPhotos(photosOnDevice);
            session.save(currentDevice);
            session.getTransaction().commit();
            session.close();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Device have not been found. Try to plug it in.",
                    "Warning!",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_acceptButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        frame.setPanel(new PhotoViewPanel(frame, frame.getCurrentUser()));
    }//GEN-LAST:event_cancelButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox<String> devicesComboBox;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}