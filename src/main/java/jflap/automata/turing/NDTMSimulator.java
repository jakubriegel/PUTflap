/* -- JFLAP 4.0 --
 *
 * Copyright information:
 *
 * Susan H. Rodger, Thomas Finley
 * Computer Science Department
 * Duke University
 * April 24, 2003
 * Supported by National Science Foundation DUE-9752583.
 *
 * Copyright (c) 2003
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms are permitted
 * provided that the above copyright notice and this paragraph are
 * duplicated in all such forms and that any documentation,
 * advertising materials, and other materials related to such
 * distribution and use acknowledge that the software was developed
 * by the author.  The name of the author may not be used to
 * endorse or promote products derived from this software without
 * specific prior written permission.
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

 //NONDETERMINISTIC TURING MACHINE SIMULATOR

package jflap.automata.turing;

import jflap.automata.*;
import jflap.gui.environment.Universe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;



/**
 * The NDTM simulator progresses TM configurations on a possibly
 * multitape Turing machine. It works for nondeterminism.
 *
 * @author Thomas Finley
 */

public class NDTMSimulator extends AutomatonSimulator {
    /**
     * Creates a TM simulator for the given automaton.
     * @param automaton the machine to simulate
     * @throws IllegalArgumentException if this automaton is not a
     * Turing machine
     */

    public NDTMSimulator(Automaton automaton) {
		super(automaton);
		if (!(automaton instanceof TuringMachine))
			throw new IllegalArgumentException(
					"Automaton is not a Turing machine, but a "
							+ automaton.getClass());

//       //MERLIN MERLIN MERLIN MERLIN MERLIN// //this code is only for show, it should be moved into a setting with a better UI before release//
//        AcceptanceFilter[] choices = new  AcceptanceFilter[] {new AcceptByHaltingFilter(), new AcceptByFinalStateFilter()};      
//        
//        List<AcceptanceFilter> tlist = new ArrayList<AcceptanceFilter>();
//        for (int i = 0; i < choices.length; i++){
//            int res = JOptionPane.showConfirmDialog(null, "Would you like to " + choices[i].getName()+ "?", "Confirm use of this acceptance criteria", JOptionPane.YES_NO_OPTION);
//            if (res == JOptionPane.YES_OPTION) tlist.add(choices[i]); 
//        }
//
//        if (tlist.size() == 0)
//            JOptionPane.showMessageDialog(null, "Will reject all inputs eventually, if you choose to persist with this.", "Warning", JOptionPane.WARNING_MESSAGE);
//
//       //END MERLIN MERLIN MERLIN MERLIN MERLIN// //this code is only for show, it should be moved into a setting with a better UI before release//

          
        List<AcceptanceFilter> tlist = new ArrayList<AcceptanceFilter>();
        
        if (Universe.curProfile.getAcceptByFinalState()) tlist.add(new AcceptByFinalStateFilter());
        if (Universe.curProfile.getAcceptByHalting()) tlist.add(new AcceptByHaltingFilter());


        myFilters = tlist.toArray(new AcceptanceFilter[0]);
	}

    /**
     * Returns a TMConfiguration object that represents the initial
     * configuration of the TM, before any read has been processed.
     * This returns an array of length one.  This method exists only
     * to provide compatibility with the general definition of
     * <CODE>AutomatonSimulator</CODE>.  One should use the version of
     * this function that accepts an array of inputs instead.
     * @param input the read string
     */
    public Configuration[] getInitialConfigurations(String input) {
	int tapes = ((TuringMachine)myAutomaton).tapes();
	String[] inputs=new String[tapes];
	for (int i=0; i<tapes; i++) inputs[i]=input;
	return getInitialConfigurations(inputs);
    }

    /**
     * Returns a TMConfiguration object that represents the initial
     * configuration of the TM, before any read has been processed.
     * This returns an array of length one.
     * @param inputs the read strings
     */

