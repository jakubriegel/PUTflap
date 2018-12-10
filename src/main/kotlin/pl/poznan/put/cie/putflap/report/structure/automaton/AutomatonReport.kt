package pl.poznan.put.cie.putflap.report.structure.automaton

import jflap.automata.Automaton
import jflap.automata.fsa.FiniteStateAutomaton
import jflap.automata.mealy.MealyMachine
import jflap.automata.mealy.MealyTransition
import jflap.automata.mealy.MooreMachine
import jflap.automata.pda.PDATransition
import jflap.automata.pda.PushdownAutomaton
import pl.poznan.put.cie.putflap.exception.IncompatibleAutomatonException
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonType
import pl.poznan.put.cie.putflap.jflapextensions.automaton.StateType
import pl.poznan.put.cie.putflap.jflapextensions.automaton.labelValue
import pl.poznan.put.cie.putflap.report.structure.StructureReport

data class AutomatonReport internal constructor(
    val type: AutomatonType,
    val states: Array<StateReport>,
    val transitions: Array<TransitionReport>
    ) : StructureReport() {

    companion object {
        fun generate(automaton: Automaton): AutomatonReport = when (automaton) {
            is FiniteStateAutomaton -> AutomatonReport(automaton)
            is MooreMachine -> AutomatonReport(automaton)
            is MealyMachine -> AutomatonReport(automaton)
            is PushdownAutomaton -> AutomatonReport(automaton)
            else -> throw IncompatibleAutomatonException("Automaton report supports only FSA, Mealy, Moore and PDA")
        }
    }

    constructor(automaton: FiniteStateAutomaton) : this(
        AutomatonType.FA,
        {
            val list = automaton.states.toList()
            val states = Array(list.size) {
                StateReport(
                    list[it].id,
                    list[it].name,
                    list[it].label ?: "",
                    StateType.get(list[it], automaton),
                    list[it].point.x,
                    list[it].point.y
                )
            }
            states
        }.invoke(),
        {
            val list = automaton.transitions.toList()
            val transitions = Array(list.size) {
                TransitionReport(
                    list[it].fromState.id,
                    list[it].toState.id,
                    list[it].labelValue()
                )
            }
            transitions
        }.invoke()
        )

    constructor(automaton: MooreMachine) : this(
        AutomatonType.MOORE,
        {
            val list = automaton.states.toList()
            val states = Array(list.size) {
                StateReport(
                    list[it].id,
                    list[it].name,
                    list[it].label ?: "",
                    StateType.get(list[it], automaton),
                    list[it].point.x,
                    list[it].point.y,
                    value = automaton.getOutput(list[it])
                )
            }
            states
        }.invoke(),
        {
            val list = automaton.transitions.toList()
            val transitions = Array(list.size) {
                TransitionReport(
                    list[it].fromState.id,
                    list[it].toState.id,
                    list[it].labelValue()
                )
            }
            transitions
        }.invoke()
    )

    constructor(automaton: MealyMachine) : this(
        AutomatonType.MEALY,
        {
            val list = automaton.states.toList()
            val states = Array(list.size) {
                StateReport(
                    list[it].id,
                    list[it].name,
                    list[it].label ?: "",
                    StateType.get(list[it], automaton),
                    list[it].point.x,
                    list[it].point.y
                )
            }
            states
        }.invoke(),
        {
            val list = automaton.transitions.toList()
            val transitions = Array(list.size) {
                TransitionReport(
                    list[it].fromState.id,
                    list[it].toState.id,
                    list[it].labelValue(),
                    value = (list[it] as MealyTransition).output
                )
            }
            transitions
        }.invoke()
    )

    constructor(automaton: PushdownAutomaton) : this(
        AutomatonType.PDA,
        {
            val list = automaton.states.toList()
            val states = Array(list.size) {
                StateReport(
                    list[it].id,
                    list[it].name,
                    list[it].label ?: "",
                    StateType.get(list[it], automaton),
                    list[it].point.x,
                    list[it].point.y
                )
            }
            states
        }.invoke(),
        {
            val list = automaton.transitions.toList()
            val transitions = Array(list.size) {
                TransitionReport(
                    list[it].fromState.id,
                    list[it].toState.id,
                    list[it].labelValue(),
                    pop = (list[it] as PDATransition).stringToPop,
                    push = (list[it] as PDATransition).stringToPush

                )
            }
            transitions
        }.invoke()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AutomatonReport

        if (type != other.type) return false
        if (!states.contentEquals(other.states)) return false
        if (!transitions.contentEquals(other.transitions)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + states.contentHashCode()
        result = 31 * result + transitions.contentHashCode()
        return result
    }
}