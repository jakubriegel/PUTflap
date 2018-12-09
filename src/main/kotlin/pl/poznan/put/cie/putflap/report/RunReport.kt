package pl.poznan.put.cie.putflap.report

import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonType
import pl.poznan.put.cie.putflap.report.structure.automaton.StepReport

data class RunReport internal constructor(
    val type: AutomatonType,
    val input: String,
    val succeed: Boolean,
    val accepted: Boolean,
    val steps: Array<StepReport>?,
    val output: String? = null,
    val unprocessed: String? = null,
    val error: ErrorReport?
) : Report() {
    constructor(type: AutomatonType, input: String, accepted: Boolean, steps: Array<StepReport>): this(
            type,
            input,
            true,
            accepted,
            steps,
            steps.last().currentOutput,
            steps.last().toProcess,
            null
        )

    constructor(type: AutomatonType, input: String, error: ErrorReport, steps: Array<StepReport>? = null): this(
            type,
            input,
            false,
            false,
            steps,
            error = error
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RunReport

        if (type != other.type) return false
        if (input != other.input) return false
        if (succeed != other.succeed) return false
        if (accepted != other.accepted) return false
        if (steps != null) {
            if (other.steps == null) return false
            if (!steps.contentEquals(other.steps)) return false
        } else if (other.steps != null) return false
        if (output != other.output) return false
        if (unprocessed != other.unprocessed) return false
        if (error != other.error) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + input.hashCode()
        result = 31 * result + succeed.hashCode()
        result = 31 * result + accepted.hashCode()
        result = 31 * result + (steps?.contentHashCode() ?: 0)
        result = 31 * result + (output?.hashCode() ?: 0)
        result = 31 * result + (unprocessed?.hashCode() ?: 0)
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }

}