package pl.poznan.put.cie.putflap.report.structure.grammar

import jflap.grammar.Grammar
import pl.poznan.put.cie.putflap.report.structure.StructureReport

data class GrammarReport internal constructor (
    val productions: Array<ProductionReport>
) : StructureReport() {

    constructor(grammar: Grammar) : this (
        Array(grammar.productions.size) { ProductionReport(grammar.productions[it]) }
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GrammarReport

        if (!productions.contentEquals(other.productions)) return false

        return true
    }

    override fun hashCode(): Int {
        return productions.contentHashCode()
    }

}