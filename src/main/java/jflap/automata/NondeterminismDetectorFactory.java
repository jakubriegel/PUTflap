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

import jflap.automata.fsa.FSANondeterminismDetector;
import jflap.automata.pda.PDANondeterminismDetector;
import jflap.automata.turing.TMNondeterminismDetector;

/**
 * The <CODE>NondeterminismDetectorFactory</CODE> is a factory class for
 * returning a <CODE>NondeterminismDetector</CODE> based on the automaton that
 * is passed in.
 * 
 * @author Thomas Finley
 */

public class NondeterminismDetectorFactory {
	/**
	 * Returns the nondeterminism detector for this type of automaton.
	 * 
	 * @param automaton
	 *            the automaton to get the nondeterminism detector for
	 * @return the appropriate automaton simulator for this automaton, or <CODE>null</CODE>
	 *         if there is no automaton simulator known for this type of
	 *         automaton
	 */
	public static NondeterminismDetector getDetector(Automaton automaton) {
		if (automaton instanceof jflap.automata.fsa.FiniteStateAutomaton)
			return new FSANondeterminismDetector();
		else if (automaton instanceof jflap.automata.pda.PushdownAutomaton)
			return new PDANondeterminismDetector();
		else if (automaton instanceof jflap.automata.turing.TuringMachine)
			return new TMNondeterminismDetector();
        else if(automaton instanceof jflap.automata.mealy.MealyMachine)
            return new jflap.automata.mealy.MealyNondeterminismDetector();
		return null;
	}
}
