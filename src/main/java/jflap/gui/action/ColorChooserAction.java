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





package jflap.gui.action;

import jflap.gui.editor.TransitionTool;
import jflap.gui.environment.Profile;
import jflap.gui.environment.Universe;
import jflap.gui.viewer.CurvedArrow;
import jflap.gui.viewer.StateDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
* The <CODE>ColorChooserAction</CODE> is an action to display a window with various color 
* palette options and allow the user to choose one.
* 
* @author Jay Patel
*/

public class ColorChooserAction extends  RestrictedAction{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFileChooser fileChooser;

    /**
     * Instantiates a new <CODE>Turing Test Action</CODE>.
     */
    public ColorChooserAction() {
        //super("Test Turing Machines", null);
    	super("Color Palette Chooser", null);   
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                MAIN_MENU_MASK));
        this.fileChooser = Universe.CHOOSER;
    }

	public void actionPerformed(ActionEvent e) {
		Profile current = Universe.curProfile;
		
//		JFrame.setDefaultLookAndFeelDecorated(true);    
		final JFrame frame = new JFrame("Color Palettes");
		
		JRadioButton original = new JRadioButton("Original");
		original.setMnemonic(KeyEvent.VK_O);
        original.setActionCommand("Original");
        original.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event){
        		current.setColor("Original");
        		StateDrawer.STATE_COLOR = new Color(255, 255, 150);
        		StateDrawer.HIGHLIGHT_COLOR = new Color(100, 200, 200);
        		TransitionTool.COLOR = new Color(95,95,95);
        		CurvedArrow.ARROW_COLOR = new Color(0,0,0);
        		CurvedArrow.HIGHLIGHT_COLOR = new Color(255, 0, 0);
        	}
        });
        JRadioButton blue = new JRadioButton("Blue");
        blue.setMnemonic(KeyEvent.VK_B);
        blue.setActionCommand("Blue");
        blue.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event){
        		current.setColor("Blue");
        		StateDrawer.STATE_COLOR = new Color(225, 255, 255);
        		StateDrawer.HIGHLIGHT_COLOR = new Color(145,219,227);
        		TransitionTool.COLOR = new Color(116,120,128);
        		CurvedArrow.ARROW_COLOR = new Color(116,120,128);
        		CurvedArrow.HIGHLIGHT_COLOR = new Color(86,97,120);
        	}
        });
        
        JRadioButton green = new JRadioButton("Green");
        green.setMnemonic(KeyEvent.VK_G);
        green.setActionCommand("Green");
        green.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event){
        		current.setColor("Green");
        		StateDrawer.STATE_COLOR = new Color(186,255,184);
        		StateDrawer.HIGHLIGHT_COLOR = new Color(124,230,120);
        		TransitionTool.COLOR = new Color(219,160,161);
        		CurvedArrow.ARROW_COLOR = new Color(219,160,161);  
        		CurvedArrow.HIGHLIGHT_COLOR = new Color(194,103,103);		
        	}
        });
        
        JRadioButton orange = new JRadioButton("Orange");
        orange.setMnemonic(KeyEvent.VK_R);
        orange.setActionCommand("Orange");
        orange.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event){
        		current.setColor("Orange");
        		StateDrawer.STATE_COLOR = new Color(255,190,121);
        		StateDrawer.HIGHLIGHT_COLOR = new Color(204,123,42);
        		TransitionTool.COLOR = new Color(110,99,86);
        		CurvedArrow.ARROW_COLOR = new Color(110,99,86);  
        		CurvedArrow.HIGHLIGHT_COLOR = new Color(97,94,84);		
        	}
        });
        
        JRadioButton purple = new JRadioButton("Purple");
        purple.setMnemonic(KeyEvent.VK_P);
        purple.setActionCommand("Purple");
        purple.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event){
        		current.setColor("Purple");
        		StateDrawer.STATE_COLOR = new Color(230,230,250);
        		StateDrawer.HIGHLIGHT_COLOR = new Color(152,141,252);
        		TransitionTool.COLOR = new Color(0,0,94);
        		CurvedArrow.ARROW_COLOR = new Color(0,0,94);  
        		CurvedArrow.HIGHLIGHT_COLOR = new Color(100,149,237);		
        	}
        });
        
        JRadioButton pink = new JRadioButton("Pink");
        pink.setMnemonic(KeyEvent.VK_K);
        pink.setActionCommand("Pink");
        pink.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event){
        		current.setColor("Pink");
        		StateDrawer.STATE_COLOR = new Color(255,214,219);
        		StateDrawer.HIGHLIGHT_COLOR = new Color(250,173,178);
        		//247, 176, 219
        		TransitionTool.COLOR = new Color(116,120,128);
        		CurvedArrow.ARROW_COLOR = new Color(116,120,128);  
        		CurvedArrow.HIGHLIGHT_COLOR = new Color(199,185,192);		
        	}
        });
        
        if(current.getColor().equals("Original"))	original.setSelected(true);
        else if(current.getColor().equals("Blue")) blue.setSelected(true);
        else if(current.getColor().equals("Green")) green.setSelected(true);
        else if(current.getColor().equals("Orange")) orange.setSelected(true);
        else if(current.getColor().equals("Purple")) purple.setSelected(true);
        else if(current.getColor().equals("Pink")) purple.setSelected(true);
        
        ButtonGroup group = new ButtonGroup();
        group.add(original);
        group.add(blue);
        group.add(green);
        group.add(orange);
        group.add(purple);
        group.add(pink);

		JPanel panel = new JPanel();
		panel.add(original);
		panel.add(blue);
		panel.add(green);
		panel.add(orange);
		panel.add(purple);
		panel.add(pink);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JButton accept = new JButton("Accept");
		accept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				frame.setVisible(false);
				Universe.curProfile.savePreferences();
			}
		});

		frame.getContentPane().add(accept, BorderLayout.SOUTH);
		frame.pack();
		Point point = new Point(100, 50);
		frame.setLocation(point);
		frame.setVisible(true);
	}
}