	public Configuration[] getInitialConfigurations(String[] inputs) {
	inputStrings = (String[]) inputs.clone();
	Tape[] tapes = new Tape[inputs.length];
	for (int i = 0; i < tapes.length; i++)
		tapes[i] = new Tape(inputs[i]);
	Configuration[] configs = new Configuration[1];
	TMState initialState = (TMState) myAutomaton.getInitialState();
    TuringMachine tm = initialState.getInnerTM();
	configs[0] = new TMConfiguration(initialState, null, tapes, myFilters);
	return configs;
}

    /**
     * Simulates one step for a particular configuration, adding
     * all possible configurations reachable in one step to 
     * set of possible configurations.
     * @param config the configuration to simulate the one step on
     */
    public ArrayList<Configuration> stepConfiguration(Configuration config) {
	ArrayList<Configuration> list = new ArrayList<>();
	TMConfiguration configuration = (TMConfiguration) config;
	/** get all information from configuration. */

	State currentState = configuration.getCurrentState();
	Transition[] transitions = 
	    myAutomaton.getTransitionsFromState(currentState);
	for (int k = 0; k < transitions.length; k++) {
	    TMTransition t = (TMTransition) transitions[k];
	    Tape[] tapes = configuration.getTapes();
	    boolean okay = true;
	    for (int i=0; okay && i<tapes.length; i++) {
		String charAtHead = tapes[i].read();
		String toRead = t.getRead(i);
		if (!charAtHead.equals(toRead)) okay=false;
	    }
	    if (!okay) continue; // One of the toReads wasn't satisfied.
	    State toState = t.getToState();
	    Tape[] tapes2 = new Tape[tapes.length];
	    for (int i=0; i<tapes.length; i++) {
		tapes2[i]=new Tape(tapes[i]);
		String toWrite = t.getWrite(i);
		String direction = t.getDirection(i);
		tapes2[i].write(toWrite);
		tapes2[i].moveHead(direction);
	    }
	    TMConfiguration configurationToAdd = 
		new TMConfiguration(toState, configuration, tapes2, myFilters);
	    list.add(configurationToAdd);
	}
	return list;
    }

    /**
     * Returns true if the simulation of the read string on the
     * automaton left the machine in a final state.    
     * @return true if the simulation of the read string on the
     * automaton left the machine in a final state
     */
    public boolean isAccepted() {
	Iterator<Configuration> it = myConfigurations.iterator();
	while (it.hasNext()) {
	    TMConfiguration configuration = (TMConfiguration) it.next();
	    State currentState = configuration.getCurrentState();
	    /** check if in final state.  contents of tape are
	     * irrelevant. */
	    if(myAutomaton.isFinalState(currentState)) {
		return true;
	    }
	} 
	return false;
    }
    
    /**
     * Runs the automaton on the read string.
     * @param input the read string to be run on the
     * automaton
     * @return true if the automaton accepts the read
     */
    public boolean simulateInput(String input) {
	/** clear the configurations to begin new simulation. */
	myConfigurations.clear();
	Configuration[] initialConfigs = getInitialConfigurations(input);
	for(int k = 0; k < initialConfigs.length; k++) {
	    TMConfiguration initialConfiguration = 
		(TMConfiguration) initialConfigs[k];
	    myConfigurations.add(initialConfiguration);
	} 
	while (!myConfigurations.isEmpty()) {
	    if(isAccepted()) return true;
	    ArrayList<Configuration> configurationsToAdd = new ArrayList<>();
	    Iterator<Configuration> it = myConfigurations.iterator();
	    while (it.hasNext()) {
		TMConfiguration configuration = (TMConfiguration) it.next();
		ArrayList<Configuration> configsToAdd = stepConfiguration(configuration);
		configurationsToAdd.addAll(configsToAdd);
		it.remove();
	    } 
	    myConfigurations.addAll(configurationsToAdd);
	}
	return false;
    }
    private AcceptanceFilter[] myFilters;

    public List<TMConfiguration> stepBlock(TMConfiguration config){
    	//EDebug.print("Inside StepBlock");
    	while (((TuringMachine)(config = (TMConfiguration) stepConfiguration(config).get(0)).getCurrentState().getAutomaton()).getParent() != null);
    	return Arrays.asList(config);
    }
    public String[] getInputStrings() {
    		return inputStrings;
    }
    private String inputStrings[];
}