package pl.poznan.put.cie.putflap.report.structure

import pl.poznan.put.cie.putflap.report.Report

abstract class StructureReport (
    val id: Int
) : Report() {
    override fun toInfoText(): String {
        return Report.getJSON(this)
    }
}