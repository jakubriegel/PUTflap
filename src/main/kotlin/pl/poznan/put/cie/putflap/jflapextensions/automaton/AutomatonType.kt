package pl.poznan.put.cie.putflap.jflapextensions.automaton

import jflap.automata.Automaton
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.automata.mealy.MealyMachine
import jflap.automata.mealy.MooreMachine
import jflap.automata.pda.PushdownAutomaton

enum class AutomatonType {
    FA, MOORE, MEALY, PDA, TURING, TURING_MT, TURING_BB, UNKNOWN;

    companion object {
        fun get(automaton: Automaton): AutomatonType {
            return when (automaton) {
                is FiniteStateAutomaton -> FA
                is MooreMachine -> MOORE
                is MealyMachine -> MEALY
                is PushdownAutomaton -> PDA
                else -> UNKNOWN
            }
        }
    }
}