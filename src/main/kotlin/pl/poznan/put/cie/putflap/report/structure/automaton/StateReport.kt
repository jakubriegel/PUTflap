package pl.poznan.put.cie.putflap.report.structure.automaton

import pl.poznan.put.cie.putflap.report.Report

class StateReport (
    val id: Int,
    val name: String,
    val label: String,
    val x: Int,
    val y: Int
) : Report() {
    override fun toString(): String {
        return "StateReport(id=$id, name='$name', label='$label', x=$x, y=$y)"
    }
}