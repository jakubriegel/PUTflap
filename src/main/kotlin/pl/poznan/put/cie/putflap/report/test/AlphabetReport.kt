package pl.poznan.put.cie.putflap.report.test

import pl.poznan.put.cie.putflap.report.Report

data class AlphabetReport (
    val inAlphabet: Array<String>,
    val outAlphabet: Array<String>? = null
) : Report() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlphabetReport

        if (!inAlphabet.contentEquals(other.inAlphabet)) return false
        if (outAlphabet != null) {
            if (other.outAlphabet == null) return false
            if (!outAlphabet.contentEquals(other.outAlphabet)) return false
        } else if (other.outAlphabet != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = inAlphabet.contentHashCode()
        result = 31 * result + (outAlphabet?.contentHashCode() ?: 0)
        return result
    }
}