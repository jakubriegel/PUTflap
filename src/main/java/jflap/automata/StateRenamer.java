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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class contains operations to contain that all states have numeric IDs
 * from 0 up. This will change the ID numbers of the states.
 * 
 * @author Thomas Finley
 */

public class StateRenamer {
	/**
	 * Renames the states for an automaton, by changing all the ID numbers so
	 * that all the ID numbers go from 0 up without interruption in the numeric
	 * sequence. This will modify the automaton passed in.
	 * 
	 * @param a
	 *            the automaton to change the IDs of the states
	 */
	public static void rename(Automaton a) {
		State[] s = a.getStates();
		int maxId = s.length - 1;
		Set<Integer> untaken = new HashSet<>();
		Set<State> reassign = new HashSet<State>(Arrays.asList(s));
		for (int i = 0; i <= maxId; i++) {
			Integer integer = Integer.valueOf(i);
			untaken.add(integer);
		}
		for (int i = 0; i < s.length; i++) {
			Integer integer = Integer.valueOf(i);
			if (untaken.remove(integer)) {
				reassign.remove(s[i]);
			}
		}
		// Now untaken has the untaken IDs, and reassign has the
		// states that need reassigning.
		s = (State[]) reassign.toArray(new State[0]);
		Iterator<Integer> it = untaken.iterator();
		for (int i = 0; i < s.length; i++) {
			s[i].setID(((Integer) it.next()).intValue());
		}
	}
}
