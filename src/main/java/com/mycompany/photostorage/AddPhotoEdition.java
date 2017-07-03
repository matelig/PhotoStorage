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
import com.mycompany.photostorage.model.CurrentUser;
import com.mycompany.photostorage.model.NewPhoto;
import com.mycompany.photostorage.util.HibernateUtil;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Class which allows user editing selected photos
 * @author m_lig
 */
public class AddPhotoEdition extends JPanel {

    /**
     * Save button
     */
    private JButton savePhotoButton;
    /**
     * List of selected photo to add
     */
    private List<NewPhoto> newPhoto = new ArrayList<>();
    /**
     * inner JPanel in which photos are added
     */
    private JPanel container = new JPanel();
    /**
     * scrollPane
     */
    private JScrollPane scrollPane;
    /**
     * Logged user
     */
    private CurrentUser currentUser;
    /**
     * Main frame of the program
     */
    private MainProgramFrame mainFrame;
    /**
     * list of existing categories from current user
     */
    private JComboBox categoriesComboBox = new JComboBox();
    /**
     * Button which allows user to determine category for all photos
     */
    private JButton applyButton;

    /**
     * Constructor of the class
     * @param newPhoto list of photos to add
     * @param currentUser logged in user
     * @param mainFrame parent JFrame
     */
    public AddPhotoEdition(List<NewPhoto> newPhoto, CurrentUser currentUser, MainProgramFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.newPhoto.addAll(newPhoto);
        this.currentUser = currentUser;
        container.setLayout(new BoxLayout(getContainer(), BoxLayout.Y_AXIS));
        initComponents();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Choose category for all photos:");
        label.setSize(new Dimension(100, 30));
        label.setAlignmentX(LEFT_ALIGNMENT);
        categoriesComboBox.setAlignmentX(LEFT_ALIGNMENT);
        FillCategoriesComboBox();
        scrollPane = new JScrollPane(getContainer());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(480, 360));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setAlignmentX(LEFT_ALIGNMENT);

        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(label);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(categoriesComboBox);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(applyButton);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(scrollPane);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(savePhotoButton);
        setVisible(true);
        scrollPane.revalidate();
    }

    /**
     * This method adds user's categories to the combo box
     */
    public void FillCategoriesComboBox() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from User u where u.idu = " + currentUser.getUserID());
        User dbUser = (User) query.list().get(0);
        Set<Category> categories = dbUser.getCategories();
        List<Category> categoriesAL = new ArrayList<>();
        categoriesAL.addAll(categories);
        categoriesComboBox.addItem("None");
        for (Category cat : categoriesAL) {
            categoriesComboBox.addItem(cat.getName());
        }
        session.close();
    }

    /**
     * Initialization of the panel. Get current user, categories list, existing photos and existing tags from Database
     */
    private void initComponents() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from User u where u.idu = " + currentUser.getUserID());
        User dbUser = (User) query.list().get(0);
        Set<Category> categories = dbUser.getCategories();
        List<Category> categoriesAL = new ArrayList<>();
        categoriesAL.addAll(categories);
        Set<Photo> dbPhotos = dbUser.getPhotos();
        Set<Tag> tags = new HashSet<>();
        for (Photo p : dbPhotos) {
            tags.addAll(p.getTags());
        }
        session.getTransaction().commit();
        session.close();
        Set<String> tagNames = new HashSet<>();
        for (Tag t : tags) {
            tagNames.add(t.getValue());
        }
        List<String> listedTags = new ArrayList<>();
        listedTags.addAll(tagNames);
        for (NewPhoto photo : newPhoto) {
            if (!FilenameUtils.getExtension(photo.getPath()).equalsIgnoreCase("gif")) {
                PhotoToEditPanel ptep = new PhotoToEditPanel(photo.getPath(), categoriesAL, tagNames, this);
                ptep.getTagPanel().addPosibility(listedTags);
                getContainer().add(ptep);
            }
        }
        applyButton = new JButton("Apply");
        applyButton.setPreferredSize(new Dimension(100, 25));
        applyButton.setAlignmentX(LEFT_ALIGNMENT);
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                changeAllCategories();
            }
        });
        this.savePhotoButton = new JButton("Save");
        this.savePhotoButton.setPreferredSize(new Dimension(100, 25));
        this.savePhotoButton.setAlignmentX(LEFT_ALIGNMENT);
        this.savePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                insertPhotoButtonActionPerformed();
            }
        });
    }

    /**
     * Method which provides adjust categories for all photos selected
     */
    private void changeAllCategories() {
        Component[] comps = container.getComponents();
        for (int i = 0; i < comps.length; i++) {
            PhotoToEditPanel panel = (PhotoToEditPanel) comps[i];
            panel.getCategoryComboBox().setSelectedItem(categoriesComboBox.getSelectedItem());
        }
    }

    /**
     * Method that inserts insormation of selected photos to database
     */
    private void insertPhotoButtonActionPerformed() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query queryUser = session.createQuery("from User where idu=" + currentUser.getUserID());
        User user = (User) queryUser.list().get(0);
        Set<Category> categories = user.getCategories();
        for (int i = 0; i < newPhoto.size(); i++) {
            PhotoToEditPanel ptep = (PhotoToEditPanel) getContainer().getComponent(i);
            Photo photo = new Photo();
            photo.setDescription(ptep.getDescription());
            photo.setFormat(newPhoto.get(i).getFormat());
            photo.setIsArchivised((byte) 0);

            Icon image = ptep.getMiniature();
            BufferedImage bi = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.createGraphics();
            image.paintIcon(null, g, 0, 0);
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                ImageIO.write(bi, "png", baos);
                byte[] imageInByte = baos.toByteArray();
                photo.setMiniature(imageInByte);
                BasicFileAttributes attr = Files.readAttributes(Paths.get(newPhoto.get(i).getPath()), BasicFileAttributes.class);
                long date = attr.creationTime().toMillis();
                photo.setDate(new Date(date));
            } catch (IOException e) {
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
            if (!ptep.getCategory().equalsIgnoreCase("none")) {
                for (Category c : categories) {
                    if (c.getName().equals(ptep.getCategory())) {
                        photo.setCategory(c);
                        break;
                    }
                }
            }
            session.save(photo);
            List<String> tagList = ptep.getTags();
            for (String s : tagList) {
                Tag tag = new Tag(photo, s);
                session.save(tag);
            }
        }
        session.getTransaction().commit();
        session.close();
        JOptionPane.showMessageDialog(this,
                "Photos have been inserted.",
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
        mainFrame.setPanel(new PhotoViewPanel(mainFrame, currentUser));
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
