/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import classes.Screen;
import forms.FieldHolder;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 *
 * @author RAVLYK
 */
public class GameFieldPanel extends ImgPanel{
    
    FieldHolder fieldHolder;
    
    Boolean draggable = false;
    Boolean drag = false;
    private Point dragLocation  = new Point();
    Screen screeen;
    String fieldName, presetName;
    
    public GameFieldPanel(FieldHolder fieldHolder, Screen screen, String presetName, String fieldName){
        this.fieldHolder = fieldHolder;
        this.screeen = screen;
        this.presetName = presetName;
        this.fieldName = fieldName;
        
         addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	drag = true;
            	dragLocation = e.getPoint();
                e.getComponent().getParent().setComponentZOrder(e.getComponent(), 0); 
                e.getComponent().getParent().repaint();  
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            	drag = false;
            }
    	});
         
         addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
            	if (drag&&draggable) {
                    int xChange = e.getComponent().getX() +e.getX()-(int)dragLocation.getX();
                    int yChange = e.getComponent().getY() +e.getY()-(int)dragLocation.getY();
                    xChange = xChange<0?0:xChange;
                    yChange = yChange<0?0:yChange;
                    setLocation(xChange, yChange);
            	}
            }
    	});
    
    }
    
    public void setDrageble(Boolean isDraggable){
        this.draggable = isDraggable;
    }
    
}
