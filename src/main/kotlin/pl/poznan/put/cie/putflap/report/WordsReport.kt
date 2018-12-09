package pl.poznan.put.cie.putflap.report

data class WordsReport (
    val requestedNumber: Int,
    val generatedNumber: Int,
    val words: Array<String>
) : Report() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WordsReport

        if (requestedNumber != other.requestedNumber) return false
        if (generatedNumber != other.generatedNumber) return false
        if (!words.contentEquals(other.words)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = requestedNumber
        result = 31 * result + generatedNumber
        result = 31 * result + words.contentHashCode()
        return result
    }
}