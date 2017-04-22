/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage;

/**
 *
 * @author Jakub
 */
public class MainClass {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainProgramFrame frame = new MainProgramFrame();
                frame.CreateAndShowGUI();
            }
        });
    }
}
