package pl.poznan.put.cie.putflap.report.structure.grammar

import jflap.grammar.Grammar
import pl.poznan.put.cie.putflap.report.structure.StructureReport
import java.util.*

class GrammarReport private constructor (
    val productions: Array<ProductionReport>
) : StructureReport() {

    constructor(grammar: Grammar) : this (
        Array(grammar.productions.size) { ProductionReport(grammar.productions[it]) }
    )

    override fun toString(): String {
        return "GrammarReport(productions=${Arrays.toString(productions)})"
    }
}