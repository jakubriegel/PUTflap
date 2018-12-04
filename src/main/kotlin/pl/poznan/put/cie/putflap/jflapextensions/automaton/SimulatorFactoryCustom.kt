package pl.poznan.put.cie.putflap.jflapextensions.automaton

import jflap.automata.Automaton
import jflap.automata.AutomatonSimulator
import jflap.automata.NondeterminismDetectorFactory
import jflap.automata.fsa.FSAStepWithClosureSimulator
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.automata.mealy.MealyMachine
import jflap.automata.mealy.MealyStepByStateSimulator
import jflap.automata.mealy.MooreMachine
import jflap.automata.mealy.MooreStepByStateSimulator
import jflap.automata.pda.PushdownAutomaton
import jflap.automata.turing.TuringMachine

object SimulatorFactoryCustom {
    /**
     * Returns the automaton simulator for this type of automaton.
     *
     * @param automaton
     * the automaton to get the simulator for
     * @return the appropriate automaton simulator for this automaton, or <CODE>null</CODE>
     * if there is no automaton simulator known for this type of
     * automaton
     */
    fun getSimulator(automaton: Automaton): AutomatonSimulator? {
        when (automaton) {
            is FiniteStateAutomaton -> return FSAStepWithClosureSimulator(automaton)
            is PushdownAutomaton -> return PDAStepByStepSimulatorCustom(
                automaton,
                PDAStepByStepSimulatorCustom.FINAL_STATE_ACCEPTANCE
            )
            is TuringMachine -> {
                val d = NondeterminismDetectorFactory.getDetector(automaton)
                val nd = d!!.getNondeterministicStates(automaton)
                return if (nd.isNotEmpty()) {
                    jflap.automata.turing.NDTMSimulator(automaton)
                } else {
                    jflap.automata.turing.TMSimulator(automaton)
                }
            }
            is MooreMachine -> return MooreStepByStateSimulator(automaton)
            is MealyMachine -> return MealyStepByStateSimulator(automaton)

            else -> return null
        }
    }
}