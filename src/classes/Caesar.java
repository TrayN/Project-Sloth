package classes;


import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.GDI32;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import panels.ImgPanel;

/**
 *
 * @author RAVLYK
 */


public class Caesar {
    
    public static final String EVE_WINDOW_CLASS = "triuiScreen";
    public static final String PRESET_FILE_PATH = "ScreenPresets.ini";
    public static final String PARAMS_FILE_PATH = "Params.ini";
    public static final String SCREENS_DIRECTORY = "ScreenPresets\\";
    
    public final ExecutorService executor = Executors.newWorkStealingPool();//Executors.newCachedThreadPool();
    
    HashMap<String, Screen> activeScreens = new HashMap();
        
    public Caesar(){
        
    }
    
    public Screen getScreenByWindowName(String windowName){
        Screen newScreen = new Screen(windowName, this);
        activeScreens.put(windowName, newScreen);
        return newScreen;
    }
    
    public void removeScreen(String windowName){
        activeScreens.get(windowName).stopScreening();
        activeScreens.remove(windowName);
    }
    
    public void saveScreen(String screenName){
        activeScreens.get(this).saveToFile();
    }
    
    public void startScreenTranslation(Screen screen, ImgPanel imgPanel){
        SwingScreenThreadTranslator stt = new SwingScreenThreadTranslator(screen, imgPanel, imgPanel.getSize());
        //executor = Executors.newFixedThreadPool(1);
        //executor.execute(stt);
        executor.execute(stt);
    }
    
    public static void main(String args[]) throws InterruptedException, IOException{
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Caesar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Caesar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Caesar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Caesar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        JFrame frame = generateScreenHolderForm();
        frame.setVisible(true);
        /*char[] chars = new char[255];
        int i=255;
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow("triuiScreen", "EVE - Dart Shikari");
        //User32.INSTANCE.GetClassName(User32.INSTANCE.GetForegroundWindow(), chars, i);
        //System.out.println(User32.INSTANCE.GetForegroundWindow());
        User32.INSTANCE.GetWindowText(hwnd, chars, i);
        
        WinDef.HDC hdc = User32.INSTANCE.GetDC(hwnd);
        //GDI32.INSTANCE.ge
        //WinDef.HDC windowDC = GDI32.INSTANCE.get.GetDC(User32.);
        WinDef.HBITMAP outputBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(hdc, 1920, 1280);
        WinDef.HDC blitDC = GDI32.INSTANCE.CreateCompatibleDC(hdc);
        
        //hdc.
        System.out.println(chars);
        BufferedImage bi = ScreenCapture.getScreenshot(new Rectangle(1200,1000));// capture = new Capture();
        File outputfile = new File("name.png");
        ImageIO.write(bi, "png", outputfile);*/
        
    }
    
    public void getScreen(){
        /*triuiScreen 
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, null).GetWindow(hwnd, null);//.GetForegroundWindow();
        User32.INSTANCE..GetWindowText(hwnd, windowText, 512);
        USER.*/
    }
    
    private static JFrame generateScreenHolderForm(){
        JFrame screenHolder = new JFrame();
        screenHolder.setSize(550, 250);
        screenHolder.setLocationRelativeTo(null);
        GroupLayout layout = new GroupLayout(screenHolder.getContentPane());
        screenHolder.setLayout(layout);
        screenHolder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JScrollPane screenScroll = new JScrollPane();
        screenScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        screenScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        screenScroll.setBounds(0, 0, 410, 200);
        screenHolder.add(screenScroll);
        
        JPanel screenContainer = new JPanel();
        layout = new GroupLayout(screenContainer);
        screenContainer.setLayout(layout);
        screenContainer.setBounds(0, 0, 400, 200);
        screenScroll.add(screenContainer);
        
        JPanel windowContainer = new JPanel();
        layout = new GroupLayout(windowContainer);
        windowContainer.setLayout(layout);
        windowContainer.setBounds(410, 0, 140, 200);
        screenHolder.add(windowContainer);
        
        JScrollPane windowScroll = new JScrollPane();
        windowScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        windowScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        windowScroll.setBounds(0, 0, 140, 100);
        windowContainer.add(windowScroll);
        
        JList windowList = new JList();
        windowList.setBounds(0, 0, 140, 100);
        windowScroll.add(windowList);
        
        JButton addScreenButton = new JButton();
        addScreenButton.setText("Add screen");
        addScreenButton.setBounds(20, 110, 100, 25);
        windowContainer.add(addScreenButton);
        
        JButton refreshWindowsButton = new JButton();
        refreshWindowsButton.setText("Refres windows");
        refreshWindowsButton.setBounds(20, 150, 100, 25);
        windowContainer.add(refreshWindowsButton);
        
        screenHolder.validate();
        screenHolder.pack();
        
        return screenHolder;
    }
    
    /*private JFrame generateAreaAdministrationForm(){
        
    }*/
    
    /*private JFrame generateFieldControllerForm(){
        
    }*/
    
    
}
