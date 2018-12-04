package pl.poznan.put.cie.putflap.report.structure.automaton

import jflap.automata.Automaton
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonType
import pl.poznan.put.cie.putflap.jflapextensions.automaton.labelValue
import pl.poznan.put.cie.putflap.report.StateReport
import pl.poznan.put.cie.putflap.report.structure.StructureReport

class AutomatonReport private constructor(
    id: Int,
    val type: String,
    val states: Array<StateReport>,
    val transitions: Array<TransitionReport>
    ) : StructureReport(id) {

    constructor(automaton: Automaton) : this(
        -1,
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
}