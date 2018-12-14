package pl.poznan.put.cie.putflap.jflapextensions.grammar

import jflap.grammar.Grammar
import jflap.grammar.Production
import jflap.grammar.reg.RegularGrammar
import pl.poznan.put.cie.putflap.report.structure.grammar.GrammarReport

/**
 * Implements creation of grammars from [reports][GrammarReport]
 */
object GrammarCreator {
    fun fromReport(grammarReport: GrammarReport): Grammar {
        val grammar = RegularGrammar()
        val productions = Array(grammarReport.productions.size) {
            Production(
                grammarReport.productions[it].left,
                grammarReport.productions[it].right
            )
        }
        grammar.addProductions(productions)

        return grammar
    }
}