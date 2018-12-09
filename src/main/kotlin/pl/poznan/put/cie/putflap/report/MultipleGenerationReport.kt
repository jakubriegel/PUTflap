package pl.poznan.put.cie.putflap.report

data class MultipleGenerationReport constructor(
    val type: String,
    val n: Int,
    val finals: Int,
    val givenAlphabet: Array<String>,
    val productions: Array<GenerationReport>
) : Report() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultipleGenerationReport

        if (type != other.type) return false
        if (n != other.n) return false
        if (finals != other.finals) return false
        if (!givenAlphabet.contentEquals(other.givenAlphabet)) return false
        if (!productions.contentEquals(other.productions)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + n
        result = 31 * result + finals
        result = 31 * result + givenAlphabet.contentHashCode()
        result = 31 * result + productions.contentHashCode()
        return result
    }

}