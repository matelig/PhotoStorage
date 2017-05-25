/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.photostorage.model;

import com.mycompany.photostorage.AddPhotoEdition;
import com.mycompany.photostorage.MainProgramFrame;
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
 * @author m_lig
 */
public class AddPhotoOption {
    private File[] photos;
    private MainProgramFrame parentFrame;
    private CurrentUser currentUser;
    
    public AddPhotoOption(MainProgramFrame frame, CurrentUser currentUser) {
        parentFrame = frame;
        this.currentUser = currentUser;           
    }
    
    public void showChoosePhotosDialog() {
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
            if (!FilenameUtils.getExtension(photos[i].getPath()).equalsIgnoreCase("gif")) {
                String fileType = mimeType.getContentType(photos[i]).split("/")[0];
                if (fileType.equalsIgnoreCase("image")) {
                    imageFile.add(photos[i]);
                }

            }
        }

        List<NewPhoto> newPhotos = new ArrayList<>(); //placeholder - i just need to remember what to do ^.^
        for (File file : imageFile) {
            try {
                BufferedImage bi;
                bi = ImageIO.read(file);

//                String[] filePart = file.getPath().split(".");
                String resolution = bi.getWidth() + "x" + bi.getHeight();
                NewPhoto photo = new NewPhoto();
                photo.setPath(file.getPath());
                photo.setFormat(FilenameUtils.getExtension(file.getPath()));
                photo.setResolution(resolution);
                photo.setSize(Long.toString(file.length()));
                photo.setDescription(file.getName());
                newPhotos.add(photo);
            } catch (IOException ex) {

            }
        }
        parentFrame.setPanel(new AddPhotoEdition(newPhotos, currentUser,parentFrame));
    }
}
