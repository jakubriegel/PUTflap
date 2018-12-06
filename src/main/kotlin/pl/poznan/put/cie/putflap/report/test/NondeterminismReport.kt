package pl.poznan.put.cie.putflap.report.test

import jflap.automata.State
import pl.poznan.put.cie.putflap.report.Report
import java.util.*

class NondeterminismReport private constructor(
    val deterministic: Boolean,
    val nonDeterministicStates: Array<Int>?
) : Report() {

    companion object {
        fun generate(deterministic: Boolean, nonDeterministicStates: Array<State>): NondeterminismReport {
            val ids = Array(nonDeterministicStates.size) { nonDeterministicStates[it].id }

            return NondeterminismReport(
                deterministic,
                ids
            )
        }
    }

    override fun toString(): String {
        return "NondeterminismReport(deterministic=$deterministic, nonDeterministicStates=${Arrays.toString(
            nonDeterministicStates
        )})"
    }
}