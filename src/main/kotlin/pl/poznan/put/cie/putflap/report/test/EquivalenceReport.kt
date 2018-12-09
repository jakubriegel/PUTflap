package pl.poznan.put.cie.putflap.report.test

import pl.poznan.put.cie.putflap.report.Report

data class EquivalenceReport(
    val equivalent: Boolean
) : Report()