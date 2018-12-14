package pl.poznan.put.cie.putflap.jflapextensions.automaton

import jflap.automata.Automaton
import jflap.automata.State
import jflap.automata.fsa.FSATransition
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.automata.mealy.MealyMachine
import jflap.automata.mealy.MealyTransition
import jflap.automata.mealy.MooreMachine
import jflap.automata.mealy.MooreTransition
import jflap.automata.pda.PDATransition
import jflap.automata.pda.PushdownAutomaton
import pl.poznan.put.cie.putflap.report.structure.automaton.AutomatonReport
import java.awt.Point

/**
 * Implements creation of automatons from [reports][AutomatonReport]
 */
object AutomatonCreator {

    fun fromReport(automatonReport: AutomatonReport): Automaton = when (automatonReport.getAutomatonClass()) {
        FiniteStateAutomaton::class -> fromReportFSA(automatonReport)
        MooreMachine::class -> fromReportMoore(automatonReport)
        MealyMachine::class -> fromReportMealy(automatonReport)
        PushdownAutomaton::class -> fromReportPDA(automatonReport)
        else -> TODO()
    }

    private fun fromReportFSA(automatonReport: AutomatonReport): FiniteStateAutomaton {
        val automaton = FiniteStateAutomaton()

        automatonReport.states.forEach {
            val state = State(it.id, Point(it.x, it.y), automaton)
            state.label = it.label
            state.name = it.name
            automaton.addState(state)
            if (it.type == StateType.INITIAL) automaton.initialState = state
            else if (it.type == StateType.FINAL) automaton.addFinalState(state)
        }

        automatonReport.transitions.forEach {
            automaton.addTransition(
                FSATransition(
                    automaton.getStateWithID(it.from),
                    automaton.getStateWithID(it.to),
                    it.read
                )
            )
        }

        return automaton
    }

    private fun fromReportMealy(automatonReport: AutomatonReport): MealyMachine {
        val automaton = MealyMachine()

        automatonReport.states.forEach {
            val state = State(it.id, Point(it.x, it.y), automaton)
            state.label = it.label
            state.name = it.name
            automaton.addState(state)
            if (it.type == StateType.INITIAL) automaton.initialState = state
            else if (it.type == StateType.FINAL) automaton.addFinalState(state)
        }

        automatonReport.transitions.forEach {
            automaton.addTransition(
                MealyTransition(
                    automaton.getStateWithID(it.from),
                    automaton.getStateWithID(it.to),
                    it.read,
                    it.value
                )
            )
        }

        return automaton
    }

    private fun fromReportMoore(automatonReport: AutomatonReport): MooreMachine {
        val automaton = MooreMachine()

        automatonReport.states.forEach {
            val state = State(it.id, Point(it.x, it.y), automaton)
            state.label = it.label
            state.name = it.name
            automaton.addState(state)
            if (it.type == StateType.INITIAL) automaton.initialState = state
            else if (it.type == StateType.FINAL) automaton.addFinalState(state)
            automaton.setOutput(state, it.value)
        }

        automatonReport.transitions.forEach {
            automaton.addTransition(
                MooreTransition(
                    automaton.getStateWithID(it.from),
                    automaton.getStateWithID(it.to),
                    it.read
                )
            )
        }

        return automaton
    }

    private fun fromReportPDA(automatonReport: AutomatonReport): PushdownAutomaton {
        val automaton = PushdownAutomaton()

        automatonReport.states.forEach {
            val state = State(it.id, Point(it.x, it.y), automaton)
            state.label = it.label
            state.name = it.name
            automaton.addState(state)
            if (it.type == StateType.INITIAL) automaton.initialState = state
            else if (it.type == StateType.FINAL) automaton.addFinalState(state)
        }

        automatonReport.transitions.forEach {
            automaton.addTransition(
                PDATransition(
                    automaton.getStateWithID(it.from),
                    automaton.getStateWithID(it.to),
                    it.read,
                    it.pop,
                    it.push
                )
            )
        }

        return automaton
    }
}