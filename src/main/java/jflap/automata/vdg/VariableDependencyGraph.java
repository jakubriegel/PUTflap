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





package jflap.automata.vdg;

import jflap.automata.Automaton;

/**
 * This subclass of <CODE>Automaton</CODE> is specifically for a variable
 * dependency graph used in the transformation of grammars (e.g. removing unit
 * productions).
 * 
 * @author Ryan Cavalcante
 */

public class VariableDependencyGraph extends Automaton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a variable dependency graph with no states and no transitions.
	 */
	public VariableDependencyGraph() {
		super();
	}

	/**
	 * Returns the class of <CODE>Transition</CODE> this automaton must
	 * accept.
	 * 
	 * @return the <CODE>Class</CODE> object for <CODE>jflap.automata.vdg.VDGTransition</CODE>
	 */
	protected Class getTransitionClass() {
		return jflap.automata.vdg.VDGTransition.class;
	}
}
