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
 * This lambda checker factory returns a lambda transition checker for the type
 * of automaton passed in.
 * 
 * @author Ryan Cavalcante
 */

public class LambdaCheckerFactory {
	/**
	 * Returns the lambda transition checker for the type of automaton that
	 * <CODE>automaton</CODE> is.
	 * 
	 * @param automaton
	 *            the automaton to get the checker for
	 * @return the lambda transition checker for the type of automaton that
	 *         <CODE>automaton</CODE> is or <CODE>null</CODE> if there is no
	 *         lambda transition checker for this type of automaton
	 */
	public static LambdaTransitionChecker getLambdaChecker(Automaton automaton) {
		if (automaton instanceof jflap.automata.fsa.FiniteStateAutomaton)
			return new jflap.automata.fsa.FSALambdaTransitionChecker();
		else if (automaton instanceof jflap.automata.pda.PushdownAutomaton)
			return new jflap.automata.pda.PDALambdaTransitionChecker();
		else if (automaton instanceof jflap.automata.turing.TuringMachine)
			return new jflap.automata.turing.TMLambdaTransitionChecker();
        else if(automaton instanceof jflap.automata.mealy.MealyMachine)
            return new jflap.automata.mealy.MealyLambdaTransitionChecker();
		return null;
	}

}
