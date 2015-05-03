/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imgUtils;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author RAVLYK
 */
public class ImageUtils {
    
    public static BufferedImage resize(BufferedImage img, Dimension newSize ){    
        int type = img.getType() == 0? BufferedImage.TYPE_INT_ARGB : img.getType();
        BufferedImage imgR = new BufferedImage(newSize.width, newSize.height, type);
        Graphics2D g = imgR.createGraphics();
        g.drawImage(img,0,0, newSize.width, newSize.height, null);
        g.dispose();
        return imgR;
    }
    
    public static BufferedImage getArea(BufferedImage img, Rectangle area){
        int type = img.getType() == 0? BufferedImage.TYPE_INT_ARGB : img.getType();
        BufferedImage imgR = new BufferedImage(area.width, area.height, type);
        Graphics2D g = imgR.createGraphics();
        g.drawImage(img, area.x, area.y, area.width, area.height, null);
        g.dispose();
        return imgR;
    }
}
