package pl.poznan.put.cie.putflap.report

import pl.poznan.put.cie.putflap.report.structure.StructureReport

class ConversionReport (
    val from: String,
    val to: String,
    val success: Boolean,
    val result: StructureReport? = null,
    val error: ErrorReport? = null
) : Report() {
    override fun toString(): String {
        return "ConversionReport(from='$from', to='$to', success=$success, result=$result, error=$error)"
    }
}