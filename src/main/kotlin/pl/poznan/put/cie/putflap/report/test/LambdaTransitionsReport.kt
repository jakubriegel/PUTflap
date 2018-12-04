package pl.poznan.put.cie.putflap.report.test

import pl.poznan.put.cie.putflap.report.Report

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

    override fun toInfoText(): String {
        return if (hasLambdaTransitions) "has lambda transitions" else "no lambda transitions"
    }
}