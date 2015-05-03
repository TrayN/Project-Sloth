/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import com.sun.jna.platform.win32.WinDef;
import imgUtils.ImageUtils;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAVLYK
 */
public class Screen implements Serializable{
    
    Caesar caesar;
    
    String windowName;
    
    BufferedImage screenImage = null;
    Rectangle windowSize = null;
    int fps = 25;
    
    WinDef.HWND windowHandle = null;
    
    HashMap <String, ScreenAreasPreset> screenAreaPresets = null;
    
    ExecutorService executor = null;
    
    Thread thread = null;

    public Screen(String windowName, Caesar caesar){
        this.windowName = windowName;
        this.caesar = caesar;
        windowHandle = User32.INSTANCE.FindWindow(Caesar.EVE_WINDOW_CLASS, windowName);
        //if(windowHandle==null){this = null; return;}//should rise exception
        WinDef.RECT windowRect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(windowHandle, windowRect);
        this.windowSize = new Rectangle(0, 0, windowRect.right-windowRect.left, windowRect.bottom - windowRect.top );
        if(loadFromFile()){
            
        }
        else{
            screenAreaPresets = new HashMap();
        }
        
    }
    
    private Boolean loadFromFile(){
        //toDO load
        File f = new File(Caesar.SCREENS_DIRECTORY+this.windowName+".preset");
        if(!f.exists()){
            return false;
        }
        FileInputStream fStream;
        HashMap <String, ScreenAreasPreset> loadedScreenAreas = null;
        try {
            fStream = new FileInputStream(f);
            ObjectInputStream fObject = new ObjectInputStream(fStream);
            loadedScreenAreas = (HashMap <String, ScreenAreasPreset>)fObject.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Caesar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Caesar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Caesar.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(loadedScreenAreas==null){
            return false;
        }
        else{
            this.screenAreaPresets = loadedScreenAreas;
        }
        return true;
    }
    
    public void saveToFile(){
        //toDO save
        File f = new File(Caesar.SCREENS_DIRECTORY+this.windowName+".preset");
        f.delete();
        try {
            f.createNewFile();
            FileOutputStream fStream;
            fStream = new FileOutputStream(f);
            ObjectOutputStream fObject = new ObjectOutputStream(fStream);
            fObject.writeObject(this.screenAreaPresets);
            fObject.flush();
            fObject.close();
            fStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setFPS(int fps){
        this.fps = fps;
        restartScreening();
    }
    
    public void restartScreening(){
        stopScreening();
        startScreening();
    }
    
    public String getName(){
        return this.windowName;
    }
    
    public void addAreaPreset(ScreenAreasPreset areasPreset){
        this.screenAreaPresets.put(areasPreset.presetName, areasPreset);
    }
    
    public void removeAreaPreset(String presetName){
        this.screenAreaPresets.remove(presetName);
    }
    
    public HashMap <String, ScreenAreasPreset> getAreaPresets(){
        return this.screenAreaPresets;
    }
    
    public void changePresetName(String oldName, String newName){
        screenAreaPresets.get(oldName).setName(newName);
        ScreenAreasPreset sap = screenAreaPresets.get(oldName);
        screenAreaPresets.remove(oldName);
        screenAreaPresets.put(newName, sap);
    }
    /*public void onLoad(){//use when loaded from file
        windowHandle = User32.INSTANCE.FindWindow(Caesar.EVE_WINDOW_CLASS, windowName);
        executor = Executors.newFixedThreadPool(1);
    }*/
    
    public void check(){
        System.out.println("checked");
    }
    
    public BufferedImage getImageArea(Rectangle imageSize){
        return ImageUtils.getArea(screenImage, imageSize);
    }
    
    public void startScreening(){
        if(this.thread == null) this.thread = new Thread(new ScreenThreadReader(windowHandle, windowSize, fps, this));
        this.thread.start();
        
        /*ScreenThreadReader str = new ScreenThreadReader(windowHandle, windowSize, fps, this);
        
        Thread th = new Thread(str);
        th.start();*/
        //executor.execute(str);
        //caesar.executor.execute(str);
    }
    
    public void stopScreening(){
        if(this.thread!=null){
            this.thread.interrupt();
            //this.thread.stop();
        }
        /*executor.shutdown();
        screenImage = null;*/
    }
    
    /*public void pauseScreening(){
        executor.wa
    }*/
    
}
