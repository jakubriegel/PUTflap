package pl.poznan.put.cie.putflap.report.test

import jflap.automata.State
import pl.poznan.put.cie.putflap.report.Report
import java.util.*

class NondeterminismReport private constructor(
    val deterministic: Boolean,
    val nonDeterministicStates: Array<Int>?,
    val lambdaTransitions: LambdaTransitionsReport?
) : Report() {

    constructor(deterministic: Boolean, nonDeterministicStates: Array<State>, lambdaTransitionsReport: LambdaTransitionsReport) : this(
        deterministic,
        Array(nonDeterministicStates.size) { nonDeterministicStates[it].id },
        lambdaTransitionsReport
    )

    override fun toString(): String {
        return "NondeterminismReport(deterministic=$deterministic, nonDeterministicStates=${Arrays.toString(
            nonDeterministicStates
        )}, lambdaTransitions=$lambdaTransitions)"
    }


}