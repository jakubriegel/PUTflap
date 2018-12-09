package pl.poznan.put.cie.putflap.report.test

import pl.poznan.put.cie.putflap.report.Report

class EquivalenceReport(
    val equivalent: Boolean
) : Report() {
    override fun toString(): String {
        return "EquivalenceReport(equivalent=$equivalent)"
    }
}