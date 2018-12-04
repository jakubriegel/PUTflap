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
import jflap.gui.environment.Environment;

import java.awt.event.KeyEvent;

/**
* The <CODE>TestTuringAction</CODE> is an action to load a structure from a file,
* and create a new environment with that object.
* 
* @author Stephen Reading
*/

public class FATestAction extends  TestAction{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Instantiates a new <CODE>Turing Test Action</CODE>.
     */
    public FATestAction() {
        //super("Test Turing Machines", null);
        super("Test Finite Automata", KeyEvent.VK_A);       

    }
    
    protected void displayMultipleRunPane(Environment env){
    		MultipleSimulateAction act = new MultipleSimulateAction((Automaton)myObjects.get(0),env);
    		act.performAction(env);
   	
    }

}
