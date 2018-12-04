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




/*
 * Created on Jun 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jflap.gui.action;

import jflap.automata.*;
import jflap.automata.turing.TMSimulator;
import jflap.automata.turing.TuringMachine;
import jflap.automata.turing.TuringMachineBuildingBlocks;
import jflap.gui.environment.Environment;
import jflap.gui.environment.tag.CriticalTag;
import jflap.gui.sim.SimulatorPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.Serializable;
/**
 * @author Andrew
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BuildingBlockSimulateAction extends SimulateAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new <CODE>NoInteractionSimulateAction</CODE>.
	 * 
	 * @param automaton
	 *            the automaton that read will be simulated on
	 * @param environment
	 *            the environment object that we shall add our simulator pane to
	 */
	public BuildingBlockSimulateAction(Automaton automaton,
			Environment environment) {
		super(automaton, environment);
		putValue(NAME, "Step by BuildingBlock");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_B,
				MAIN_MENU_MASK));
		this.automaton = automaton;
		this.environment = environment;
	}

	/**
	 * Performs the action.
	 */
	public void actionPerformed(ActionEvent e) {
		if (automaton.getInitialState() == null) {
			JOptionPane.showMessageDialog((Component) e.getSource(),
					"Simulation requires an automaton\n"
							+ "with an initial state!", "No Initial State",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		Object input = initialInput((Component) e.getSource(), "");
		if (input == null)
			return;
		Configuration[] configs = null;
		AutomatonSimulator simulator = getSimulator(automaton);
		// Get the initial configurations.
		if (getObject() instanceof TuringMachine) {
			//if the TM is nondeterministic, display error message and stop
			NondeterminismDetector d = NondeterminismDetectorFactory.getDetector(automaton);
            State[] nd = d.getNondeterministicStates(automaton);
            if(nd.length > 0)
            {
                JOptionPane.showMessageDialog((Component) e.getSource(),
                    "Please remove nondeterminism for simulation.\n" +
                    "Select menu item Test : Highlight Nondeterminism\nto see nondeterministic states.",
                    "Nondeterministic states detected", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
            		String[] s = (String[]) input;
            		configs = ((TMSimulator) simulator).getInitialConfigurations(s);
            }
          //if not a Turing machine
		} else {
			String s = (String) input;
			configs = simulator.getInitialConfigurations(s);
		}
		handleInteraction(automaton, simulator, configs, input);
	}

	/**
	 * Given initial configurations, the simulator, and the automaton, takes any
	 * further action that may be necessary. In the case of stepwise operation,
	 * which is the default, an additional tab is added to the environment
	 * 
	 * @param automaton
	 *            the automaton read is simulated on
	 * @param simulator
	 *            the automaton simulator for this automaton
	 * @param configurations
	 *            the initial configurations generated
	 * @param initialInput
	 *            the object that represents the initial read; this is a String
	 *            object in most cases, but may differ for multiple tape turing
	 *            machines
	 */
	public void handleInteraction(Automaton automaton,
			AutomatonSimulator simulator, Configuration[] configurations,
			Object initialInput) {
		SimulatorPane simpane = new SimulatorPane(automaton, simulator,
				configurations, environment, true);
		if (initialInput instanceof String[])
			initialInput = java.util.Arrays.asList((String[]) initialInput);
		environment.add(simpane, "Simulate: " + initialInput,
				new CriticalTag() {
				});
		environment.setActive(simpane);
	}

	/**
	 * This particular action may only be applied to finite state
	 * 
	 * @param object
	 *            the object to test for applicability
	 * @return <CODE>true</CODE> if the passed in object is a finite state
	 *         automaton, <CODE>false</CODE> otherwise
	 */
	public static boolean isApplicable(Serializable object) {
		if (object instanceof TuringMachineBuildingBlocks) {
			if (((TuringMachine) object).tapes() == 1) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/** The automaton this simulate action runs simulations on! */
	private Automaton automaton;

	/** The environment. */
	private Environment environment = null;
}
