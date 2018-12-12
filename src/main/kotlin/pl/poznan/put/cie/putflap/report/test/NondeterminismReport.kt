package pl.poznan.put.cie.putflap.report.test

import jflap.automata.State
import pl.poznan.put.cie.putflap.report.Report

data class NondeterminismReport internal constructor(
    val deterministic: Boolean,
    val nonDeterministicStates: Array<Int>?,
    val lambdaTransitions: LambdaTransitionsReport?
) : Report() {

    constructor(deterministic: Boolean, nonDeterministicStates: Array<State>, lambdaTransitionsReport: LambdaTransitionsReport) : this(
        deterministic,
        if (nonDeterministicStates.isEmpty()) null else Array(nonDeterministicStates.size) { nonDeterministicStates[it].id },
        lambdaTransitionsReport
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NondeterminismReport

        if (deterministic != other.deterministic) return false
        if (nonDeterministicStates != null) {
            if (other.nonDeterministicStates == null) return false
            if (!nonDeterministicStates.contentEquals(other.nonDeterministicStates)) return false
        } else if (other.nonDeterministicStates != null) return false
        if (lambdaTransitions != other.lambdaTransitions) return false

        return true
    }

    override fun hashCode(): Int {
        var result = deterministic.hashCode()
        result = 31 * result + (nonDeterministicStates?.contentHashCode() ?: 0)
        result = 31 * result + (lambdaTransitions?.hashCode() ?: 0)
        return result
    }


}