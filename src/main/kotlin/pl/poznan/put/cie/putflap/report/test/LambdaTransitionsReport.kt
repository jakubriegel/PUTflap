package pl.poznan.put.cie.putflap.report.test

import pl.poznan.put.cie.putflap.report.Report
import java.util.*

class LambdaTransitionsReport(
    val hasLambdaTransitions: Boolean,
    val lambdaTransitions: Array<Int>? = null
) : Report() {

    companion object {
        fun generate(hasLambdaTransitions: Boolean): LambdaTransitionsReport {
            return LambdaTransitionsReport(
                hasLambdaTransitions
            )
        }
    }

    override fun toString(): String {
        return "LambdaTransitionsReport(hasLambdaTransitions=$hasLambdaTransitions, lambdaTransitions=${Arrays.toString(
            lambdaTransitions
        )})"
    }
}