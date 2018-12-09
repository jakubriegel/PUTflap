package pl.poznan.put.cie.putflap.report.test

import pl.poznan.put.cie.putflap.report.Report

data class LambdaTransitionsReport(
    val hasLambdaTransitions: Boolean,
    val lambdaTransitions: Array<Int>? = null
) : Report() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LambdaTransitionsReport

        if (hasLambdaTransitions != other.hasLambdaTransitions) return false
        if (lambdaTransitions != null) {
            if (other.lambdaTransitions == null) return false
            if (!lambdaTransitions.contentEquals(other.lambdaTransitions)) return false
        } else if (other.lambdaTransitions != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hasLambdaTransitions.hashCode()
        result = 31 * result + (lambdaTransitions?.contentHashCode() ?: 0)
        return result
    }

}