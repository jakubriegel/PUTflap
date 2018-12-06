package pl.poznan.put.cie.putflap.report.test

import pl.poznan.put.cie.putflap.report.Report

class EqualityReport(
    val equal: Boolean
) : Report() {
    override fun toString(): String {
        return "EqualityReport(equal=$equal)"
    }
}