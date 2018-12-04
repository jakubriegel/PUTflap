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

    override fun toInfoText(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}