package panels;



import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author TrayN
 */
public class ImgPanel extends JPanel {
    protected BufferedImage img = null;
    
    @Override
    protected void paintComponent(Graphics grphcs) {
        if (img!=null){
            grphcs.drawImage(img, 0, 0, this);
            grphcs.dispose();
        }
    }
    
    public void setImg(BufferedImage img) {
        this.img = img;
    }
    
    public BufferedImage getImg(){
        return this.img;
    }
    
}
