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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author Wojtek
 */
public class PhotoViewPanel extends javax.swing.JPanel {

    private CurrentUser currentUser;
    private List<String> categoriesNames;
    private List<Photo> selectedPhotos = new ArrayList<>();
    private List<SinglePhotoPanel> photoPanels = new ArrayList<>();
    private MainProgramFrame frame;
    private List<Category> allCategories = new ArrayList<>();
    private Date startDate;
    private Date endDate;

    /**
     * Creates new form PhotoViewPanel
     */
    public PhotoViewPanel() {
        initComponents();
    }

    public PhotoViewPanel(MainProgramFrame frame, CurrentUser currentUser) {
        this.frame = frame;
        this.currentUser = currentUser;
        initComponents();
        categoriesNames = new ArrayList<>();
        photosScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        photosPanel.setLayout(new WrapLayout());
        fillView();
        prepareCreation();
        addJDatePickerListener();
        photosPanel.revalidate();
        categoryTree.setSelectionRow(0);
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
        allCategories.addAll(categoriesAL);
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
                updateMainView();
            }
        });
        session.getTransaction().commit();
        session.close();
    }

    private void updatePhotoView() {
        photoPanels.clear();
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
        sortPhotos(selectedPhotos);
        checkPhotoDate(selectedPhotos);
        for (Photo p : selectedPhotos) {
            SinglePhotoPanel photoPanel = new SinglePhotoPanel(p.getMiniature(), p.getDescription(), p.getIdp());
            photoPanels.add(photoPanel);
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
        tagPane = new javax.swing.JScrollPane();
        tagsTextField = new javax.swing.JTextArea();
        startDatePicker = new org.jdesktop.swingx.JXDatePicker();
        endDatePicker = new org.jdesktop.swingx.JXDatePicker();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        deletePhotoButton = new javax.swing.JButton();
        movePhotoButton = new javax.swing.JButton();
        editPhotoButton = new javax.swing.JButton();

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
        tagPane.setViewportView(tagsTextField);

        jButton1.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jButton1.setText("x");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(startDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(endDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(tagPane, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tagPane, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(startDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(endDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1))))
        );

        deletePhotoButton.setText("Delete");
        deletePhotoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePhotoButtonActionPerformed(evt);
            }
        });

        movePhotoButton.setText("Move");
        movePhotoButton.setPreferredSize(new java.awt.Dimension(66, 32));

        editPhotoButton.setText("Edit");
        editPhotoButton.setPreferredSize(new java.awt.Dimension(66, 32));
        editPhotoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPhotoButtonActionPerformed(evt);
            }
        });

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 325, Short.MAX_VALUE)
                .addComponent(editPhotoButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(movePhotoButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(deletePhotoButton)
                .addGap(36, 36, 36))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deletePhotoButton)
                    .addComponent(movePhotoButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editPhotoButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editPhotoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPhotoButtonActionPerformed
        List<SinglePhotoPanel> selectedPhotos = new ArrayList<>();
        for (SinglePhotoPanel p : photoPanels) {
            if (p.isChecked()) {
                selectedPhotos.add(p);
            }
        }
        frame.setPanel(new PhotoPanelEdit(selectedPhotos, allCategories));
    }//GEN-LAST:event_editPhotoButtonActionPerformed

    private void deletePhotoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePhotoButtonActionPerformed
        Object[] options = {"Yes", "No"};
        int x = JOptionPane.showOptionDialog(null,
                "Do you really want to delete selected photos?",
                "Delete",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[1]);
        if (x == 0) {
            List<SinglePhotoPanel> photosToDelete = new ArrayList<>();
            for (SinglePhotoPanel p : photoPanels) {
                if (p.isChecked()) {
                    photosToDelete.add(p);
                }
            }
            deletePhotoFromDatabase(photosToDelete);
            updateMainView();
        }
    }//GEN-LAST:event_deletePhotoButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        startDatePicker.setDate(null);
        updateMainView();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        endDatePicker.setDate(null);
        updateMainView();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree categoryTree;
    private javax.swing.JButton deletePhotoButton;
    private javax.swing.JButton editPhotoButton;
    private org.jdesktop.swingx.JXDatePicker endDatePicker;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JButton movePhotoButton;
    private javax.swing.JPanel photosPanel;
    private javax.swing.JScrollPane photosScrollPane;
    private org.jdesktop.swingx.JXDatePicker startDatePicker;
    private javax.swing.JScrollPane tagPane;
    private javax.swing.JTextArea tagsTextField;
    // End of variables declaration//GEN-END:variables

    private void fillView() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from User where idu=" + currentUser.getUserID());
        User dbUser = (User) query.list().get(0);
        Set<Photo> photos = dbUser.getPhotos();
        List<Photo> selectedPhotos = new ArrayList<>();
        selectedPhotos.addAll(photos);
        checkPhotoDate(selectedPhotos);
        sortPhotos(selectedPhotos);
        for (Photo p : selectedPhotos) {
            SinglePhotoPanel photoPanel = new SinglePhotoPanel(p.getMiniature(), p.getDescription(), p.getIdp());
            photoPanels.add(photoPanel);
            photosPanel.add(photoPanel);
        }
        session.getTransaction().commit();
        session.close();
    }

    private void deletePhotoFromDatabase(List<SinglePhotoPanel> selectedPhotos) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        for (SinglePhotoPanel p : selectedPhotos) {
            Query query = session.createQuery("from Photo where idp=" + p.getPhotoID());
            Photo dbPhoto = (Photo) query.list().get(0);
            session.delete(dbPhoto);
        }
        session.getTransaction().commit();
        session.close();
    }

    private void addJDatePickerListener() {
        ActionListener l = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (JXDatePicker.COMMIT_KEY.equals(ae.getActionCommand())) {
                    startDate = startDatePicker.getDate();
                    updateMainView();
                }
            }
        };
        startDatePicker.addActionListener(l);
        ActionListener l1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (JXDatePicker.COMMIT_KEY.equals(ae.getActionCommand())) {
                    endDate = endDatePicker.getDate();
                    updateMainView();
                }
            }
        };
        endDatePicker.addActionListener(l1);
    }
    
    private void updateMainView() {
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

    private void checkPhotoDate(List<Photo> selectedPhotos) {
        for (int i = selectedPhotos.size()-1;i>=0;i--) {
            if (startDate!=null) {
                if (selectedPhotos.get(i).getDate().before(startDate)) {
                    selectedPhotos.remove(i);
                }
            }
        }
        for (int i = selectedPhotos.size()-1;i>=0;i--) {
            if (endDate!=null) {
                if (selectedPhotos.get(i).getDate().after(endDate)) {
                    selectedPhotos.remove(i);
                }
            }
        }
    }

    private void sortPhotos(List<Photo> photos) {
        Collections.sort(photos,new Comparator<Photo>() {
            @Override
            public int compare(Photo t, Photo t1) {
                return t.getIdp().compareTo(t1.getIdp());
            }
        });
    }
}
