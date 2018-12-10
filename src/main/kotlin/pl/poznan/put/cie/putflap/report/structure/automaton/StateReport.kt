package pl.poznan.put.cie.putflap.report.structure.automaton

import pl.poznan.put.cie.putflap.report.Report

data class StateReport (
    val id: Int,
    val name: String,
    val label: String,
    val x: Int,
    val y: Int,
    val value: String? = null
) : Report()