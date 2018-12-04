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





package jflap.automata.fsa;

import jflap.automata.Automaton;
import jflap.automata.State;
import jflap.automata.Transition;
import jflap.grammar.Production;
import jflap.grammar.reg.RegularGrammar;

import java.util.*;

/**
 * The FSA to jflap.regular jflap.grammar converter can be used to convert a finite state
 * automaton into an equivalent jflap.regular jflap.grammar. The fsa and jflap.grammar will be
 * equivalent in that they will accept exactly the same language. In order to
 * use this converter you must first call initializeConverter to map the states
 * in the fsa to variables in the jflap.regular jflap.grammar. Then you can perform the
 * conversion simply by calling convertToRegularGrammar, or you can perform the
 * conversion step by step by repeatedly calling getProductionForTransition for
 * every transition in the automaton and adding the returned productions to the
 * jflap.grammar. If you do this for every Transition in the fsa, you will have an
 * equivalent jflap.regular jflap.grammar.
 * 
 * @see jflap.grammar.reg.RegularGrammar
 * 
 * @author Ryan Cavalcante
 */

public class FSAToRegularGrammarConverter {
	/**
	 * Creates an instance of <CODE>FSAToRegularGrammarConverter</CODE>
	 */
	public FSAToRegularGrammarConverter() {

	}

	/**
	 * Maps the states in <CODE>automaton</CODE> to the variables in the
	 * jflap.grammar the converter will produce.
	 * 
	 * @param automaton
	 *            the automaton.
	 */
	public void initializeConverter(Automaton automaton) {
		MAP = new HashMap<>();
		State[] states = automaton.getStates();
		State initialState = automaton.getInitialState();
		// Do the variables.
		VARIABLE = new LinkedList<>();
		for (char c = 'A'; c <= 'Z'; c++)
			VARIABLE.add("" + c);
		// Map the initial state to S.
		if (initialState != null) {
			VARIABLE.remove(START_VARIABLE);
			MAP.put(initialState, START_VARIABLE);
		}
		// Assign variables to the other states.
		List<Object> stateList = new ArrayList<>(Arrays.asList(states));
		stateList.remove(initialState);
		Collections.sort(stateList, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				return ((State) o1).getID() - ((State) o2).getID();
			}

			public boolean equals(Object o) {
				return false;
			}
		});
		Iterator<Object> it = stateList.iterator();
		while (it.hasNext()) {
			State state = (State) it.next();
			MAP.put(state, VARIABLE.removeFirst());
		}
	}

	/**
	 * Returns a production object that is equivalent to <CODE>transition</CODE>.
	 * 
	 * @param transition
	 *            the transition.
	 * @return a produciton object that is equivalent to <CODE>transition</CODE>.
	 */
	public Production getProductionForTransition(Transition transition) {
		FSATransition trans = (FSATransition) transition;

		State toState = trans.getToState();
		State fromState = trans.getFromState();
		String label = trans.getLabel();
		String lhs = (String) MAP.get(fromState);
		String rhs = label.concat((String) MAP.get(toState));
		Production production = new Production(lhs, rhs);

		return production;
	}

	/**
	 * Returns a lambda production on the variable mapped to <CODE>state</CODE>
	 * in <CODE>map</CODE>.
	 * 
	 * @param automaton
	 *            the automaton that <CODE>state</CODE> is in.
	 * @param state
	 *            the state to make the lambda production for.
	 * @return a lambda production on the variable mapped to <CODE>state</CODE>
	 *         in <CODE>MAP</CODE>.
	 */
	public Production getLambdaProductionForFinalState(Automaton automaton,
			State state) {
		/** Check if state is a final state. */
		if (!automaton.isFinalState(state)) {
			System.err.println(state + " IS NOT A FINAL STATE");
			return null;
		}
		String llhs = (String) MAP.get(state);
		String lrhs = LAMBDA;
		Production lprod = new Production(llhs, lrhs);
		return lprod;
	}

	/**
	 * Returns a RegularGrammar object that represents a jflap.grammar equivalent to
	 * <CODE>automaton</CODE>.
	 * 
	 * @param automaton
	 *            the automaton.
	 * @return a jflap.regular jflap.grammar equivalent to <CODE>automaton</CODE>
	 */
	public RegularGrammar convertToRegularGrammar(Automaton automaton) {
		/** check if automaton is fsa. */
		if (!(automaton instanceof FiniteStateAutomaton)) {
			System.err.println("ATTEMPTING TO CONVERT NON FSA TO "
					+ "REGULAR GRAMMAR");
			return null;
		}

		RegularGrammar grammar = new RegularGrammar();
		/** map states in automaton to variables in jflap.grammar. */
		initializeConverter(automaton);
		/**
		 * go through all transitions in automaton, creating production for
		 * each.
		 */
		Transition[] transitions = automaton.getTransitions();
		for (int k = 0; k < transitions.length; k++) {
			Production production = getProductionForTransition(transitions[k]);
			grammar.addProduction(production);
		}

		/**
		 * for all final states in automaton, add lambda production.
		 */
		State[] finalStates = automaton.getFinalStates();
		for (int j = 0; j < finalStates.length; j++) {
			Production lprod = getLambdaProductionForFinalState(automaton,
					finalStates[j]);
			grammar.addProduction(lprod);
		}

		return grammar;
	}

	/**
	 * Returns the variable in the jflap.grammar corresponding to the state.
	 * 
	 * @param state
	 *            the state to get the variable for
	 * @return the variable in the jflap.grammar corresponding to the state, or <CODE>null</CODE>
	 *         if there is no variable corresponding to this state
	 */
	public String variableForState(State state) {
		return (String) MAP.get(state);
	}

	/** The map of states in the fsa to variables in the jflap.grammar. */
	protected HashMap<State, String> MAP;

	/** The start variable. */
	protected static final String START_VARIABLE = "S";

	/** The string for lambda. */
	protected static final String LAMBDA = "";

	/** The list of unclaimed variable symbols. */
	protected LinkedList<String> VARIABLE;
}
