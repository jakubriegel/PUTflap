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





package jflap.grammar;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * The Grammar checker object can be used to check certain properties of jflap.grammar
 * objects.
 * 
 * @author Ryan Cavalcante
 */

public class GrammarChecker {
	/**
	 * Creates an instance of <CODE>GrammarChecker</CODE>.
	 */
	public GrammarChecker() {

	}

	/**
	 * Returns true if <CODE>jflap.grammar</CODE> is a jflap.regular jflap.grammar (i.e. if it
	 * is either a right or left linear jflap.grammar).
	 * 
	 * @param grammar
	 *            the jflap.grammar.
	 * @return true if <CODE>jflap.grammar</CODE> is a jflap.regular jflap.grammar.
	 */
	public static boolean isRegularGrammar(Grammar grammar) {
		if (isRightLinearGrammar(grammar) || isLeftLinearGrammar(grammar))
			return true;
		return false;
	}

	/**
	 * Returns true if <CODE>jflap.grammar</CODE> is a right-linear jflap.grammar (i.e.
	 * all productions are of the form A->xB or A->x).
	 * 
	 * @param grammar
	 *            the jflap.grammar.
	 * @return true if <CODE>jflap.grammar</CODE> is a right-linear jflap.grammar.
	 */
	public static boolean isRightLinearGrammar(Grammar grammar) {
		ProductionChecker pc = new ProductionChecker();
		Production[] productions = grammar.getProductions();
		for (int k = 0; k < productions.length; k++) {
			if (!ProductionChecker.isRightLinear(productions[k]))
				return false;
		}
		return true;
	}

	/**
	 * Returns true if <CODE>jflap.grammar</CODE> is a left-linear jflap.grammar (i.e. all
	 * productions are of the form A->Bx or A->x).
	 * 
	 * @param grammar
	 *            the jflap.grammar.
	 * @return true if <CODE>jflap.grammar</CODE> is a left-linear jflap.grammar.
	 */
	public static boolean isLeftLinearGrammar(Grammar grammar) {
		ProductionChecker pc = new ProductionChecker();
		Production[] productions = grammar.getProductions();
		for (int k = 0; k < productions.length; k++) {
			if (!ProductionChecker.isLeftLinear(productions[k]))
				return false;
		}
		return true;
	}

	/**
	 * Returns true if <CODE>jflap.grammar</CODE> is a context-free jflap.grammar (i.e.
	 * all productions are of the form A->x).
	 * 
	 * @param grammar
	 *            the jflap.grammar.
	 * @return true if <CODE>jflap.grammar</CODE> is a context-free jflap.grammar.
	 */
	public static boolean isContextFreeGrammar(Grammar grammar) {
		ProductionChecker pc = new ProductionChecker();
		Production[] productions = grammar.getProductions();
		for (int k = 0; k < productions.length; k++) {
			if (!ProductionChecker.isRestrictedOnLHS(productions[k]))
				return false;
		}
		return true;
	}

