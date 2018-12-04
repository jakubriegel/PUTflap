package pl.poznan.put.cie.putflap.report.structure.automaton

import pl.poznan.put.cie.putflap.report.Report

class TransitionReport (
    val from: Int,
    val to: Int,
    val read: String
) : Report() {
    override fun toInfoText(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}