/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import de.humatic.dsj.CaptureDeviceControls;
import de.humatic.dsj.DSCapture;
import de.humatic.dsj.DSEnvironment;
import de.humatic.dsj.DSFilterInfo;
import de.humatic.dsj.DSFiltergraph;
import de.humatic.dsj.DSJUtils;
import de.humatic.dsj.DSMovie;
import de.humatic.dsj.SwingMovieController;

/**
 *
 * @author RAVLYK
 */
public class SimpleCapture implements java.beans.PropertyChangeListener {

	private DSCapture graph;
        private DSMovie movie;

	public SimpleCapture() {}

	public void createGraph() {

		javax.swing.JFrame f = new javax.swing.JFrame("dsj SimpleCapture");

		/** queryDevices returns video device infos in slot 0 / audio device infos in slot 1 **/

		DSFilterInfo[][] dsi = DSCapture.queryDevices();

		/** this sample only uses video **/

		graph = new DSCapture(DSFiltergraph.DD7, dsi[0][0], false, DSFilterInfo.doNotRender(), this);

		f.add(java.awt.BorderLayout.CENTER, graph.asComponent());

		f.add(java.awt.BorderLayout.SOUTH, new SwingMovieController(graph));

		final javax.swing.JButton toFile = new javax.swing.JButton("set capture file");

		toFile.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent e) {

				if (graph.getState() == DSCapture.PREVIEW) {

					/* capture to a Windows Media file using the default profile */

					graph.setCaptureFile("captureTest.asf", DSFilterInfo.doNotRender(), DSFilterInfo.doNotRender(), true);

					toFile.setText("set preview");

					/* start recording right away. Outcomment to control this from GUI */

					graph.record();

				} else {

					graph.setPreview();

					toFile.setText("set capture file");

				}

			}

		});

		f.add(java.awt.BorderLayout.NORTH, toFile);

		f.pack();

		f.setVisible(true);

		javax.swing.JFrame jf = new javax.swing.JFrame("Device control");

		jf.setLayout(new java.awt.GridLayout(0,1));

		if (graph.getActiveVideoDevice() != null && graph.getActiveVideoDevice().getControls() != null) {

			for (int i = CaptureDeviceControls.BRIGHTNESS; i < CaptureDeviceControls.LT_FINDFACE; i++) try { jf.add(graph.getActiveVideoDevice().getControls().getController(i, 0, true)); }catch (Exception ex){}

		}

		if (graph.getActiveAudioDevice() != null) for (int i = CaptureDeviceControls.MASTER_VOL; i < CaptureDeviceControls.TREBLE; i++) try { jf.add(graph.getActiveAudioDevice().getControls().getController(i, 0, true)); }catch (Exception ex){}

		if (jf.getContentPane().getComponentCount() == 0) return;

		jf.pack();

		jf.setVisible(true);

		/**
		Don't do this at home. This demo relies on dsj closing and disposing off filtergraphs when the JVM exits. This is
		OK for a "open graph, do something & exit" style demo, but real world applications should take care of calling
		dispose() on filtergraphs they're done with themselves.
		**/

		f.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	}

	public void propertyChange(java.beans.PropertyChangeEvent pe) {

		switch(DSJUtils.getEventType(pe)) {

		}

	}

	public static void main(String[] args){
                //DSEnvironment.setDLLPath("f:\\EVE_market\\EVECaesar\\lib");
		new SimpleCapture().createGraph();

	}


}
