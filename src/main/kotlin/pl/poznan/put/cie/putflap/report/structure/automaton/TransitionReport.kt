package pl.poznan.put.cie.putflap.report.structure.automaton

import pl.poznan.put.cie.putflap.report.Report

class TransitionReport (
    val from: Int,
    val to: Int,
    val read: String
) : Report() {
    override fun toString(): String {
        return "TransitionReport(from=$from, to=$to, read='$read')"
    }
}