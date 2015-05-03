package forms;

import classes.Screen;
import classes.SwingScreenThreadTranslator;
import classes.ViewArea;
import imgUtils.ImageUtils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.HashMap;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import panels.AreaPanel;
import panels.ImgPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RAVLYK
 */
public class AreaAdministrator extends javax.swing.JFrame {

    /**
     * Creates new form AreaAdministrator
     */
    ViewArea viewArea;
    //HashMap <String, Rectangle> namedAreas;
    HashMap <String, AreaPanel> namedAreaPanels;
    Screen screen = null;
    String presetName;
    Thread thread = null;
    ImgPanel screenView;
    
    public AreaAdministrator() {
        initComponents();
        namedAreaPanels = new HashMap();
        this.setLocationRelativeTo(null);
        /*AreaPanel ap = new AreaPanel();
        jPanel1.add(ap);
        ap.setBounds(0, 0, 100, 100);
        jPanel1.validate();*/
        
    }
    
    public AreaAdministrator(Screen screen, String presetName, ScreenHolder screenHolder){
        this();
        this.screen = screen;
        this.presetName = presetName;
        //this.viewArea = viewArea;
        //HashMap <String, Rectangle> namedAreas = viewArea.getNamedAreas();
        if(screen.getAreaPresets().get(presetName).getAreas().size()>0){
            createAreaPanels();
            loadScrollPane();
            jList1.setSelectedIndex(0);
        }
        /*else{
            //namedAreas = new HashMap<>();
            //namedAreaPanels = new HashMap<>();
        }*/
        screenView = new ImgPanel();
        
        this.areaHolder.add(screenView);
        screenView.setBounds(0, 0, areaHolder.getSize().width, areaHolder.getSize().height);
        //areaHolder.setco
        thread = new Thread(new SwingScreenThreadTranslator(screen, screenView, screenView.getSize()));
        thread.start();
        //imgPanel1.setImg(ImageUtils.resize(viewArea.getImage(), new Dimension(imgPanel1.getWidth(),imgPanel1.getHeight())));
        
    }
    
    public void setSelectedArea(String areaName){
        int i;
        for(i=0;i<jList1.getModel().getSize();i++){
            if(jList1.getModel().getElementAt(i).toString().contentEquals(areaName)){
                break;
            }
        }
        jList1.setSelectedIndex(i);        
        selectItem();
    }
    
    private void loadScrollPane(){
        jList1.setListData(namedAreaPanels.keySet().toArray());
        
    }
    
    private void selectItem(){
        String selectedItem = jList1.getSelectedValue().toString();
        for(String key : this.namedAreaPanels.keySet()){
            this.namedAreaPanels.get(key).setBorder(new LineBorder(Color.BLUE, 2));
        }
        AreaPanel ap = this.namedAreaPanels.get(selectedItem);
        ap.setBorder(new LineBorder(Color.RED, 2));
        areaHolder.setComponentZOrder(ap, 0);
        areaHolder.repaint();
    }
    
    private void deleteSelectedPanel(){
        String selectedItem = jList1.getSelectedValue().toString();
        areaHolder.remove(namedAreaPanels.get(selectedItem));
        namedAreaPanels.remove(selectedItem);
        //screen.getAreaPresets().get(presetName).getAreas().remove(selectedItem);
        loadScrollPane();
        if(jList1.getModel().getSize()>0) {
            jList1.setSelectedIndex(0);
            selectItem();
        }
    }
    
    private void createAreaPanels(){
        for(String key : screen.getAreaPresets().get(presetName).getAreas().keySet()){
            AreaPanel ap = new AreaPanel(this,key);
            areaHolder.add(ap);
            Rectangle bound = screen.getAreaPresets().get(presetName).getAreas().get(key);
            ap.setBounds(bound.x, bound.y, bound.width, bound.height);
            areaHolder.setComponentZOrder(ap, 0);
            namedAreaPanels.put(key, ap);
        }
        areaHolder.validate();
    }
    
    private void savePreset(){
        String newPresetName = jTextField2.getText();
        
        screen.getAreaPresets().get(presetName).getAreas().clear();
        for(String key : namedAreaPanels.keySet()){
            screen.getAreaPresets().get(presetName).getAreas().put(key, namedAreaPanels.get(key).getBounds());
        }
        screen.changePresetName(presetName, newPresetName);
        screen.saveToFile();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        areaHolder = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel1.setText("Window Name");

        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jButton1.setText("Add Area");
        jButton1.setPreferredSize(new java.awt.Dimension(77, 25));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("Remove Area");
        jButton2.setPreferredSize(new java.awt.Dimension(97, 25));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jTextField1.setText("Area name");

        jTextField2.setText("Areas Preset Name");

        jButton3.setText("Save preset");
        jButton3.setPreferredSize(new java.awt.Dimension(73, 25));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout areaHolderLayout = new javax.swing.GroupLayout(areaHolder);
        areaHolder.setLayout(areaHolderLayout);
        areaHolderLayout.setHorizontalGroup(
            areaHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        areaHolderLayout.setVerticalGroup(
            areaHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jTextField2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(areaHolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(areaHolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 170, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        //this.namedAreas.put(jTextField1.getText(), new Rectangle(0,0,100,100));
        String areaName = jTextField1.getText();
        while(this.namedAreaPanels.containsKey(areaName)){
            areaName+="_new";
        }
        AreaPanel ap = new AreaPanel(this,areaName);
        areaHolder.add(ap);
        this.namedAreaPanels.put(ap.getName(), ap);
        ap.setBounds(0, 0, 100, 100);
        //screen.getAreaPresets().get(presetName).getAreas().put(ap.getName(), ap.getBounds());
        jList1.setListData(namedAreaPanels.keySet().toArray());
        jList1.setSelectedValue(ap.getName(), true);
        selectItem();
        /*AreaPanel ap = new AreaPanel();
        jPanel1.add(ap);
        ap.setBounds(0, 0, 100, 100);
        this.namedAreaPanels.put(jTextField1.getText(), ap);*/
    }//GEN-LAST:event_jButton1MouseClicked

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        selectItem();
    }//GEN-LAST:event_jList1MouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(thread!=null)this.thread.interrupt();
        savePreset();
        //screen.check();
    }//GEN-LAST:event_formWindowClosing

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        savePreset();
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        if(jList1.getModel().getSize()>0)deleteSelectedPanel();
    }//GEN-LAST:event_jButton2MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AreaAdministrator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AreaAdministrator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AreaAdministrator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AreaAdministrator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AreaAdministrator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel areaHolder;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}