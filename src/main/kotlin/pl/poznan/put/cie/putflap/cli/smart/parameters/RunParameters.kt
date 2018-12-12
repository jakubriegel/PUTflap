package pl.poznan.put.cie.putflap.cli.smart.parameters

import pl.poznan.put.cie.putflap.report.structure.automaton.AutomatonReport

data class RunParameters (
    val input: Array<AutomatonReport>,
    val words: Array<String>
) : SmartParameters() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RunParameters

        if (!input.contentEquals(other.input)) return false
        if (!words.contentEquals(other.words)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = input.contentHashCode()
        result = 31 * result + words.contentHashCode()
        return result
    }
}