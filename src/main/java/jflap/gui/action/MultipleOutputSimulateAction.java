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
import jflap.gui.JTableExtender;
import jflap.gui.environment.Environment;
import jflap.gui.sim.multiple.InputTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * This is the action used for the simulation of multiple inputs on an automaton
 * with no interaction, and it also produces the output that a machine produces.
 * This is useful in situations where you are running read on a Turing machine
 * as a transducer. This is almost identical to its superclass except for a few
 * different names, and this one does not remove the columns corresponding to
 * the output.
 * 
 * @author Thomas Finley
 */

public class MultipleOutputSimulateAction extends MultipleSimulateAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new <CODE>MultipleOuptutSimulateAction</CODE>.
	 * 
	 * @param automaton
	 *            the automaton that read will be simulated on
	 * @param environment
	 *            the environment object that we shall add our simulator pane to
	 */
	public MultipleOutputSimulateAction(Automaton automaton,
			Environment environment) {
		super(automaton, environment);
		putValue(NAME, "Multiple Run (Transducer)");
         putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T,
                    MAIN_MENU_MASK));
	}

	/**
	 * Returns the title for the type of compontent we will add to the
	 * environment.
	 * 
	 * @return in this base class, returns "Multiple Inputs"
	 */
	public String getComponentTitle() {
		return "Multiple Runs";
	}

	/**
	 * Provides an initialized multiple read table object.
	 * 
	 * @param automaton
	 *            the automaton to provide the multiple read table for
	 * @return a table object for this automaton
	 * @see jflap.gui.sim.multiple.InputTableModel
	 */
	protected JTableExtender initializeTable(Object obj) {
        boolean multiple = false;
        if(this.getEnvironment().myObjects!=null) multiple = true;
		TableModel model = InputTableModel.getModel((Automaton)getObject(), multiple);
		JTableExtender table = new JTableExtender(model, this);
		table.setShowGrid(true);
		table.setGridColor(Color.lightGray);
        if(multiple){
            ArrayList<Object> autos  = this.getEnvironment().myObjects;
            ArrayList<String> strings = this.getEnvironment().myTestStrings;
            ArrayList<String> outs = this.getEnvironment().myTransducerStrings;
            int offset = strings.size();
            int tapeNum = 1;
            if(autos.get(0) instanceof TuringMachine){
            	tapeNum = ((TuringMachine)autos.get(0)).tapes;
            	offset = offset/tapeNum;
            }     
            int row = 0;
            for(int m = 0; m < autos.size(); m++){      
                for(int k = 0; k < strings.size(); k++){
                    row = k/tapeNum+offset*m;
                    model.setValueAt(((Automaton)autos.get(m)).getFileName(), row, 0);  
                    model.setValueAt((String)strings.get(k), row, k%tapeNum+1);
                }
                
            }
        }
		return table;
	}

	/**
	 * This simulate action is only applicable to those types of automata that
	 * can be considered to generate output, that is, Turing machines.
	 * 
	 * @param object
	 *            to object to test for applicability
	 */
	public static boolean isApplicable(Object object) {
		return object instanceof TuringMachine;
	}
}
