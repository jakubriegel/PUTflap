package pl.poznan.put.cie.putflap.report

import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonType
import pl.poznan.put.cie.putflap.report.structure.automaton.StepReport
import java.util.*

class RunReport private constructor(
    val type: AutomatonType,
    val input: String,
    val succeed: Boolean,
    val accepted: Boolean,
    val steps: Array<StepReport>?,
    val output: String? = null,
    val unprocessed: String? = null,
    val error: ErrorReport?
) : Report() {
    companion object {
        fun generate(type: AutomatonType, input: String, accepted: Boolean, steps: Array<StepReport>): RunReport {
            return RunReport(
                type,
                input,
                true,
                accepted,
                steps,
                steps.last().currentOutput,
                steps.last().toProcess,
                null
            )
        }

        fun generateWithError(type: AutomatonType, input: String, error: ErrorReport, steps: Array<StepReport>? = null): RunReport {
            return RunReport(
                type,
                input,
                false,
                false,
                steps,
                error = error
            )
        }
    }

    override fun toString(): String {
        return "RunReport(type=$type, input='$input', succeed=$succeed, accepted=$accepted, steps=${Arrays.toString(
            steps
        )}, output=$output, unprocessed=$unprocessed, error=$error)"
    }

}