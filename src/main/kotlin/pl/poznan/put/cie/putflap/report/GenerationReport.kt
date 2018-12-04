package pl.poznan.put.cie.putflap.report

import jflap.automata.Automaton
import jflap.grammar.Grammar
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonTester
import pl.poznan.put.cie.putflap.jflapextensions.automaton.AutomatonType
import pl.poznan.put.cie.putflap.report.structure.automaton.AutomatonReport
import pl.poznan.put.cie.putflap.report.structure.grammar.GrammarReport
import pl.poznan.put.cie.putflap.report.test.LambdaTransitionsReport
import pl.poznan.put.cie.putflap.report.test.NondeterminismReport

class GenerationReport private constructor(
    val id:  Int,
    val type: String,
    val alphabet: AlphabetReport,
    val nondeterminism: NondeterminismReport?,
    val lambdaTransitions: LambdaTransitionsReport?,
    val production: Report
) : Report() {

    constructor(id: Int, automaton: Automaton) : this(
        id,
        AutomatonType.get(automaton).toString(),
        AutomatonTester.getAlphabet(automaton),
        AutomatonTester.checkNondeterminism(automaton),
        AutomatonTester.checkLambdaTransitions(automaton),
        AutomatonReport(automaton)
    )

    constructor(id: Int, grammar: Grammar) : this(
        id,
        "grammar",
        AutomatonTester.getAlphabet(grammar),
        null,
        null,
        GrammarReport(grammar)
    )

    override fun toInfoText(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}