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

import jflap.automata.turing.TuringMachine;
import jflap.automata.turing.TuringMachineBuildingBlocks;
import jflap.gui.viewer.AutomatonDrawer;
import jflap.gui.viewer.AutomatonPane;

import java.util.List;

/**
 * The <CODE>DefaultToolBox</CODE> has all the tools for general editing of an
 * automaton.
 */

public class DefaultToolBox implements ToolBox {
	/**
	 * Returns a list of tools including a <CODE>ArrowTool</CODE>, <CODE>StateTool</CODE>,
	 * <CODE>TransitionTool</CODE> and <CODE>DeleteTool</CODE>, in that
	 * order.
	 * 
	 * @param view
	 *            the component that the automaton will be drawn in
	 * @param drawer
	 *            the drawer that will draw the automaton in the view
	 * @return a list of <CODE>Tool</CODE> objects.
	 */
	public List<Tool> tools(AutomatonPane view, AutomatonDrawer drawer) {
		List<Tool> list = new java.util.ArrayList<>();
		list.add(new ArrowTool(view, drawer));
		list.add(new StateTool(view, drawer));
		list.add(new TransitionTool(view, drawer));
		list.add(new DeleteTool(view, drawer));
		list.add(new UndoTool(view, drawer));
		list.add(new RedoTool(view, drawer));
		if (drawer.getAutomaton() instanceof TuringMachine) {
			TuringMachine turingMachine = (TuringMachine) drawer.getAutomaton();
			if (turingMachine.tapes() == 1) {
				if (drawer.getAutomaton() instanceof TuringMachineBuildingBlocks) {
					list.add(new BuildingBlockTool(view, drawer));
					list.add(new BlockTransitionTool(view, drawer));
				}
			}
		}
		return list;
	}
}
