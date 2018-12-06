package pl.poznan.put.cie.putflap.report.structure.grammar

import jflap.grammar.Production
import pl.poznan.put.cie.putflap.report.Report

class ProductionReport private constructor(
    val left: String,
    val right: String
): Report() {

    constructor (production: Production) : this(
        production.lhs,
        production.rhs
    )

    override fun toString(): String {
        return "ProductionReport(left='$left', right='$right')"
    }
}