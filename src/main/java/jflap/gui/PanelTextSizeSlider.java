/*
 *  JFLAP - Formal Languages and Automata Package
 * 
 * 
 *  Susan H. Rodger
 *  Computer Science Department
 *  Duke University
 *  August 27, 2009

 *  Copyright (c) 2002-2009
 *  All rights reserved.

 *  JFLAP is open source software. Please see the LICENSE for terms.
 *
 */




package jflap.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;


/**
 * A JSlider that adjusts the size of JTable cells and the font.
* @author Jonathan Su
*/

public class PanelTextSizeSlider extends JSlider{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int FONT_SIZE_MIN = 1;
    static final int FONT_SIZE_MAX = 600;
    static final int FONT_SIZE_INIT = 200;
    static final String PANEL_SIZE_TITLE = "Panel Text Size";
    
    JPanel myPanel;
	
	public PanelTextSizeSlider(JPanel panel) {
		super(FONT_SIZE_MIN, FONT_SIZE_MAX, FONT_SIZE_INIT); 
	    this.addChangeListener(new SliderListener());
	    setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), PANEL_SIZE_TITLE));
	    myPanel = panel;
	}


      class SliderListener implements ChangeListener {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                myPanel.setFont(new Font("Default", Font.PLAIN, source.getValue()/10));
                myPanel.setSize(myPanel.getWidth(), (int) source.getValue()/10+10);
            }
      }
}
