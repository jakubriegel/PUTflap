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





package jflap.automata;

/**
 * This class is an exception that is thrown in the event an incompatible <CODE>Transition</CODE>
 * object is assigned to an automaton.
 * 
 * @see jflap.automata.Automaton
 * @see jflap.automata.Transition
 * @see jflap.automata.Automaton#getTransitionClass
 * @see jflap.automata.Automaton#addTransition
 * @author Thomas Finley
 */

public class IncompatibleTransitionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
