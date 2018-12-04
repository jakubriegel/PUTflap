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
 * A JSlider that adjusts the size of JTextFields and the font.
* @author Jay Patel
*/

public class TripleTextFieldSizeSlider extends JSlider{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int FONT_SIZE_MIN = 1;
    static final int FONT_SIZE_MAX = 600;
    static final int FONT_SIZE_INIT = 300;
    static String TEXTFIELD_SIZE_TITLE = "Input Field Text Size "
    		+ "(For optimization, move one of the window size adjustors around this window after resizing the text field)";
    
    JTextField myField1;
    JTextField myField2;
    JTextField myField3;
	
	public TripleTextFieldSizeSlider(JTextField field1, JTextField field2, JTextField field3, int orientation, String title) {
		super(orientation, FONT_SIZE_MIN, FONT_SIZE_MAX, FONT_SIZE_INIT);
		if (title.equals("Input Field Text Size")) {
			title = TEXTFIELD_SIZE_TITLE;
		}
	    this.addChangeListener(new SliderListener());
	    setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title));
	    //setMajorTickSpacing(100);
	    //setMinorTickSpacing(25);
	    //setPaintTicks(true);
	    myField1 = field1;
	    myField2 = field2;
	    myField3 = field3;
	}


      class SliderListener implements ChangeListener {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                myField1.setFont(new Font("Default", Font.PLAIN, source.getValue()/10));
                myField1.setSize(myField1.getWidth(), (int) source.getValue()/10+10);
                myField2.setFont(new Font("Default", Font.PLAIN, source.getValue()/10));
                myField2.setSize(myField2.getWidth(), (int) source.getValue()/10+10);
                myField3.setFont(new Font("Default", Font.PLAIN, source.getValue()/10));
                myField3.setSize(myField3.getWidth(), (int) source.getValue()/10+10);
            }
      }
} 