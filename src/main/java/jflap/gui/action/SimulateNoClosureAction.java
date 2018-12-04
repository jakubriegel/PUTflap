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

import jflap.automata.Automaton;
import jflap.automata.AutomatonSimulator;
import jflap.automata.fsa.FSAStepByStateSimulator;
import jflap.automata.fsa.FiniteStateAutomaton;
import jflap.automata.pda.PDAStepByStateSimulator;
import jflap.automata.pda.PushdownAutomaton;
import jflap.gui.environment.Environment;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.Serializable;

/**
 * This is the action used for the stepwise simulation of data without closure,
 * that is, without lambda transitions being automatically traversed.
 * 
 * @author Thomas Finley
 */

public class SimulateNoClosureAction extends SimulateAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new <CODE>SimulateNoClosureAction</CODE>.
	 * 
	 * @param automaton
	 *            the automaton that read will be simulated on
	 * @param environment
	 *            the environment object that we shall add our simulator pane to
	 */
	public SimulateNoClosureAction(Automaton automaton, Environment environment) {
		super(automaton, environment);
		putValue(NAME, "Step by State...");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R,
				MAIN_MENU_MASK + InputEvent.SHIFT_MASK));
	}

	/**
	 * Returns the simulator for this automaton.
	 * 
	 * @param automaton
	 *            the automaton to get the simulator for
	 * @return a simulator for this automaton
	 */
	protected AutomatonSimulator getSimulator(Automaton automaton) {
		if (automaton instanceof FiniteStateAutomaton)
			return new FSAStepByStateSimulator(automaton);
		else
			return new PDAStepByStateSimulator(automaton);
	}

	/**
	 * This particular action may only be applied to finite state
	 * 
	 * @param object
	 *            the object to test for applicability
	 * @return <CODE>true</CODE> if the passed in object is a finite state
	 *         automaton, <CODE>false</CODE> otherwise
	 */
	public static boolean isApplicable(Serializable object) {
		return object instanceof FiniteStateAutomaton
				|| object instanceof PushdownAutomaton;
	}
}
