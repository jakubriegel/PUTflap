package pl.poznan.put.cie.putflap.report

data class MultipleConversionReport (
    val to: String,
    val allSucceed: Boolean,
    val conversions: Array<ConversionReport>
) : Report() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultipleConversionReport

        if (to != other.to) return false
        if (allSucceed != other.allSucceed) return false
        if (!conversions.contentEquals(other.conversions)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = to.hashCode()
        result = 31 * result + allSucceed.hashCode()
        result = 31 * result + conversions.contentHashCode()
        return result
    }
}