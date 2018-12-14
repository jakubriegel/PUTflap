package pl.poznan.put.cie.putflap.cli.smart.parameters

import pl.poznan.put.cie.putflap.cli.Types
import pl.poznan.put.cie.putflap.report.structure.automaton.AutomatonReport

data class ConvertParameters (
    val type: Types.ConvertType,
    val json: Boolean,
    val input: Array<AutomatonReport>
) : SmartParameters() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConvertParameters

        if (type != other.type) return false
        if (json != other.json) return false
        if (!input.contentEquals(other.input)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + json.hashCode()
        result = 31 * result + input.contentHashCode()
        return result
    }
}