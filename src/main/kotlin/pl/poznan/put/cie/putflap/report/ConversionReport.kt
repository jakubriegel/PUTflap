package pl.poznan.put.cie.putflap.report

import pl.poznan.put.cie.putflap.report.structure.StructureReport

data class ConversionReport (
    val from: String,
    val to: String,
    val success: Boolean,
    val result: StructureReport? = null,
    val error: ErrorReport? = null
) : Report()