	/**
	 * Returns true if <CODE>variable</CODE> is in any production, either on
	 * the right or left hand side of the production, of <CODE>jflap.grammar</CODE>.
	 * 
	 * @param variable
	 *            the variable.
	 * @return true if <CODE>variable</CODE> is in any production of <CODE>jflap.grammar</CODE>.
	 */
	public static boolean isVariableInProductions(Grammar grammar,
			String variable) {
		ProductionChecker pc = new ProductionChecker();
		Production[] productions = grammar.getProductions();
		for (int k = 0; k < productions.length; k++) {
			if (ProductionChecker.isVariableInProduction(variable,
					productions[k])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if <CODE>terminal</CODE> is in any production, either on
	 * the right or left hand side of the production, of <CODE>jflap.grammar</CODE>.
	 * 
	 * @param terminal
	 *            the terminal.
	 * @return true if <CODE>terminal</CODE> is in any production in <CODE>jflap.grammar</CODE>.
	 */
	public static boolean isTerminalInProductions(Grammar grammar,
			String terminal) {
		ProductionChecker pc = new ProductionChecker();
		Production[] productions = grammar.getProductions();
		for (int k = 0; k < productions.length; k++) {
			if (ProductionChecker.isTerminalInProduction(terminal,
					productions[k])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns all productions in <CODE>jflap.grammar</CODE> whose lhs is <CODE>variable</CODE>.
	 * 
	 * @param variable
	 *            the variable
	 * @param grammar
	 *            the jflap.grammar
	 * @return all productions in <CODE>jflap.grammar</CODE> whose lhs is <CODE>variable</CODE>.
	 */
	public static Production[] getProductionsOnVariable(String variable,
			Grammar grammar) {
		ArrayList<Production> list = new ArrayList<>();
		ProductionChecker pc = new ProductionChecker();
		Production[] productions = grammar.getProductions();
		for (int k = 0; k < productions.length; k++) {
			if (variable.equals(productions[k].getLHS())) {
				list.add(productions[k]);
			}
		}
		return (Production[]) list.toArray(new Production[0]);
	}

	/**
	 * Returns all productions in <CODE>jflap.grammar</CODE> that have <CODE>variable</CODE>
	 * as the only character on the left hand side and that are not unit
	 * productions.
	 * 
	 * @param variable
	 *            the variable
	 * @param grammar
	 *            the jflap.grammar
	 * @return all productions in <CODE>jflap.grammar</CODE> that have <CODE>variable</CODE>
	 *         as the only character on the left hand side and are not unit
	 *         productions.
	 */
	public static Production[] getNonUnitProductionsOnVariable(String variable,
			Grammar grammar) {
		ArrayList<Production> list = new ArrayList<>();
		ProductionChecker pc = new ProductionChecker();
		Production[] productions = grammar.getProductions();
		for (int k = 0; k < productions.length; k++) {
			if (variable.equals(productions[k].getLHS())
					&& !ProductionChecker.isUnitProduction(productions[k])) {
				list.add(productions[k]);
			}
		}
		return (Production[]) list.toArray(new Production[0]);
	}

	/**
	 * Returns true if <CODE>production</CODE>, or an identical production,
	 * is already in <CODE>jflap.grammar</CODE>.
	 * 
	 * @param production
	 *            the production
	 * @param grammar
	 *            the jflap.grammar
	 * @return true if <CODE>production</CODE>, or an identical production,
	 *         is already in <CODE>jflap.grammar</CODE>.
	 */
	public static boolean isProductionInGrammar(Production production,
			Grammar grammar) {
		Production[] productions = grammar.getProductions();
		for (int k = 0; k < productions.length; k++) {
			if (production.equals(productions[k]))
				return true;
		}
		return false;
	}

	/**
	 * Returns all productions in <CODE>jflap.grammar</CODE> that have <CODE>variable</CODE>
	 * in them, either on the rhs or lhs.
	 * 
	 * @param variable
	 *            the variable
	 * @param grammar
	 *            the jflap.grammar
	 * @return all productions in <CODE>jflap.grammar</CODE> that have <CODE>variable</CODE>
	 *         in them, either on the rhs or lhs.
	 */
	public static Production[] getProductionsWithVariable(String variable,
			Grammar grammar) {
		ArrayList<Production> list = new ArrayList<>();
		ProductionChecker pc = new ProductionChecker();
		Production[] productions = grammar.getProductions();
		for (int k = 0; k < productions.length; k++) {
			if (ProductionChecker.isVariableInProduction(variable,
					productions[k])) {
				list.add(productions[k]);
			}
		}
		return (Production[]) list.toArray(new Production[0]);
	}

	/**
	 * Returns all productions in <CODE>jflap.grammar</CODE> that have <CODE>variable</CODE>
	 * on the right hand side.
	 * 
	 * @param variable
	 *            the variable
	 * @param grammar
	 *            the jflap.grammar
	 * @return all productions in <CODE>jflap.grammar</CODE> that have <CODE>variable</CODE>
	 *         on the right hand side.
	 */
	public static Production[] getProductionsWithVariableOnRHS(String variable,
			Grammar grammar) {
		ProductionChecker pc = new ProductionChecker();
		ArrayList<Production> list = new ArrayList<>();
		Production[] productions = grammar.getProductions();
		for (int k = 0; k < productions.length; k++) {
			if (ProductionChecker.isVariableOnRHS(productions[k], variable))
				list.add(productions[k]);
		}
		return (Production[]) list.toArray(new Production[0]);
	}

	/**
	 * Returns a list of those variables which are unresolved, i.e., which
	 * appears in the right hand side but do not appear in the left hand side.
	 * 
	 * @param grammar
	 *            the jflap.grammar to check
	 * @return an array of the unresolved variables
	 */
	public static String[] getUnresolvedVariables(Grammar grammar) {
		String[] variables = grammar.getVariables();
		HashSet<String> variableSet = new HashSet<String>();
		for (int i = 0; i < variables.length; i++)
			variableSet.add(variables[i]);
		Production[] productions = grammar.getProductions();
		for (int i = 0; i < productions.length; i++) {
			String[] lhsVariables = productions[i].getVariablesOnLHS();
			for (int j = 0; j < lhsVariables.length; j++)
				variableSet.remove(lhsVariables[j]);
		}
		return (String[]) variableSet.toArray(new String[0]);
	}
}
