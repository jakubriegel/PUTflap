package pl.poznan.put.cie.putflap.report

data class MultipleWordsReport internal constructor(
    val requestedNumber: Int,
    val averageGeneratedNumber: Int,
    val reports: Array<WordsReport>
) : Report() {

    constructor(reports: Array<WordsReport>) : this(
        reports[0].requestedNumber,
        reports.sumBy { it.generatedNumber } / reports.size,
        reports
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultipleWordsReport

        if (!reports.contentEquals(other.reports)) return false

        return true
    }

    override fun hashCode(): Int {
        return reports.contentHashCode()
    }
}