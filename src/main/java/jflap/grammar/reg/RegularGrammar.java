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

import jflap.grammar.Grammar;
import jflap.grammar.Production;
import jflap.grammar.ProductionChecker;

/**
 * The jflap.regular jflap.grammar object is a representation of a jflap.regular jflap.grammar. This
 * object is a data structure of sorts, maintaining the data pertinent to the
 * definition of a jflap.regular jflap.grammar.
 * 
 * @author Ryan Cavalcante
 */

public class RegularGrammar extends Grammar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates an instance of <CODE>RegularGrammar</CODE>. The created
	 * instance has no productions, no terminals, no variables, and specifically
	 * no start variable.
	 */
	public RegularGrammar() {
		super();
		setLinearity(-1);
	}

	/**
	 * Sets the linearity of the jflap.regular jflap.grammar to the value represented by
	 * <CODE>linearity</CODE>. (0 is left-linear, 1 is right-linear).
	 * 
	 * @param linearity
	 *            the linearity of the jflap.grammar.
	 */
	private void setLinearity(int linearity) {
		myLinearity = linearity;
	}

	/**
	 * Returns the linearity of the jflap.grammar in the form of an int. 0 means
	 * left-linear, 1 means right-linear.
	 * 
	 * @return the linearity of the jflap.grammar.
	 */
	private int getLinearity() {
		return myLinearity;
	}

	/**
	 * This checks the production.
	 * 
	 * @param production
	 *            the production
	 * @throws IllegalArgumentException
	 *             if the production is in some way illegal for the jflap.grammar
	 */
	public void checkProduction(Production production) {
		if (!ProductionChecker.isRestrictedOnLHS(production))
			throw new IllegalArgumentException(
					"The production is unrestricted on the left hand side.");
		if (!ProductionChecker.isLeftLinear(production)
				&& !ProductionChecker.isRightLinear(production))
			throw new IllegalArgumentException(
					"The production is neither left nor right linear!");
		// Have we even MADE a decision yet?
		if (getLinearity() != LEFT_LINEAR && getLinearity() != RIGHT_LINEAR)
			return;
		// What if it's just a terminal?
		if (ProductionChecker.isLeftLinear(production)
				&& ProductionChecker.isRightLinear(production))
			return;
		// Does linearity match up?
		if (getLinearity() == LEFT_LINEAR
				&& ProductionChecker.isRightLinear(production))
			throw new IllegalArgumentException(
					"The production is right linear, "
							+ "the jflap.grammar is left linear.");
		if (getLinearity() == RIGHT_LINEAR
				&& ProductionChecker.isLeftLinear(production))
			throw new IllegalArgumentException(
					"The production is left linear, "
							+ "the jflap.grammar is right linear.");
	}

	/**
	 * Adds a production to the jflap.grammar. After the production is added, this
	 * method also sets the linearity of this jflap.grammar.
	 * 
	 * @throws IllegalArgumentException
	 *             if this production is somehow illegal for this jflap.grammar (i.e.,
	 *             linearities don't match up)
	 */
	public void addProduction(Production production) {
		super.addProduction(production);
		// If it's both, we shouldn't change at all.
		if (ProductionChecker.isRightLinear(production)
				&& ProductionChecker.isLeftLinear(production))
			return;
		// If we get to this point it must be either left or right
		// linear, and not both.
		setLinearity(ProductionChecker.isRightLinear(production) ? RIGHT_LINEAR
				: LEFT_LINEAR);
	}

	/** The linearity of the jflap.grammar, either right or left. */
	protected int myLinearity;

	/** The int that represents the linearity of the jflap.grammar. */
	protected static final int LEFT_LINEAR = 0;

	/** The int to represent a right-linear jflap.grammar. */
	protected static final int RIGHT_LINEAR = 1;

	/** The production checker. */
	private static final ProductionChecker PC = new ProductionChecker();

	@Override
	public boolean isConverted() {
		// TODO Auto-generated method stub
		return false;
	}

}
