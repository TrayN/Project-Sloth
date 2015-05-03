/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import imgUtils.ImageUtils;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import panels.ImgPanel;

/**
 *
 * @author RAVLYK
 */
public class ScreenThreadTranslator implements Runnable{
    BufferedImage img, toImg;
    Screen screen;
    Dimension size;
    int sleepTime;
    ImgPanel imgPanel;
    
    public ScreenThreadTranslator(Screen screen, ImgPanel imgPanel, Dimension size){
        this.screen = screen;
        this.imgPanel = imgPanel;
        this.size = size;
        this.sleepTime = 1000/screen.fps;
    }
    
    public void run() {
        while(true){
            if(screen.screenImage!=null){
                //imgPanel.
                imgPanel.setImg(ImageUtils.resize(screen.screenImage, size));
                //imgPanel.validate();
                imgPanel.repaint();
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(ScreenThreadReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
