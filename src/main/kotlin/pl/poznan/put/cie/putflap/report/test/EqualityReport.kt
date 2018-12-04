package pl.poznan.put.cie.putflap.report.test

import pl.poznan.put.cie.putflap.report.Report

class EqualityReport(
    val equal: Boolean
) : Report() {
    override fun toInfoText(): String {
        return if (equal) "equal" else "not equal"
    }
}