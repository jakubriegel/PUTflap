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





package jflap.gui.editor;

import jflap.automata.Automaton;
import jflap.automata.State;
import jflap.gui.viewer.SelectionDrawer;

/**
 * This is a view that holds a tool bar and the canvas where the automaton is
 * displayed.
 * 
 * @author Thomas Finley
 */

public class EditBlockPane extends EditorPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Instantiates a new editor pane for the given automaton.
	 * 
	 * @param automaton
	 *            the automaton to create the editor pane for
	 */
	public EditBlockPane(Automaton automaton) {
		super(new SelectionDrawer(automaton));
	}

	public void setBlock(State state) {
		myBlock = state;
	}

	public State getBlock() {
		return myBlock;
	}
	
	public void setOldBlock(State state) {
		myOldBlock = state;
	}

	public State getOldBlock() {
		return myOldBlock;
	}

	protected State myBlock = null;
	protected State myOldBlock = null;

}
