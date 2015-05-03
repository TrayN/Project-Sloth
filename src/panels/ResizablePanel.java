/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author RAVLYK
 */
public class ResizablePanel extends JPanel{
    
    private boolean drag = false;
    private Point dragLocation  = new Point();

    public  ResizablePanel() {
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
            	if (drag) {
                    if (dragLocation.getX()> getWidth()-10 && dragLocation.getY()>getHeight()-10) {
            		setSize((int)(getWidth()+(e.getPoint().getX()-dragLocation.getX())), (int)(getHeight()+(e.getPoint().getY()-dragLocation.getY())));
                        setSize(getWidth()<10?10:getWidth(), getHeight()<10?10:getHeight());
            		dragLocation = e.getPoint();
                    }
                    else{
                        int xChange = e.getComponent().getX() +e.getX()-(int)dragLocation.getX();
                        int yChange = e.getComponent().getY() +e.getY()-(int)dragLocation.getY();
                        xChange = xChange<0?0:xChange;
                        yChange = yChange<0?0:yChange;
                        setLocation(xChange, yChange);
                    }
            	}
            }
    	});
    }
}
