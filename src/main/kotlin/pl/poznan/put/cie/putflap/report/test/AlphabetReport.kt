package pl.poznan.put.cie.putflap.report.test

import pl.poznan.put.cie.putflap.report.Report
import java.util.*

class AlphabetReport (
    val inAlphabet: Array<String>,
    val outAlphabet: Array<String>? = null
) : Report() {
    override fun toString(): String {
        return "AlphabetReport(inAlphabet=${Arrays.toString(inAlphabet)}, outAlphabet=${Arrays.toString(outAlphabet)})"
    }
}