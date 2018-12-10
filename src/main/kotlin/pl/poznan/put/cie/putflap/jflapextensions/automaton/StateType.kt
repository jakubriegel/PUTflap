package pl.poznan.put.cie.putflap.jflapextensions.automaton

import jflap.automata.Automaton
import jflap.automata.State

enum class StateType {
    INITIAL, FINAL, STATE;
    companion object {
        fun get(state: State, automaton: Automaton): StateType = when {
            automaton.isInitialState(state) -> INITIAL
            automaton.isFinalState(state) -> FINAL
            else -> STATE
        }
    }
}