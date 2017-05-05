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
import com.mycompany.photostorage.model.WrapLayout;
import com.mycompany.photostorage.util.HibernateUtil;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Wojtek
 */
public class PhotoViewPanel extends javax.swing.JPanel {

    private CurrentUser currentUser;
    private List<String> categoriesNames;
    private List<Photo> selectedPhotos = new ArrayList<>();

    /**
     * Creates new form PhotoViewPanel
     */
    public PhotoViewPanel() {
        initComponents();
    }

    public PhotoViewPanel(CurrentUser currentUser) {
        this.currentUser = currentUser;
        initComponents();
        categoriesNames = new ArrayList<>();
        photosScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        photosPanel.setLayout(new WrapLayout());
        fillView();
        prepareCreation();
        photosPanel.revalidate();
    }

    private void createCategoriesTree(List<Category> categoriesAL, DefaultMutableTreeNode supCategory) {
        DefaultMutableTreeNode category = null;
        if (!categoriesAL.isEmpty()) {
            for (int i = categoriesAL.size() - 1; i >= 0; i--) {
                Category cat = categoriesAL.get(i);
                if (cat.getCategory() != null && cat.getCategory().getName().equals(supCategory.getUserObject())) {
                    category = new DefaultMutableTreeNode(cat.getName());
                    supCategory.add(category);
                    categoriesAL.remove(i);
                    createCategoriesTree(categoriesAL, category);
                }
            }
        }
    }

    private void prepareCreation() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query queryUser = session.createQuery("from User where idu=" + currentUser.getUserID());
        User user = (User) queryUser.list().get(0);
        Set<Category> categories = user.getCategories();
        List<Category> categoriesAL = new ArrayList<>();
        categoriesAL.addAll(categories);
        Collections.sort(categoriesAL, (Category a, Category b) -> {
            return a.getIdc().compareTo(b.getIdc());
        });
        DefaultMutableTreeNode root = null;
        DefaultMutableTreeNode category = null;
        root = new DefaultMutableTreeNode("Categories");
        for (int i = categoriesAL.size() - 1; i >= 0; i--) {
            Category cat = categoriesAL.get(i);
            if (cat.getCategory() == null) {
                category = new DefaultMutableTreeNode(cat.getName());
                root.add(category);
                categoriesAL.remove(i);
                createCategoriesTree(categoriesAL, category);
            }
        }
        categoryTree.setModel(new DefaultTreeModel(root));
        categoryTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                categoriesNames = new ArrayList<>();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) categoryTree.getLastSelectedPathComponent();
                photosPanel.removeAll();
                if (node.isRoot()) {
                    fillView();
                } else {
                    getNodePhotos(node);
                    updatePhotoView();
                }
                photosPanel.revalidate();
                photosPanel.repaint();
            }
        });
        session.getTransaction().commit();
        session.close();
    }

    private void updatePhotoView() {
        selectedPhotos = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query queryUser = session.createQuery("from User where idu=" + currentUser.getUserID());
        User user = (User) queryUser.list().get(0);
        Set<Category> categories = user.getCategories();
        List<Category> categoriesAL = new ArrayList<>();
        categoriesAL.addAll(categories);
        for (int i = categoriesAL.size() - 1; i >= 0; i--) {
            if (!categoriesNames.contains(categoriesAL.get(i).getName())) {
                categoriesAL.remove(i);
            }
        }
        for (Category cat : categoriesAL) {
            Set<Photo> photos = cat.getPhotos();
            selectedPhotos.addAll(photos);
        }
        for (Photo p : selectedPhotos) {
            SinglePhotoPanel photoPanel = new SinglePhotoPanel(p.getMiniature(), p.getDescription());
            photosPanel.add(photoPanel);
        }
        session.getTransaction().commit();
        session.close();
    }

    private void getNodePhotos(DefaultMutableTreeNode node) {
        categoriesNames.add((String) node.getUserObject());
        if (node.isLeaf()) {
            return;
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            getNodePhotos((DefaultMutableTreeNode) node.getChildAt(i));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        categoryTree = new javax.swing.JTree();
        photosScrollPane = new javax.swing.JScrollPane();
        photosPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tagsTextField = new javax.swing.JTextArea();

        jSplitPane1.setDividerLocation(150);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Categories");
        categoryTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(categoryTree);

        jSplitPane1.setLeftComponent(jScrollPane1);

        photosScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout photosPanelLayout = new javax.swing.GroupLayout(photosPanel);
        photosPanel.setLayout(photosPanelLayout);
        photosPanelLayout.setHorizontalGroup(
            photosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 552, Short.MAX_VALUE)
        );
        photosPanelLayout.setVerticalGroup(
            photosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 365, Short.MAX_VALUE)
        );

        photosScrollPane.setViewportView(photosPanel);

        jSplitPane1.setRightComponent(photosScrollPane);

        jLabel1.setText("Start date");

        jLabel2.setText("Finish date");

        jLabel3.setText("Tags");

        tagsTextField.setColumns(20);
        tagsTextField.setRows(5);
        jScrollPane2.setViewportView(tagsTextField);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(107, 107, 107)
                .addComponent(jLabel2)
                .addGap(110, 110, 110)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap())
                    .addComponent(jScrollPane2)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree categoryTree;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel photosPanel;
    private javax.swing.JScrollPane photosScrollPane;
    private javax.swing.JTextArea tagsTextField;
    // End of variables declaration//GEN-END:variables

    private void fillView() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from User where idu=" + currentUser.getUserID());
        User dbUser = (User) query.list().get(0);
        Set<Photo> photos = dbUser.getPhotos();
        for (Photo p : photos) {
            SinglePhotoPanel photoPanel = new SinglePhotoPanel(p.getMiniature(), p.getDescription());
            photosPanel.add(photoPanel);
        }
        session.getTransaction().commit();
        session.close();
    }
}
