package pl.poznan.put.cie.putflap.report.structure.grammar

import jflap.grammar.Production
import pl.poznan.put.cie.putflap.report.Report

data class ProductionReport internal constructor(
    val left: String,
    val right: String
): Report() {

    constructor (production: Production) : this(
        production.lhs,
        production.rhs
    )
}