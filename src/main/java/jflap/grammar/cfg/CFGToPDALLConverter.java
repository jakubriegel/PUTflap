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





package jflap.grammar.cfg;

import jflap.automata.Automaton;
import jflap.automata.State;
import jflap.automata.StatePlacer;
import jflap.automata.Transition;
import jflap.automata.pda.PDATransition;
import jflap.grammar.Grammar;
import jflap.grammar.GrammarToAutomatonConverter;
import jflap.grammar.Production;

/**
 * The CFG to PDA (LL parsing) converter can be used to convert a context free
 * jflap.grammar to a pushdown automaton that can be used for LL parsing. You can do
 * the conversion simply by calling convertToAutomaton, or you can do the
 * conversion step by step by first calling createStatesForConversion, which
 * will create all the states in the pushdown automaton necessary for the
 * conversion, and then calling getTransitionForProduction for each production
 * in the jflap.grammar. You must of course add each Transition returned by this call
 * to your pushdown automaton. When you have done this for each production in
 * your jflap.grammar, the equivalent PDA will be complete.
 * 
 * @author Ryan Cavalcante
 */

public class CFGToPDALLConverter extends GrammarToAutomatonConverter {
	/**
	 * Creates an instance of <CODE>CFGToPDALLConverter</CODE>.
	 */
	public CFGToPDALLConverter() {

	}

	/**
	 * Returns the transition created by converting <CODE>production</CODE> to
	 * its equivalent transition.
	 * 
	 * @param production
	 *            the production
	 * @return the equivalent transition.
	 */
	public Transition getTransitionForProduction(Production production) {
		String lhs = production.getLHS();
		String rhs = production.getRHS();
		Transition transition = new PDATransition(INTERMEDIATE_STATE,
				INTERMEDIATE_STATE, "", lhs, rhs);
		return transition;
	}

	/**
	 * Adds all states to <CODE>automaton</CODE> necessary for the conversion
	 * of <CODE>jflap.grammar</CODE> to its equivalent automaton. This creates three
	 * states--an initial state, an intermediate state, and a final state. It
	 * also adds transitions connecting the three states, and transitions for
	 * each terminal in <CODE>jflap.grammar</CODE>
	 * 
	 * @param grammar
	 *            the jflap.grammar being converted.
	 * @param automaton
	 *            the automaton being created.
	 */
	public void createStatesForConversion(Grammar grammar, Automaton automaton) {
		initialize();
		StatePlacer sp = new StatePlacer();

		State initialState = automaton.createState(sp
				.getPointForState(automaton));
		automaton.setInitialState(initialState);

		State intermediateState = automaton.createState(sp
				.getPointForState(automaton));
		INTERMEDIATE_STATE = intermediateState;

		State finalState = automaton
				.createState(sp.getPointForState(automaton));
		automaton.addFinalState(finalState);

		String startVariable = grammar.getStartVariable();
		String temp = startVariable.concat(BOTTOM_OF_STACK);
		PDATransition trans1 = new PDATransition(initialState,
				intermediateState, "", BOTTOM_OF_STACK, temp);
		automaton.addTransition(trans1);
		PDATransition trans2 = new PDATransition(intermediateState, finalState,
				"", BOTTOM_OF_STACK, "");
		automaton.addTransition(trans2);

		String[] terminals = grammar.getTerminals();
		for (int k = 0; k < terminals.length; k++) {
			PDATransition trans = new PDATransition(intermediateState,
					intermediateState, terminals[k], terminals[k], "");
			automaton.addTransition(trans);
		}

	}

	/** the intermediate state in the automaton. */
	protected State INTERMEDIATE_STATE;
}
