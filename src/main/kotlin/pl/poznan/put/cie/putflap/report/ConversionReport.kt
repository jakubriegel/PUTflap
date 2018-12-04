package pl.poznan.put.cie.putflap.report

import pl.poznan.put.cie.putflap.report.structure.StructureReport

class ConversionReport (
    val from: String,
    val to: String,
    val success: Boolean,
    val result: StructureReport? = null,
    val error: ErrorReport? = null
) : Report() {
    override fun toInfoText(): String {
        return "Conversion report:\n" +
                "from: $from\n" +
                "to: $to\n" +
                "success: $success\n" +
                (if (result != null) "result: $result\n" else "") +
                (if (error != null) "error: ${error.toInfoText()}\n" else "")
    }

    override fun toString(): String {
        return "ConversionReport(from='$from', to='$to', success=$success, result=$result, error=$error)"
    }
}