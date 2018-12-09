package pl.poznan.put.cie.putflap.report.structure.automaton

import jflap.automata.Automaton
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonType
import pl.poznan.put.cie.putflap.jflapextensions.automaton.labelValue
import pl.poznan.put.cie.putflap.report.structure.StructureReport

data class AutomatonReport internal constructor(
    val type: String,
    val states: Array<StateReport>,
    val transitions: Array<TransitionReport>
    ) : StructureReport() {

    constructor(automaton: Automaton) : this(
        AutomatonType.get(automaton).toString(),
        {
            val list = automaton.states.toList()
            val states = Array(list.size) {
                StateReport(
                    list[it].id,
                    list[it].name,
                    list[it].label ?: "",
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