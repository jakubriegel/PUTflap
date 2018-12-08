package pl.poznan.put.cie.putflap.report

import java.util.*

class WordsReport (
    val requestedNumber: Int,
    val generatedNumber: Int,
    val words: Array<String>
) : Report() {
    override fun toString(): String {
        return "WordsReport(requestedNumber=$requestedNumber, generatedNumber=$generatedNumber, words=${Arrays.toString(
            words
        )})"
    }
}