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





package jflap.automata.pda;

import jflap.automata.Automaton;

/**
 * This subclass of <CODE>Automaton</CODE> is specifically for a definition of
 * a Pushdown Automaton.
 * 
 * @author Ryan Cavalcante
 */

public class PushdownAutomaton extends Automaton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean singleInputPDA = false;
	
	/**
	 * Creates a pushdown automaton with no states and no transitions.
	 */
	public PushdownAutomaton(boolean singleinput) {
		super();
		singleInputPDA = singleinput;
	}
	
	public PushdownAutomaton(){
		super();
	}

	/**
	 * Returns the class of <CODE>Transition</CODE> this automaton must
	 * accept.
	 * 
	 * @return the <CODE>Class</CODE> object for <CODE>jflap.automata.pda.PDATransition</CODE>
	 */
	protected Class getTransitionClass() {
		return jflap.automata.pda.PDATransition.class;
	}
}
