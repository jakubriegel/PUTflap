package pl.poznan.put.cie.putflap.report.structure.grammar

import jflap.grammar.Grammar
import pl.poznan.put.cie.putflap.report.structure.StructureReport

class GrammarReport private constructor (
    id: Int,
    val productions: Array<ProductionReport>
) : StructureReport(id) {

    constructor(grammar: Grammar) : this (
        -1,
        Array(grammar.productions.size) { ProductionReport(grammar.productions[it]) }
    )
}