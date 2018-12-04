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





package jflap.grammar.parse;

import jflap.automata.State;
import jflap.automata.Transition;
import jflap.automata.fsa.FSATransition;
import jflap.automata.fsa.FiniteStateAutomaton;
import jflap.grammar.Grammar;
import jflap.grammar.Production;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This class generates {@link jflap.grammar.parse.LRParseTable}s. The intention is
 * that this shall be used once the item goto graph is generated.
 * 
 * @author Thomas Finley
 */

public abstract class LRParseTableGenerator {
	/**
	 * Generates an LR parse table.
	 * 
	 * @param grammar
	 *            the augmented jflap.grammar
	 * @param gotoGraph
	 *            the FSA that represents the completed goto graph
	 * @param stateToItems
	 *            the mapping of states to items
	 * @param itemsToState
	 *            the mapping of items to states
	 * @param followSets
	 *            the mapping of variables to follow sets
	 */
	public static LRParseTable generate(Grammar grammar,
			FiniteStateAutomaton gotoGraph, Map<State, Set<Production>> stateToItems, Map<Set<Production>, State> itemsToState,
			Map<String, Set<String>> followSets) {
		LRParseTable pt = new LRParseTable(grammar, gotoGraph) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		Transition[] ts = gotoGraph.getTransitions();
		Production[] ps = grammar.getProductions();
		for (int i = 0; i < ts.length; i++) {
			FSATransition t = (FSATransition) ts[i];
			if (grammar.isVariable(t.getLabel())) {
				// Is a move.
				pt.appendValueAt("" + t.getToState().getID(), t.getFromState()
						.getID(), t.getLabel());
			} else {
				// Is a shift.
				pt.appendValueAt("s" + t.getToState().getID(), t.getFromState()
						.getID(), t.getLabel());
			}
		}
		// Find the acceptance and reduction.
		State[] finals = gotoGraph.getFinalStates();
		for (int i = 0; i < finals.length; i++) {
			Set<Production> items = (Set<Production>) stateToItems.get(finals[i]);
			Iterator<Production> it = items.iterator();
			while (it.hasNext()) {
				Production p = (Production) it.next();
				if (p.getLHS().length() == 2) {
					// This is the S' production.
					if (p.getRHS().length() == 2
							&& p.getRHS().charAt(1) == Operations.ITEM_POSITION)
						pt.appendValueAt("acc", finals[i].getID(), "$");
					continue;
				}
				if (p.getRHS().endsWith("" + Operations.ITEM_POSITION)) {
					Production p2 = new Production(p.getLHS(), p.getRHS()
							.substring(0, p.getRHS().length() - 1));
					int j = 0;
					while (!p2.equals(ps[j]))
						j++;
					Set<String> follow = (Set<String>) followSets.get(p.getLHS());
					Iterator<String> fit = follow.iterator();
					while (fit.hasNext()) {
						String followSymbol = (String) fit.next();
						pt.appendValueAt("r" + j, finals[i].getID(),
								followSymbol);
					}
				}
			}
		}
		return pt;
	}
}
