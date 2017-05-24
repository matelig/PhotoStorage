/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

import com.mycompany.photostorage.entity.Category;
import com.mycompany.photostorage.entity.Photo;
import com.mycompany.photostorage.entity.User;
import com.mycompany.photostorage.util.HibernateUtil;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author m_lig
 */
public class PhotoPanelEdit extends JPanel {

    private JScrollPane scrollPane;
    private JButton editButton;
    private JPanel container = new JPanel();
    private List<SinglePhotoPanel> selectedPhotos = new ArrayList<>();
    private List<Category> categoriesAL = new ArrayList<>();

    public PhotoPanelEdit(List<SinglePhotoPanel> selectedPhotos, List<Category> categoriesAL) {
        this.selectedPhotos.addAll(selectedPhotos);
        this.categoriesAL.addAll(categoriesAL);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        initComponent();
        this.setLayout(new BorderLayout());
        scrollPane = new JScrollPane(container);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(480, 360));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(editButton, BorderLayout.SOUTH);
        setVisible(true);
        scrollPane.revalidate();

    }

    private void initComponent() {
        for (SinglePhotoPanel panel : selectedPhotos) {
            PhotoToEditPanel ptep = new PhotoToEditPanel(panel.getPhotoID(), categoriesAL);
            container.add(ptep);
        }
        this.editButton = new JButton("Edit");
        this.editButton.setSize(new Dimension(40, 20));
        this.editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                onEditButtonClick();
            }
        });
    }

    private void onEditButtonClick() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        for (int i = 0; i < selectedPhotos.size(); i++) {
            PhotoToEditPanel ptep = (PhotoToEditPanel) container.getComponent(i);
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
            session.update(dbPhoto);
        }
        session.getTransaction().commit();
        session.close();
    }
}
