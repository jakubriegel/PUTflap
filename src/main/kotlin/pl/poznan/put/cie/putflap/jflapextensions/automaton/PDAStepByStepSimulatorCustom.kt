package pl.poznan.put.cie.putflap.jflapextensions.automaton

import jflap.automata.AutomatonSimulator
import jflap.automata.Configuration
import jflap.automata.pda.CharacterStack
import jflap.automata.pda.PDAConfiguration
import jflap.automata.pda.PDATransition
import jflap.automata.pda.PushdownAutomaton
import java.util.*
import javax.swing.JOptionPane

/**
 * Custom implementation of JFLAP PDAStepByStepSimulator. Gets rid of GUI elements and optimizes code for Kotlin
 */
class PDAStepByStepSimulatorCustom (val automaton: PushdownAutomaton, val acceptance: Int) : AutomatonSimulator(automaton)  {

    companion object {
        /** The variable to represent accept by empty stack.  */
        const val EMPTY_STACK_ACCEPTANCE = 0

        /** The variable to represent accept by final state.  */
        const val FINAL_STATE_ACCEPTANCE = 1
    }

    /**
     * Returns a PDAConfiguration array that represents the initial
     * configuration of the PDA, before any read has been processed. It returns
     * an array of length one.
     *
     * @param input
     * the read string.
     */
    override fun getInitialConfigurations(input: String): Array<Configuration> {
        /* The stack should contain the bottom of stack marker.  */
        val stack = CharacterStack()
        stack.push("Z")
        val config = PDAConfiguration(
            myAutomaton.initialState, null,
            input, input, stack, acceptance
        )
        return arrayOf(config)
    }

    /**
     * Simulates one step for a particular configuration, adding all possible
     * configurations reachable in one step to set of possible configurations.
     *
     * @param config
     * the configuration to simulate the one step on
     */
    override fun stepConfiguration(config: Configuration): ArrayList<Configuration> {
        val list = ArrayList<Configuration>()
        val configuration = config as PDAConfiguration
        /** get all information from configuration.  */
        val unprocessedInput = configuration.unprocessedInput
        val totalInput = configuration.input
        val currentState = configuration.currentState
        val transitions = myAutomaton
            .getTransitionsFromState(currentState)
        for (k in transitions.indices) {
            val transition = transitions[k] as PDATransition
            /** get all information from transition.  */
            val inputToRead = transition.inputToRead
            val stringToPop = transition.stringToPop
            val tempStack = configuration.stack
            /** copy stack object so as to not alter original.  */
            val stack = CharacterStack(tempStack)
            val stackContents = stack.pop(stringToPop.length)
            if (unprocessedInput.startsWith(inputToRead) && stringToPop == stackContents) {
                var input = ""
                if (inputToRead.length < unprocessedInput.length) {
                    input = unprocessedInput.substring(inputToRead.length)
                }
                val toState = transition.toState
                stack.push(transition.stringToPush)
                val configurationToAdd = PDAConfiguration(
                    toState, configuration, totalInput, input, stack, acceptance
                )
                list.add(configurationToAdd)
            }
        }

        return list
    }

    /**
     * Returns true if the simulation of the read string on the automaton left
     * the machine in a final state. If the entire read string is processed and
     * the machine is in a final state, return true.
     *
     * @return true if the simulation of the read string on the automaton left
     * the machine in a final state.
     */
    override fun isAccepted(): Boolean {
        val it = myConfigurations.iterator()
        while (it.hasNext()) {
            val configuration = it.next() as PDAConfiguration
            if (acceptance == FINAL_STATE_ACCEPTANCE) {
                val currentState = configuration.currentState
                if (configuration.unprocessedInput === "" && myAutomaton.isFinalState(currentState)) {
                    return true
                }
            } else if (acceptance == EMPTY_STACK_ACCEPTANCE) {
                val stack = configuration.stack
                if (configuration.unprocessedInput === "" && stack.height() == 0) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * Runs the automaton on the read string.
     *
     * @param input
     * the read string to be run on the automaton
     * @return true if the automaton accepts the read
     */
    override fun simulateInput(input: String): Boolean {
        /** clear the configurations to begin new simulation.  */
        myConfigurations.clear()
        val initialConfigs = getInitialConfigurations(input)
        for (k in initialConfigs.indices) {
            val initialConfiguration = initialConfigs[k] as PDAConfiguration
            myConfigurations.add(initialConfiguration)
        }
        var count = 0
        while (!myConfigurations.isEmpty()) {
            if (isAccepted)
                return true
            val configurationsToAdd = ArrayList<Configuration>()
            val it = myConfigurations.iterator()
            while (it.hasNext()) {
                val configuration = it.next() as PDAConfiguration
                val configsToAdd = stepConfiguration(configuration)
                configurationsToAdd.addAll(configsToAdd)
                it.remove()
                count++
                if (count > 10000) {
                    val result =
                        JOptionPane.showConfirmDialog(null, "JFLAP has generated 10000 configurations. Continue?")
                    when (result) {
                        JOptionPane.CANCEL_OPTION -> {}
                        JOptionPane.NO_OPTION -> return false
                        else -> {}
                    }
                }
            }
            myConfigurations.addAll(configurationsToAdd)
        }
        return false
    }

}