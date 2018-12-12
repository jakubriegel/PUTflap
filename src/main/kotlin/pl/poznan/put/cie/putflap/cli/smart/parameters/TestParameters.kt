package pl.poznan.put.cie.putflap.cli.smart.parameters

import pl.poznan.put.cie.putflap.cli.Types
import pl.poznan.put.cie.putflap.report.structure.automaton.AutomatonReport

data class TestParameters (
    val type: Types.TestType,
    val inputs: Array<AutomatonReport>
) : SmartParameters() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TestParameters

        if (type != other.type) return false
        if (!inputs.contentEquals(other.inputs)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + inputs.contentHashCode()
        return result
    }
}