/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import imgUtils.ImageUtils;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import panels.ImgPanel;

/**
 *
 * @author RAVLYK
 */
public class SwingScreenThreadTranslator extends SwingWorker<Integer, BufferedImage>{
    BufferedImage img, toImg;
    Screen screen;
    Dimension size;
    int sleepTime;
    ImgPanel imgPanel;
    
    public SwingScreenThreadTranslator(Screen screen, ImgPanel imgPanel, Dimension size){
        this.screen = screen;
        this.imgPanel = imgPanel;
        this.size = size;
        this.sleepTime = 1000/screen.fps;
    }
    
    @Override
    public Integer doInBackground() {
           while(true){
            if(screen.screenImage!=null){
                publish(ImageUtils.resize(screen.screenImage, size));                
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(ScreenThreadReader.class.getName()).log(Level.SEVERE, null, ex);
                return 0;
            }
        }
    }
    @Override
    protected void process(List<BufferedImage> chunks) {
        for(BufferedImage bi : chunks){
            Toolkit.getDefaultToolkit().sync();
            imgPanel.setImg(bi);
            imgPanel.repaint();
            imgPanel.getParent().repaint();
            imgPanel.getParent().validate();
            //imgPanel.va
            System.out.println(System.currentTimeMillis());
        }        
    }
}
