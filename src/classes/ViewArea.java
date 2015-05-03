/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import panels.ImgPanel;

/**
 *
 * @author RAVLYK
 */
public class ViewArea {
    
    String viewName;
    //ImgPanel screen = new ImgPanel();
    BufferedImage img = null;
    ArrayList <Rectangle> areas = new ArrayList<>();
    HashMap <String, Rectangle> namedAreas = new HashMap<>();
    ViewPresets viewPresets;
    
    public ViewArea(BufferedImage screenImg, ViewPresets presets){
        if(presets!=null){
            this.viewPresets = presets;
            loadFromPresets();
        }
        viewName = presets.getName();
        img = screenImg;
    }
    
    
    private void loadFromPresets(){
        namedAreas = viewPresets.getNamedAreas();
        
    }
    
    public BufferedImage getImage(){
        return img;
    }
    
    public HashMap <String, Rectangle> getNamedAreas(){
        return namedAreas;
    }
    
    
    
}
