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





package jflap.gui.action;

import jflap.automata.Automaton;
import jflap.automata.LambdaCheckerFactory;
import jflap.automata.LambdaTransitionChecker;
import jflap.automata.Transition;
import jflap.gui.editor.ArrowDisplayOnlyTool;
import jflap.gui.environment.Environment;
import jflap.gui.environment.Universe;
import jflap.gui.environment.tag.CriticalTag;
import jflap.gui.viewer.AutomatonPane;
import jflap.gui.viewer.SelectionDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This is an action that will highlight all states that have
 * lambda-transitions.
 * 
 * @author Thomas Finley
 */

public class LambdaHighlightAction extends AutomatonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LambdaHighlightAction(Automaton automaton, Environment environment) {
		super("Highlight "+Universe.curProfile.getEmptyString()+"-Transitions", null);
		this.automaton = automaton;
		this.environment = environment;
	}
	
	/**
	 * Highlights states with lambda transitions.
	 */
	public void actionPerformed(ActionEvent event) {
		Transition[] t = automaton.getTransitions();
		Set<Transition> lambdas = new HashSet<>();
		LambdaTransitionChecker checker = LambdaCheckerFactory
				.getLambdaChecker(automaton);
		for (int i = 0; i < t.length; i++)
			if (checker.isLambdaTransition(t[i]))
				lambdas.add(t[i]);

		// Create the selection drawer thingie.
		SelectionDrawer as = new SelectionDrawer(automaton);
		Iterator<Transition> it = lambdas.iterator();
		while (it.hasNext()) {
			Transition lt = (Transition) it.next();
			as.addSelected(lt);
		}

		// Put that in the environment.
		LambdaPane pane = new LambdaPane(new AutomatonPane(as));
		environment.add(pane, Universe.curProfile.getEmptyString()+"-Transitions", new CriticalTag() {
		});
		environment.setActive(pane);
	}

	/**
	 * A class that exists to make integration with the help system feasible.
	 */
	private class LambdaPane extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public LambdaPane(AutomatonPane ap) {
			super(new BorderLayout());
			add(ap, BorderLayout.CENTER);
			add(new JLabel(Universe.curProfile.getEmptyString()+"-transitions are highlighted."),
					BorderLayout.NORTH);
			ArrowDisplayOnlyTool tool = new ArrowDisplayOnlyTool(ap, ap
					.getDrawer());
			ap.addMouseListener(tool);
		}
	}

	/** The automaton to find the lambda-transitions of. */
	private Automaton automaton;

	/** The environment to add the pane with the highlighted lambdas to. */
	private Environment environment;
}
