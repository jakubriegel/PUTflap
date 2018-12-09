package pl.poznan.put.cie.putflap.report.test

import pl.poznan.put.cie.putflap.report.Report

data class MultipleAlphabetReport (
    val reports: Array<AlphabetReport>
) : Report() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultipleAlphabetReport

        if (!reports.contentEquals(other.reports)) return false

        return true
    }

    override fun hashCode(): Int {
        return reports.contentHashCode()
    }
}