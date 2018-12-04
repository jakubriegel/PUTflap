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





package jflap.gui.sim;

import jflap.gui.TooltipAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * This is a control panel with buttons for invoking methods on a configuration
 * controller.
 * 
 * @author Thomas Finley
 */

public class ControlPanel extends JToolBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new <CODE>ControlPanel</CODE> for the given
	 * configuration controller.
	 * 
	 * @param controller
	 *            the configuration controller object
	 */
	public ControlPanel(ConfigurationController controller) {
		this.controller = controller;
		initView();
	}

	/**
	 * Returns the configuration controller object this panel controls.
	 * 
	 * @return the configuration controller object this panel controls
	 */
	public ConfigurationController getController() {
		return controller;
	}

	/**
	 * A simple helper function that initializes the jflap.gui.
	 */
	protected void initView() {
		this.add(new TooltipAction("Step", "Moves existing valid "
				+ "configurations to the next " + "configurations.") {
			/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				controller.step(blockStep);
			}
		});

		this.add(new TooltipAction("Reset", "Resets the simulation to "
				+ "start conditions.") {
			/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				controller.reset();
			}
		});

        /*
         * Add Focus and Defocus buttons only if it is a Turing machine.
         */
        if(controller.isTuringMachine()) {
    		this.add(new AbstractAction("Focus") {
    			/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
    				controller.focus();
    			}
    		});
    
    		this.add(new AbstractAction("Defocus") {
    			/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
    				controller.defocus();
    			}
    		});
        }
		this.add(new AbstractAction("Freeze") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				controller.freeze();
			}
		});

		this.add(new AbstractAction("Thaw") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				controller.thaw();
			}
		});

		this.add(new AbstractAction("Trace") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				controller.trace();
			}
		});

		this.add(new AbstractAction("Remove") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				controller.remove();
			}
		});
	}


	/**
	 * @param blockStep
	 */
	public void setBlock(boolean step) {
		blockStep = step;

	}

	private boolean blockStep = false;

	/** The configuration controller object. */
	private ConfigurationController controller;

}
