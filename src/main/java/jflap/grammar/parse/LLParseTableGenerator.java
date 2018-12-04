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

import jflap.grammar.Grammar;
import jflap.grammar.Production;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This class generates {@link jflap.grammar.parse.LLParseTable}s for grammars.
 * 
 * @author Thomas Finley
 */

public class LLParseTableGenerator {
	/**
	 * Can't instantiate this bad boy sparky.
	 */
	private LLParseTableGenerator() {
	}

	/**
	 * Generates a parse table for a particular jflap.grammar.
	 * 
	 * @param grammar
	 *            the jflap.grammar for which a complete parse table should be
	 *            generated
	 */
	public static LLParseTable generate(Grammar grammar) {
		LLParseTable table = new LLParseTable(grammar);
		Map<String, Set<String>> first = Operations.first(grammar), follow = Operations
				.follow(grammar);
		Production[] productions = grammar.getProductions();
		for (int i = 0; i < productions.length; i++) {
			String alpha = productions[i].getRHS();
			String A = productions[i].getLHS();
			Set<String> firsts = Operations.first(first, alpha);
			Iterator<String> it = firsts.iterator();
			while (it.hasNext()) {
				String a = (String) it.next();
				if (!a.equals(""))
					table.addEntry(A, a, alpha);
			}
			if (!firsts.contains(""))
				continue;
			Set<String> follows = (Set<String>) follow.get(A);
			it = follows.iterator();
			while (it.hasNext())
				table.addEntry(A, (String) it.next(), alpha);
		}
		return table;
	}
}
