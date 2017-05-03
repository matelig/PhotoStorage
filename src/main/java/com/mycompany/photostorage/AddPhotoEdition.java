/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

import com.mycompany.photostorage.entity.Category;
import com.mycompany.photostorage.entity.Photo;
import com.mycompany.photostorage.entity.User;
import com.mycompany.photostorage.model.CurrentUser;
import com.mycompany.photostorage.model.NewPhoto;
import com.mycompany.photostorage.util.HibernateUtil;
import com.mysql.jdbc.Blob;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author m_lig
 */
public class AddPhotoEdition extends JPanel { 

    private JButton insertPhotoButton;
    private List<NewPhoto> newPhoto = new ArrayList<>();
    private JPanel container = new JPanel();
    private JScrollPane scrollPane;
    private CurrentUser currentUser;

    public AddPhotoEdition(List<NewPhoto> newPhoto, CurrentUser currentUser) {
        this.newPhoto.addAll(newPhoto);
        this.currentUser = currentUser;
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        initComponents();
        this.setLayout(new BorderLayout());
        scrollPane = new JScrollPane(container);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(480, 360));

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(insertPhotoButton, BorderLayout.SOUTH);
        setVisible(true);
        scrollPane.revalidate();
    }

    private void initComponents() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from User u where u.idu = " + currentUser.getUserID());
        User dbUser = (User) query.list().get(0);
        Set<Category> categories = dbUser.getCategories();
        List<Category> categoriesAL = new ArrayList<>();
        categoriesAL.addAll(categories);
        session.getTransaction().commit();
        session.close();
        for (NewPhoto photo : newPhoto) {
            PhotoToEditPanel ptep = new PhotoToEditPanel(photo.getPath(),categoriesAL);
            container.add(ptep);
        }

        this.insertPhotoButton = new JButton("Save");
        this.insertPhotoButton.setSize(new Dimension(40, 20));
        this.insertPhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                insertPhotoButtonActionPerformed();
            }
        });
    }

    private void insertPhotoButtonActionPerformed() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Query queryUser = session.createQuery("from User where idu="+currentUser.getUserID());
        User user = (User) queryUser.list().get(0);
        Set<Category> categories = user.getCategories();
        for (int i = 0; i < newPhoto.size(); i++) {
            PhotoToEditPanel ptep = (PhotoToEditPanel) container.getComponent(i);

            Photo photo = new Photo();
            photo.setDate(new Date());
            photo.setDescription(ptep.getDescription());
            photo.setFormat(newPhoto.get(i).getFormat());
            photo.setIsArchivised((byte) 0);
            Icon image = ptep.getMiniature();
            BufferedImage bi = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();//TODO: BLOB NEED TO BE FIXED - something's wrong
                ImageIO.write(bi, "png", baos);                  
                photo.setMiniature(baos.toByteArray());
            } 
            catch( IOException e) {
            } finally {
                try {
                    baos.close();
                } catch (Exception e) {
                }
            }          
            
            photo.setPath(newPhoto.get(i).getPath());
            photo.setResolution(newPhoto.get(i).getResolution());
            photo.setSize(Integer.parseInt(newPhoto.get(i).getSize()));
            photo.setUser(user);            
            for (Category c : categories) {
                if (c.getName().equals(ptep.getCategory())) {
                    photo.setCategory(c);
                    break;
                }
            }
            session.save(photo);            
        }
        session.getTransaction().commit();
        session.close();
    }
}
