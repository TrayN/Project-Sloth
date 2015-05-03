/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author RAVLYK
 */
public class ScreenAreasPreset implements Serializable{
    
    String presetName;
    
    HashMap<String, Rectangle> areas;
    HashMap<String, Rectangle> fields;
    
    public ScreenAreasPreset(String name){
        presetName = name;
        areas = new HashMap();
        fields = new HashMap();
    }
    
    public String getName(){
        return this.presetName;
    }
    
    public HashMap<String, Rectangle> getAreas(){
        return this.areas;
    }
    
    public void setName(String name){
        this.presetName = name;
    }
    
    public Point convertFieldPointToAreaPoint(String fieldName, Point fieldPoint){
        
    }
    
}
