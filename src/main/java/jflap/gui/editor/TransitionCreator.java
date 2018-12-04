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





package jflap.gui.editor;

import jflap.automata.Automaton;
import jflap.automata.State;
import jflap.automata.Transition;
import jflap.automata.fsa.FiniteStateAutomaton;
import jflap.automata.mealy.MealyMachine;
import jflap.automata.mealy.MooreMachine;
import jflap.automata.pda.PushdownAutomaton;
import jflap.automata.turing.TuringMachine;
import jflap.automata.vdg.VariableDependencyGraph;
import jflap.gui.viewer.AutomatonPane;

import javax.swing.*;
import java.awt.*;

/**
 * A <CODE>TransitionCreator</CODE> object is used to present a graphical
 * environment for the creation and editing of transitions for insertion into
 *
 * 
 * @author Thomas Finley
 */

public abstract class TransitionCreator {
	public TransitionCreator() {

	}

	public TransitionCreator(AutomatonPane parent) {
		this.parent = parent;
	}

	/**
	 * Returns the automaton.
	 * 
	 * @return the automaton
	 */
	protected Automaton getAutomaton() {
		return getParent().getDrawer().getAutomaton();
	}

	/**
	 * Begins the process of creating a transition and returns it.
	 * 
	 * @param from
	 *            the state the transition will go from
	 * @param to
	 *            the state the transition will go to
	 */
	public abstract Transition createTransition(State from, State to);

	/**
	 * Edits a given transition. Ideally this should use the same interface as
	 * that given by <CODE>createTransition</CODE>.
	 * 
	 * @param transition
	 *            the transition to edit
	 * @return <CODE>false</CODE> if the user decided to edit a transition
	 */
	public abstract boolean editTransition(Transition transition);

	/**
	 * This is a static method used to return a transition creator for the sort
	 * of automaton that is being edited.
	 * 
	 * @param automaton
	 *            the automaton for which there will be created a compatible
	 *            transition creator
	 * @param parent
	 *            the component that should be the parent of any dialog boxes or
	 *            other windows brought up
	 * @return a transition creator that generates transitions compatible with
	 *         the type of automaton passed in, or <CODE>null</CODE> if this
	 *         type of automaton is unknown
	 */
	public static TransitionCreator creatorForAutomaton(Automaton automaton,
			AutomatonPane parent) {
		if (automaton instanceof FiniteStateAutomaton)
			return new FSATransitionCreator(parent);
		if (automaton instanceof PushdownAutomaton)
			return new PDATransitionCreator(parent);
		if (automaton instanceof TuringMachine)
			return new TMTransitionCreator(parent);
		if (automaton instanceof VariableDependencyGraph)
			return new VDGTransitionCreator(parent);
        if(automaton instanceof MooreMachine)
            return new MooreTransitionCreator(parent);
        if(automaton instanceof MealyMachine)
            return new MealyTransitionCreator(parent);
		return null;
	}

	/**
	 * Edits the transition at the particular point. By default, this envokes
	 * the pointless method.
	 * 
	 * @param transition
	 *            the transition to edit
	 * @param point
	 *            the point to edit the transition at
	 */
	public void editTransition(Transition transition, Point point) {
		editTransition(transition);
	}

	/**
	 * Returns the parent component of this transition creator.
	 * 
	 * @return the parent component of this transition creator
	 */
	public AutomatonPane getParent() {
		return parent;
	}

	/**
	 * Reports an error to the user through a dialog box based on an illegal
	 * argument exception.
	 * 
	 * @param e
	 *            the illegal argument exception
	 */
	public void reportException(IllegalArgumentException e) {
		JOptionPane.showMessageDialog(getParent(), "Bad format!\n"
				+ e.getMessage(), "Bad Format", JOptionPane.ERROR_MESSAGE);
	}

	/** The parent component for this transition creator. */
	private AutomatonPane parent = null;
}
