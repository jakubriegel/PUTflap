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




package jflap.automata.turing;

import jflap.automata.Automaton;
import jflap.automata.State;

import java.awt.*;


/**
  This class represents the TuringMachine-specific aspects of states, such as the ability to hold inner machines.


  @author Henry Qin
  */
public class TMState extends State{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TuringMachineBuildingBlocks myInnerTuringMachine;

    public TMState(int id, Point point, Automaton tm){ //do we really need a pointer to the parent?
        super(id, point, tm);

        assert(tm instanceof TuringMachine);

        myInnerTuringMachine = new TuringMachineBuildingBlocks();
        myInnerTuringMachine.setParent(this);
    }

    public TMState(TMState copyMe){ //do we really need a pointer to the parent?
        this(copyMe.getID(), (Point)copyMe.getPoint().clone(), copyMe.getAutomaton());

        myInnerTuringMachine = (TuringMachineBuildingBlocks) copyMe.getInnerTM().clone(); //this should output in recursion until we reach a TMState whose inner TM does not contain states.
    }

    public void setInnerTM(TuringMachineBuildingBlocks tm){
        myInnerTuringMachine = tm;
        myInnerTuringMachine.setParent(this);
        assert (myInnerTuringMachine.getParent() == this);
    }
    public TuringMachineBuildingBlocks getInnerTM(){
        return myInnerTuringMachine;
    }
    public String getInternalName(){ //just for trying to preserve old way of saving.
        //ASSUME that ID's are Independent
        return myInternalName == null? myInternalName = "Machine"+ getID() : myInternalName; //create an internal name if one has not been assigned explicitly
    }
    public void setInternalName(String s){ //just for trying to preserve old way of saving.
        myInternalName = s;
    }

    public String myInternalName = null;

}
