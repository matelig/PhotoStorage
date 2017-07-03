/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

import com.mycompany.photostorage.model.AddPhotoOption;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

/**
 * Class creating JToolbar allowing navigation through application
 *
 * @author Jakub
 */
public class ToolBar implements ActionListener {

    /**
     * Program ToolBar
     */
    JToolBar toolBar;
    /**
     * Button showed on tool bar
     */
    JButton button;
    /**
     * Program frame
     */
    MainProgramFrame frame;
    /**
     * Panel with wait information
     */
    JOptionPane pleaseWaitPane = new JOptionPane("Photos are loading. Please wait.",
            JOptionPane.INFORMATION_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null,
            new Object[]{});
    /**
     * Wait dialog
     */
    private JDialog waitDialog;

    /**
     * Method that prepares waitDialog for use
     */
    private void prepareDialog() {
        waitDialog = pleaseWaitPane.createDialog(frame.getFrame(), "Please Wait");
        waitDialog.setContentPane(pleaseWaitPane);
        waitDialog.setModal(true);
        waitDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        waitDialog.pack();
        waitDialog.setLocationRelativeTo(frame.getFrame());
    }

    /**
     * Creates JToolbar with navigation buttons
     *
     * @param parentFrame frame containing JToolbar
     * @return created JToolBar
     */
    public JToolBar createToolBar(MainProgramFrame parentFrame) {
        frame = parentFrame;
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        button = new JButton("Add photos");
        button.setActionCommand("Add photos");
        button.addActionListener(this);
        toolBar.add(button);

        button = new JButton("Add category");
        button.setActionCommand("Add category");
        button.addActionListener(this);
        toolBar.add(button);

        button = new JButton("Delete category");
        button.setActionCommand("Delete category");
        button.addActionListener(this);
        toolBar.add(button);

        button = new JButton("Add device");
        button.setActionCommand("Add device");
        button.addActionListener(this);
        toolBar.add(button);

        button = new JButton("Delete device");
        button.setActionCommand("Delete device");
        button.addActionListener(this);
        toolBar.add(button);

        button = new JButton("Search");
        button.setActionCommand("Search");
        button.addActionListener(this);
        toolBar.add(button);

        button = new JButton("Generate report");
        button.setActionCommand("Generate report");
        button.addActionListener(this);
        toolBar.add(button);

        button = new JButton("Log out");
        button.setActionCommand("Log out");
        button.addActionListener(this);
        toolBar.add(button);

        return toolBar;
    }

    /**
     * Does action depending on button pressed
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add photos":
                AddPhotoOption addPhoto = new AddPhotoOption(frame, frame.getCurrentUser());
                addPhoto.showChoosePhotosDialog();
                break;
            case "Add device":
                frame.setPanel(new AddDevicePanel(frame));
                break;
            case "Delete device":
                frame.setPanel(new DeleteDevicePanel(frame));
                break;
            case "Add category":
                frame.setPanel(new AddCategoryPanel(frame.getCurrentUser(), frame));
                break;
            case "Delete category":
                frame.setPanel(new DeleteCategoryPanel(frame.getCurrentUser(), frame));
                break;
            case "Search":
                prepareDialog();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        frame.setPanel(new PhotoViewPanel(frame, frame.getCurrentUser()));
                        waitDialog.dispose();
                    }
                }).start();
                waitDialog.setVisible(true);
                break;
            case "Generate report":
                frame.setPanel(new GenerateReportPanel(frame.getCurrentUser()));
                break;
            case "Log out":
                frame.setPanel(new SignIn(frame));
                frame.setRelative();
                break;
            default:
                break;
        }
    }
}
