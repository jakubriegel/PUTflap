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





package jflap.gui.regular;

import jflap.automata.State;
import jflap.automata.fsa.FSATransition;
import jflap.automata.fsa.FiniteStateAutomaton;
import jflap.gui.editor.ArrowNontransitionTool;
import jflap.gui.editor.Tool;
import jflap.gui.editor.ToolBox;
import jflap.gui.environment.RegularEnvironment;
import jflap.gui.environment.Universe;
import jflap.gui.viewer.AutomatonDrawer;
import jflap.gui.viewer.AutomatonPane;
import jflap.gui.viewer.SelectionDrawer;
import jflap.regular.Discretizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the pane that holds the tools necessary for the conversion of a
 * regular expression to a finite state automaton.
 * 
 * @author Thomas Finley
 */

public class ConvertToAutomatonPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new conversion pane for the conversion of a regular expression
	 * to an automaton.
	 * 
	 * @param environment
	 *            the environment that this convert pane will be a part of
	 */
	public ConvertToAutomatonPane(RegularEnvironment environment) {
		this.environment = environment;
		JFrame frame = Universe.frameForEnvironment(environment);

		setLayout(new BorderLayout());

		JPanel labels = new JPanel(new BorderLayout());
		labels.add(mainLabel, BorderLayout.NORTH);
		labels.add(detailLabel, BorderLayout.SOUTH);
		mainLabel.setText(" ");
		detailLabel.setText(" ");

		add(labels, BorderLayout.NORTH);
		SelectionDrawer automatonDrawer = new SelectionDrawer(automaton);

		// Do the initialization of the automaton.
		State initialState = automaton.createState(new Point(60, 40));
		State finalState = automaton.createState(new Point(450, 250));
		automaton.setInitialState(initialState);
		automaton.addFinalState(finalState);
		FSATransition initialTransition = new FSATransition(initialState,
				finalState, Discretizer.delambda(environment.getExpression()
						.asString().replace('!', Universe.curProfile.getEmptyString().charAt(0))));
		automaton.addTransition(initialTransition);

		controller = new REToFSAController(this, automaton);

		jflap.gui.editor.EditorPane ep = new jflap.gui.editor.EditorPane(automatonDrawer,
				new ToolBox() {
					public List<Tool> tools(AutomatonPane view, AutomatonDrawer drawer) {
						LinkedList<Tool> tools = new LinkedList<>();
						tools.add(new ArrowNontransitionTool(view, drawer));
						tools.add(new RegularToAutomatonTransitionTool(view,
								drawer, controller));
						tools.add(new DeexpressionifyTransitionTool(view,
								drawer, controller));
						return tools;
					}
				});

		JToolBar bar = ep.getToolBar();
		bar.addSeparator();
		bar.add(doStepAction);
		bar.add(doAllAction);
		bar.add(exportAction);
		// bar.add(exportAction2);

		add(ep, BorderLayout.CENTER);
	}

	/**
	 * The environment that holds the regular expression. The regular expression
	 * from the environment is itself not modified.
	 */
	RegularEnvironment environment;

	/**
	 * The automaton being built, which will be modified throughout this
	 * process.
	 */
	private FiniteStateAutomaton automaton = new FiniteStateAutomaton();

	/** The controller object. */
	private REToFSAController controller;

	/** The frame that holds the environment. */
	JFrame frame;

	/** The labels holding the current directions. */
	JLabel mainLabel = new JLabel();

	JLabel detailLabel = new JLabel();

	/** The actions. */
	AbstractAction doStepAction = new AbstractAction("Do Step") {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			controller.completeStep();
		}
	};

	AbstractAction doAllAction = new AbstractAction("Do All") {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			controller.completeAll();
		}
	};

	AbstractAction exportAction = new AbstractAction("Export") {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			controller.export();
		}
	};

	AbstractAction exportAction2 = new AbstractAction("Export Now") {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			controller.exportToTab();
		}
	};
}
