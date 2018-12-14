package pl.poznan.put.cie.putflap.cli.smart.parameters

import pl.poznan.put.cie.putflap.report.structure.automaton.AutomatonReport

data class WordParameters (
    val multiple: Int,
    val inputs: Array<AutomatonReport>
) : SmartParameters() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WordParameters

        if (multiple != other.multiple) return false
        if (!inputs.contentEquals(other.inputs)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = multiple
        result = 31 * result + inputs.contentHashCode()
        return result
    }
}