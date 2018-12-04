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
import jflap.automata.turing.TuringMachine;
import jflap.automata.turing.TuringMachineBuildingBlocks;
import jflap.gui.environment.AutomatonEnvironment;
import jflap.gui.grammar.automata.ConvertController;
import jflap.gui.grammar.automata.ConvertPane;
import jflap.gui.grammar.automata.TuringConvertController;
import jflap.gui.viewer.SelectionDrawer;

/**
 * Class for converting Turing Machine to Unrestricted Grammar
 * @author Kyung Min (Jason) Lee
 *
 */
public class TuringToUnrestrictGrammarAction extends ConvertAutomatonToGrammarAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TuringToUnrestrictGrammarAction(AutomatonEnvironment automatonEnvironment) {
		super("Convert to Unrestricted Grammar", automatonEnvironment);
		// TODO Auto-generated constructor stub
	}

	@Override
	//TODO: Need to put error check, make sure there is only one letter popping and placing
	// More error check?
	protected boolean checkAutomaton() {
		// TODO Auto-generated method stub
		//return true for now
		return true;
	}

	@Override
	protected ConvertController initializeController(ConvertPane pane,
			SelectionDrawer drawer, Automaton automaton) {
		// TODO Auto-generated method stub
		return new TuringConvertController(pane, drawer,
				(TuringMachine) automaton);
	}

	public static boolean isApplicable(Object object) {
		if (object instanceof TuringMachineBuildingBlocks) {
			return false;   //buildingblocks TM
		}
		if (object instanceof TuringMachine) {
			if (((TuringMachine) object).tapes() == 1) {
				return true;
			} else {
				return false;   //multi-tape TM
			}
		}
		return false;   //not a TM
	} 

}
