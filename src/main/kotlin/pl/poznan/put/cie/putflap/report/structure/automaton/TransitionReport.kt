package pl.poznan.put.cie.putflap.report.structure.automaton

import pl.poznan.put.cie.putflap.report.Report

data class TransitionReport (
    val from: Int,
    val to: Int,
    val read: String,
    val value: String? = null,
    val pop: String? = null,
    val push: String? = null
) : Report()