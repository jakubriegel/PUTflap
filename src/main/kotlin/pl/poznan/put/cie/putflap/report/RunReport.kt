package pl.poznan.put.cie.putflap.report

import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonType
import pl.poznan.put.cie.putflap.report.structure.automaton.StepReport
import java.util.*

class RunReport private constructor(
    val id: Int,
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
                -1,
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
                -1,
                type,
                input,
                false,
                false,
                steps,
                error = error
            )
        }
    }

    override fun toInfoText(): String {
        return "Run report:\n" +
                "id: $id, type: $type, succeed: $succeed,\n" +
                "read: $input\n" +
                "accepted: $accepted\n" +
                (if (output != null) "output: $output\n" else "") +
                (if (unprocessed != null) "unprocessed: $unprocessed\n" else "") +
                "path:\n" + (if (steps != null) {
                    var path = ""
                    steps.forEach { path += "\t${it.toInfoText()}\n" }
                    path
                } else "") +
                (if (error != null) "error: ${error.toInfoText()}\n" else "")
    }

    override fun toString(): String {
        return "RunReport(id=$id, type=$type, input='$input', succeed=$succeed, accepted=$accepted, steps=${Arrays.toString(
            steps
        )}, output=$output, unprocessed=$unprocessed, error=$error)"
    }


}