/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.HashMap;
import panels.AreaPanel;

/**
 *
 * @author RAVLYK
 */
public class ViewPresets implements Serializable{
    
    private HashMap <String, Rectangle> namedAreas = new HashMap<>();
    private String windowName = null;
    private Boolean modified = false;
    
    public ViewPresets(String name){
        this.windowName = name;
    }
    
    public HashMap <String, Rectangle> getNamedAreas(){
        return namedAreas;
    }
    
    public String getName(){
        return this.windowName;
    }
    
    public void setNamedAreas(HashMap <String, AreaPanel> areaPanels){
        namedAreas = new HashMap<>();
        for(String key : areaPanels.keySet()){
            namedAreas.put(key, areaPanels.get(key).getBounds());
        }
        modified = true;
    }
    
    public Boolean isModified(){
        return this.modified;
    }
    
}
