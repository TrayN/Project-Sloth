package classes;


import com.sun.jna.platform.win32.WinDef;
import imgUtils.ImageUtils;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import panels.ImgPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RAVLYK
 */
public class ScreenThreadReader implements Runnable{
    
    BufferedImage img;
    WinDef.HWND windowHandle;
    Rectangle windowSize;
    int sleepTime;
    Screen screen;
    
    public ScreenThreadReader(WinDef.HWND windowHandle, Rectangle windowSize, int fps, Screen screen){
        //this.img = img;
        this.windowHandle = windowHandle;
        this.windowSize = windowSize;
        this.sleepTime = 1000/fps;
        this.screen = screen;
    }
    
    public void run() {
        while(true){
            //if(screen.screenImage!=null) screen.screenImage.getGraphics().dispose();
            //screen.screenImage = null;
            screen.screenImage = ScreenCapture.getScreenshot(windowHandle, windowSize);//new BufferedImage(1000, 1000, 2);//ScreenCapture.getScreenshot(windowHandle, windowSize);
            //img = ScreenCapture.getScreenshot(windowHandle, windowSize);
            System.out.println(System.currentTimeMillis()+" screen");
            System.runFinalization();
            
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(ScreenThreadReader.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }
    }
    
}
