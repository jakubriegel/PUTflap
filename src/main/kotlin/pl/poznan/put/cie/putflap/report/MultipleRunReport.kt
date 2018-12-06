package pl.poznan.put.cie.putflap.report

import pl.poznan.put.cie.putflap.report.structure.StructureReport
import java.util.*

class MultipleRunReport private constructor(
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

    override fun toString(): String {
        return "MultipleRunReport(structure=$structure, allSucceed=$allSucceed, allAccepted=$allAccepted, results=${Arrays.toString(
            results
        )}, error=$error)"
    }

}