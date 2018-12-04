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





package jflap.gui.grammar.parse;

import jflap.automata.fsa.FiniteStateAutomaton;
import jflap.grammar.Grammar;
import jflap.grammar.parse.LRParseTable;
import jflap.grammar.parse.Operations;
import jflap.gui.SplitPaneFactory;
import jflap.gui.TableTextSizeSlider;
import jflap.gui.editor.ArrowNontransitionTool;
import jflap.gui.editor.EditorPane;
import jflap.gui.editor.Tool;
import jflap.gui.editor.ToolBox;
import jflap.gui.environment.GrammarEnvironment;
import jflap.gui.grammar.GrammarTable;
import jflap.gui.viewer.AutomatonDraggerPane;
import jflap.gui.viewer.AutomatonDrawer;
import jflap.gui.viewer.AutomatonPane;
import jflap.gui.viewer.SelectionDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the view for the derivation of a LR parse table from a grammar.
 * 
 * @author Thomas Finley
 */

public class LRParseTableDerivationPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new derivation pane for a grammar environment.
	 * 
	 * @param environment
	 *            the grammar environment
	 */
	public LRParseTableDerivationPane(GrammarEnvironment environment) {
		super(new BorderLayout());
		Grammar g = environment.getGrammar();
		augmentedGrammar = Operations.getAugmentedGrammar(g);
        if(augmentedGrammar == null) return;
		right = new JPanel(new BorderLayout());

		// right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

		JLabel description = new JLabel();
		right.add(description, BorderLayout.NORTH);

		// FirstFollowModel ffmodel = new FirstFollowModel(g);
		FirstFollowTable fftable = new FirstFollowTable(g);
		fftable.getColumnModel().getColumn(0).setPreferredWidth(30);
		right.add(new JScrollPane(fftable));
		right.add(new TableTextSizeSlider(fftable, JSlider.VERTICAL), BorderLayout.EAST);
		fftable.getFFModel().setCanEditFirst(true);
		fftable.getFFModel().setCanEditFollow(true);

		dfa = new FiniteStateAutomaton();
		// The right split pane.
		controller = new LRParseDerivationController(g, augmentedGrammar,
				environment, fftable, description, dfa, this);
		JPanel editorHolder = new JPanel(new BorderLayout());
		EditorPane editor = createEditor(editorHolder);
		controller.editor = editor;
		editorHolder.add(editor, BorderLayout.CENTER);
		split = SplitPaneFactory.createSplit(environment, false, 0.4,
				new JScrollPane(fftable), editorHolder);
		split2 = SplitPaneFactory.createSplit(environment, false, 0.7, split,
				null);
		right.add(split2, BorderLayout.CENTER);

		GrammarTable table = new GrammarTable(
				new jflap.gui.grammar.GrammarTableModel(augmentedGrammar) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int r, int c) {
						return false;
					}
				}) {
			/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

			public String getToolTipText(MouseEvent event) {
				try {
					int row = rowAtPoint(event.getPoint());
					return getGrammarModel().getProduction(row).toString()
							+ " is production " + row;
				} catch (Throwable e) {
					return null;
				}
			}
		};
		JPanel grammarHolder = new JPanel();
		grammarHolder.setLayout(new BorderLayout());
		grammarHolder.add(new TableTextSizeSlider(table, JSlider.HORIZONTAL), BorderLayout.NORTH);
		grammarHolder.add(table, BorderLayout.CENTER);
		JSplitPane big = SplitPaneFactory.createSplit(environment, true, 0.3,
				grammarHolder, right);
		this.add(big, BorderLayout.CENTER);

		// Make the tool bar.
		JToolBar toolbar = new JToolBar();
		toolbar.add(controller.doSelectedAction);
		toolbar.add(controller.doStepAction);
		toolbar.add(controller.doAllAction);
		toolbar.addSeparator();
		toolbar.add(controller.nextAction);
		toolbar.addSeparator();
		toolbar.add(controller.parseAction);
		this.add(toolbar, BorderLayout.NORTH);
	}

	/**
	 * Creates an editor pane for the DFA.
	 * 
	 * @param panel
	 *            a panel that will hold the editor pane
	 * @return the editor pane
	 */
	private EditorPane createEditor(final Component panel) {
		final SelectionDrawer drawer = new SelectionDrawer(dfa);
		EditorPane editor = new EditorPane(drawer, new ToolBox() {
			public List<Tool> tools(AutomatonPane view,
					AutomatonDrawer drawer) {
				List<Tool> tools = new LinkedList<>();
				tools.add(new ArrowNontransitionTool(view, drawer) {
					public boolean shouldAllowOnlyFinalStateChange() {
						return true;
					}

					public boolean shouldShowStatePopup() {
						return true;
					}
				});
				tools.add(new GotoTransitionTool(view, drawer, controller));
				return tools;
			}
		});
		// addExtras(editor.getToolBar());
		return editor;
	}

	/**
	 * When called, this will make the DFA move into a non-editable (but state
	 * draggable) pane.
	 */
	void moveDFA() {
		AutomatonDraggerPane dp = new AutomatonDraggerPane(dfa);
		split.setRightComponent(dp);
	}

	/**
	 * Sets the LR parse table.
	 * 
	 * @param table
	 *            the parse table to put in
	 */
	void setParseTable(LRParseTable table) {
		if (tableView == null) {
			/*tableView = new LRParseTableChooserPane(table);
			JPanel tableHolder = new JPanel();
			tableHolder.setLayout(new BorderLayout());
			tableHolder.add(tableView, BorderLayout.CENTER);
			tableHolder.add(new TableTextSizeSlider(tableView, JSlider.HORIZONTAL), BorderLayout.SOUTH);
			split2.setRightComponent(new JScrollPane(tableHolder));
			// add(new JScrollPane(tableView), BorderLayout.SOUTH);*/
			tableView = new LRParseTableChooserPane(table);
			split2.setRightComponent(new JScrollPane(tableView));
			right.add(new TableTextSizeSlider(tableView, JSlider.HORIZONTAL), BorderLayout.SOUTH);
		} else
			tableView.setModel(table);
	}

	/**
	 * Returns the table view for the parse table.
	 * 
	 * @return the view for the parse table, or <CODE>null</CODE> if the parse
	 *         table has not been set yet
	 */
	LRParseTableChooserPane getParseTableView() {
		return tableView;
	}
    
    public Grammar getAugmentedGrammar(){
        return augmentedGrammar;
    }

	/** The augmented grammar. */
	private Grammar augmentedGrammar;

	/** The parse derivation controller object. */
	private LRParseDerivationController controller;

	/** The DFA for the set of items constructions. */
	private FiniteStateAutomaton dfa;

	/** The right split pane. */
	private JSplitPane split;

	/** The split pane. */
	private JSplitPane split2;

	/** The parse table view. */
	private LRParseTableChooserPane tableView;
	
	private JPanel right;
}
