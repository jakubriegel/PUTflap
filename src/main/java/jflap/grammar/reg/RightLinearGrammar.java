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





package jflap.grammar.reg;

import jflap.grammar.Production;
import jflap.grammar.ProductionChecker;

/**
 * This <CODE>RightLinearGrammar</CODE> is a jflap.regular jflap.grammar with the
 * additional restriction that the jflap.grammar cannot be a left linear jflap.grammar.
 * 
 * @author Thomas Finley
 */

public class RightLinearGrammar extends RegularGrammar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The production checker makes sure that the production added is a proper
	 * right linear production.
	 * 
	 * @param production
	 *            the production to check
	 * @throws IllegalArgumentException
	 *             if the production is not a right linear production
	 */
	public void checkProduction(Production production) {
		if (!ProductionChecker.isRightLinear(production))
			throw new IllegalArgumentException(
					"The production is not right linear.");
	}

	/** The production checker. */
	private static ProductionChecker PC = new ProductionChecker();
}
