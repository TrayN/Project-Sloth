/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;

import classes.Caesar;
import classes.Screen;
import classes.ScreenAreasPreset;
import classes.ScreenThreadReader;
import classes.ScreenThreadTranslator;
import classes.SwingScreenThreadTranslator;
import classes.ViewPresets;
import forms.ScreenHolder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.*;

/**
 *
 * @author RAVLYK
 */
public class ScreenPanel extends JPanel{
    
    ImgPanel imagePanel = new ImgPanel();
    JLabel screenName = new JLabel();
    JComboBox presetList = new JComboBox();
    JButton createPresetButton = new JButton();
    JButton changePresetButton = new JButton();
    JButton removePresetButton = new JButton();
    JButton removePanelButton = new JButton();
    
    
    
    Screen screen;
    //ExecutorService executor;
    Caesar caesar;
    
    HashMap <String, ScreenAreasPreset> areasPresets = null;
    ScreenAreasPreset preset = null;
    ScreenHolder screenHolder = null;
    
    Thread thread = null;
    
    public ScreenPanel(String windowName, ScreenHolder screenHolder, Caesar caesar){
        this.caesar = caesar;
        
        this.screen = caesar.getScreenByWindowName(windowName);
        this.areasPresets = screen.getAreaPresets();
        this.screen.startScreening();
        this.screenHolder = screenHolder;
        
        this.setPreferredSize(new Dimension(400, 200));
        Border border = new LineBorder(Color.BLACK,1);
        this.setBorder(border);

        screenName.setText(windowName);
        Font font = new Font("Tahoma", Font.BOLD, 17);
        screenName.setFont(font);
        screenName.setBounds(25,10,150,25);
        
        for(String key : areasPresets.keySet()){
            presetList.addItem(key);
        }
        if(presetList.getItemCount()>0) presetList.setSelectedIndex(0);
        presetList.setBounds(25, 40, 150, 20);
        
        createPresetButton.setBounds(25,70,150,25);
        createPresetButton.setText("Create preset");
        
        changePresetButton.setBounds(25,100,150,25);
        changePresetButton.setText("Change preset");
        
        removePresetButton.setBounds(25, 130, 150, 25);
        removePresetButton.setText("RemovePreset");
        
        removePanelButton.setBounds(10, 165, 150, 25);
        removePanelButton.setText("Remove View");
        
        imagePanel.setBounds(200, 25, 200, 150);

        GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        this.add(screenName);
        this.add(presetList);
        this.add(imagePanel);
        this.add(removePanelButton);
        this.add(removePresetButton);
        this.add(createPresetButton);
        this.add(changePresetButton);
        this.validate();
        
        addButtonListeners();
        startScreenTranslationThread();        
    }
    
    public void startScreenTranslationThread(){
        if(this.thread == null) this.thread = new Thread(new SwingScreenThreadTranslator(screen, imagePanel, imagePanel.getSize()));
        this.thread.start();
        //caesar.startScreenTranslation(screen, imagePanel);
        //ScreenThreadReader str = new ScreenThreadReader(screen, Caesar.EVE_WINDOW_CLASS, windowName, new Rectangle(1920,1280));
        //ScreenThreadTranslator stt = new ScreenThreadTranslator(screen, imagePanel, imagePanel.getSize());
        //SwingScreenThreadTranslator stt = new SwingScreenThreadTranslator(screen, imagePanel, imagePanel.getSize());
        //executor = Executors.newFixedThreadPool(1);
        //executor.execute(stt);
        //executor.execute(stt);
    }
    
    public void stopScreenTranslationThread(){
        if(this.thread!=null){
            this.thread.interrupt();
            //this.thread.stop();
        }
    }
    
    private void addButtonListeners(){
        this.createPresetButton.addMouseListener(
            new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    ScreenAreasPreset newPreset = new ScreenAreasPreset("New Preset");
                    //should check if New Preset name already exists
                    areasPresets.put(newPreset.getName(), newPreset);
                    screenHolder.startAreaAdministrator(screen, newPreset);
                }
            }
        );
        
        this.changePresetButton.addMouseListener(null);
        
        this.removePanelButton.addMouseListener(null);
        
        this.removePanelButton.addMouseListener(
            new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    stopScreenTranslationThread();
                    screenHolder.removeScreenPanel(screen.getName());
                    caesar.removeScreen(screen.getName());
                }
            }
        );
        
        
        
        
        
    }
    
    /*private void loadPresets(String windowName){
        File f = new File(Caesar.PRESET_FILE_PATH);
        HashMap <String, ViewPresets> allPresets = null;
        if(!f.exists()){
            presets = new ViewPresets(this.windowName);
            return;
        }
        FileInputStream fStream;
        try {
            fStream = new FileInputStream(f);
            ObjectInputStream fObject = new ObjectInputStream(fStream);
            allPresets = (HashMap <String, ViewPresets>)fObject.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Caesar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Caesar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Caesar.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(allPresets==null){
            presets = new ViewPresets(this.windowName);
            return;
        }
        else{
            presets = allPresets.get(windowName);
            if(presets == null) presets = new ViewPresets(this.windowName);
        }
    }*/
    
    /*public void savePresets(){
        screenHolder.savePresets();
        /*File f = new File(Optimization.FUNCTION_PARAMS_PATH);
        f.delete();
        if(functionParams==null){return;}
        try {
            f.createNewFile();
            FileOutputStream fStream;
            fStream = new FileOutputStream(f);
            ObjectOutputStream fObject = new ObjectOutputStream(fStream);
                fObject.writeObject(this.functionParams);
            fObject.flush();
            fObject.close();
            fStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Optimization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Optimization.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
    
}
