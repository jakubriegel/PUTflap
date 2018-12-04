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

import jflap.gui.environment.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The <CODE>SaveGraphJPGAction</CODE> is an action to save the graph in window
 * to a JPG image file always using a dialog box.
 * 
 * @author Jonathan Su, Henry Qin
 */

public class SaveGraphJPGAction extends RestrictedAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The environment that this save action gets it's object from. */
	protected Environment environment;
	protected JMenu myMenu;
	
	/** The file chooser. */
	private JFileChooser fileChooser;

	/**
	 * Instantiates a new <CODE>SaveGraphBMPAction</CODE>.
	 * 
	 * @param environment
	 *            the environment that holds the action
	 * @param menu
	 *            the JMenu where the action is contained
	 */
	public SaveGraphJPGAction(Environment environment, JMenu menu) {
		super("Save Graph as JPG", null);
		this.environment = environment;
		this.myMenu = menu;
	}
	
	/**
	 * Displays JFileChooser for location to save the graph canvas as jpg image.
	 * 
	 * @param arg0
	 *            the action event
	 */
	public void actionPerformed(ActionEvent arg0) {
           Component apane = environment.tabbed.getSelectedComponent();
           JComponent c=(JComponent)environment.getActive();
           SaveGraphUtility.saveGraph(apane, c,"JPEG files", "jpg,jpeg"); 
    }  
}
