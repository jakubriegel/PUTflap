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





package jflap.gui.lsystem;

import java.util.EventObject;

/**
 * This event is given to listeners of an L-system read pane who are interested
 * when the read system registers a change.
 * 
 * @see jflap.gui.lsystem.LSystemInputPane
 * @see jflap.gui.lsystem.LSystemInputListener
 * 
 * @author Thomas Finley
 */

public class LSystemInputEvent extends EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new <CODE>LSystemInputEvent</CODE>.
	 * 
	 * @param input
	 *            the <CODE>LSystemInputPane</CODE> that was edited
	 */
	public LSystemInputEvent(LSystemInputPane input) {
		super(input);
	}

	/**
	 * Returns the <CODE>LSystemInputPane</CODE> that generated this event.
	 * 
	 * @return the <CODE>LSystemInputPane</CODE> that generated this event
	 */
	public LSystemInputPane getInputPane() {
		return (LSystemInputPane) getSource();
	}
}
