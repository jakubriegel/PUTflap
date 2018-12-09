package pl.poznan.put.cie.putflap.report

import pl.poznan.put.cie.putflap.report.structure.StructureReport

data class MultipleRunReport internal constructor(
    val structure: StructureReport,
    val allSucceed: Boolean,
    val allAccepted: Boolean,
    val results: Array<RunReport>,
    val error: ErrorReport? = null
) : Report() {
    constructor(structure: StructureReport, results: Array<RunReport>) : this(
        structure,
        results.all { it.succeed },
        results.all { it.accepted },
        results
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultipleRunReport

        if (structure != other.structure) return false
        if (allSucceed != other.allSucceed) return false
        if (allAccepted != other.allAccepted) return false
        if (!results.contentEquals(other.results)) return false
        if (error != other.error) return false

        return true
    }

    override fun hashCode(): Int {
        var result = structure.hashCode()
        result = 31 * result + allSucceed.hashCode()
        result = 31 * result + allAccepted.hashCode()
        result = 31 * result + results.contentHashCode()
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }
}