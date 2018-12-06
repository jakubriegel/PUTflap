package pl.poznan.put.cie.putflap.report

import java.util.*

class MultipleGenerationReport constructor(
    val type: String,
    val n: Int,
    val finals: Int,
    val givenAlphabet: Array<String>,
    val productions: Array<GenerationReport>
) : Report() {

    override fun toString(): String {
        return "MultipleGenerationReport(type='$type', n=$n, finals=$finals, givenAlphabet=${Arrays.toString(
            givenAlphabet
        )}, productions=${Arrays.toString(productions)})"
    }
}